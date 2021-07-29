package name.buycycle.vendor.ebest.manage.command;

import name.buycycle.configuration.ebest.vo.EBestConfig;
import name.buycycle.vendor.ebest.event.XARealResponseEvent;
import name.buycycle.vendor.ebest.event.vo.req.Request;

/**
 * XARealSubscribeManager 제어를 위한 명령어 오브젝트
 */
public class XARealSubscribeCommand {

    private String command;
    private EBestConfig eBestConfig;
    private Request request;
    private XARealResponseEvent xaRealResponseEvent;

    public XARealSubscribeCommand(String command) {
        this.command = command;
    }

    public Request getRequest() {
        return request;
    }

    public XARealSubscribeCommand setRequest(Request request) {
        this.request = request;
        return this;
    }

    public EBestConfig geteBestConfig() {
        return eBestConfig;
    }

    public XARealSubscribeCommand seteBestConfig(EBestConfig eBestConfig) {
        this.eBestConfig = eBestConfig;
        return this;
    }

    public XARealResponseEvent getXaRealResponseEvent() {
        return xaRealResponseEvent;
    }

    public XARealSubscribeCommand setXaRealResponseEvent(XARealResponseEvent xaRealResponseEvent) {
        this.xaRealResponseEvent = xaRealResponseEvent;
        return this;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public EBestConfig getEBestConfig() {
        return eBestConfig;
    }

    public XARealSubscribeCommand setEBestConfig(EBestConfig eBestConfig) {
        this.eBestConfig = eBestConfig;
        return this;
    }

    @Override
    public String toString() {
        return this.command;
    }
}
