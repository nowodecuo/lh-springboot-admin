/**
 * 管理员列表入参
 * @author 1874
 */
package luohao.application.model.vo.admin;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import luohao.application.common.pojo.PageParam;

@Data
@Schema(description = "管理后台-管理员管理分页 Request VO")
public class AdminListReqVO extends PageParam {
    @Schema(description = "姓名", example = "张三")
    private String adName; // 姓名

    @Schema(description = "角色id", example = "1")
    private Long adRoleId; // 角色id

    @Schema(description = "城市", example = "四川省泸州市江阳区")
    private String adCity; // 城市

    @Schema(description = "手机", example = "13551778899")
    private String adPhone; // 手机

    @Schema(description = "状态", example = "1")
    private String adStatus; /** {@link luohao.application.common.enums.StatusEnum} */
}
