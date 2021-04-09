package name.buycycle.vendor.ebest.manage;

import name.buycycle.vendor.ebest.config.vo.EBestConfig;
import name.buycycle.vendor.ebest.event.vo.req.Request;
import name.buycycle.vendor.ebest.invoke.XARealSubscribeHelper;
import name.buycycle.vendor.ebest.manage.command.XARealSubscribeCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.WebSocketSession;

import java.util.ArrayList;
import java.util.List;

public class XARealSubscribeManager extends AbstractManager<XARealSubscribeCommand>{

    private static final Logger logger = LoggerFactory.getLogger(XARealSubscribeManager.class);
    private static final XARealSubscribeManager instance = new XARealSubscribeManager();
    public static XARealSubscribeManager getInstance(){
        return instance;
    }

    public static final String REQUEST = "REQUEST";
    public static final String SHUTDOWN = "SHUTDOWN";

    private List<XARealSubscribeHelper> threadList;

    private XARealSubscribeManager() {
        super("XARealSubscribeManager", logger);
        this.threadList = new ArrayList<>();
    }

    @Override
    void init() {}

    /**
     * 실시간 tr 요청
     * @param request
     */
    public void realTrRequest(WebSocketSession session, EBestConfig eBestConfig, Request request){
        requestCommand(new XARealSubscribeCommand(REQUEST)
                        .setSession(session)
                        .setEBestConfig(eBestConfig)
                        .setRequest(request)
        );
    }

    public void shutdown(){
        requestCommand(new XARealSubscribeCommand(SHUTDOWN));
    }

    @Override
    void request(XARealSubscribeCommand command) throws Exception{
        switch (command.toString()){
            case REQUEST:
                this.realTrRequestProcess(command);
                break;
            case SHUTDOWN:
                this.shutdownProcess();
                break;
            default:
                if(logger.isErrorEnabled())
                    logger.error("unsupported operation", new UnsupportedOperationException(command.toString()));
        }
    }

    public void realTrRequestProcess(XARealSubscribeCommand command){
        XARealSubscribeHelper helper = new XARealSubscribeHelper(command);
        helper.start();
        threadList.add(helper);
    }

    public void shutdownProcess(){
        threadList.forEach(thread -> {
            thread.shutdown();
            try { thread.join(); } catch (InterruptedException e) {}
        });
        setRunning(false);
    }
}
