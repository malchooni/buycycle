package name.buycycle.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import name.buycycle.controller.vo.ResTable;
import name.buycycle.service.ebest.EBestDescriptionHelper;
import name.buycycle.service.ebest.vo.ResDesc;
import name.buycycle.vendor.ebest.event.vo.req.Request;
import name.buycycle.vendor.ebest.event.vo.res.Response;
import name.buycycle.vendor.ebest.invoke.XAQueryRequestHelper;
import name.buycycle.vendor.ebest.message.ResFileData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 이베스트 컨트롤러
 * @author : ijyoon
 */
@RestController
@RequestMapping("/ebest")
public class EBest {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

    @Autowired
    private XAQueryRequestHelper xaQueryRequestHelper;
    @Autowired
    private EBestDescriptionHelper eBestDescriptionHelper;

    /**
     * xing api 호출
     *
     * @param request 요청 값
     * @return api 요청 응답값
     * @throws Exception
     */
    @PostMapping(value = "/queries", produces = "application/json;charset=utf-8")
    ResponseEntity<Response> queryRequest(@RequestBody Request request) throws Exception {

        if(logger.isDebugEnabled())
            logger.debug(" => request message \n---\n{}\n---", objectMapper.writeValueAsString(request));

        Response response = xaQueryRequestHelper.requestQuery(request);

        if(logger.isDebugEnabled())
            logger.debug(" <= response message \n---\n{}\n---", objectMapper.writeValueAsString(response));

        return ResponseEntity.ok(response);
    }

    /**
     * real 목록
     *
     * @return XAReal 요청 목록
     */
    @GetMapping("/description/realtime")
    ResponseEntity<ResTable> listReal() {
        if(logger.isInfoEnabled())
            logger.info("Real 목록 요청");

        ResTable result = eBestDescriptionHelper.resList(ResFileData.REAL);
        return ResponseEntity.ok(result);
    }

    /**
     * query 목록
     *
     * @return XAQuery 요청 목록
     */
    @GetMapping("/description/queries")
    ResponseEntity<ResTable> listQuery() {
        if(logger.isInfoEnabled())
            logger.info("Query 목록 요청");

        ResTable result = eBestDescriptionHelper.resList(ResFileData.QUERY);
        return ResponseEntity.ok(result);
    }

    /**
     * real tr 명세
     *
     * @param trName TR 이름
     * @return 대상 TR 명세서
     */
    @GetMapping("/description/realtime/{trName}")
    ResponseEntity<ResDesc> listReal(@PathVariable String trName) {
        if(logger.isInfoEnabled())
            logger.info("[{}] Real 명세 요청", trName);

        ResDesc result = eBestDescriptionHelper.resDesc(trName);
        return ResponseEntity.ok(result);
    }

    /**
     * query tr 명세
     *
     * @param trName TR 이름
     * @return 대상 TR 명세서
     */
    @GetMapping("/description/queries/{trName}")
    ResponseEntity<EntityModel<ResDesc>> listQuery(@PathVariable String trName) {
        if(logger.isInfoEnabled())
            logger.info("[{}] Query 명세 요청", trName);
        ResDesc result =  eBestDescriptionHelper.resDesc(trName);

        return ResponseEntity.ok(
                EntityModel.of(result)
                .add( WebMvcLinkBuilder.linkTo( WebMvcLinkBuilder.methodOn(EBest.class).listQuery() ).withSelfRel())
        );
    }

    /**
     * 샘플 요청 메시지 반환
     * @param trName 대상 TR
     * @return 대상 TR 요청 메시지
     */
    @GetMapping(value = "/request-messages/{trName}")
    ResponseEntity<Request> requestMessage(@PathVariable String trName) {
        if(logger.isInfoEnabled())
            logger.info("[{}] 요청 메시지 샘플", trName);
        Request result = eBestDescriptionHelper.requestMessage(trName);
        return ResponseEntity.ok(result);
    }

}
