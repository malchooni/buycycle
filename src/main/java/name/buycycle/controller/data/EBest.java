package name.buycycle.controller.data;

import name.buycycle.controller.data.vo.ResTable;
import name.buycycle.vendor.ebest.data.EBestDescriptionHelper;
import name.buycycle.vendor.ebest.data.vo.ResDesc;
import name.buycycle.vendor.ebest.event.vo.req.Request;
import name.buycycle.vendor.ebest.event.vo.res.Response;
import name.buycycle.vendor.ebest.invoke.XAQueryRequestHelper;
import name.buycycle.vendor.ebest.message.ResFileData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/data/ebest")
public class EBest {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private XAQueryRequestHelper xaQueryRequestHelper;
    @Autowired
    private EBestDescriptionHelper eBestDescriptionHelper;

    /**
     * xing api 호출
     *
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/query", produces = "application/json;charset=utf-8")
    Response queryRequest(@RequestBody Request request) throws Exception {
        return xaQueryRequestHelper.requestQuery(request);
    }

    /**
     * real 목록
     *
     * @return
     */
    @RequestMapping("/list/real")
    ResTable listReal() {
        return eBestDescriptionHelper.resList(ResFileData.REAL);
    }

    /**
     * query 목록
     *
     * @return
     */
    @RequestMapping("/list/query")
    ResTable listQuery() {
        return eBestDescriptionHelper.resList(ResFileData.QUERY);
    }

    /**
     * real tr 명세
     *
     * @param trName
     * @return
     */
    @RequestMapping("/list/real/{trName}")
    ResDesc listReal(@PathVariable String trName) {
        logger.info(trName);
        return eBestDescriptionHelper.resDesc(trName);
    }

    /**
     * query tr 명세
     *
     * @param trName
     * @return
     */
    @RequestMapping("/list/query/{trName}")
    ResDesc listQuery(@PathVariable String trName) {
        logger.info(trName);
        return eBestDescriptionHelper.resDesc(trName);
    }

    /**
     * 샘플 요청 메시지 반환
     * @param trName
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/requestmessage/{trName}")
    Request requestMessage(@PathVariable String trName) throws Exception {
        return eBestDescriptionHelper.requestMessage(trName);
    }

}
