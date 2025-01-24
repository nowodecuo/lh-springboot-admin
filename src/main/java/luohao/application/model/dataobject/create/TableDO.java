/**
 * 创建CURD 表模型
 * @author 1874
 */
package luohao.application.model.dataobject.create;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "管理后台-创建CURD 数据表列表 Response DO")
public class TableDO {
    @Schema(description = "表名")
    private String name; // 表名

    @Schema(description = "存储引擎")
    private String engine; // 存储引擎

    @Schema(description = "数据量")
    private String rows; // 数据量

    @Schema(description = "数据大小")
    private String dataLength; // 数据大小

    @Schema(description = "创建时间")
    private String createTime; // 创建时间

    @Schema(description = "备注")
    private String comment; // 备注
}
