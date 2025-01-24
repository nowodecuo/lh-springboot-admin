package luohao.application.common.pojo;
import lombok.Data;

import java.util.function.Function;

@Data
public class FormValidator {
    private Object value; // 值

    private String message; // 提示信息

    private String pattern; // 正则

    private Function<String, String> function; // 自定义函数方法
    /** 设置表单校验所需数据，默认为空校验 */
    public static FormValidator setFormCheck(Object value, String message) {
        FormValidator formValidator = new FormValidator();
        formValidator.setValue(value);
        formValidator.setMessage(message);
        return formValidator;
    }
    /** 重载设置表单校验所需数据，正则校验 */
    public static FormValidator setFormCheck(Object value, String pattern, String message) {
        FormValidator formValidator = setFormCheck(value, message);
        formValidator.setPattern(pattern);
        return formValidator;
    }
    /** 设置表单校验所需数据，自定义函数方法校验 */
    public static FormValidator setFormFuncCheck(Object value, Function<String, String> function) {
        FormValidator formValidator = new FormValidator();
        formValidator.setValue(value);
        formValidator.setFunction(function);
        return formValidator;
    }
}
