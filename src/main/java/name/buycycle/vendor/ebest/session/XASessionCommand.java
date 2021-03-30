package name.buycycle.vendor.ebest.session;

public class XASessionCommand {
    private Object command;
    private boolean empty = true;
    private long timeOut;

    public XASessionCommand(long timeOut) {
        this.timeOut = timeOut;
    }

    public synchronized <T> T take(){
        while(this.empty){
            try {
                wait(this.timeOut);
                if(this.empty){
                    this.command = XASessionManager.CHECK;
                    this.empty = false;
                }
            } catch (InterruptedException e) {}
        }
        this.empty = true;
        notifyAll();
        return (T) command;
    }

    public synchronized void put(Object command){
        while(!this.empty){
            try {
                wait();
            } catch (InterruptedException e) {}
        }
        this.empty = false;
        this.command = command;
        notifyAll();
    }
}
