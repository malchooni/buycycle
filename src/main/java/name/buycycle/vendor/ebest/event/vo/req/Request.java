package name.buycycle.vendor.ebest.event.vo.req;

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

    @Override
    public boolean equals(Object obj) {
        Request target = (Request) obj;
        String targetTrName = target.getBody().getTrName();
        String targetQuery = target.getBody().getQuery().get(0);

        if(this.body.getTrName().equals(targetTrName) && this.body.getQuery().get(0).equals(targetQuery))
            return true;
        else
            return false;
    }
}
