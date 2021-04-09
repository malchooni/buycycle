package name.buycycle.vendor.ebest;

import name.buycycle.vendor.ebest.config.vo.EBestConfig;
import name.buycycle.vendor.ebest.manage.XARealSubscribeManager;
import name.buycycle.vendor.ebest.message.MessageHelper;
import name.buycycle.vendor.ebest.manage.XASessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * ebest 초기화
 * res file 읽기, 세션 종료
 */
@Component
public class EBestInitialization {

    private Logger logger = LoggerFactory.getLogger(EBestInitialization.class);

    @Autowired
    private EBestConfig eBestConfig;

    /**
     * res 파일을 메시지 맵으로 읽는다.
     * @throws Exception
     */
    @PostConstruct
    public void resFileRead() throws Exception {
        MessageHelper messageHelper = MessageHelper.getInstance();
        messageHelper.setResRootPath(eBestConfig.getResRootPath());
        messageHelper.initialize();
    }

    /**
     * 연결 관리자 초기화
     */
    @PostConstruct
    public void xaSessionManager(){
        XASessionManager xaSessionManager = XASessionManager.getInstance();
        xaSessionManager.setEBestConfig(eBestConfig);
        xaSessionManager.start();
    }

    @PostConstruct
    public void xaRealSubscribeManager(){
        XARealSubscribeManager xaRealSubscribeManager = XARealSubscribeManager.getInstance();
        xaRealSubscribeManager.start();
    }

    /**
     * xa session 종료.
     */
    @PreDestroy
    public void shutdown(){
        XARealSubscribeManager xaRealSubscribeManager = XARealSubscribeManager.getInstance();
        xaRealSubscribeManager.shutdown();
        try {
            xaRealSubscribeManager.join();
        } catch (InterruptedException e) {}

        XASessionManager xaSessionManager = XASessionManager.getInstance();
        xaSessionManager.shutdown();
        try {
            xaSessionManager.join();
        } catch (InterruptedException e) {}

        if(logger.isInfoEnabled()){
            logger.info("======================");
            logger.info("  Buycycle stopped... ");
            logger.info("======================");
        }
    }
}
