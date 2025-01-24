/**
 * 权限管理
 * @author 1874
 */
package luohao.application.controller.backService.authRule;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import luohao.application.common.pojo.FormValidator;
import luohao.application.common.pojo.CommonResult;
import luohao.application.common.enums.MsgEnum;
import luohao.application.common.utils.Utils;
import luohao.application.model.vo.authRule.AuthRuleListReqVo;
import luohao.application.model.vo.authRule.AuthRuleActionReqVo;
import luohao.application.model.common.ActionAdd;
import luohao.application.model.common.ActionUpdate;
import luohao.application.model.vo.authRule.AuthRuleListRespVo;
import luohao.application.service.authRule.AuthRuleService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/authRule")
@Tag(name = "管理后台-权限管理")
public class AuthRuleController {
    @Resource
    private AuthRuleService authRuleService;
    /** 权限列表 */
    @PostMapping("/list")
    @Operation(summary = "权限列表")
    public CommonResult<List<AuthRuleListRespVo>> authRuleList(@RequestBody AuthRuleListReqVo params) {
        List<AuthRuleListRespVo> pageResult = authRuleService.queryAuthRuleList(params);
        return CommonResult.success(pageResult);
    }
    /** 角色权限授权分页 */
    @PostMapping("/empowerList")
    @Operation(summary = "角色权限授权列表")
    public CommonResult<List<AuthRuleListRespVo>> authRuleEmpowerList(@RequestBody AuthRuleListReqVo params) {
        List<AuthRuleListRespVo> pageResult = authRuleService.queryAuthRuleList(params);
        return CommonResult.success(pageResult);
    }
    /** 权限新增 */
    @PostMapping("/add")
    @Operation(summary = "权限新增")
    public CommonResult<Boolean> authRuleAdd(@RequestBody @Validated({ActionAdd.class}) AuthRuleActionReqVo params) throws Exception {
        authRuleService.insertAuthRule(params);
        return CommonResult.success(true, MsgEnum.ADD_SUCCESS.VALUE);
    }
    /** 权限更新 */
    @PostMapping("/update")
    @Operation(summary = "权限更新")
    public CommonResult<Boolean> authRuleUpdate(@RequestBody @Validated({ActionUpdate.class}) AuthRuleActionReqVo params) throws Exception {
        // 验证通过后执行修改操作
        authRuleService.updateAuthRule(params);
        return CommonResult.success(true, MsgEnum.UPDATE_SUCCESS.VALUE);
    }
    /** 权限删除 */
    @PostMapping("/delete")
    @Operation(summary = "权限删除")
    @Parameter(name = "id", description = "管理员id", required = true)
    public CommonResult<Boolean> authRuleDelete(@RequestBody Map<String, String> map) throws Exception {
        Long id = Long.valueOf(map.get("id"));
        // 校验数据
        Utils.formCheck(FormValidator.setFormCheck(id, "ID不能为空"));
        // 验证通过后执行删除操作
        authRuleService.deleteBackMenu(id);
        return CommonResult.success(true, MsgEnum.DELETE_SUCCESS.VALUE);
    }
}
