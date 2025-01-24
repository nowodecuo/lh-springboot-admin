/***
 * 分页信息返参
 * @author 1874
 */
package luohao.application.common.pojo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class PageResult<T> {
    @Schema(description = "当前页数", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer pageNum = 1; // 当前页数

    @Schema(description = "每页数量", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer pageSize = 10; // 每页数量

    @Schema(description = "总数", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long total = 0L; // 总数

    @Schema(description = "列表数据", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<T> list; // 列表数据
    /** 创建page */
    public static <T> PageResult<T> createPageRes(List<T> list, Long total) {
        PageResult<T> pageResult = new PageResult<>();
        pageResult.setList(list);
        pageResult.setTotal(total);
        return pageResult;
    }
}

