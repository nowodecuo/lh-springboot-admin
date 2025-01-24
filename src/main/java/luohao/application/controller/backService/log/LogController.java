package luohao.application.controller.backService.log;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import luohao.application.common.pojo.FormValidator;
import luohao.application.common.enums.MsgEnum;
import luohao.application.common.pojo.CommonResult;
import luohao.application.common.pojo.PageResult;
import luohao.application.model.vo.log.LogListRespVO;
import luohao.application.service.log.LogService;
import luohao.application.model.vo.log.LogListReqVO;
import luohao.application.common.utils.Utils;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/log")
@Tag(name = "管理后台-日志管理")
public class LogController {
    @Resource
    private LogService logService; // 依赖注入
    /** 日志列表分页 */
    @PostMapping("/page")
    @Operation(description = "日志列表分页")
    public CommonResult<PageResult<LogListRespVO>> logPage(@RequestBody LogListReqVO params) {
        PageResult<LogListRespVO> pageResult = logService.queryTablePage(params);
        return CommonResult.success(pageResult);
    }
    /** 日志批量删除 */
    @PostMapping("/batchDelete")
    @Operation(description = "日志批量删除")
    @Parameter(name = "ids", description = "日志id (1,2,3)", required = true)
    public CommonResult<Boolean> logBatchDelete(@RequestBody Map<String, String> map) throws Exception {
        String ids = map.get("ids");
        // 校验数据
        Utils.formCheck(FormValidator.setFormCheck(ids, "ids不能为空"));
        // 验证通过后执行删除操作
        logService.deleteBatchData(ids);
        return CommonResult.success(true, MsgEnum.DELETE_SUCCESS.VALUE);
    }
}