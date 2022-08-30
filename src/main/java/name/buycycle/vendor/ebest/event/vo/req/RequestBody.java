package name.buycycle.vendor.ebest.event.vo.req;

import java.util.Map;

/**
 * 요청 바디
 *
 * @author : ijyoon
 * @date : 2021/03/24
 */
public class RequestBody {

  private String trName;
  private boolean bNext = false;
  private Map<String, String> query;

  public String getTrName() {
    return trName;
  }

  public void setTrName(String trName) {
    this.trName = trName;
  }

  public boolean isbNext() {
    return bNext;
  }

  public void setbNext(boolean bNext) {
    this.bNext = bNext;
  }

  public Map<String, String> getQuery() {
    return query;
  }

  public void setQuery(Map<String, String> query) {
    this.query = query;
  }
}
