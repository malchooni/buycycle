package name.buycycle.vendor.ebest.session;

import com4j.COM4J;
import com4j.EventCookie;
import name.buycycle.vendor.ebest.config.vo.Connect;
import name.buycycle.vendor.ebest.config.vo.EBestConfig;
import name.buycycle.vendor.ebest.config.vo.User;
import name.buycycle.vendor.ebest.event.handler.XASessionEventHandler;
import name.buycycle.vendor.ebest.event.vo.res.Response;
import name.buycycle.vendor.ebest.exception.ConnectFailException;
import name.buycycle.vendor.ebest.exception.RequestTimeOutException;
import name.buycycle.vendor.ebest.session.com4j.ClassFactory;
import name.buycycle.vendor.ebest.session.com4j.IXASession;
import name.buycycle.vendor.ebest.session.com4j.XA_SERVER_TYPE;
import name.buycycle.vendor.ebest.session.com4j._IXASessionEvents;

/**
 * xing session 인터페이스
 * @author : ijyoon
 * date : 2021/03/24
 */
public class XASession extends Thread{

    private final EBestConfig eBestConfig;

    private IXASession ixaSession;
    private EventCookie eventCookie;

    public XASession(EBestConfig eBestConfig) {
        super("XASession");
        this.eBestConfig = eBestConfig;
    }

    /**
     * 연결 확인
     * @return 연결 여부
     */
    public boolean isConnected() {
        if(ixaSession == null) return false;
        return ixaSession.isConnected();
    }

    /**
     * 연결
     * @param connectInfo 대상 서버 정보
     * @return 연결 여부
     */
    private boolean connect(Connect connectInfo) {
        try{
            this.ixaSession.connectTimeOut(connectInfo.getConnectTimeOut());
            return this.ixaSession.connectServer(connectInfo.getSzServerIP(), connectInfo.getnServerPort());
        }catch (Exception e){
            return false;
        }
    }

    /**
     * 로그인 요청
     * @return 응답
     * @throws InterruptedException wait
     * @throws RequestTimeOutException 타임 아웃
     * @throws ConnectFailException 연결 실패
     */
    public synchronized Response login() throws InterruptedException, RequestTimeOutException, ConnectFailException {

        final XASessionEventHandler xaSessionEventHandler = new XASessionEventHandler();
        this.ixaSession = ClassFactory.createXASession();
        this.eventCookie = this.ixaSession.advise(_IXASessionEvents.class, xaSessionEventHandler);

        if (connect(eBestConfig.getConnect())) {
            User user = eBestConfig.getUser();
            this.ixaSession.login(user.getId(), user.getPw(), user.getCpwd(), XA_SERVER_TYPE.XA_REAL_SERVER.comEnumValue(), false);

            synchronized (xaSessionEventHandler) {
                long startTime = System.currentTimeMillis();
                xaSessionEventHandler.wait(eBestConfig.getConnect().getRequestReadTimeOut());
                long elapsedTime = System.currentTimeMillis() - startTime;

                if(elapsedTime >= eBestConfig.getConnect().getRequestReadTimeOut())
                    throw new RequestTimeOutException(eBestConfig.getConnect().getRequestReadTimeOut());
            }
            return xaSessionEventHandler.getResponse();
        } else {
            throw new ConnectFailException();
        }
    }

    /**
     * session close
     */
    public synchronized void close() {
        if (this.ixaSession.isConnected())
            this.ixaSession.disconnectServer();

        if (this.eventCookie != null)
            this.eventCookie.close();

        COM4J.cleanUp();
    }
}
