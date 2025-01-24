/**
 * 登录成功返参
 * @author 1874
 */
package luohao.application.model.vo.login;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "管理后台-用户登录 Response VO")
public class LoginRespVO {
    @Schema(description = "id")
    private Long id; // id

    @Schema(description = "姓名")
    private String name; // 姓名

    @Schema(description = "账号")
    private String account; // 账号

    @Schema(description = "头像")
    private String headerImg; // 头像

    @Schema(description = "角色id")
    private Long roleId; // 角色id

    @Schema(description = "角色名")
    private String roleName; // 角色名

    @Schema(description = "token")
    private String token;
}
