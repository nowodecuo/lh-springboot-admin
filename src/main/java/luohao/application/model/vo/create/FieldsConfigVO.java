/**
 * 创建curd 字段列表配置
 * @author 1874
 */
package luohao.application.model.vo.create;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
@Data
@Schema(description = "字段配置")
public class FieldsConfigVO {
    @Schema(description = "字段名", requiredMode = Schema.RequiredMode.REQUIRED)
    private String field; // 字段名

    @Schema(description = "组件类型", requiredMode = Schema.RequiredMode.REQUIRED)
    private String componentType; // 组件类型

    @Schema(description = "字段键", requiredMode = Schema.RequiredMode.REQUIRED)
    private String key; // 字段键， PRI = 主键

    @Schema(description = "备注", requiredMode = Schema.RequiredMode.REQUIRED)
    private String comment = "ID"; // 备注
}
