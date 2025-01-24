/**
 * 返参成功、失败标识枚举
 * @author 1874
 */
package luohao.application.common.enums;

public enum ResEnum {
    SUCCESS("success"),
    FAIL("fail");

    public final String VALUE;
    ResEnum(String value) {
        this.VALUE = value;
    }
}
