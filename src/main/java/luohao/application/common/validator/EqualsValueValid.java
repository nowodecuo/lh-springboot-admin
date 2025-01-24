/**
 * 校验值相等 实现类
 * @author 1874
 */
package luohao.application.common.validator;

import luohao.application.common.validator.annotation.EqualsValue;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class EqualsValueValid implements ConstraintValidator<EqualsValue, Object> {
    private String[] valueArray = {};
    @Override
    public void initialize(EqualsValue constraintAnnotation) {
        this.valueArray = constraintAnnotation.values();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        return Arrays.asList(this.valueArray).contains(value);
    }
}
