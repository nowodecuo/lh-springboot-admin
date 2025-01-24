/**
 * 角色管理
 * @author 1874
 */
package luohao.application.controller.backService.role;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import luohao.application.common.pojo.FormValidator;
import luohao.application.common.pojo.CommonResult;
import luohao.application.common.enums.MsgEnum;
import luohao.application.common.utils.Utils;
import luohao.application.model.common.ActionUpdate;
import luohao.application.model.vo.role.RoleListRespVO;
import luohao.application.model.vo.role.RoleActionReqVo;
import luohao.application.model.vo.role.RoleMenuRespVO;
import luohao.application.service.role.RoleService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/role")
@Tag(name = "管理后台-角色管理")
public class RoleController {
    @Resource
    private RoleService roleService;
    /** 角色列表 */
    @PostMapping("/list")
    @Operation(summary = "角色列表")
    public CommonResult<List<RoleListRespVO>> roleList() {
        List<RoleListRespVO> roleList = roleService.queryRoleList();
        return CommonResult.success(roleList);
    }
    /** 角色新增 */
    @PostMapping("/add")
    @Operation(summary = "角色新增")
    public CommonResult<Boolean> roleAdd(@RequestBody @Validated RoleActionReqVo params) throws Exception {
        roleService.insertRole(params);
        return CommonResult.success(true, MsgEnum.ADD_SUCCESS.VALUE);
    }
    /** 角色更新 */
    @PostMapping("/update")
    @Operation(summary = "角色更新")
    public CommonResult<Boolean> roleUpdate(@RequestBody @Validated({ActionUpdate.class}) RoleActionReqVo params) throws Exception {
        // 验证通过后执行修改操作
        roleService.updateRole(params);
        return CommonResult.success(true, MsgEnum.UPDATE_SUCCESS.VALUE);
    }
    /** 角色删除 */
    @PostMapping("/delete")
    @Operation(summary = "角色删除")
    @Parameter(name = "id", description = "角色id", required = true)
    public CommonResult<Boolean> roleDelete(@RequestBody Map<String, String> map) throws Exception {
        Long id = Long.valueOf(map.get("id"));
        // 校验数据
        Utils.formCheck(FormValidator.setFormCheck(id, "ID不能为空"));
        // 验证通过后执行删除操作
        roleService.deleteRole(id);
        return CommonResult.success(true, MsgEnum.DELETE_SUCCESS.VALUE);
    }
    /** 角色后台菜单授权更新 */
    @PostMapping("/updateRoleBackMenu")
    @Operation(summary = "角色后台菜单授权更新", parameters = {
        @Parameter(name = "roId", description = "角色id", required = true),
        @Parameter(name = "menuIds", description = "菜单ids", required = true, example = "1,2,3")
    })
    public CommonResult<Boolean> updateRoleBackMenu(@RequestBody Map<String, String> map) throws Exception {
        String roId = map.get("roId");
        String menuIds = map.get("menuIds");
        Utils.formCheck(FormValidator.setFormCheck(roId, "ID不能为空"));
        Utils.formCheck(FormValidator.setFormCheck(menuIds, "菜单ID不能为空"));
        // 验证通过后执行修改操作
        roleService.updateRoleBackMenu(Long.valueOf(roId), menuIds);
        return CommonResult.success(true, MsgEnum.UPDATE_SUCCESS.VALUE);
    }
    /** 角色权限授权更新 */
    @PostMapping("/updateRoleAuthRule")
    @Operation(summary = "角色权限授权更新", parameters = {
        @Parameter(name = "roId", description = "角色id", required = true),
        @Parameter(name = "ruleIds", description = "权限ids", required = true, example = "1,2,3")
    })
    public CommonResult<Boolean> updateRoleAuthRule(@RequestBody Map<String, String> map) throws Exception {
        String roId = map.get("roId");
        String ruleIds = map.get("ruleIds");
        Utils.formCheck(FormValidator.setFormCheck(roId, "ID不能为空"));
        Utils.formCheck(FormValidator.setFormCheck(ruleIds, "权限ID不能为空"));
        // 验证通过后执行修改操作
        roleService.updateRoleAuthRule(Long.valueOf(roId), ruleIds);
        return CommonResult.success(true, MsgEnum.UPDATE_SUCCESS.VALUE);
    }
    /** 我的下级角色列表 */
    @PostMapping("/mySubordinatesRoleList")
    @Operation(summary = "我的下级角色列表")
    public CommonResult<List<RoleListRespVO>> mySubordinatesRoleList() {
        List<RoleListRespVO> roleList = roleService.queryMySubordinatesList();
        return CommonResult.success(roleList);
    }
    /** 查询登录角色的菜单和权限 */
    @PostMapping("/queryRoleMenuAndRule")
    @Operation(summary = "登录角色的菜单和权限")
    public CommonResult<RoleMenuRespVO> queryRoleMenuAndRule() throws Exception {
        return CommonResult.success(roleService.queryRoleMenuAndRule());
    }
}
