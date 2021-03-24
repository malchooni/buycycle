package name.buycycle.vendor.ebest.event.vo.res;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * json 응답
 * @author : ijyoon
 * @date : 2021/03/24
 */
public class Response {
    private Map<String,String> header;
    private Map<String, List<Map<String,String>>> body;

    public Response(String uuid) {
        this.header = new LinkedHashMap<>();
        this.body = new LinkedHashMap<>();
        putHeader("uuid", uuid);
    }

    public Map<String, String> getHeader() {
        return header;
    }

    public String getHeader(String key) {
        return header.get(key);
    }

    public Map<String, List<Map<String,String>>> getBody() {
        return body;
    }

    public void putHeader(String key, String value){
        this.header.put(key, value);
    }

    public void putBody(String blockName, Map<String,String> row){
        List<Map<String, String>> rows = this.body.computeIfAbsent(blockName, k -> new LinkedList<>());
        rows.add(row);
    }
}

