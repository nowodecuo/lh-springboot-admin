/**
 * APP端统一拦截器
 * @author 1874
 */
package luohao.application.handler;

import luohao.application.common.enums.MsgEnum;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class InterceptorAppHandler implements HandlerInterceptor {
    /**
     * 目标资源方法运行前执行
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 404 拦截
        if (response.getStatus() == HttpServletResponse.SC_NOT_FOUND) {
            throw new NotFoundException(MsgEnum.NOT_FOUND.VALUE);
        }
        // 500 拦截
        if (response.getStatus() == HttpServletResponse.SC_INTERNAL_SERVER_ERROR) {
            throw new Exception(MsgEnum.SYSTEM_ERROR.VALUE);
        }
        return true;
    }
    /**
     * 目标资源方法运行后执行
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }
    /**
     * 视图渲染完毕最后运行
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

    }
}
