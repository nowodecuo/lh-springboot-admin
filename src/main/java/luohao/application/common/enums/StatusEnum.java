/**
 * 状态枚举
 * @author 1874
 */
package luohao.application.common.enums;
public enum StatusEnum {
    USING("1", "启用"),
    DISABLED("0", "禁用");
    public final String VALUE;
    public final String LABEL;
    StatusEnum(String value, String label) {
        this.VALUE = value;
        this.LABEL = label;
    }
}
