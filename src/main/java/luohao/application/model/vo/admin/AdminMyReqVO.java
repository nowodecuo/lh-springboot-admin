/**
 * 管理员个人设置入参
 * @author 1874
 */
package luohao.application.model.vo.admin;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import luohao.application.common.utils.Config;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@Schema(description = "管理后台-管理员个人设置 Request VO")
public class AdminMyReqVO {
    @Schema(description = "姓名", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "姓名不能为空")
    private String adName;

    @Schema(description = "账号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "账号不能为空")
    @Pattern(regexp = Config.ACCOUNT_PATTERN, message = Config.ACCOUNT_ERROR_MSG)
    private String adAccount;

    @Schema(description = "城市", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "城市不能为空")
    private String adCity;

    @Schema(description = "手机", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "手机号不能为空")
    private String adPhone;

    @Schema(description = "头像地址")
    private String adHeaderImg; // 头像
}
