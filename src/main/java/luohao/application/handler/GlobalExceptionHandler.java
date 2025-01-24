/**
 * 统一异常处理
 * @author 1874
 */
package luohao.application.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import luohao.application.common.pojo.CommonResult;
import luohao.application.common.enums.MsgEnum;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.dao.DataAccessException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;
import java.util.StringJoiner;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({ ConstraintViolationException.class, MethodArgumentNotValidException.class, NotFoundException.class, Exception.class }) // 指定获取错误类型
    public CommonResult<Object> resolveViolationException(Exception ex) {
        log.error("捕获异常信息：{}",ex.getMessage());
        // 错误消息
        StringJoiner messages = new StringJoiner(",");
        // hibernate-validator 校验失败异常错误处理
        if (ex instanceof ConstraintViolationException){
            Set<ConstraintViolation<?>> violations = ((ConstraintViolationException) ex).getConstraintViolations();
            for (ConstraintViolation<?> violation : violations) {
                messages.add(violation.getMessage());
            }
        } else if (ex instanceof MethodArgumentNotValidException) {
            List<ObjectError> allErrors = ((MethodArgumentNotValidException) ex).getBindingResult().getAllErrors();
            for (ObjectError error : allErrors) {
                messages.add(error.getDefaultMessage());
            }
        // 404 not found异常
        } else if (ex instanceof NotFoundException) {
            return CommonResult.error(ex.getMessage());
        // 数据库异常处理
        } else if (ex instanceof DataAccessException) {
            return CommonResult.error(MsgEnum.DATABASE_ERROR.VALUE);
        // 其他Exception异常处理
        } else {
            // 如果错误信息为json字符串，则返回自定义code、msg的json错误信息
            if (JSON.isValid(ex.getMessage())) {
                JSONObject jsonObject = JSONObject.parseObject(ex.getMessage());
                String code = jsonObject.getString("code");
                String msg = jsonObject.getString("msg");
                return CommonResult.error(msg, Integer.parseInt(code));
            }
            return CommonResult.error(ex.getMessage());
        }
        return CommonResult.error(messages.toString());
    }
}
