/**
 * 后台菜单管理
 * @author 1874
 */
package luohao.application.controller.backService.backMenu;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import luohao.application.common.pojo.FormValidator;
import luohao.application.common.pojo.CommonResult;
import luohao.application.common.enums.MsgEnum;
import luohao.application.common.enums.StatusEnum;
import luohao.application.common.utils.Utils;
import luohao.application.model.vo.backMenu.BackMenuListRespVo;
import luohao.application.model.vo.backMenu.BackMenuActionReqVo;
import luohao.application.model.common.ActionAdd;
import luohao.application.model.common.ActionUpdate;
import luohao.application.service.backMenu.BackMenuService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/backMenu")
@Tag(name = "管理后台-后台菜单管理")
public class BackMenuController {
    @Resource
    private BackMenuService backMenuService;
    /** 后台菜单列表 */
    @PostMapping("/list")
    @Operation(summary = "后台菜单列表")
    public CommonResult<List<BackMenuListRespVo>> backMenuList() {
        List<BackMenuListRespVo> menuList = backMenuService.queryBackMenuList(null);
        return CommonResult.success(menuList);
    }
    /** 后台菜单授权列表 */
    @PostMapping("/empowerList")
    @Operation(summary = "后台菜单授权列表")
    public CommonResult<List<BackMenuListRespVo>> backMenuEmpowerList() {
        List<BackMenuListRespVo> menuList = backMenuService.queryBackMenuList(StatusEnum.USING.VALUE);
        return CommonResult.success(menuList);
    }
    /** 后台菜单新增 */
    @PostMapping("/add")
    @Operation(summary = "后台菜单新增")
    public CommonResult<Boolean> backMenuAdd(@RequestBody @Validated({ActionAdd.class}) BackMenuActionReqVo params) throws Exception {
        backMenuService.insertBackMenu(params);
        return CommonResult.success(true, MsgEnum.ADD_SUCCESS.VALUE);
    }
    /** 后台菜单更新 */
    @PostMapping("/update")
    @Operation(summary = "后台菜单更新")
    public CommonResult<Boolean> backMenuUpdate(@RequestBody @Validated({ActionUpdate.class}) BackMenuActionReqVo params) throws Exception {
        // 验证通过后执行修改操作
        backMenuService.updateBackMenu(params);
        return CommonResult.success(true, MsgEnum.UPDATE_SUCCESS.VALUE);
    }
    /** 后台菜单删除 */
    @PostMapping("/delete")
    @Operation(summary = "后台菜单删除")
    @Parameter(name = "id", description = "后台菜单id", required = true)
    public CommonResult<Boolean> backMenuDelete(@RequestBody Map<String, String> map) throws Exception {
        Long id = Long.valueOf(map.get("id"));
        // 校验数据
        Utils.formCheck(FormValidator.setFormCheck(id, "ID不能为空"));
        // 验证通过后执行删除操作
        backMenuService.deleteBackMenu(id);
        return CommonResult.success(true, MsgEnum.DELETE_SUCCESS.VALUE);
    }
}
