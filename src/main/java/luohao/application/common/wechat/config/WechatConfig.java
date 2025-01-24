/**
 * 微信配置
 * @author 1874
 *
 * ConfigurationProperties读取yml配置中wechat，相同字段自动映射，服务注入IOC容器后生效
 * application.yml 配置如下：
 * wechat:
 *   appid: xxxx
 *   appSecret: xxxx
 */
package luohao.application.common.wechat.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "wechat")
@Data
public class WechatConfig {
    private String appid; // 公众号应用ID
    private String appSecret; // 公众号应用秘钥
    private String token; // 开发配置token
    private String mchId; // 商户号ID
    private String mchApiKey; // 商户号api秘钥
    private String apiClientKey; // 商户apiclient_key证书路径
    private String apiClientCert; // 商户apiclient_cert证书路径
}
