package name.buycycle.vendor.ebest.session;

import com4j.EventCookie;
import name.buycycle.vendor.ebest.event.handler.XASessionEventHandler;
import name.buycycle.vendor.ebest.event.vo.res.Response;
import name.buycycle.vendor.ebest.exception.ConnectFailException;
import name.buycycle.vendor.ebest.exception.RequestTimeOutException;
import name.buycycle.vendor.ebest.session.com4j.ClassFactory;
import name.buycycle.vendor.ebest.session.com4j.IXASession;
import name.buycycle.vendor.ebest.session.com4j.XA_SERVER_TYPE;
import name.buycycle.vendor.ebest.session.com4j._IXASessionEvents;
import name.buycycle.vendor.ebest.config.vo.Connect;
import name.buycycle.vendor.ebest.config.vo.EBestConfig;
import name.buycycle.vendor.ebest.config.vo.User;

/**
 * ebest 세션
 */
public class XASession {

    private static XASession instance;

    private IXASession ixaSession;
    private EventCookie eventCookie;
    private XASessionEventHandler xaSessionEventHandler;

    public synchronized static XASession getInstance() {
        if(instance == null)
            instance = new XASession();
        return instance;
    }

    private XASession() {}

    public boolean isConnected() {
        if(ixaSession == null) return false;
        return ixaSession.isConnected();
    }

    public synchronized Response loginRequest(EBestConfig eBestConfig) throws InterruptedException, ConnectFailException, RequestTimeOutException {
        if (isConnected()) return null;
        return login(eBestConfig);
    }

    public synchronized void close() {
        if (this.ixaSession.isConnected())
            this.ixaSession.disconnectServer();

        if (this.eventCookie != null)
            this.eventCookie.close();

        if (instance != null)
            instance = null;

    }

    private boolean connect(Connect connectInfo) {
        this.ixaSession.connectTimeOut(connectInfo.getConnectTimeOut());
        return this.ixaSession.connectServer(connectInfo.getSzServerIP(), connectInfo.getnServerPort());
    }

    private Response login(EBestConfig eBestConfig) throws InterruptedException, RequestTimeOutException, ConnectFailException {

        this.ixaSession = ClassFactory.createXASession();
        this.xaSessionEventHandler = new XASessionEventHandler(null);
        this.eventCookie = this.ixaSession.advise(_IXASessionEvents.class, xaSessionEventHandler);

        if (connect(eBestConfig.getConnect())) {
            User user = eBestConfig.getUser();
            this.ixaSession.login(user.getId(), user.getPw(), user.getCpwd(), XA_SERVER_TYPE.XA_REAL_SERVER.comEnumValue(), false);

            synchronized (this.xaSessionEventHandler) {
                long startTime = System.currentTimeMillis();
                this.xaSessionEventHandler.wait(eBestConfig.getConnect().getRequestReadTimeOut());
                long elapsedTime = System.currentTimeMillis() - startTime;

                if(elapsedTime >= eBestConfig.getConnect().getRequestReadTimeOut())
                    throw new RequestTimeOutException(eBestConfig.getConnect().getRequestReadTimeOut());
            }
            return this.xaSessionEventHandler.getResponse();
        } else {
            throw new ConnectFailException();
        }
    }
}
