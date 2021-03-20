package name.buycycle.vendor.ebest.config.vo;

public class Connect {
    private String szServerIP;
    private int nServerPort;
    private int connectTimeOut;
    private int requestReadTimeOut;

    public String getSzServerIP() {
        return szServerIP;
    }

    public void setSzServerIP(String szServerIP) {
        this.szServerIP = szServerIP;
    }

    public int getnServerPort() {
        return nServerPort;
    }

    public void setnServerPort(int nServerPort) {
        this.nServerPort = nServerPort;
    }

    public int getConnectTimeOut() {
        return connectTimeOut;
    }

    public void setConnectTimeOut(int connectTimeOut) {
        this.connectTimeOut = connectTimeOut;
    }

    public int getRequestReadTimeOut() {
        return requestReadTimeOut;
    }

    public void setRequestReadTimeOut(int requestReadTimeOut) {
        this.requestReadTimeOut = requestReadTimeOut;
    }
}
