/**
 * 微信集成处理
 * @author 1874
 */
package luohao.application.controller.appService.wechat;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import luohao.application.common.pojo.CommonResult;
import luohao.application.common.wechat.pojo.OauthAccessToken;
import luohao.application.common.wechat.pojo.WechatUserInfo;
import luohao.application.common.wechat.Wechat;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/wechat")
@Tag(name = "通用-微信方法")
public class WechatController {
    /** 微信服务依赖注入 */
    @Resource
    private Wechat wechat;
    /**
     * 跳转微信获取code并重定向地址
     * @param redirectUrl 重定向地址
     */
    @GetMapping("getCodeRedirect")
    @Operation(summary = "跳转微信获取code并重定向地址")
    @Parameter(name = "redirectUrl", description = "重定向地址")
    public void getCodeRedirect(HttpServletResponse response, @RequestParam String redirectUrl) throws IOException {
        // 微信获取code重定向地址
        String url = wechat.getCodeRedirect(redirectUrl, Wechat.Scope.USERINFO);
        // 重定向跳转
        response.sendRedirect(url);
    }
    /**
     * 获取微信用户信息
     * @param code 从微信网页授权获取的code
     */
    @GetMapping("/getWechatUserInfo")
    @Operation(summary = "获取微信用户信息")
    @Parameter(name = "code", description = "微信code")
    public CommonResult<WechatUserInfo> getWechatUserInfo(@RequestParam String code) throws Exception {
        if (code == null || code.equals("")) {
            throw new Exception("未接收到code");
        }
        // 获取网页授权access_token
        OauthAccessToken accessToken = wechat.getOauthAccessToken(code, Wechat.AppType.APP);
        // 使用网页授权access_token获取授权用户信息
        WechatUserInfo wechatUserInfo = wechat.getOauthUserInfo(accessToken.getAccess_token(), accessToken.getOpenid());
        return CommonResult.success(wechatUserInfo);
    }
}
