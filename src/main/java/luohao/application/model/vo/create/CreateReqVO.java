/**
 * 创建curd入参
 * @author 1874
 */
package luohao.application.model.vo.create;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@Schema(description = "管理后台-创建CURD Request VO")
public class CreateReqVO {
    @Schema(description = "表名", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "表名不能为空")
    private String tableName;

    @Schema(description = "中文类名", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "中文类名不能为空")
    private String cnClassName;

    @Schema(description = "类名前缀", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "类名前缀不能为空")
    private String className;

    @Schema(description = "方法名前缀", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "方法名前缀不能为空")
    private String methodName;

    @Schema(description = "创建类型(APP|BACK)", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "创建类型")
    private String createType;

    @Schema(description = "创建功能", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "创建功能不能为空")
    private List<String> functions; // list | add | update | delete | batchDelete

    @Schema(description = "字段配置", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "字段配置不能为空")
    private List<FieldsConfigVO> fieldsConfig;
}
