/***
 * 微信集成服务
 * @author 1874
 */
package luohao.application.common.wechat;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import luohao.application.common.wechat.pojo.OauthAccessToken;
import luohao.application.common.wechat.config.WechatConfig;
import luohao.application.common.wechat.pojo.WechatUserInfo;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

@Component
public class Wechat {
    @Resource
    private WechatConfig wechatConfig; // 微信SDK配置注入
    public enum Method { GET, POST }; // 请求类型枚举
    public enum AppType { APP, APPLET }; // 公众号、小程序标识
    public enum Scope {
        USERINFO("snsapi_userinfo"), // 可以获取更多的用户资料
        BASE("snsapi_base"); // 只能获取access_token和openid
        private String value;
        Scope(String value) {
            this.value = value;
        }
    };
    /***
     * 获取唯一凭证access_token
     */
    public String getAccessToken() throws Exception {
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+wechatConfig.getAppid()+"&secret="+wechatConfig.getAppSecret();
        String res = this.request(url, Method.GET);
        JSONObject jsonObject= JSON.parseObject(res);
        // 获取错误信息
        String errMsg = jsonObject.getString("errmsg");
        // 如果没有错误信息，返回access_token
        if (errMsg == null) {
            return jsonObject.getString("access_token");
        } else {
            throw new Exception(errMsg);
        }
    }
    /***
     * 微信获取code重定向地址
     * @param redirectUrl 获取code重定向跳转的链接
     * @param scope 获取用户信息的类型 [snsapi_userinfo:可以获取更多的用户资料 | snsapi_base:只能获取access_token和openid]
     */
    public String getCodeRedirect(String redirectUrl, Scope scope) {
        String redirect = URLEncoder.encode(redirectUrl);
        return "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+wechatConfig.getAppid()+"&redirect_uri="+redirect+"&response_type=code&scope="+scope.value+"&state=STATE#wechat_redirect";
    }
    /***
     * 获取网页授权access_token
     * @param code 跳转微信获取的code
     * @param appType 公众号 | 小程序 标识
     */
    public OauthAccessToken getOauthAccessToken(String code, AppType appType) throws Exception {
        // 公众号请求地址
        String appUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+wechatConfig.getAppid()+"&secret="+wechatConfig.getAppSecret()+"&code="+code+"&grant_type=authorization_code";
        // 小程序请求地址
        String appletUrl = "https://api.weixin.qq.com/sns/jscode2session?appid="+wechatConfig.getAppid()+"&secret="+wechatConfig.getAppSecret()+"&js_code="+code+"&grant_type=authorization_code";
        // 根据appType判断请求哪个地址
        String url = appType == AppType.APP ? appUrl : appletUrl;
        // 发起请求
        String res = this.request(url, Method.GET);
        // json字符转对象
        JSONObject jsonObject= JSON.parseObject(res);
        // 获取错误信息
        String errMsg = jsonObject.getString("errmsg");
        // 如果没有错误信息，则赋值access_token和openid
        OauthAccessToken oauthAccessToken = new OauthAccessToken();
        if (errMsg == null) {
            oauthAccessToken.setAccess_token(jsonObject.getString("access_token"));
            oauthAccessToken.setOpenid(jsonObject.getString("openid"));
            return oauthAccessToken;
        } else {
            throw new Exception(errMsg);
        }
    }
    /***
     * 使用网页授权access_token获取授权用户信息
     * @param accessToken 网页授权access_token
     * @param openid 微信用户openid
     */
    public WechatUserInfo getOauthUserInfo(String accessToken, String openid) throws Exception {
        String url = "https://api.weixin.qq.com/sns/userinfo?access_token="+accessToken+"&openid="+openid+"&lang=zh_CN";
        String res = this.request(url, Method.GET);
        // json字符转对象
        JSONObject jsonObject= JSON.parseObject(res);
        // 获取错误信息
        String errMsg = jsonObject.getString("errmsg");
        // 如果没有错误信息，则赋值access_token和openid
        WechatUserInfo wechatUserInfo = new WechatUserInfo();
        if (errMsg == null) {
            wechatUserInfo.setOpenid(jsonObject.getString("openid"));
            wechatUserInfo.setNickname(jsonObject.getString("nickname"));
            wechatUserInfo.setSex(Integer.valueOf(jsonObject.getString("sex")));
            wechatUserInfo.setPrivilege(jsonObject.getString("province"));
            wechatUserInfo.setCity(jsonObject.getString("city"));
            wechatUserInfo.setCountry(jsonObject.getString("country"));
            wechatUserInfo.setHeadimgurl(jsonObject.getString("headimgurl"));
            wechatUserInfo.setPrivilege(jsonObject.getString("privilege"));
            wechatUserInfo.setUnionid(jsonObject.getString("unionid"));
            return wechatUserInfo;
        } else {
            throw new Exception(errMsg);
        }
    }
    /***
     * request请求
     * @param url 请求地址
     * @param method 请求方法类型 POST | GET
     */
    private String request(String url, Method method) {
        try {
            URL uri = new URL(url);
            // 打开链接
            HttpURLConnection connection = (HttpURLConnection) uri.openConnection();
            // 设置请求类型 POST | GET
            connection.setRequestMethod(String.valueOf(method));
            // 发送请求
            connection.getResponseCode();
            // 读取响应
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            // 关闭连接
            connection.disconnect();
            // 处理响应
            return response.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
