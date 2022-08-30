package name.buycycle.vendor.ebest.manage;

import name.buycycle.configuration.ebest.vo.EBestConfig;
import name.buycycle.vendor.ebest.event.XAQueryRequest;
import name.buycycle.vendor.ebest.event.vo.req.Request;
import name.buycycle.vendor.ebest.event.vo.res.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XAQueryManager implements Manager {

  private static final XAQueryManager instance = new XAQueryManager();

  public static XAQueryManager getInstance() {
    return instance;
  }

  private EBestConfig eBestConfig;
  private XAQueryRequest xaQueryRequest;

  private XAQueryManager() {
  }

  @Override
  public void setEBestConfig(EBestConfig eBestConfig) {
    this.eBestConfig = eBestConfig;
  }

  @Override
  public void initialize() {
      if (eBestConfig == null) {
          throw new NullPointerException("EBestConfig is null.");
      }
    this.xaQueryRequest = new XAQueryRequest(this.eBestConfig);
  }

  public Response requestQuery(Request request) {
    return this.xaQueryRequest.requestQuery(request);
  }
}
