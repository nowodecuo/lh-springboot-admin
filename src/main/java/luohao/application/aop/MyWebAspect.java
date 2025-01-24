/**
 * AOP类
 * @author 1874
 */
package luohao.application.aop;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import luohao.application.annotation.LogAnnotation;
import luohao.application.service.log.LogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;

@Slf4j
@Component
@Aspect // aop注解
public class MyWebAspect {
    @Resource
    private LogService logService;
    /**
     * 记录成功操作日志
     */
    @AfterReturning(pointcut = "@annotation(logAnnot)")
    public void recordSuccessLog(JoinPoint joinPoint, LogAnnotation logAnnot) {
        this.recordLog(joinPoint, logAnnot, null);
    }
    /**
     * 记录失败操作日志
     */
    @AfterThrowing(pointcut = "@annotation(logAnnot)", throwing = "error")
    public void recordErrorLog(JoinPoint joinPoint, LogAnnotation logAnnot, Exception error) {
        this.recordLog(joinPoint, logAnnot, error);
    }
    /**
     * 记录操作日志方法
     */
    private void recordLog(JoinPoint joinPoint, LogAnnotation logAnnot, Exception error) {
        String params = null;
        String title = logAnnot.title();
        String runResult = error == null ? "1" : "0"; // 运行结果(1成功0失败)
        String method = joinPoint.getSignature().getName(); // 获取目标方法名
        String season = error != null ? error.getMessage() : null; // 原因
        log.info("开始日志：{}", title);
        // logAnnot.writeParam = true 表示写入参数，反之不写入
        if (logAnnot.writeParam()) {
            Object[] objects = joinPoint.getArgs(); // 获取参数
            params = JSONObject.toJSONString(objects); // 参数转为json字符串
            if (params.isEmpty()) params = Arrays.toString(objects); // json转换失败转为字符串
        }
        logService.insertLog(title, method, params, runResult, season); // 写入数据库
        log.info("结束日志：{}", title);
    }
}
