/**
 * 校验值相等
 * @author 1874
 */
package luohao.application.common.validator.annotation;

import luohao.application.common.validator.EqualsValueValid;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = EqualsValueValid.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD})
public @interface EqualsValue {
    String message() default "";
    String[] values() default {};
    Class<?> [] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
