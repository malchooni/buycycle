package name.buycycle.vendor.ebest.exception;

public class RequestTimeOutException extends Exception{

    public RequestTimeOutException(long timeOut) {
        super("요청 경과시간이 초과 되었습니다. timeOut = " + timeOut);
    }
}
