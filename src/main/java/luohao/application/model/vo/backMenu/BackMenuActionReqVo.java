/**
 * 后台菜单新增、编辑入参
 * @author 1874
 */
package luohao.application.model.vo.backMenu;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import luohao.application.common.validator.annotation.EqualsValue;
import luohao.application.model.common.ActionAdd;
import luohao.application.model.common.ActionUpdate;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Schema(description = "管理后台-后台菜单新增、编辑 Request VO")
public class BackMenuActionReqVo {
    @Schema(description = "id(更新必填，新增可为空)", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "ID不能为空", groups = ActionUpdate.class)
    @Min(value = 1, message = "ID不能小于1", groups = ActionUpdate.class)
    private Long bmId;

    @Schema(description = "父id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "父ID不能为空", groups = ActionAdd.class)
    private String bmPids;

    @Schema(description = "标题", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "标题不能为空")
    private String bmTitle;

    @Schema(description = "图标")
    private String bmIcon; // 图标

    @Schema(description = "访问地址")
    private String bmPath; // 访问地址

    @Schema(description = "组件地址")
    private String bmComponent; // 组件地址

    @Schema(description = "排序", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "排序不能为空")
    private Integer bmSort;

    @Schema(description = "状态", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "状态不能为空")
    @EqualsValue(message = "状态只能为1和0", values = {"0", "1"})
    private String bmStatus;
}
