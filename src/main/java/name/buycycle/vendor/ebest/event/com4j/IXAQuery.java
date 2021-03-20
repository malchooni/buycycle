package name.buycycle.vendor.ebest.event.com4j;

import com4j.*;
import name.buycycle.vendor.ebest.event.IXAType;

/**
 * IXAQuery Interface
 */
@IID("{255B43AE-B290-4435-9BA7-37FCAAD04D77}")
public interface IXAQuery extends Com4jObject, IXAType {
  // Methods:
  /**
   * <p>
   * method GetFieldData
   * </p>
   * @param szBlockName Mandatory java.lang.String parameter.
   * @param szFieldName Mandatory java.lang.String parameter.
   * @param nRecordIndex Mandatory int parameter.
   * @return  Returns a value of type java.lang.String
   */

  @DISPID(2) //= 0x2. The runtime will prefer the VTID if present
  @VTID(7)
  String getFieldData(
    String szBlockName,
    String szFieldName,
    int nRecordIndex);


  /**
   * <p>
   * method Request
   * </p>
   * @param bNext Mandatory boolean parameter.
   * @return  Returns a value of type int
   */

  @DISPID(3) //= 0x3. The runtime will prefer the VTID if present
  @VTID(8)
  int request(
    boolean bNext);


  /**
   * <p>
   * property ResFileName
   * </p>
   * <p>
   * Getter method for the COM property "ResFileName"
   * </p>
   * @return  Returns a value of type java.lang.String
   */

  @DISPID(5) //= 0x5. The runtime will prefer the VTID if present
  @VTID(9)
  String resFileName();


  /**
   * <p>
   * property ResFileName
   * </p>
   * <p>
   * Setter method for the COM property "ResFileName"
   * </p>
   * @param pVal Mandatory java.lang.String parameter.
   */

  @DISPID(5) //= 0x5. The runtime will prefer the VTID if present
  @VTID(10)
  void resFileName(
    String pVal);


  /**
   * <p>
   * method LoadFromResFile
   * </p>
   * @param szFileName Mandatory java.lang.String parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(6) //= 0x6. The runtime will prefer the VTID if present
  @VTID(11)
  boolean loadFromResFile(
    String szFileName);


  /**
   * <p>
   * property GetTrCode
   * </p>
   * @return  Returns a value of type java.lang.String
   */

  @DISPID(7) //= 0x7. The runtime will prefer the VTID if present
  @VTID(12)
  String getTrCode();


  /**
   * <p>
   * property GetTrDesc
   * </p>
   * @return  Returns a value of type java.lang.String
   */

  @DISPID(8) //= 0x8. The runtime will prefer the VTID if present
  @VTID(13)
  String getTrDesc();


  /**
   * <p>
   * method GetBlockInfo
   * </p>
   * @param szFieldName Mandatory java.lang.String parameter.
   * @param szNameK Mandatory Holder<java.lang.String> parameter.
   * @param szNameE Mandatory Holder<java.lang.String> parameter.
   * @param nRecordType Mandatory Holder<Integer> parameter.
   */

  @DISPID(14) //= 0xe. The runtime will prefer the VTID if present
  @VTID(14)
  void getBlockInfo(
    String szFieldName,
    Holder<String> szNameK,
    Holder<String> szNameE,
    Holder<Integer> nRecordType);


  /**
   * <p>
   * Input Record�� Setting.
   * </p>
   * @param szBlockName Mandatory java.lang.String parameter.
   * @param szFieldName Mandatory java.lang.String parameter.
   * @param nOccursIndex Mandatory int parameter.
   * @param szData Mandatory java.lang.String parameter.
   */

  @DISPID(15) //= 0xf. The runtime will prefer the VTID if present
  @VTID(15)
  void setFieldData(
    String szBlockName,
    String szFieldName,
    int nOccursIndex,
    String szData);


  /**
   * <p>
   * method GetFieldInfo
   * </p>
   * @param szFieldName Mandatory java.lang.String parameter.
   * @param szItemName Mandatory java.lang.String parameter.
   * @param nItemType Mandatory Holder<Integer> parameter.
   * @param nDataSize Mandatory Holder<Integer> parameter.
   * @param nDotPoint Mandatory Holder<Integer> parameter.
   * @param nOffSet Mandatory Holder<Integer> parameter.
   */

  @DISPID(17) //= 0x11. The runtime will prefer the VTID if present
  @VTID(16)
  void getFieldInfo(
    String szFieldName,
    String szItemName,
    Holder<Integer> nItemType,
    Holder<Integer> nDataSize,
    Holder<Integer> nDotPoint,
    Holder<Integer> nOffSet);


  /**
   * <p>
   * property GetBlockType
   * </p>
   * @param szBlockName Mandatory java.lang.String parameter.
   * @return  Returns a value of type int
   */

  @DISPID(18) //= 0x12. The runtime will prefer the VTID if present
  @VTID(17)
  int getBlockType(
    String szBlockName);


  /**
   * <p>
   * method GetResData
   * </p>
   * @return  Returns a value of type java.lang.String
   */

  @DISPID(19) //= 0x13. The runtime will prefer the VTID if present
  @VTID(18)
  String getResData();


  /**
   * <p>
   * method GetBlockSize
   * </p>
   * @param szBlockName Mandatory java.lang.String parameter.
   * @return  Returns a value of type int
   */

  @DISPID(23) //= 0x17. The runtime will prefer the VTID if present
  @VTID(19)
  int getBlockSize(
    String szBlockName);


  /**
   * <p>
   * method GetFieldDescList
   * </p>
   * @param szBlockName Mandatory java.lang.String parameter.
   * @return  Returns a value of type java.lang.String
   */

  @DISPID(24) //= 0x18. The runtime will prefer the VTID if present
  @VTID(20)
  String getFieldDescList(
    String szBlockName);


  /**
   * <p>
   * property IsNext
   * </p>
   * <p>
   * Getter method for the COM property "IsNext"
   * </p>
   * @return  Returns a value of type boolean
   */

  @DISPID(29) //= 0x1d. The runtime will prefer the VTID if present
  @VTID(21)
  boolean isNext();


  /**
   * <p>
   * property ContinueKey
   * </p>
   * <p>
   * Getter method for the COM property "ContinueKey"
   * </p>
   * @return  Returns a value of type java.lang.String
   */

  @DISPID(30) //= 0x1e. The runtime will prefer the VTID if present
  @VTID(22)
  String continueKey();


  /**
   * <p>
   * method GetBlockCount
   * </p>
   * @param szBlockName Mandatory java.lang.String parameter.
   * @return  Returns a value of type int
   */

  @DISPID(31) //= 0x1f. The runtime will prefer the VTID if present
  @VTID(23)
  int getBlockCount(
    String szBlockName);


  /**
   * <p>
   * method SetBlockCount
   * </p>
   * @param szBlockName Mandatory java.lang.String parameter.
   * @param nCount Mandatory int parameter.
   */

  @DISPID(32) //= 0x20. The runtime will prefer the VTID if present
  @VTID(24)
  void setBlockCount(
    String szBlockName,
    int nCount);


  /**
   * <p>
   * method ClearBlockdata
   * </p>
   * @param szFieldName Mandatory java.lang.String parameter.
   */

  @DISPID(33) //= 0x21. The runtime will prefer the VTID if present
  @VTID(25)
  void clearBlockdata(
    String szFieldName);


  /**
   * <p>
   * method GetLastError
   * </p>
   * @return  Returns a value of type int
   */

  @DISPID(34) //= 0x22. The runtime will prefer the VTID if present
  @VTID(26)
  int getLastError();


  /**
   * <p>
   * method GetErrorMessage
   * </p>
   * @param nErrorCode Mandatory int parameter.
   * @return  Returns a value of type java.lang.String
   */

  @DISPID(35) //= 0x23. The runtime will prefer the VTID if present
  @VTID(27)
  String getErrorMessage(
    int nErrorCode);


  /**
   * <p>
   * method GetAccountList
   * </p>
   * @param nIndex Mandatory int parameter.
   * @return  Returns a value of type java.lang.String
   */

  @DISPID(36) //= 0x24. The runtime will prefer the VTID if present
  @VTID(28)
  String getAccountList(
    int nIndex);


  /**
   * <p>
   * method GetAccountListCount
   * </p>
   * @return  Returns a value of type int
   */

  @DISPID(37) //= 0x25. The runtime will prefer the VTID if present
  @VTID(29)
  int getAccountListCount();


  /**
   * <p>
   * method GetBlockData
   * </p>
   * @param szBlockName Mandatory java.lang.String parameter.
   * @return  Returns a value of type java.lang.String
   */

  @DISPID(38) //= 0x26. The runtime will prefer the VTID if present
  @VTID(30)
  String getBlockData(
    String szBlockName);


  /**
   * <p>
   * method RequestService
   * </p>
   * @param szCode Mandatory java.lang.String parameter.
   * @param szData Mandatory java.lang.String parameter.
   * @return  Returns a value of type int
   */

  @DISPID(39) //= 0x27. The runtime will prefer the VTID if present
  @VTID(31)
  int requestService(
    String szCode,
    String szData);


  /**
   * <p>
   * method RemoveService
   * </p>
   * @param szCode Mandatory java.lang.String parameter.
   * @param szData Mandatory java.lang.String parameter.
   * @return  Returns a value of type int
   */

  @DISPID(40) //= 0x28. The runtime will prefer the VTID if present
  @VTID(32)
  int removeService(
    String szCode,
    String szData);


  /**
   * <p>
   * method RequestLinkToHTS
   * </p>
   * @param szLinkName Mandatory java.lang.String parameter.
   * @param szData Mandatory java.lang.String parameter.
   * @param szFiller Mandatory java.lang.String parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(41) //= 0x29. The runtime will prefer the VTID if present
  @VTID(33)
  boolean requestLinkToHTS(
    String szLinkName,
    String szData,
    String szFiller);


  /**
   * <p>
   * method Decompress
   * </p>
   * @param szBlockName Mandatory java.lang.String parameter.
   * @return  Returns a value of type int
   */

  @DISPID(42) //= 0x2a. The runtime will prefer the VTID if present
  @VTID(34)
  int decompress(
    String szBlockName);


  /**
   * <p>
   * method GetTRCountPerSec
   * </p>
   * @param szCode Mandatory java.lang.String parameter.
   * @return  Returns a value of type int
   */

  @DISPID(43) //= 0x2b. The runtime will prefer the VTID if present
  @VTID(35)
  int getTRCountPerSec(
    String szCode);


  /**
   * <p>
   * method GetAccountName
   * </p>
   * @param szAcc Mandatory java.lang.String parameter.
   * @return  Returns a value of type java.lang.String
   */

  @DISPID(44) //= 0x2c. The runtime will prefer the VTID if present
  @VTID(36)
  String getAccountName(
    String szAcc);


  /**
   * <p>
   * method GetAcctDetailName
   * </p>
   * @param szAcc Mandatory java.lang.String parameter.
   * @return  Returns a value of type java.lang.String
   */

  @DISPID(45) //= 0x2d. The runtime will prefer the VTID if present
  @VTID(37)
  String getAcctDetailName(
    String szAcc);


  /**
   * <p>
   * method GetAcctNickname
   * </p>
   * @param szAcc Mandatory java.lang.String parameter.
   * @return  Returns a value of type java.lang.String
   */

  @DISPID(46) //= 0x2e. The runtime will prefer the VTID if present
  @VTID(38)
  String getAcctNickname(
    String szAcc);


  /**
   * <p>
   * method GetFieldChartRealData
   * </p>
   * @param szBlockName Mandatory java.lang.String parameter.
   * @param szFieldName Mandatory java.lang.String parameter.
   * @return  Returns a value of type java.lang.String
   */

  @DISPID(47) //= 0x2f. The runtime will prefer the VTID if present
  @VTID(39)
  String getFieldChartRealData(
    String szBlockName,
    String szFieldName);


  /**
   * <p>
   * method GetAttribute
   * </p>
   * @param szBlockName Mandatory java.lang.String parameter.
   * @param szFieldName Mandatory java.lang.String parameter.
   * @param szAttribute Mandatory java.lang.String parameter.
   * @param nRecordIndex Mandatory int parameter.
   * @return  Returns a value of type java.lang.String
   */

  @DISPID(48) //= 0x30. The runtime will prefer the VTID if present
  @VTID(40)
  String getAttribute(
    String szBlockName,
    String szFieldName,
    String szAttribute,
    int nRecordIndex);


  /**
   * <p>
   * method GetTRCountBaseSec
   * </p>
   * @param szCode Mandatory java.lang.String parameter.
   * @return  Returns a value of type int
   */

  @DISPID(49) //= 0x31. The runtime will prefer the VTID if present
  @VTID(41)
  int getTRCountBaseSec(
    String szCode);


  /**
   * <p>
   * method GetTRCountRequest
   * </p>
   * @param szCode Mandatory java.lang.String parameter.
   * @return  Returns a value of type int
   */

  @DISPID(50) //= 0x32. The runtime will prefer the VTID if present
  @VTID(42)
  int getTRCountRequest(
    String szCode);


  /**
   * <p>
   * method GetTRCountLimit
   * </p>
   * @param szCode Mandatory java.lang.String parameter.
   * @return  Returns a value of type int
   */

  @DISPID(51) //= 0x33. The runtime will prefer the VTID if present
  @VTID(43)
  int getTRCountLimit(
    String szCode);


  /**
   * <p>
   * method GetFieldSearchRealData
   * </p>
   * @param szBlockName Mandatory java.lang.String parameter.
   * @param szFieldName Mandatory java.lang.String parameter.
   * @return  Returns a value of type java.lang.String
   */

  @DISPID(52) //= 0x34. The runtime will prefer the VTID if present
  @VTID(44)
  String getFieldSearchRealData(
    String szBlockName,
    String szFieldName);


  // Properties:
}
