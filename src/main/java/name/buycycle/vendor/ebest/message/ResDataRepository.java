package name.buycycle.vendor.ebest.message;

import java.util.Map;
import java.util.TreeMap;

/**
 * res 파일 정보 레파지토리
 * @author : ijyoon
 * @date : 2021/03/24
 */
public class ResDataRepository {

    private Map<String, ResFileData> resReal;
    private Map<String, ResFileData> resQuery;

    public ResDataRepository() {
        this.resReal = new TreeMap<>();
        this.resQuery = new TreeMap<>();
    }

    /**
     * res 파일 정보 반환
     * @param resName res 파일명
     * @return res 정보 객체
     */
    public ResFileData getResFileData(String resName) {
        if(this.resQuery.containsKey(resName)){
            return this.resQuery.get(resName);
        }else{
            return this.resReal.get(resName);
        }
    }

    /**
     * res file 정보 등록
     * @param resName res 파일명
     * @param resFIleData res 정보 객체
     * @throws Exception
     */
    public void putResFileData(String resName, ResFileData resFIleData) throws Exception {
        switch (resFIleData.getResType()){
            case ResFileData.QUERY:
                this.resQuery.put(resName, resFIleData);
                break;
            case ResFileData.REAL:
                this.resReal.put(resName, resFIleData);
                break;
            default:
                throw new Exception("invalid res type");
        }
    }

    /**
     * res map 반환
     * @param type real or query
     * @return res map
     */
    public Map<String, ResFileData> getResMap(String type){
        switch (type){
            case ResFileData.QUERY:
                return this.resQuery;
            case ResFileData.REAL:
                return this.resReal;
            default:
                return null;
        }
    }
}
