package name.buycycle.vendor.ebest.message;

import java.util.*;

/**
 * RES DESCRIPTION value object
 */
public class ResFileData {

    public static final int RES_TYPE_IDX = 0;
    public static final int RES_DESCRIPTION_IDX = 1;
    public static final String REAL = ".Feed";
    public static final String QUERY = ".Func";

    public static final int DESC_IDX = 0;
    public static final int COLUMN_IDX = 1;
    public static final int TYPE_IDX = 3;
    public static final int SIZE_IDX = 4;

    public static final String DESC = "DESC";
    public static final String COLUMN = "COLUMN";
    public static final String TYPE = "TYPE";
    public static final String SIZE = "SIZE";

    public static final String REQUEST = "InBlock";
    public static final String RESPONSE = "OutBlock";

    public Map<String, List<Map<String, String>>> dataMap;

    private String name;
    private String resType;
    private String description;

    public ResFileData(String name){
        this.name = name;
        this.dataMap = new LinkedHashMap<>();
    }

    public String getName() {
        return name;
    }

    public String getResType() {
        return resType;
    }

    public void setResType(String resType) {
        this.resType = resType;
    }

    public String getDescription() {
        return description;
    }

    public Map<String, List<Map<String, String>>> getDataMap() {
        return dataMap;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDataBlock(String dataBlockName, List<Map<String, String>> dataBlock){
        this.dataMap.put(dataBlockName, dataBlock);
    }

    public List<Map<String, String>> getDataBlock(String dataBlockName){
        return this.dataMap.get(dataBlockName);
    }

    public List<String> getRequestColumnList(){
        List<String> result = new LinkedList<>();
        List<Map<String, String>> inBlock = this.dataMap.get(getRequestBlockName());

        for(Map<String, String> row : inBlock){
            result.add(row.get(COLUMN));
        }
        return result;
    }

    public Map<String, List<String>> getResponseColumnMap(){
        Map<String, List<String>> result = new HashMap<>();
        String[] responseBlocks = this.getResponseBlockName();

        for(String blockName : responseBlocks){
            List<Map<String, String>> outBlock = this.dataMap.get(blockName);

            List<String> block = new LinkedList<>();
            for(Map<String, String> row : outBlock){
                block.add(row.get(COLUMN));
            }

            result.put(blockName, block);
        }

        return result;
    }

    /**
     * 요청 블럭 이름 반환
     * @return 요청 블럭 이름
     */
    public String getRequestBlockName(){
        List<String> reqBlockName = findKey(REQUEST);
        return reqBlockName.get(0);
    }

    /**
     * 응답 블럭 이름 반환
     * @return 응답 블럭 이름
     */
    public String[] getResponseBlockName(){
        List<String> resBlockName = findKey(RESPONSE);
        return resBlockName.toArray(new String[resBlockName.size()]);
    }

    private List<String> findKey(String word){
        List<String> result = new ArrayList<>();
        Set<String> keys = this.dataMap.keySet();
        for(String key : keys){
            if(key.toLowerCase().indexOf(word.toLowerCase()) > -1){
                result.add(key);
            }
        }
        return result;
    }
}


