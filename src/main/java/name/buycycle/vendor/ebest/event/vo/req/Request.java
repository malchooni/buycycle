package name.buycycle.vendor.ebest.event.vo.req;

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
}
