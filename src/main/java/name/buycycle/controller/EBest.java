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
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

/**
 * 이베스트 컨트롤러
 * @author : ijyoon
 */
@RestController
@RequestMapping("/ebest")
public class EBest {

    private final static String REAL_TR_LIST = "real_tr_list"; // real tr 목록
    private final static String REAL_TR_DESC = "real_tr_desc"; // real tr 상세
    private final static String QUERY_TR_LIST = "query_tr_list"; // query tr 목록
    private final static String QUERY_TR_DESC = "query_tr_desc"; // query tr 상세
    private final static String REQUEST_MESSAGE = "request_message";  // 요청메시지
    private final static String REQUEST = "request"; // 요청

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
     * @throws Exception exception
     */
    @PostMapping(value = "/queries", produces = "application/json;charset=utf-8")
    ResponseEntity<EntityModel<Response>> queryRequest(@RequestBody Request request) throws Exception {

        if(logger.isDebugEnabled())
            logger.debug(" => request message \n---\n{}\n---", objectMapper.writeValueAsString(request));

        Response response = xaQueryRequestHelper.requestQuery(request);

        if(logger.isDebugEnabled())
            logger.debug(" <= response message \n---\n{}\n---", objectMapper.writeValueAsString(response));

        return ResponseEntity.ok(
                EntityModel.of(response)
                        .add( linkTo( methodOn(EBest.class).queryRequest(null)).withSelfRel())
        );
    }

    /**
     * real 목록
     *
     * @return XAReal 요청 목록
     */
    @GetMapping("/description/realtime")
    ResponseEntity<EntityModel<ResTable>> listReal() throws Exception {
        if(logger.isInfoEnabled())
            logger.info("Real 목록 요청");

        ResTable result = eBestDescriptionHelper.resList(ResFileData.REAL);
        return ResponseEntity.ok(
                EntityModel.of(result)
                        .add( linkTo( methodOn(EBest.class).listReal()).withSelfRel())
                        .add( linkTo( methodOn(EBest.class).listReal(null)).withRel(REAL_TR_DESC))
                        .add( linkTo( methodOn(EBest.class).requestMessage(null)).withRel(REQUEST_MESSAGE))
        );
    }

    /**
     * query 목록
     *
     * @return XAQuery 요청 목록
     */
    @GetMapping("/description/queries")
    ResponseEntity<EntityModel<ResTable>> listQuery() throws Exception {
        if(logger.isInfoEnabled())
            logger.info("Query 목록 요청");

        ResTable result = eBestDescriptionHelper.resList(ResFileData.QUERY);

        return ResponseEntity.ok(
                EntityModel.of(result)
                        .add( linkTo( methodOn(EBest.class).listQuery()).withSelfRel())
                        .add( linkTo( methodOn(EBest.class).listQuery(null)).withRel(QUERY_TR_DESC))
                        .add( linkTo( methodOn(EBest.class).requestMessage(null)).withRel(REQUEST_MESSAGE))
        );
    }

    /**
     * real tr 명세
     *
     * @param trName TR 이름
     * @return 대상 TR 명세서
     */
    @GetMapping("/description/realtime/{trName}")
    ResponseEntity<EntityModel<ResDesc>> listReal(@PathVariable String trName) throws Exception {
        if(logger.isInfoEnabled())
            logger.info("[{}] Real 명세 요청", trName);

        ResDesc result = eBestDescriptionHelper.resDesc(trName);

        return ResponseEntity.ok(
                EntityModel.of(result)
                        .add( linkTo( methodOn(EBest.class).listReal(trName)).withSelfRel())
                        .add( linkTo( methodOn(EBest.class).listReal()).withRel(REAL_TR_LIST))
                        .add( linkTo( methodOn(EBest.class).requestMessage(trName)).withRel(REQUEST_MESSAGE))
        );
    }

    /**
     * query tr 명세
     *
     * @param trName TR 이름
     * @return 대상 TR 명세서
     */
    @GetMapping("/description/queries/{trName}")
    ResponseEntity<EntityModel<ResDesc>> listQuery(@PathVariable String trName) throws Exception {
        if(logger.isInfoEnabled())
            logger.info("[{}] Query 명세 요청", trName);
        ResDesc result =  eBestDescriptionHelper.resDesc(trName);

        return ResponseEntity.ok(
                EntityModel.of(result)
                        .add( linkTo( methodOn(EBest.class).listQuery(trName)).withSelfRel())
                        .add( linkTo( methodOn(EBest.class).listQuery()).withRel(QUERY_TR_LIST))
                        .add( linkTo( methodOn(EBest.class).requestMessage(trName)).withRel(REQUEST_MESSAGE))
                        .add( linkTo( methodOn(EBest.class).queryRequest(null)).withRel(REQUEST))
        );
    }

    /**
     * 샘플 요청 메시지 반환
     * @param trName 대상 TR
     * @return 대상 TR 요청 메시지
     */
    @GetMapping(value = "/request-messages/{trName}")
    ResponseEntity<EntityModel<Request>> requestMessage(@PathVariable String trName) throws Exception {
        if(logger.isInfoEnabled())
            logger.info("[{}] 요청 메시지 샘플", trName);
        Request result = eBestDescriptionHelper.requestMessage(trName);

        return ResponseEntity.ok(
                EntityModel.of(result)
                    .add( linkTo( methodOn(EBest.class).requestMessage(trName)).withSelfRel())
                    .add( linkTo( methodOn(EBest.class).queryRequest(null)).withRel(REQUEST))
        );
    }
}
