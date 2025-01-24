/**
 * 权限列表入参
 * @author 1874
 */
package luohao.application.model.vo.authRule;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import luohao.application.common.pojo.PageParam;

@Data
@Schema(description = "管理后台-权限列表 Request VO")
public class AuthRuleListReqVo {
    @Schema(description = "权限名")
    private String arName; // 权限名

    @Schema(description = "方法名")
    private String arMethod; // 方法名

    @Schema(description = "状态")
    private String arStatus; /** {@link luohao.application.common.enums.StatusEnum} */
}
