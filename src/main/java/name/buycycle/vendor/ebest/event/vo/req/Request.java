package name.buycycle.vendor.ebest.event.vo.req;

import com.google.common.base.Objects;

import java.util.Map;

/**
 * json 요청
 * @author : ijyoon
 * @date : 2021/03/24
 */
public class Request {
    private RequestHeader requestHeader;
    private RequestBody body;

    public RequestHeader getHeader() {
        return requestHeader;
    }

    public void setHeader(RequestHeader requestHeader) {
        this.requestHeader = requestHeader;
    }

    public RequestBody getBody() {
        return body;
    }

    public void setBody(RequestBody body) {
        this.body = body;
    }

    public String getFirstValue(){
        return this.body.getQuery().entrySet().iterator().next().getValue();
    }

    @Override
    public boolean equals(Object obj) {
        Request target = (Request) obj;
        String targetTrName = target.getBody().getTrName();

        Map.Entry<String,String> targetEntry = target.getBody().getQuery().entrySet().iterator().next();

        if(target.getBody().getQuery().size() != 1 || this.body.getQuery().size() != 1)
            return false;

        if(this.body.getTrName().equals(targetTrName) && getFirstValue().equals(targetEntry.getValue()))
            return true;
        else
            return false;
    }

    @Override
    public int hashCode(){
        return Objects.hashCode(this.body.getTrName(), getFirstValue());
    }
}
