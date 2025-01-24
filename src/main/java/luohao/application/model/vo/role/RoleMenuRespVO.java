/**
 * 角色菜单、权限返参
 * @author 1874
 */
package luohao.application.model.vo.role;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import luohao.application.model.dataobject.authRule.AuthRuleDO;
import luohao.application.model.dataobject.backMenu.BackMenuDO;

import java.util.List;

@Data
@Schema(description = "管理后台-角色菜单、权限 Response VO")
public class RoleMenuRespVO {
    @Schema(description = "权限列表")
    private List<AuthRuleDO> authRuleList;

    @Schema(description = "菜单列表")
    private List<BackMenuDO> backMenuList;
}
