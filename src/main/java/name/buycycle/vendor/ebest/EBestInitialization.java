package name.buycycle.vendor.ebest;

import name.buycycle.vendor.ebest.config.vo.EBestConfig;
import name.buycycle.vendor.ebest.message.MessageHelper;
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
    EBestConfig eBestConfig;

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
     * xa session 종료.
     */
    @PreDestroy
    public void closeXASession(){
        logger.info("======================");
        logger.info("  Buycycle stopped... ");
        logger.info("======================");
    }
}
