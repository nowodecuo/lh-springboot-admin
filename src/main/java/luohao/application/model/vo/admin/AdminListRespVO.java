/***
 * 管理员列表返参
 * @author 1874
 */
package luohao.application.model.vo.admin;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "管理后台-管理员管理分页 Response VO")
public class AdminListRespVO {
    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long adId;

    @Schema(description = "姓名", requiredMode = Schema.RequiredMode.REQUIRED)
    private String adName; // 姓名

    @Schema(description = "角色id", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long adRoleId; // 角色ID

    @Schema(description = "头像", requiredMode = Schema.RequiredMode.REQUIRED)
    private String adHeaderImg; // 头像

    @Schema(description = "账号", requiredMode = Schema.RequiredMode.REQUIRED)
    private String adAccount; // 账号

    @Schema(description = "密码", requiredMode = Schema.RequiredMode.REQUIRED)
    private String adPassword; // 密码

    @Schema(description = "城市", requiredMode = Schema.RequiredMode.REQUIRED)
    private String adCity; // 城市

    @Schema(description = "手机号", requiredMode = Schema.RequiredMode.REQUIRED)
    private String adPhone; // 手机号

    @Schema(description = "状态", requiredMode = Schema.RequiredMode.REQUIRED)
    private String adStatus; /** {@link luohao.application.common.enums.StatusEnum} */

    @Schema(description = "角色id", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long roId; // 角色id

    @Schema(description = "角色名", requiredMode = Schema.RequiredMode.REQUIRED)
    private String roName; // 角色名
}
