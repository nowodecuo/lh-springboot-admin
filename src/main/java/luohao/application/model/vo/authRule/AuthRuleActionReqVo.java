/**
 * 权限新增、编辑入参
 * @author 1874
 */
package luohao.application.model.vo.authRule;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import luohao.application.common.validator.annotation.EqualsValue;
import luohao.application.model.common.ActionUpdate;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Schema(description = "管理后台-权限新增、编辑 Request VO")
public class AuthRuleActionReqVo {
    @Schema(description = "id(更新必填，新增可为空)", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "ID不能为空", groups = ActionUpdate.class)
    @Min(value = 1, message = "ID不能小于1", groups = ActionUpdate.class)
    private Long arId;

    @Schema(description = "父ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "所属上级不能为空")
    private Long arPid;

    @Schema(description = "权限名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "权限名称不能为空")
    private String arName;

    @Schema(description = "方法名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "方法名称不能为空")
    private String arMethod;

    @Schema(description = "状态", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "状态不能为空")
    @EqualsValue(message = "状态只能为1和0", values = {"0", "1"})
    private String arStatus; /** {@link luohao.application.common.enums.StatusEnum} */
}
