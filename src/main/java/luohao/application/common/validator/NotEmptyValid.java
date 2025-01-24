/**
 * 校验值不能为空 实现类
 * @author 1874
 */
package luohao.application.common.validator;

import luohao.application.common.validator.annotation.NotEmpty;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NotEmptyValid implements ConstraintValidator<NotEmpty, Object> {
    @Override
    public void initialize(NotEmpty constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        return value != null && !value.equals("");
    }
}
