package name.buycycle.vendor.ebest.manage;

import name.buycycle.vendor.ebest.event.vo.res.Response;
import name.buycycle.vendor.ebest.exception.ConnectFailException;
import name.buycycle.vendor.ebest.exception.RequestTimeOutException;
import name.buycycle.vendor.ebest.session.XASession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * xa session 연결 관리자
 */
public class XASessionManager extends AbstractManager<String> {

    private static final Logger logger = LoggerFactory.getLogger(XASessionManager.class);
    private static final XASessionManager instance = new XASessionManager();
    public static XASessionManager getInstance(){
        return instance;
    }

    public static final String LOGIN = "LOGIN";
    public static final String CLOSE = "CLOSE";
    public static final String SHUTDOWN = "SHUTDOWN";
    public static final String TOUCH = "TOUCH";
    public static final String CHECK = "CHECK";

    private boolean succeedLogin = false;
    private long touchTime;
    private XASession xaSession;

    private XASessionManager() {
        super("XASessionManager", logger);
        this.setRequestTimeOut(10000).setRequestTimeOutCommand(CHECK);
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
        requestCommand(LOGIN);
        return responseTake();
    }

    /**
     * 세션 종료 요청
     */
    public void close(){
        if( this.succeedLogin ){
            requestCommand(CLOSE);
        }
    }

    /**
     * 스레드 종료 요청
     */
    public void shutdown(){
        requestCommand(SHUTDOWN);
        try {
            this.join();
        } catch (InterruptedException ignored) {}
    }

    /**
     * idle time 갱신
     */
    public void touch(){
        requestCommand(TOUCH);
    }

    /**
     * 스레드 초기화
     */
    @Override
    public void initialize() {
        if(eBestConfig == null) throw new NullPointerException("EBestConfig is null.");
        this.xaSession = new XASession(this.eBestConfig);
        this.touchRequest();
    }

    @Override
    void request(String command){
        switch (command){
            case LOGIN:
                this.loginRequest(this.xaSession);
                break;
            case CLOSE:
                this.closeRequest(this.xaSession);
                break;
            case SHUTDOWN:
                this.shutdownRequest(this.xaSession);
                break;
            case TOUCH:
                this.touchRequest();
                break;
            case CHECK:
                this.checkRequest(this.xaSession);
                break;
            default:
                if(logger.isErrorEnabled())
                    logger.error("unsupported operation", new UnsupportedOperationException(command));
        }
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
                if(logger.isErrorEnabled())
                    logger.error(e.getMessage(), e);
                response = errorResponse(e);
            }

            if(response != null && response.getHeader("szCode").equals("0000")) {
                this.succeedLogin = true;
            }
            responseCommand(response);
        }else{
            responseCommand(null);
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
        if(isSucceedLogin()){
            this.closeRequest(xaSession);
        }
        setRunning(false);
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
                if(logger.isInfoEnabled())
                    logger.info("xasession idle time out. connection closed.");
            }
        }
    }
}
