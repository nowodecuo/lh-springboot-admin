/**
 * 统一过滤器
 * @author 1874
 */
package luohao.application.handler;

import com.alibaba.fastjson.JSONObject;
import luohao.application.common.utils.Utils;
import luohao.application.common.pojo.CommonResult;
import luohao.application.common.enums.CodeEnum;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@WebFilter(urlPatterns = "/*")
public class GlobalFilterHandler implements Filter {
    @Value("${jwt-secret}")
    private String jwtSecret; // jwt秘钥
    /** 拦截方法，每次请求拦截调用 */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("content-type", "application/json;charset=UTF-8");
        // 获取url, 如果是登录控制器中的请求则放行
        String url = httpServletRequest.getRequestURI();
        if (url.contains("/login")) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }
        // 判断是否接收到token
        String token = httpServletRequest.getHeader("admin-token");
        if (token == null || token.length() == 0) {
            CommonResult<Object> res = CommonResult.error("您还未登录，请前往登录", CodeEnum.NOT_LOGIN_CODE.VALUE);
            String jsonStr = JSONObject.toJSONString(res);
            httpServletResponse.getWriter().write(jsonStr);
            return;
        }
        // 解析token，失败则返回错误信息
        try {
            Utils.decryptJwt(token, jwtSecret);
        } catch (Exception e) {
            CommonResult<Object> res = CommonResult.error("登录已过期，请重新登录", CodeEnum.EXPIRED_LOGIN.VALUE);
            String jsonStr = JSONObject.toJSONString(res);
            httpServletResponse.getWriter().write(jsonStr);
            return;
        }
        // 执行放行
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
