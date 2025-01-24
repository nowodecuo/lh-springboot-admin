/**
 * 日志标识注解
 * @author 1874
 */
package luohao.application.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME) // 运行时生效
@Target(ElementType.METHOD) // 作用目标在方法上
public @interface LogAnnotation {
    String title(); // 执行的标题
    boolean writeParam() default true; // 是否写入参数
}
