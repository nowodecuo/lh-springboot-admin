/***
 * 分页信息入参
 * @author 1874
 */
package luohao.application.common.pojo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class PageParam {
    @Schema(description = "当前页数", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "当前页数不能为空")
    @Min(value = 1, message = "当前页数不能小于1")
    private Integer pageNum = 1; // 当前页数

    @Schema(description = "每页数量", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "每页数量不能为空")
    @Min(value = 1, message = "每页数量不能小于1")
    private Integer pageSize = 10; // 每页数量
}

