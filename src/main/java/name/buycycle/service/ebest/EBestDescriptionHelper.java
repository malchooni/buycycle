package name.buycycle.service.ebest;

import name.buycycle.controller.vo.TableHeader;
import name.buycycle.controller.vo.ResTable;
import name.buycycle.service.ebest.vo.ResDesc;
import name.buycycle.service.ebest.vo.TableHeaderItem;
import name.buycycle.vendor.ebest.event.vo.req.Request;
import name.buycycle.vendor.ebest.event.vo.req.RequestBody;
import name.buycycle.vendor.ebest.event.vo.req.RequestHeader;
import name.buycycle.vendor.ebest.message.MessageHelper;
import name.buycycle.vendor.ebest.message.ResDataRepository;
import name.buycycle.vendor.ebest.message.ResFileData;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * res 파일에 대한 정보
 */
@Component
public class EBestDescriptionHelper {

    private ResDataRepository resDataRepository = MessageHelper.getInstance().getResDataRepository();

    /**
     * res  목록
     * @param resDataType
     * @return
     */
    public ResTable resList(String resDataType) {
        ResTable resTable = new ResTable();

        TableHeader interfaceHeader = new TableHeader("interface", "name");
        interfaceHeader.setAlign("center");
        resTable.setHeaders(interfaceHeader);
        resTable.setHeaders(new TableHeader("description", "desc"));

        Map<String, ResFileData> resFileDataMap = resDataRepository.getResMap(resDataType);
        Set<String> keySet = resFileDataMap.keySet();

        ResFileData resFileData;
        TableHeaderItem tableHeaderItem;
        for (String key : keySet) {
            resFileData = resFileDataMap.get(key);
            tableHeaderItem = new TableHeaderItem();
            tableHeaderItem.setName(resFileData.getName());
            tableHeaderItem.setDesc(resFileData.getDescription());
            resTable.setItems(tableHeaderItem);
        }
        return resTable;
    }

    /**
     * description trName
     * @param trName
     * @return
     */
    public ResDesc resDesc(String trName) {
        ResFileData resFileData = resDataRepository.getResFileData(trName);
        return new ResDesc(resFileData.getDataMap());
    }

    /**
     *  테스트용 메시지 생성
     * @param trName
     * @return
     */
    public Request requestMessage(String trName){
        Request request = new Request();
        RequestHeader header = new RequestHeader();
        request.setHeader(header);
        Map<String, String> bodyMap = new LinkedHashMap<>();

        ResFileData resFileData = resDataRepository.getResFileData(trName);
        if( resFileData == null ){
            bodyMap.put("message", trName + " res file not found");
        }else{
            RequestBody body = new RequestBody();
            request.setBody(body);
            body.setTrName(trName);
            resFileData.getRequestColumnList().forEach( value -> bodyMap.put(value, ""));
            body.setQuery(bodyMap);
        }
        return request;
    }
}
