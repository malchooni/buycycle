package name.buycycle.vendor.ebest.manage;

import name.buycycle.vendor.ebest.event.XARealResponseEvent;
import name.buycycle.vendor.ebest.event.XARealSubscribe;
import name.buycycle.vendor.ebest.event.vo.req.Request;
import name.buycycle.vendor.ebest.manage.command.XARealSubscribeCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class XARealSubscribeManager extends AbstractManager<XARealSubscribeCommand> {

    private static final Logger logger = LoggerFactory.getLogger(XARealSubscribeManager.class);
    private static final XARealSubscribeManager instance = new XARealSubscribeManager();
    public static XARealSubscribeManager getInstance(){
        return instance;
    }

    public static final String REQUEST = "REQUEST";
    public static final String SHUTDOWN = "SHUTDOWN";

    private List<XARealSubscribe> threadList;

    private XARealSubscribeManager() {
        super("XARealSubscribeManager", logger);
        this.threadList = new ArrayList<>();
    }

    /**
     * 실시간 tr 요청
     * @param request
     */
    public void realTrRequest(XARealResponseEvent event, Request request){
        requestCommand(new XARealSubscribeCommand(REQUEST)
                        .setXaRealResponseEvent(event)
                        .setRequest(request)
        );
    }

    public void shutdown(){
        requestCommand(new XARealSubscribeCommand(SHUTDOWN));
    }

    /**
     * 스레드 초기화
     */
    @Override
    public void initialize() {}

    @Override
    void request(XARealSubscribeCommand command){
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
        command.setEBestConfig(this.eBestConfig);
        XARealSubscribe helper = new XARealSubscribe(command);
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
