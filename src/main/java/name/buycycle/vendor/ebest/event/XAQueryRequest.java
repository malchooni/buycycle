package name.buycycle.vendor.ebest.event;

import com4j.COM4J;
import name.buycycle.configuration.ebest.vo.EBestConfig;
import name.buycycle.vendor.ebest.event.com4j.IXAQuery;
import name.buycycle.vendor.ebest.event.com4j._IXAQueryEvents;
import name.buycycle.vendor.ebest.event.handler.XAQueryEventHandler;
import name.buycycle.vendor.ebest.event.vo.req.Request;
import name.buycycle.vendor.ebest.event.vo.req.RequestBody;
import name.buycycle.vendor.ebest.event.vo.res.Response;
import name.buycycle.vendor.ebest.event.xaobject.XAObjectHelper;
import name.buycycle.vendor.ebest.event.xaobject.vo.XAObject;
import name.buycycle.vendor.ebest.message.MessageHelper;
import name.buycycle.vendor.ebest.message.ResFileData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * xa query 요청 / 응답
 *
 * @author : ijyoon
 * @date : 2021/03/24
 */
public class XAQueryRequest {

  private Logger logger = LoggerFactory.getLogger(getClass());
  private final MessageHelper messageHelper = MessageHelper.getInstance();

  private EBestConfig eBestConfig;

  public XAQueryRequest(EBestConfig eBestConfig) {
    this.eBestConfig = eBestConfig;
  }

  /**
   * xa query 요청, 응답
   *
   * @param request 요청 값
   * @return 응답
   */
  public Response requestQuery(Request request) {

    RequestBody requestBody = request.getBody();
    XAObject xaObject = null;
    Response response = null;

    try {
      ResFileData resFileData = this.messageHelper.getResFileData(ResFileData.QUERY,
          requestBody.getTrName());
      XAQueryEventHandler xaQueryEventHandler = new XAQueryEventHandler(
          request.getHeader().getUuid());
      xaObject = XAObjectHelper.createXAObject(this.eBestConfig.getResRootPath(), resFileData,
          _IXAQueryEvents.class, xaQueryEventHandler);
      IXAQuery ixaQuery = xaObject.getIxaType();

      XAObjectHelper.readyForRequest(ixaQuery, resFileData, requestBody.getQuery());
      ixaQuery.request(requestBody.isbNext());

      synchronized (xaQueryEventHandler) {
        xaQueryEventHandler.wait(eBestConfig.getConnect().getRequestReadTimeOut());
      }

      response = xaQueryEventHandler.getResponse();
      setResponseData(ixaQuery, resFileData.getResponseColumnMap(), response);
    } catch (InterruptedException e) {
      if (logger.isErrorEnabled()) {
        logger.error(e.getMessage(), e);
      }
      Thread.currentThread().interrupt();

    } catch (Exception e) {
      if (logger.isErrorEnabled()) {
        logger.error(e.getMessage(), e);
      }
      exceptionResponseMsg(request, response, e);
    } finally {
      if (xaObject != null && xaObject.getEventCookie() != null) {
        xaObject.getEventCookie().close();
        COM4J.cleanUp();
      }
    }

    return response;
  }

  /**
   * ixaQuery 응답값을 vo로 변환
   *
   * @param ixaQuery     ixaQuery 응답 값
   * @param resSchemaMap res file data
   * @param response     응답 vo
   */
  private void setResponseData(IXAQuery ixaQuery, Map<String, List<String>> resSchemaMap,
      Response response) {
    Set<String> blocks = resSchemaMap.keySet();

    int count;
    String originBlockName;
    for (String blockName : blocks) {
      if (blockName.endsWith("_repeat")) {
        originBlockName = blockName.replace("_repeat", "");
        count = ixaQuery.getBlockCount(originBlockName);
      } else {
        originBlockName = blockName;
        count = 1;
      }

      List<String> columns = resSchemaMap.get(blockName);
      Map<String, String> row;
      for (int i = 0; i < count; i++) {
        row = new HashMap<>();
        for (String columnName : columns) {
          row.put(columnName, ixaQuery.getFieldData(originBlockName, columnName, i));
        }
        response.putBody(originBlockName, row);
      }
    }
  }

  /**
   * 예외 발생 시 해당 메시지를 응답 값으로 반환
   *
   * @param request  요청 vo
   * @param response 응답 vo
   * @param e        예외
   */
  private void exceptionResponseMsg(Request request, Response response, Exception e) {
    if (response == null) {
      response = new Response(request.getHeader().getUuid());
    }
    response.putHeader("buyCycleErrMsg", e.getMessage());
  }
}
