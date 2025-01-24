/**
 * 后台登录
 * @author 1874
 */
package luohao.application.controller.backService.login;

import com.alibaba.fastjson.JSONObject;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import luohao.application.common.pojo.CaptchaResult;
import luohao.application.common.pojo.CommonResult;
import luohao.application.common.utils.Captcha;
import luohao.application.common.utils.Utils;
import luohao.application.model.vo.login.LoginReqVO;
import luohao.application.model.vo.login.LoginRespVO;
import luohao.application.service.login.LoginService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/login")
@Tag(name = "管理后台-用户登录")
public class LoginController {
    @Value("${spring.profiles.active}")
    private String env;

    @Value("${user.captcha-check}")
    private Boolean captchaCheck;

    @Value("${sm4-secret}")
    private String sm4Secret; // sm4秘钥
    @Resource
    private LoginService loginService;
    @Resource
    private Captcha captcha;
    /** 获取验证码 */
    @PostMapping("/getVerifyCode")
    @Operation(summary = "获取验证码")
    public CommonResult<CaptchaResult> getVerifyCode() throws Exception {
        CaptchaResult data = captcha.createImageCaptcha();
        return CommonResult.success(data);
    }
    /** 登录验证 */
    @PostMapping("/loginCheck")
    @Operation(summary = "登录验证")
    public CommonResult<String> loginCheck(@RequestBody @Validated LoginReqVO params) throws Exception {
        // 如果开启验证校验，则校验验证码并清除
        if (captchaCheck) captcha.checkImageCaptcha(params.getUuid(), params.getVerifyCode());
        // 查询管理员信息和token的sm4加密字符串
        LoginRespVO adminInfoVO = loginService.handleLoginCheck(params.getAccount(), params.getPassword());
        String adminInfoStr = JSONObject.toJSONString(adminInfoVO); // 转换为json字符串
        String encryptStr = Utils.sm4Encryption(sm4Secret, adminInfoStr); // sm4加密json字符串
        return CommonResult.success(encryptStr);
    }
    /** 登录验证-postman */
    @PostMapping("/loginCheckDev")
    @Operation(summary = "登录验证-dev (开发环境 postman 调试时可调用该接口获取token)")
    public CommonResult<LoginRespVO> loginCheckDev(@RequestBody @Validated LoginReqVO params) throws Exception {
        if (env.equals("dev")) {
            // 查询管理员信息和token的sm4加密字符串
            LoginRespVO adminInfoVO = loginService.handleLoginCheck(params.getAccount(), params.getPassword());
            return CommonResult.success(adminInfoVO);
        }
        throw new Exception("请求地址错误");
    }
    /** 登录退出 */
    @PostMapping("/logout")
    @Operation(summary = "登录退出")
    public CommonResult<Boolean> logout() {
        loginService.handleLogout();
        return CommonResult.success(true, "退出成功");
    }
}
