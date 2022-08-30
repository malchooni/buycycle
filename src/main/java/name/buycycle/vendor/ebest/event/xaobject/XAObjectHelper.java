package name.buycycle.vendor.ebest.event.xaobject;

import java.io.File;
import name.buycycle.vendor.ebest.event.com4j.ClassFactory;
import name.buycycle.vendor.ebest.event.com4j.IXAQuery;
import name.buycycle.vendor.ebest.event.com4j.IXAReal;
import name.buycycle.vendor.ebest.event.xaobject.vo.XAObject;
import name.buycycle.vendor.ebest.message.ResFileData;

import java.util.List;
import java.util.Map;

/**
 * xing api 인터페이스 객체 제어
 *
 * @author : ijyoon
 * @date : 2021/03/24
 */
public class XAObjectHelper {

  private XAObjectHelper() {
    throw new IllegalStateException("XAObjectHelper is Utility class");
  }

  /**
   * 인터페이스 객체 생성
   *
   * @param resRootPath    res 파일 루트 경로
   * @param resFileData    res 파일 객체
   * @param eventInterface 이벤트 핸들러 클래스
   * @param receiver       인터페이스 객체
   * @param <T>            IXAQuery or IXAReal
   * @return
   * @throws Exception
   */
  public static <T> XAObject createXAObject(String resRootPath, ResFileData resFileData,
      Class<T> eventInterface, T receiver) throws XAObjectException {

    String filePath = resRootPath + File.separator + resFileData.getName() + ".res";
    XAObject xaObject = new XAObject();

    switch (resFileData.getResType()) {
      case ResFileData.REAL:
        IXAReal ixaReal = ClassFactory.createXAReal();
        ixaReal.loadFromResFile(filePath);

        xaObject.setEventCookie(ixaReal.advise(eventInterface, receiver));
        xaObject.setIxaType(ixaReal);

        break;
      case ResFileData.QUERY:
        IXAQuery ixaQuery = ClassFactory.createXAQuery();
        ixaQuery.loadFromResFile(filePath);

        xaObject.setEventCookie(ixaQuery.advise(eventInterface, receiver));
        xaObject.setIxaType(ixaQuery);
        break;
      default:
        throw new XAObjectException("invalid res type.");
    }

    return xaObject;
  }

  /**
   * ixaQuery 객체로 맵에 있는 요청값 셋팅
   *
   * @param ixaQuery    인터페이스 객체
   * @param resFileData res 파일 객체
   * @param query       요청 값
   */
  public static void readyForRequest(IXAQuery ixaQuery, ResFileData resFileData,
      Map<String, String> query) {
    String szBlockName = resFileData.getRequestBlockName();
    List<String> requestColumnList = resFileData.getRequestColumnList();

    String value;
    for (String requestColumn : requestColumnList) {
      value = query.get(requestColumn);
      if (value != null) {
        ixaQuery.setFieldData(szBlockName, requestColumn, 0, value);
      }
    }
  }

  /**
   * ixaReal 객체로 맵에 있는 요청값 셋팅
   *
   * @param ixaReal     인터페이스 객체
   * @param resFileData res 파일 객체
   * @param query       요청 값
   */
  public static void readyForRequest(IXAReal ixaReal, ResFileData resFileData,
      Map<String, String> query) {

    String szBlockName = resFileData.getRequestBlockName();
    List<String> requestColumnList = resFileData.getRequestColumnList();

    for (String requestColumn : requestColumnList) {
      ixaReal.setFieldData(szBlockName, requestColumn, query.get(requestColumn));
    }
  }
}
