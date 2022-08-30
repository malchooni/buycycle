package name.buycycle.vendor.ebest.manage;

import name.buycycle.configuration.ebest.vo.EBestConfig;
import name.buycycle.vendor.ebest.manage.command.BlockingCommand;
import org.slf4j.Logger;

abstract class AbstractManager<T> extends Thread implements Manager {

  private Logger logger;
  private boolean running = true;

  private final BlockingCommand request;
  private final BlockingCommand response;

  protected EBestConfig eBestConfig;

  protected AbstractManager(String threadName, Logger logger) {
    super(threadName);
    this.logger = logger;
    this.request = new BlockingCommand();
    this.response = new BlockingCommand();
  }

  protected AbstractManager setRequestTimeOut(long timeout) {
    this.request.setTimeOut(timeout);
    return this;
  }

  protected AbstractManager setRequestTimeOutCommand(String command) {
    this.request.setTimeOutCommand(command);
    return this;
  }

  protected AbstractManager setResponseTimeOut(long timeout) {
    this.response.setTimeOut(timeout);
    return this;
  }

  protected AbstractManager setResponseTimeOutCommand(String command) {
    this.response.setTimeOutCommand(command);
    return this;
  }

  protected void requestCommand(T command) {
    this.request.put(command);
  }

  protected <V> V responseTake() {
    return this.response.take();
  }

  protected void responseCommand(Object command) {
    this.response.put(command);
  }

  protected void setRunning(boolean running) {
    this.running = running;
  }

  /**
   * 설정 주입
   *
   * @param eBestConfig EBest 환경 설정
   */
  public void setEBestConfig(EBestConfig eBestConfig) {
    this.eBestConfig = eBestConfig;
  }

  @Override
  public void run() {
    if (logger.isInfoEnabled()) {
      logger.info("{} started..", this.getName());
    }

    initialize();
    while (running) {
      try {
        T command = this.request.take();
        this.request(command);
      } catch (Exception e) {
        if (logger.isErrorEnabled()) {
          logger.error(e.getMessage(), e);
        }
      }
    }

    if (logger.isInfoEnabled()) {
      logger.info("{} shutdown done..", this.getName());
    }
  }

  abstract void request(T command);
}
