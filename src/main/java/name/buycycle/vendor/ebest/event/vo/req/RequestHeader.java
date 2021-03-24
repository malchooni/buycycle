package name.buycycle.vendor.ebest.event.vo.req;

import java.util.UUID;

/**
 * 요청 헤더
 * @author : ijyoon
 * @date : 2021/03/24
 */
public class RequestHeader {

    private String uuid;

    public String getUuid() {
        if(uuid == null || uuid.length() < 1){
            uuid = UUID.randomUUID().toString();
        }
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
