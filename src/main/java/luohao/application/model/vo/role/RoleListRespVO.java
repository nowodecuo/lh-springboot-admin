/**
 * 角色列表返参
 * @author 1874
 */
package luohao.application.model.vo.role;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "管理后台-角色管理列表 Response VO")
public class RoleListRespVO {
    @Schema(description = "id")
    private Long roId;

    @Schema(description = "角色名")
    private String roName; // 角色名

    @Schema(description = "父id")
    private String roPids; // 父id

    @Schema(description = "权限规则ids")
    private String roRuleIds; // 权限规则ids

    @Schema(description = "后台菜单ids")
    private String roBackMenuIds; // 后台菜单ids

    @Schema(description = "状态")
    private String roStatus; /** {@link luohao.application.common.enums.StatusEnum} */
}
