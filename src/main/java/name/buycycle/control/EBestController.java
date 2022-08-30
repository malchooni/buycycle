package name.buycycle.control;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import name.buycycle.control.rvo.ResTable;
import name.buycycle.service.ebest.EBestDescription;
import name.buycycle.service.ebest.vo.ResDesc;
import name.buycycle.vendor.ebest.event.vo.req.Request;
import name.buycycle.vendor.ebest.event.vo.res.Response;
import name.buycycle.vendor.ebest.manage.XAQueryManager;
import name.buycycle.vendor.ebest.message.ResFileData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * 이베스트 컨트롤러
 *
 * @author : ijyoon
 */
@RestController
@RequestMapping("/ebest")
public class EBestController {

  private static final String REAL_TR_LIST = "real_tr_list"; // real tr 목록
  private static final String REAL_TR_DESC = "real_tr_desc"; // real tr 상세
  private static final String QUERY_TR_LIST = "query_tr_list"; // query tr 목록
  private static final String QUERY_TR_DESC = "query_tr_desc"; // query tr 상세
  private static final String REQUEST_MESSAGE = "request_message";  // 요청메시지
  private static final String REQUEST = "request"; // 요청

  private final Logger logger = LoggerFactory.getLogger(getClass());
  private final ObjectMapper objectMapper = new ObjectMapper().enable(
      SerializationFeature.INDENT_OUTPUT);

  private final XAQueryManager xaQueryManager = XAQueryManager.getInstance();

  @Autowired
  private EBestDescription eBestDescription;

  /**
   * xing api 호출
   *
   * @param request 요청 값
   * @return api 요청 응답값
   * @throws Exception exception
   */
  @PostMapping(value = "/queries", produces = "application/json;charset=utf-8")
  public ResponseEntity<EntityModel<Response>> queryRequest(@RequestBody Request request)
      throws Exception {

      if (logger.isDebugEnabled()) {
          logger.debug(" => request message \n---\n{}\n---",
              objectMapper.writeValueAsString(request));
      }

    Response response = this.xaQueryManager.requestQuery(request);

      if (logger.isDebugEnabled()) {
          logger.debug(" <= response message \n---\n{}\n---",
              objectMapper.writeValueAsString(response));
      }

    return ResponseEntity.ok(
        EntityModel.of(response)
            .add(linkTo(methodOn(EBestController.class).queryRequest(null)).withSelfRel())
    );
  }

  /**
   * real 목록
   *
   * @return XAReal 요청 목록
   */
  @GetMapping("/description/realtime")
  public ResponseEntity<EntityModel<ResTable>> listReal() throws Exception {
      if (logger.isInfoEnabled()) {
          logger.info("Real 목록 요청");
      }

    ResTable result = eBestDescription.resList(ResFileData.REAL);
    return ResponseEntity.ok(
        EntityModel.of(result)
            .add(linkTo(methodOn(EBestController.class).listReal()).withSelfRel())
            .add(linkTo(methodOn(EBestController.class).listReal(null)).withRel(REAL_TR_DESC))
            .add(linkTo(methodOn(EBestController.class).requestMessage(null)).withRel(
                REQUEST_MESSAGE))
    );
  }

  /**
   * query 목록
   *
   * @return XAQuery 요청 목록
   */
  @GetMapping("/description/queries")
  public ResponseEntity<EntityModel<ResTable>> listQuery() throws Exception {
      if (logger.isInfoEnabled()) {
          logger.info("Query 목록 요청");
      }

    ResTable result = eBestDescription.resList(ResFileData.QUERY);

    return ResponseEntity.ok(
        EntityModel.of(result)
            .add(linkTo(methodOn(EBestController.class).listQuery()).withSelfRel())
            .add(linkTo(methodOn(EBestController.class).listQuery(null)).withRel(QUERY_TR_DESC))
            .add(linkTo(methodOn(EBestController.class).requestMessage(null)).withRel(
                REQUEST_MESSAGE))
    );
  }

  /**
   * real tr 명세
   *
   * @param trName TR 이름
   * @return 대상 TR 명세서
   */
  @GetMapping("/description/realtime/{trName}")
  public ResponseEntity<EntityModel<ResDesc>> listReal(@PathVariable String trName)
      throws Exception {
      if (logger.isInfoEnabled()) {
          logger.info("[{}] Real 명세 요청", trName);
      }

    ResDesc result = eBestDescription.resDesc(trName);

    return ResponseEntity.ok(
        EntityModel.of(result)
            .add(linkTo(methodOn(EBestController.class).listReal(trName)).withSelfRel())
            .add(linkTo(methodOn(EBestController.class).listReal()).withRel(REAL_TR_LIST))
            .add(linkTo(methodOn(EBestController.class).requestMessage(trName)).withRel(
                REQUEST_MESSAGE))
    );
  }

  /**
   * query tr 명세
   *
   * @param trName TR 이름
   * @return 대상 TR 명세서
   */
  @GetMapping("/description/queries/{trName}")
  public ResponseEntity<EntityModel<ResDesc>> listQuery(@PathVariable String trName)
      throws Exception {
      if (logger.isInfoEnabled()) {
          logger.info("[{}] Query 명세 요청", trName);
      }
    ResDesc result = eBestDescription.resDesc(trName);

    return ResponseEntity.ok(
        EntityModel.of(result)
            .add(linkTo(methodOn(EBestController.class).listQuery(trName)).withSelfRel())
            .add(linkTo(methodOn(EBestController.class).listQuery()).withRel(QUERY_TR_LIST))
            .add(linkTo(methodOn(EBestController.class).requestMessage(trName)).withRel(
                REQUEST_MESSAGE))
            .add(linkTo(methodOn(EBestController.class).queryRequest(null)).withRel(REQUEST))
    );
  }

  /**
   * 샘플 요청 메시지 반환
   *
   * @param trName 대상 TR
   * @return 대상 TR 요청 메시지
   */
  @GetMapping(value = "/request-messages/{trName}")
  public ResponseEntity<EntityModel<Request>> requestMessage(@PathVariable String trName)
      throws Exception {
      if (logger.isInfoEnabled()) {
          logger.info("[{}] 요청 메시지 샘플", trName);
      }
    Request result = eBestDescription.requestMessage(trName);

    return ResponseEntity.ok(
        EntityModel.of(result)
            .add(linkTo(methodOn(EBestController.class).requestMessage(trName)).withSelfRel())
            .add(linkTo(methodOn(EBestController.class).queryRequest(null)).withRel(REQUEST))
    );
  }
}
