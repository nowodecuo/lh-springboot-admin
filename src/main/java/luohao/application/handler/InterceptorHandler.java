/**
 * 统一拦截器
 * @author 1874
 */
package luohao.application.handler;

import com.alibaba.fastjson.JSONObject;
import luohao.application.common.enums.MsgEnum;
import luohao.application.common.utils.RuleUtil;
import luohao.application.common.utils.Utils;
import luohao.application.common.pojo.CommonResult;
import luohao.application.common.enums.CodeEnum;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Component
public class InterceptorHandler implements HandlerInterceptor {
    @Value("${jwt-secret}")
    private String jwtSecret; // jwt秘钥

    @Value("${sm4-secret}")
    private String sm4Secret; // sm4秘钥

    @Value("${auth-rule.open}")
    private Boolean authRuleOpen; // 开启授权规则校验
    @Resource
    private RuleUtil ruleUtil;
    @Resource
    private UserInfoHandler userInfoHandler;
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
        // 判断是否接收到token
        String sm4Token = request.getHeader(userInfoHandler.ADMIN_TOKEN);
        if (sm4Token == null || sm4Token.length() == 0) {
            userInfoHandler.deleteUserInfoMap();
            CommonResult<Object> res = CommonResult.error(MsgEnum.NOT_LOGIN_CODE.VALUE, CodeEnum.NOT_LOGIN_CODE.VALUE);
            String jsonStr = JSONObject.toJSONString(res);
            throw new Exception(jsonStr);
        }
        // 解析token，失败则返回错误信息
        Map<String, Object> adminInfoMap;
        try {
            String token = Utils.sm4Decryption(sm4Secret,sm4Token); // sm4解密
            adminInfoMap = Utils.decryptJwt(token, jwtSecret); // 解析token
        } catch (Exception e) {
            userInfoHandler.deleteUserInfoMap();
            CommonResult<Object> res = CommonResult.error(MsgEnum.EXPIRED_LOGIN.VALUE, CodeEnum.EXPIRED_LOGIN.VALUE);
            String jsonStr = JSONObject.toJSONString(res);
            throw new Exception(jsonStr);
        }
        // 检查是否其他设备登录
        Long adminId = Long.valueOf(adminInfoMap.get(userInfoHandler.ID).toString()); // 管理员id
        if (!userInfoHandler.checkUserExtrusion(sm4Token, adminId)) {
            userInfoHandler.deleteUserInfoMap();
            CommonResult<Object> res = CommonResult.error(MsgEnum.USER_EXTRUSION.VALUE, CodeEnum.USER_EXTRUSION.VALUE);
            String jsonStr = JSONObject.toJSONString(res);
            throw new Exception(jsonStr);
        }
        // 如果开启规则校验，则验证登录管理员拥有的规则
        if (authRuleOpen) {
            Long roleId = Long.valueOf(adminInfoMap.get(userInfoHandler.ROLE_ID).toString()); // 角色id
            // 如果不是超级管理员则进行校验，超级管理员直接放行
            if (!roleId.equals(userInfoHandler.SUPER_ADMIN_ROLE_ID)) {
                String method = ((HandlerMethod) handler).getMethod().getName(); // 访问的方法名称
                // 校验管理员是否有该方法权限
                if(!ruleUtil.checkUserRule(method, adminId)) {
                    throw new Exception(MsgEnum.AUTH_RULE_CHECK_ERROR.VALUE);
                }
            }
        }
        // token验证通过后，将登录管理员存储到ThreadLocal中
        userInfoHandler.setUserInfoMap(adminInfoMap);
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
        userInfoHandler.deleteUserInfoMap();
    }
}
