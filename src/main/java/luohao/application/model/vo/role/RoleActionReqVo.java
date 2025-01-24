/**
 * 角色新增、编辑入参
 * @author 1874
 */
package luohao.application.model.vo.role;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import luohao.application.common.validator.annotation.EqualsValue;
import luohao.application.model.common.ActionAdd;
import luohao.application.model.common.ActionUpdate;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Schema(description = "管理后台-角色管理新增、修改 Request VO")
public class RoleActionReqVo {
    @Schema(description = "id(更新必填，新增可为空)", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "ID不能为空", groups = ActionUpdate.class)
    @Min(value = 1, message = "ID不能小于1", groups = ActionUpdate.class)
    private Long roId;

    @Schema(description = "角色名", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "角色名不能为空")
    private String roName; // 角色名

    @Schema(description = "上级ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "上级ID不能为空", groups = ActionAdd.class)
    private String roPids; // 父id

    @Schema(description = "状态", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "状态不能为空")
    @EqualsValue(message = "状态只能为1和0", values = {"0", "1"})
    private String roStatus; // 状态(1启用0禁用)
}
