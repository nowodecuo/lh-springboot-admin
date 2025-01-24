/***
 * 统一响应结果
 * @author 1874
 */
package luohao.application.common.pojo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import luohao.application.common.enums.CodeEnum;

@Data
public class CommonResult<T> {
    @Schema(description = "错误码", requiredMode = Schema.RequiredMode.REQUIRED, example = "200")
    private Integer code; // 错误码

    @Schema(description = "提示信息", requiredMode = Schema.RequiredMode.REQUIRED, example = "success")
    private String msg; // 提示信息

    @Schema(description = "数据信息", requiredMode = Schema.RequiredMode.REQUIRED)
    private T data; // 数据

    public CommonResult(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
    public static <T> CommonResult<T> success(T data) {
        return new CommonResult<>(CodeEnum.SUCCESS.VALUE, "success", data);
    }
    public static <T> CommonResult<T> success(T data, String msg) {
        return new CommonResult<>(CodeEnum.SUCCESS.VALUE, msg, data);
    }
    public static <T> CommonResult<T> error(String msg) {
        return new CommonResult<>(CodeEnum.FAIL.VALUE, msg, null);
    }
    public static <T> CommonResult<T> error(String msg, Integer code) {
        return new CommonResult<>(code, msg, null);
    }
}

