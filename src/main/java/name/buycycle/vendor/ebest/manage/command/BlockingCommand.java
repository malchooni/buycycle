package name.buycycle.vendor.ebest.manage.command;

public class BlockingCommand {
    private Object command;
    private boolean empty = true;
    private long timeOut = 0;
    private String timeOutCommand;

    public BlockingCommand() {
    }

    public BlockingCommand(long timeOut) {
        this.timeOut = timeOut;
    }

    public void setTimeOut(long timeOut) {
        this.timeOut = timeOut;
    }

    public BlockingCommand setTimeOutCommand(String timeOutCommand) {
        this.timeOutCommand = timeOutCommand;
        return this;
    }

    public synchronized <T> T take(){
        while(this.empty){
            try {
                wait(this.timeOut);
                if(this.empty){
                    if(this.timeOutCommand != null){
                        this.command = this.timeOutCommand;
                        this.empty = false;
                    }else{
                        continue;
                    }
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
