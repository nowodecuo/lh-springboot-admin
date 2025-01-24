/**
 * 提示消息枚举
 * @author 1874
 */
package luohao.application.common.enums;

public enum MsgEnum {
    NOT_LOGIN_CODE("您还未登录，请前往登录"), // 未登录提示
    EXPIRED_LOGIN("登录已过期，请重新登录"), // 登录过期提示
    LOGIN_CHECK_ERR("账户或密码错误"),
    VERIFY_CODE_ERR("验证码错误"),
    VERIFY_CODE_EXPIRE("验证码已过期，请重新获取"),
    ACCOUNT_EXISTS("该账号已存在"),
    USER_EXTRUSION("您已在其他设备登录"),
    ADMIN_DISABLED("该管理账户已被禁用，请联系平台管理员"), // 管理员被禁用提示
    ADMIN_NO_SUBORDINATE("未找到管理员或该管理员不是您的下级，无法操作"),
    ROLE_NO_SUBORDINATE("未找到角色或该角色不是您的下级，无法操作"),
    ROLE_ID_EQUALS("上级角色不能和本身一致"),
    ROLE_ID_SUBORDINATE("上级角色不能指定为自己的下级"),
    ROLE_IS_SUBORDINATE("该角色还有下级角色，无法操作"),
    ADMIN_IS_SUBORDINATE("该角色还有所属管理员"),
    BACK_MENU_ID_EQUALS("上级菜单不能和本身一致"),
    BACK_MENU_NOT_RULE("您未拥有该菜单权限，无法操作"),
    BACK_MENU_IS_SUBORDINATE("该菜单还有下级菜单，无法操作"),
    ROLE_NOT_RULE("您未拥有该权限，无法操作"),
    AUTH_RULE_CHECK_ERROR("抱歉！您暂无权限访问该方法"),
    PASSWORD_NOT_MYSELF("无法修改非本人密码"),
    REDIS_ERROR("redis缓存异常"),
    IMAGE_CAPTCHA_ERROR("图形验证码创建异常"),
    TABLE_NOT_EXISTS("数据表不存在"),
    DATABASE_ERROR("数据信息异常"),
    NOT_FOUND("未找到请求资源"),
    SYSTEM_ERROR("系统信息异常"),
    ADD_SUCCESS("新增成功"),
    ADD_FAIL("新增失败"),
    CREATE_SUCCESS("创建成功"),
    CREATE_FAIL("创建失败"),
    UPDATE_SUCCESS("更新成功"),
    UPDATE_FAIL("更新失败"),
    DELETE_SUCCESS("删除成功"),
    DELETE_FAIL("删除失败");

    public final String VALUE;
    MsgEnum(String value) {
        this.VALUE = value;
    }
}
