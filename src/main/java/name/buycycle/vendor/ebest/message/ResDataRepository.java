package name.buycycle.vendor.ebest.message;

import java.util.Map;
import java.util.TreeMap;

public class ResDataRepository {

    private Map<String, ResFileData> resReal;
    private Map<String, ResFileData> resQuery;

    public ResDataRepository() {
        this.resReal = new TreeMap<>();
        this.resQuery = new TreeMap<>();
    }

    public ResFileData getResFileData(String resName) {
        if(this.resQuery.containsKey(resName)){
            return this.resQuery.get(resName);
        }else{
            return this.resReal.get(resName);
        }
    }

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
