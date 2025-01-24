/**
 * 管理员新增、编辑入参
 * @author 1874
 */
package luohao.application.model.vo.admin;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import luohao.application.common.utils.Config;
import luohao.application.common.validator.annotation.EqualsValue;
import luohao.application.model.common.ActionAdd;
import luohao.application.model.common.ActionUpdate;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@Schema(description = "管理后台-管理员管理新增、修改 Request VO")
public class AdminActionReqVO {
    @Schema(description = "id(更新必填，新增可为空)", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "ID不能为空", groups = ActionUpdate.class)
    @Min(value = 1, message = "ID不能小于1", groups = ActionUpdate.class)
    private Long adId;

    @Schema(description = "姓名", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "姓名不能为空")
    private String adName;

    @Schema(description = "角色id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "角色ID不能为空")
    private Long adRoleId;

    @Schema(description = "账号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "账号不能为空", groups = ActionAdd.class)
    @Pattern(regexp = Config.ACCOUNT_PATTERN, message = Config.ACCOUNT_ERROR_MSG, groups = ActionAdd.class)
    private String adAccount;

    @Schema(description = "密码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "密码不能为空", groups = ActionAdd.class)
    @Pattern(regexp = Config.PASSWORD_PATTERN, message = Config.PASSWORD_ERROR_MSG)
    private String adPassword;

    @Schema(description = "城市", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "城市不能为空")
    private String adCity;

    @Schema(description = "手机号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "手机号不能为空")
    private String adPhone;

    /** {@link luohao.application.common.enums.StatusEnum} */
    @Schema(description = "状态", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "状态不能为空")
    @EqualsValue(message = "状态只能为1和0", values = {"0", "1"})
    private String adStatus;
}
