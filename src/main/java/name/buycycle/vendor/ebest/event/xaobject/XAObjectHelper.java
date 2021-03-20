package name.buycycle.vendor.ebest.event.xaobject;

import name.buycycle.vendor.ebest.event.com4j.ClassFactory;
import name.buycycle.vendor.ebest.event.com4j.IXAQuery;
import name.buycycle.vendor.ebest.event.com4j.IXAReal;
import name.buycycle.vendor.ebest.event.xaobject.vo.XAObject;
import name.buycycle.vendor.ebest.message.ResFileData;

import java.util.List;
import java.util.Map;

public class XAObjectHelper {

    public static <T> XAObject createXAObject(String resRootPath, ResFileData resFileData, Class<T> eventInterface, T receiver) throws Exception {

        String filePath = resRootPath + "\\" + resFileData.getName() + ".res";
        XAObject xaObject = new XAObject();

        switch (resFileData.getResType()){
            case ResFileData.REAL:
                IXAReal ixaReal = ClassFactory.createXAReal();
                ixaReal.loadFromResFile(filePath);

                xaObject.setEventCookie(ixaReal.advise(eventInterface, receiver));
                xaObject.setIxaType(ixaReal);

                break;
            case ResFileData.QUERY:
                IXAQuery ixaQuery  = ClassFactory.createXAQuery();
                ixaQuery.loadFromResFile(filePath);

                xaObject.setEventCookie(ixaQuery.advise(eventInterface, receiver));
                xaObject.setIxaType(ixaQuery);
                break;
            default:
                throw new Exception("invalid res type.");
        }

        return xaObject;
    }

    public static void readyForRequest(IXAQuery ixaQuery, ResFileData resFileData, Map<String, String> query){
        String szBlockName = resFileData.getRequestBlockName();
        List<String> requestColumnList = resFileData.getRequestColumnList();

        String value;
        for(String requestColumn : requestColumnList){
            value = query.get(requestColumn);
            if(value != null)
                ixaQuery.setFieldData(szBlockName, requestColumn, 0, value);
        }
    }

    public static void readyForResponse(IXAQuery ixaQuery, ResFileData resFileData){

    }

    public static void readyForRequest(IXAReal ixaReal, ResFileData resFileData, Map<String, String> query){

        String szBlockName = resFileData.getRequestBlockName();
        List<String> requestColumnList = resFileData.getRequestColumnList();

        for(String requestColumn : requestColumnList){
            ixaReal.setFieldData(szBlockName, requestColumn, query.get(requestColumn));
        }
    }
}
