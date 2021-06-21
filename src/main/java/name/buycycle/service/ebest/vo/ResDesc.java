package name.buycycle.service.ebest.vo;

import java.util.List;
import java.util.Map;

/**
 * res 상세
 */
public class ResDesc {
    private Map<String, List<Map<String, String>>> resDesc;

    public ResDesc(Map<String, List<Map<String, String>>> resDesc) {
        this.resDesc = resDesc;
    }

    public Map<String, List<Map<String, String>>> getResDesc() {
        return resDesc;
    }

    public void setResDesc(Map<String, List<Map<String, String>>> resDesc) {
        this.resDesc = resDesc;
    }
}
