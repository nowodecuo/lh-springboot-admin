/**
 * 管理员管理
 * @author 1874
 */
package luohao.application.controller.backService.admin;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import luohao.application.common.enums.MsgEnum;
import luohao.application.common.pojo.PageResult;
import luohao.application.common.utils.Config;
import luohao.application.model.vo.admin.*;
import luohao.application.model.common.ActionAdd;
import luohao.application.model.common.ActionUpdate;
import luohao.application.common.pojo.FormValidator;
import luohao.application.common.pojo.CommonResult;
import luohao.application.service.admin.AdminService;
import luohao.application.common.utils.Utils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/admin")
@Tag(name = "管理后台-管理员管理")
public class AdminController {
    @Resource
    private AdminService adminService; // 依赖注入
    /** 管理员分页 */
    @PostMapping("/page")
    @Operation(summary = "管理员分页")
    public CommonResult<PageResult<AdminListRespVO>> adminPage(@RequestBody AdminListReqVO params) {
        PageResult<AdminListRespVO> pageResult = adminService.queryAdminPage(params);
        return CommonResult.success(pageResult);
    }
    /** 管理员新增 */
    @PostMapping("/add")
    @Operation(summary = "管理员新增")
    public CommonResult<Boolean> adminAdd(@RequestBody @Validated({ActionAdd.class}) AdminActionReqVO params) throws Exception {
        // 验证通过后执行新增操作
        adminService.insertAdmin(params);
        return CommonResult.success(true, MsgEnum.ADD_SUCCESS.VALUE);
    }
    /** 管理员更新 */
    @PostMapping("/update")
    @Operation(summary = "管理员更新")
    public CommonResult<Boolean> adminUpdate(@RequestBody @Validated({ActionUpdate.class}) AdminActionReqVO params) throws Exception {
        // 验证通过后执行更新操作
        adminService.updateAdmin(params);
        return CommonResult.success(true, MsgEnum.UPDATE_SUCCESS.VALUE);
    }
    /** 管理员删除 */
    @PostMapping("/delete")
    @Operation(summary = "管理员删除")
    @Parameter(name = "id", description = "管理员id", required = true)
    public CommonResult<Boolean> adminDelete(@RequestBody Map<String, String> map) throws Exception {
        Long id = Long.valueOf(map.get("id"));
        Utils.formCheck(FormValidator.setFormCheck(id, "ID不能为空"));
        // 验证通过后执行删除操作
        adminService.deleteAdmin(id);
        return CommonResult.success(true, MsgEnum.DELETE_SUCCESS.VALUE);
    }
    /** 管理员密码更新 */
    @PostMapping("/adminPasswordUpdate")
    @Operation(summary = "管理员密码更新", parameters = {
        @Parameter(name = "adId", description = "管理员id", required = true),
        @Parameter(name = "adPassword", description = "密码", required = true)
    })
    public CommonResult<Boolean> adminPasswordUpdate(@RequestBody Map<String, String> map) throws Exception {
        String id = map.get("adId");
        String password = map.get("adPassword");
        // 校验数据
        Utils.formCheck(FormValidator.setFormCheck(id, "ID不能为空"));
        Utils.formCheck(FormValidator.setFormCheck(password, "密码不能为空"));
        Utils.formCheck(FormValidator.setFormCheck(password, Config.PASSWORD_PATTERN, Config.PASSWORD_ERROR_MSG));
        // 验证通过后执行删除操作
        adminService.updatePassword(Long.valueOf(id), password, false);
        return CommonResult.success(true, MsgEnum.DELETE_SUCCESS.VALUE);
    }
    /** 批量更新管理员角色(角色迁移) */
    @PostMapping("/adminBatchUpdateRole")
    @Operation(summary = "管理员批量角色迁移", parameters = {
        @Parameter(name = "adRoleId", description = "角色id", required = true),
        @Parameter(name = "adIds", description = "管理员ids", required = true, example = "1,2,3")
    })
    public CommonResult<Boolean> adminBatchUpdateRole(@RequestBody Map<String, String> map) throws Exception {
        String adRoleId = map.get("adRoleId");
        String adIds = map.get("adIds");
        Utils.formCheck(FormValidator.setFormCheck(adRoleId, "角色ID不能为空"));
        Utils.formCheck(FormValidator.setFormCheck(adIds, "管理员ID不能为空"));
        // 验证通过后执行修改操作
        adminService.updateBatchRole(Integer.valueOf(adRoleId), adIds);
        return CommonResult.success(true, MsgEnum.UPDATE_SUCCESS.VALUE);
    }
    /** (个人设置)查询管理员个人信息 */
    @PostMapping("myAdminData")
    @Operation(summary = "管理员个人信息")
    public CommonResult<AdminMyRespVO> myAdminData() {
        AdminMyRespVO adminInfo = adminService.queryMyAdminData();
        return CommonResult.success(adminInfo);
    }
    /** (个人设置)管理员个人信息更新 */
    @PostMapping("/myAdminUpdate")
    @Operation(summary = "管理员个人更新")
    public CommonResult<Boolean> myAdminUpdate(@RequestBody AdminMyReqVO params) throws Exception {
        // 验证通过后执行更新操作
        adminService.updateMyAdmin(params);
        return CommonResult.success(true, MsgEnum.UPDATE_SUCCESS.VALUE);
    }
    /** (个人设置)管理员个人密码更新 */
    @PostMapping("/adminMyPasswordUpdate")
    @Operation(summary = "管理员个人密码更新", parameters = {
        @Parameter(name = "adId", description = "管理员id", required = true),
        @Parameter(name = "adPassword", description = "密码", required = true)
    })
    public CommonResult<Boolean> adminMyPasswordUpdate(@RequestBody Map<String, String> map) throws Exception {
        String id = map.get("adId");
        String password = map.get("adPassword");
        // 校验数据
        Utils.formCheck(FormValidator.setFormCheck(id, "ID不能为空"));
        Utils.formCheck(FormValidator.setFormCheck(password, "密码不能为空"));
        Utils.formCheck(FormValidator.setFormCheck(password, Config.PASSWORD_PATTERN, Config.PASSWORD_ERROR_MSG));
        // 验证通过后执行删除操作
        adminService.updatePassword(Long.valueOf(id), password, true);
        return CommonResult.success(true, MsgEnum.UPDATE_FAIL.VALUE);
    }
}
