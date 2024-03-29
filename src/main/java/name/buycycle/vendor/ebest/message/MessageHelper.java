package name.buycycle.vendor.ebest.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * RES file to data map, util.
 */
public class MessageHelper {

  private Logger logger = LoggerFactory.getLogger(MessageHelper.class);
  private static MessageHelper messageHelper;

  private String resRootPath;
  private ResDataRepository resDataRepository;

  private MessageHelper() {
    this.resDataRepository = new ResDataRepository();
  }

  public static MessageHelper getInstance() {
    if (messageHelper == null) {
      messageHelper = new MessageHelper();
    }
    return messageHelper;
  }

  public void setResRootPath(String resRootPath) {
    this.resRootPath = resRootPath;
  }

  /**
   * 초기화
   *
   * @throws Exception
   */
  public void initialize() throws Exception {

    if (this.resRootPath == null) {
      throw new MessageException("res root path is null.");
    }

    File resRoot = new File(this.resRootPath);
    if (!resRoot.isDirectory()) {
      if (logger.isErrorEnabled()) {
        logger.error("this path is not directory. [{}]", this.resRootPath);
      }
      return;
    }

    File[] files = resRoot.listFiles(file -> file.isFile() &&
        !Pattern.compile("^([\\S]+(_[1-2]\\.(?i)(res))$)").matcher(file.getName()).matches());

    if (files.length < 1) {
      return;
    }

    ResFileReader resFileReader = new ResFileReader();
    int successCount = 0;
    for (File resFile : files) {
      try {
        ResFileData resFileData = resFileReader.load(resFile);
        this.resDataRepository.putResFileData(resFileData.getName(), resFileData);
        successCount++;

        if (logger.isDebugEnabled()) {
          logger.debug("[{}][{}][{}] file loaded..", resFileData.getName(),
              resFileData.getResType(), resFileData.getDescription());
        }
      } catch (Exception e) {
        if (logger.isErrorEnabled()) {
          logger.error(e.getMessage(), e);
        }
      }
    }
    if (logger.isInfoEnabled()) {
      logger.info("{} res file loaded..", successCount);
      logger.info("MessageHelper initialization completed..");
    }
  }

  public ResFileData getResFileData(String trName) {
    return this.resDataRepository.getResFileData(trName);
  }

  /**
   * res 레파지토리 반환
   *
   * @return
   */
  public ResDataRepository getResDataRepository() {
    return this.resDataRepository;
  }

  /**
   * res file data 반환
   *
   * @param type   REAL or QUERY
   * @param trName
   * @return
   */
  public ResFileData getResFileData(String type, String trName) {
    Map<String, ResFileData> resMap = this.resDataRepository.getResMap(type);
    return resMap.get(trName);
  }
}
