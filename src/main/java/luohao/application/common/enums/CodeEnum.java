/**
 * request返回code枚举
 * @author 1874
 */
package luohao.application.common.enums;

public enum CodeEnum {
    SUCCESS(200, "请求成功"),
    FAIL(500, "请求失败"),
    NOT_LOGIN_CODE(1001, "未登录"),
    EXPIRED_LOGIN(1002, "登录过期"),
    USER_EXTRUSION(1003, "其他设备登录");

    public final Integer VALUE;
    public final String LABEL;
    CodeEnum(Integer value, String label) {
        this.VALUE = value;
        this.LABEL = label;
    }
}
