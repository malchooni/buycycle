package name.buycycle.vendor.ebest.manage;

import name.buycycle.configuration.ebest.vo.EBestConfig;

public interface Manager {

  void setEBestConfig(EBestConfig eBestConfig);

  void initialize();
}
