/**
 * 后台菜单列表返参
 * @author 1874
 */
package luohao.application.model.vo.backMenu;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "管理后台-后台菜单列表 Response VO")
public class BackMenuListRespVo {
    @Schema(description = "id")
    private Long bmId;

    @Schema(description = "父id", example = "1,2,3")
    private String bmPids;

    @Schema(description = "标题")
    private String bmTitle;

    @Schema(description = "图标")
    private String bmIcon;

    @Schema(description = "访问地址")
    private String bmPath;

    @Schema(description = "组件地址")
    private String bmComponent;

    @Schema(description = "排序")
    private Integer bmSort;

    @Schema(description = "状态")
    private String bmStatus; /** {@link luohao.application.common.enums.StatusEnum} */
}
