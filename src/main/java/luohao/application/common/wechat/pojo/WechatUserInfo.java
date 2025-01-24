/***
 * 微信用户信息实体
 * @author 1874
 */
package luohao.application.common.wechat.pojo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "通用-微信方法 获取微信用户信息 Response VO")
public class WechatUserInfo {
    @Schema(description = "用户唯一标识")
    private String openid; // 用户唯一标识

    @Schema(description = "昵称")
    private String nickname; // 昵称

    @Schema(description = "性别 1=男 2=女 0=未知")
    private Integer sex; // 性别 1=男 2=女 0=未知

    @Schema(description = "省")
    private String province; // 省

    @Schema(description = "市")
    private String city; // 市

    @Schema(description = "区")
    private String country; // 区

    @Schema(description = "头像")
    private String headimgurl; // 头像

    @Schema(description = "用户特权信息，json 数组，如微信沃卡用户为（chinaunicom）")
    private String privilege; // 用户特权信息，json 数组，如微信沃卡用户为（chinaunicom）

    @Schema(description = " 用户统一标识")
    private String unionid; // 用户统一标识（针对一个微信开放平台账号下的应用，同一用户的 unionid 是唯一的），只有当scope为"snsapi_userinfo"时返回

    @Schema(description = "错误信息")
    private String errmsg; // 错误信息
}
