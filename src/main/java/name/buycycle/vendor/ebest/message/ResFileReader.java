package name.buycycle.vendor.ebest.message;

import java.io.*;
import java.util.*;

public class ResFileReader {

    private static final String START_MARK = "BEGIN_DATA_MAP";
    private static final String END_MARK = "END_DATA_MAP";

    private static final String BEGIN = "begin";
    private static final String END = "end";

    private String resRootPath;

    public String getResRootPath() {
        return resRootPath;
    }

    public void setResRootPath(String resRootPath) {
        this.resRootPath = resRootPath;
    }

    public ResFileData load(String resFileName) throws Exception {
        if(this.resRootPath == null)
            throw new Exception("res root path is null.");

        return this.load(new File(this.resRootPath + "\\" + resFileName + ".res"));
    }

    /**
     * res 파일을 읽어와 data map 으로 변환한다.
     * @param resFile 대상 res 파일
     * @return ResFileData object
     * @throws Exception
     */
    public ResFileData load(File resFile) throws Exception {

        boolean findDataMapStart = false;
        BufferedReader br = null;
        ResFileData resFileData = new ResFileData(resFile.getName().split("\\.")[0]);

        try {
            br = new BufferedReader( new InputStreamReader( new FileInputStream(resFile), "MS949"));

            String readLine;
            int lineNum = 0;
            while( (readLine = br.readLine()) != null){
                lineNum++;
                if(readLine.endsWith(START_MARK)){
                    findDataMapStart = true;
                }else if(readLine.endsWith(END_MARK)){
                    break;
                }else {
                    String[] s = readLine
                            .replace("\t", "")
                            .replace(" ", "")
                            .split(",");
                    if(findDataMapStart){
                        String[] subjects = s;
                        if(subjects.length == 4 && subjects[3].startsWith("occurs"))
                            subjects[0] += "_repeat";

                        resFileData.setDataBlock(subjects[0], extractionData(br));
                    }else if(lineNum == 2){
                        String[] data = s;
                        resFileData.setResType(data[ResFileData.RES_TYPE_IDX]);
                        resFileData.setDescription(data[ResFileData.RES_DESCRIPTION_IDX]);
                    }
                }
            }
        } catch (UnsupportedEncodingException e) {
            throw e;
        } catch (FileNotFoundException e) {
            throw e;
        } catch (IOException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        } finally {
            if(br != null)
                try{br.close();}catch (IOException i){}
        }
        return resFileData;
    }

    /**
     * res file parser
     * @param br res file
     * @return res data to map
     * @throws Exception invalid end of data
     */
    private List<Map<String, String>> extractionData(BufferedReader br) throws Exception {

        boolean findBegin = false;
        List<Map<String, String>> dataList = new LinkedList<>();

        String readLine;
        while( (readLine = br.readLine()) != null){

            if(readLine.endsWith(BEGIN)){
                findBegin = true;
            }else if(readLine.endsWith(END)){
                return dataList;
            }else if(findBegin){
                String[] data = readLine.replace("\t", "")
                        .replace(" ", "")
                        .split(",");
                if(data.length < 5)
                    continue;

                Map<String, String> dataSet = new LinkedHashMap<>();
                dataSet.put(ResFileData.COLUMN, data[ResFileData.COLUMN_IDX]);
                dataSet.put(ResFileData.DESC, data[ResFileData.DESC_IDX]);
                dataSet.put(ResFileData.TYPE, data[ResFileData.TYPE_IDX]);
                dataSet.put(ResFileData.SIZE, data[ResFileData.SIZE_IDX].replace(";",""));
                dataList.add(dataSet);
            }
        }
        throw new Exception("not found END character : ["+ END+"]");
    }
}
