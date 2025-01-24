/**
 * 创建CURD 表详情模型
 * @author 1874
 */
package luohao.application.model.dataobject.create;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "管理后台-创建CURD 表详情 Response DO")
public class TableInfoDO {
    @Schema(description = "字段名")
    private String field; // 字段名

    @Schema(description = "类型")
    private String type; // 类型

    @Schema(description = "键类型")
    private String key; // 键类型

    @Schema(description = "备注")
    private String comment; // 备注
}
