package name.buycycle.vendor.ebest.session;

import name.buycycle.vendor.ebest.config.vo.EBestConfig;
import name.buycycle.vendor.ebest.event.vo.res.Response;
import name.buycycle.vendor.ebest.exception.ConnectFailException;
import name.buycycle.vendor.ebest.exception.RequestTimeOutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XASessionManager extends Thread{

    private final Logger logger = LoggerFactory.getLogger(XASession.class);
    private static final XASessionManager instance = new XASessionManager();

    public static final String LOGIN = "LOGIN";
    public static final String CLOSE = "CLOSE";
    public static final String SHUTDOWN = "SHUTDOWN";
    public static final String TOUCH = "TOUCH";
    public static final String CHECK = "CHECK";

    private final Object sessionMonitor;
    private final XASessionCommand request;
    private final XASessionCommand response;

    private EBestConfig eBestConfig;

    private boolean running = true;
    private boolean succeedLogin = false;

    private long touchTime;

    private XASessionManager() {
        super("XASessionManager");
        this.sessionMonitor = new Object();
        this.request = new XASessionCommand(10000);
        this.response = new XASessionCommand(0);
    }

    public static XASessionManager getInstance(){
        return instance;
    }

    /**
     * 설정 주입
     * @param eBestConfig EBest 환경 설정
     */
    public void setEBestConfig(EBestConfig eBestConfig) {
        this.eBestConfig = eBestConfig;
    }

    /**
     * 로그인 확인
     * @return 로그인 여부
     */
    public boolean isSucceedLogin() {
        return succeedLogin;
    }

    /**
     * 로그인 요청
     * @return 응답 값
     */
    public Response login(){
        synchronized (this.sessionMonitor){
            this.request.put(LOGIN);
            return this.response.take();
        }
    }

    /**
     * 세션 종료 요청
     */
    public void close(){
        synchronized (this.sessionMonitor){
            if( this.succeedLogin){
                this.request.put(CLOSE);
            }
        }
    }

    /**
     * 스레드 종료 요청
     */
    public void shutdown(){
        synchronized (this.sessionMonitor){
            this.request.put(SHUTDOWN);
            try {
                this.join();
            } catch (InterruptedException ignored) {}
        }
    }

    public void touch(){
        synchronized (this.sessionMonitor){
            this.request.put(TOUCH);
        }
    }

    @Override
    public void run() {
        logger.info("XASessionManager started..");
        XASession xaSession = new XASession(this.eBestConfig);
        this.touchRequest();

        while(running){
            try{
                String command = this.request.take();
                switch (command){
                    case LOGIN:
                        this.loginRequest(xaSession);
                        break;
                    case CLOSE:
                        this.closeRequest(xaSession);
                        break;
                    case SHUTDOWN:
                        this.shutdownRequest(xaSession);
                        break;
                    case TOUCH:
                        this.touchRequest();
                        break;
                    case CHECK:
                        this.checkRequest(xaSession);
                        break;
                    default:
                        logger.error("unsupported operation", new UnsupportedOperationException(command));
                }
            }catch (Exception e){
                logger.error(e.getMessage(), e);
            }
        }

        logger.info("XASessionManager shutdown done..");
    }

    /**
     * 로그인 요청
     */
    private void loginRequest(XASession xaSession) {

        if (!this.succeedLogin){
            Response response;
            try{
                response = xaSession.login();
            }catch (InterruptedException | RequestTimeOutException | ConnectFailException e){
                logger.error(e.getMessage(), e);
                response = errorResponse(e);
            }

            if(response != null && response.getHeader("szCode").equals("0000")) {
                this.succeedLogin = true;
            }
            this.response.put(response);
        }else{
            this.response.put(null);
        }

    }

    private Response errorResponse(Throwable e){
        Response response = new Response();
        response.putHeader("buyCycleErrMsg", e.getMessage());
        return response;
    }

    /**
     * 세션 종료 요청
     * @param xaSession XASession
     */
    private void closeRequest(XASession xaSession){
        xaSession.close();
        this.succeedLogin = false;
    }

    /**
     * 스레드 종료
     * @param xaSession XASession
     */
    private void shutdownRequest(XASession xaSession){
        this.closeRequest(xaSession);
        this.running = false;
    }

    /**
     * idle time 갱신
     */
    private void touchRequest() {
        this.touchTime = System.currentTimeMillis();
    }

    /**
     * idle time out session close
     */
    private void checkRequest(XASession xaSession) {
        long now = System.currentTimeMillis();
        if( (now - this.touchTime) > (5 * 60 * 1000) ){
            if(isSucceedLogin()) {
                this.closeRequest(xaSession);
                this.logger.info("xasession idle time out. connection closed.");
            }
        }
    }
}
