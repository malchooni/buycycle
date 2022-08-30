package name.buycycle.service.ebest.vo;

import java.util.List;
import java.util.Map;

/**
 * res 상세
 */
public class ResDesc {

  private Map<String, List<Map<String, String>>> resDescMap;

  public ResDesc(Map<String, List<Map<String, String>>> resDescMap) {
    this.resDescMap = resDescMap;
  }

  public Map<String, List<Map<String, String>>> getResDescMap() {
    return resDescMap;
  }

  public void setResDescMap(Map<String, List<Map<String, String>>> resDescMap) {
    this.resDescMap = resDescMap;
  }
}
