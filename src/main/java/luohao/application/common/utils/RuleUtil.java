/**
 * 权限工具
 * @auhtor 1874
 */
package luohao.application.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import luohao.application.common.enums.MsgEnum;
import luohao.application.model.dataobject.authRule.AuthRuleDO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Component
public class RuleUtil {
    @Value("${auth-rule.open}")
    private boolean authRuleOpen; // 权限校验开关

    @Value("${auth-rule.key-prefix}")
    private String keyPrefix; // 权限规则缓存key前缀

    @Value("${cache-expire}")
    private Integer cacheExpire; // 缓存过期时间
    @Resource
    private RedisUtil redisUtil;
    /**
     * 用户权限规则保存
     */
    public void saveCacheUserRule(List<AuthRuleDO> authRuleList, Long adminId) throws Exception {
        if (authRuleOpen) {
            String ruleListJson = JSONObject.toJSONString(authRuleList); // 权限方法名数组转为json字符串
            try {
                String key = keyPrefix + adminId; // redis key
                redisUtil.set(key, ruleListJson, cacheExpire);
            } catch (Exception e) {
                throw new Exception(MsgEnum.REDIS_ERROR.VALUE);
            }
        }
    }
    /**
     * 用户权限校验
     */
    public boolean checkUserRule(String method, Long adminId) throws Exception {
        if (authRuleOpen) {
            // 如果是不需要验证的方法，则直接通过
            if (notCheckMethod(method)) return true;
            // 获取redis中的权限数组
            try {
                String key = keyPrefix + adminId; // redis key
                String ruleListStr = (String) redisUtil.get(key);
                // 权限json字符串转为数组, 校验是否拥有该方法权限
                List<String> ruleList = JSON.parseArray(ruleListStr, String.class);
                return ruleList.contains(method);
            } catch (Exception e) {
                throw new Exception(MsgEnum.REDIS_ERROR.VALUE);
            }
        }
        return true;
    }
    /**
     * 权限缓存是否存在
     */
    public boolean hasUserRule(Long adminId) throws Exception {
        try {
            String key = keyPrefix + adminId; // redis key
            return redisUtil.hasKey(key);
        } catch (Exception e) {
            throw new Exception(MsgEnum.REDIS_ERROR.VALUE);
        }
    }
    /**
     * 返回权限缓存数据
     */
    public List<AuthRuleDO> getUserRule(Long adminId) throws Exception {
        try {
            String key = keyPrefix + adminId; // redis key
            String ruleListStr = (String) redisUtil.get(key);
            return JSON.parseArray(ruleListStr, AuthRuleDO.class);
        } catch (Exception e) {
            throw new Exception(MsgEnum.REDIS_ERROR.VALUE);
        }
    }
    /**
     * 不验证的方法名
     */
    private boolean notCheckMethod(String method) {
        ArrayList<String> passList = new ArrayList<>();
        passList.add("fileUpload"); // 上传文件
        passList.add("queryRoleMenuAndRule"); // 查询登录角色的菜单和权限
        passList.add("myAdminData"); // (个人设置)查询管理员个人信息
        passList.add("myAdminUpdate"); // (个人设置)管理员个人信息更新
        passList.add("adminMyPasswordUpdate"); // (个人设置)管理员个人密码更新
        passList.add("mySubordinatesRoleList"); // 我的下级角色列表
        return passList.contains(method);
    }
}
