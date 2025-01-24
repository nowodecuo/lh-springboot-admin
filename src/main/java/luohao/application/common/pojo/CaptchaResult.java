/***
 * 验证码响应结果
 * @author 1874
 */
package luohao.application.common.pojo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "验证码 Response")
public class CaptchaResult {
    @Schema(description = "uuid")
    private String uuid;

    @Schema(description = "base64编码")
    private String imageBase64;

    public static CaptchaResult create(String uuid, String imageBase64) {
        CaptchaResult captchaResult = new CaptchaResult();
        captchaResult.setUuid(uuid);
        captchaResult.setImageBase64(imageBase64);
        return captchaResult;
    }
}
