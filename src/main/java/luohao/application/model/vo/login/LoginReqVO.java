/**
 * 登录入参
 * @author 1874
 */
package luohao.application.model.vo.login;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import luohao.application.common.utils.Config;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@Schema(description = "管理后台-用户登录 Request VO")
public class LoginReqVO {
    @Schema(description = "账号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "账号不能为空")
    @Pattern(regexp = Config.ACCOUNT_PATTERN, message = Config.ACCOUNT_ERROR_MSG)
    private String account; // 账号

    @Schema(description = "密码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "密码不能为空")
    private String password; // 密码

    @Schema(description = "验证码 (loginCheckDev 接口可为空)", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "验证码不能为空")
    private String verifyCode; // 验证码

    @Schema(description = "验证码标识uuid (loginCheckDev 接口可为空)", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "验证码标识uuid不能为空")
    private String uuid; // 验证码
}
