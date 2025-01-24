/**
 * 微信网页授权access_token返回实体
 * @author 1874
 */
package luohao.application.common.wechat.pojo;

import lombok.Data;

@Data
public class OauthAccessToken {
    private String access_token; // 网页授权接口调用凭证

    private String expires_in; // access_token接口调用凭证超时时间 /s

    private String refresh_token; // 用户刷新access_token

    private String openid; // 用户唯一标识

    private String scope; // 用户授权的作用域，使用逗号（,）分隔

    private Integer is_snapshotuser; // 是否为快照页模式虚拟账号，只有当用户是快照页模式虚拟账号时返回，值为1

    private String unionid; // 用户统一标识（针对一个微信开放平台账号下的应用，同一用户的 unionid 是唯一的），只有当scope为"snsapi_userinfo"时返回

    private String errmsg; // 错误信息
}
