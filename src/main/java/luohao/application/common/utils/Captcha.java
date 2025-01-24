/**
 * 图形验证码
 * @author 1874
 */
package luohao.application.common.utils;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import luohao.application.common.enums.MsgEnum;
import luohao.application.common.pojo.CaptchaResult;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.UUID;

@Component
public class Captcha {
    @Value("${user.captcha-prefix}")
    private String captchaPrefix; // 验证码缓存前缀

    @Value("${user.captcha-expire}")
    private Integer captchaExpire; // 验证码过期时间
    @Resource
    private DefaultKaptcha defaultKaptcha;
    @Resource
    private RedisUtil redisUtil;
    /** 创建图形验证码 */
    public CaptchaResult createImageCaptcha() throws Exception {
        String base64Code = ""; // 图形验证码base64字符
        String captchaText = defaultKaptcha.createText(); // 验证码值
        BufferedImage captchaImg = defaultKaptcha.createImage(captchaText);
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()){
            ImageIO.write(captchaImg, "jpg", outputStream);
            base64Code = Base64.encodeBase64String(outputStream.toByteArray());
        } catch (Exception e) {
            throw new Exception(MsgEnum.IMAGE_CAPTCHA_ERROR.VALUE);
        }
        try {
            String uuid =  UUID.randomUUID().toString().replace("-", ""); // 生成uuid作为缓存key
            redisUtil.set(captchaPrefix+uuid, captchaText, captchaExpire); // 将uuid作为缓存key，存储验证码值
            return CaptchaResult.create(uuid, base64Code);
        } catch (Exception e) {
            throw new Exception(MsgEnum.REDIS_ERROR.VALUE);
        }
    }
    /** 图形验证码校验 */
    public void checkImageCaptcha(String uuid, String code) throws Exception {
        String key = captchaPrefix + uuid;
        String captcha;
        try {
            captcha = (String) redisUtil.get(key);
        } catch (Exception e) {
            throw new Exception(MsgEnum.REDIS_ERROR.VALUE);
        }
        // 验证码是否过期
        if (captcha == null) {
            throw new Exception(MsgEnum.VERIFY_CODE_EXPIRE.VALUE);
        }
        // 清除验证码缓存
        redisUtil.delete(key);
        // 校验验证码
        if (!captcha.equals(code)) {
            throw new Exception(MsgEnum.VERIFY_CODE_ERR.VALUE);
        }
    }
}
