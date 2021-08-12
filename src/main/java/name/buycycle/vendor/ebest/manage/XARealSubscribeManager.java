package name.buycycle.vendor.ebest.manage;

import name.buycycle.vendor.ebest.event.XARealResponseEvent;
import name.buycycle.vendor.ebest.event.XARealSubscribe;
import name.buycycle.vendor.ebest.event.vo.req.Request;
import name.buycycle.vendor.ebest.manage.command.XARealSubscribeCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class XARealSubscribeManager extends AbstractManager<XARealSubscribeCommand> {

    private static final Logger logger = LoggerFactory.getLogger(XARealSubscribeManager.class);
    private static final XARealSubscribeManager instance = new XARealSubscribeManager();
    public static XARealSubscribeManager getInstance(){
        return instance;
    }

    public static final String REQUEST = "REQUEST";
    public static final String STOP = "STOP";
    public static final String SHUTDOWN = "SHUTDOWN";

    private Map<Request, XARealSubscribe> threadMap;

    private XARealSubscribeManager() {
        super("XARealSubscribeManager", logger);
        this.threadMap = new HashMap<>();
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

    /**
     * 특정 스레드 중지 요청
     * @param request
     */
    public void realTrStop(Request request){
        requestCommand(new XARealSubscribeCommand(STOP)
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
            case STOP:
                this.realTrStopProcess(command);
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

        if(this.threadMap.containsKey(command.getRequest())){
            return;
        }

        command.setEBestConfig(this.eBestConfig);
        XARealSubscribe xaRealSubscribe = new XARealSubscribe(command);
        xaRealSubscribe.start();
        this.threadMap.put(command.getRequest(), xaRealSubscribe);
    }

    public void realTrStopProcess(XARealSubscribeCommand command){

        XARealSubscribe xaRealSubscribe = this.threadMap.remove(command.getRequest());

        if(xaRealSubscribe == null) return;

        xaRealSubscribe.shutdown();
        try { xaRealSubscribe.join(); } catch (InterruptedException e) {}
    }

    public void shutdownProcess(){

        threadMap.forEach((request, xaRealSubscribe) -> {
            xaRealSubscribe.shutdown();
            try { xaRealSubscribe.join(); } catch (InterruptedException e) {}
        });
        setRunning(false);
    }
}
