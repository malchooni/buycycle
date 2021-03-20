package name.buycycle.vendor.ebest.event.vo.req;

import java.util.UUID;

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
