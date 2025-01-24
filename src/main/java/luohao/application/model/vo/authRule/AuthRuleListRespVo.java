/**
 * 权限列表返参
 * @author 1874
 */
package luohao.application.model.vo.authRule;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
@Data
@Schema(description = "管理后台-权限列表 Response VO")
public class AuthRuleListRespVo {
    @Schema(description = "id")
    private Long arId;

    @Schema(description = "父ID")
    private Long arPid;

    @Schema(description = "权限名")
    private String arName;

    @Schema(description = "方法名")
    private String arMethod;

    @Schema(description = "状态")
    private String arStatus; /** {@link luohao.application.common.enums.StatusEnum} */
}
