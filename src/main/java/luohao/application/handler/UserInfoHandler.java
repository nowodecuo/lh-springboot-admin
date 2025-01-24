/**
 * 登录用户处理
 * @author 1874
 */
package luohao.application.handler;

import luohao.application.common.enums.MsgEnum;
import luohao.application.common.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Component
public class UserInfoHandler {
    public Long SUPER_ADMIN_ROLE_ID = 0L; // 超级管理员角色ID值
    public String ROLE_NAME = "超级管理员";
    public String ADMIN_TOKEN = "admin-token"; // token标识
    public String ID = "id"; // 登录管理员ID
    public String NAME = "name"; // 姓名
    public String ROLE_ID = "roleId"; // 角色ID
    private final ThreadLocal<Map<String, Object>> threadLocal = new ThreadLocal<>();

    @Value("${user.key-prefix}")
    private String keyPrefix; // 用户信息缓存key

    @Value("${cache-expire}")
    private Integer cacheExpire; // 缓存过期时间
    @Resource
    private RedisUtil redisUtil;
    /**
     * 获取登录用户信息
     */
    public Map<String, Object> getUserInfoMap() {
        Map<String, Object> userInfoMap = threadLocal.get();
        // 如果线程map为空，则添加一个空map
        if (userInfoMap == null || userInfoMap.isEmpty()) {
            userInfoMap = new HashMap<>();
            threadLocal.set(userInfoMap);
        }
        return userInfoMap;
    }
    /**
     * 将map数据添加到线程中用户信息中
     */
    public void setUserInfoMap(Map<String, Object> userInfoMap) {
        threadLocal.set(userInfoMap);
    }
    /**
     * 获取线程中用户信息key对应的值
     */
    public Object get(String key) {
        Map<String, Object> userInfoMap = getUserInfoMap();
        return userInfoMap.get(key);
    }
    /**
     * 获取线程中用户id
     */
    public Long getUserId() {
        Map<String, Object> userInfoMap = getUserInfoMap();
        return Long.valueOf(userInfoMap.get(ID).toString());
    }
    /**
     * 获取线程中用户角色id
     */
    public Long getUserRoleId() {
        Map<String, Object> userInfoMap = getUserInfoMap();
        return Long.valueOf(userInfoMap.get(ROLE_ID).toString());
    }
    /**
     * 设置线程中用户信息key的值
     */
    public Object set(String key, Object value) {
        Map<String, Object> userInfoMap = getUserInfoMap();
        return userInfoMap.put(key, value);
    }
    /**
     * 删除线程map
     */
    public void deleteUserInfoMap() {
        threadLocal.remove();
    }
    /**
     * 将用户信息token存入缓存，用于检查其他设备登录挤掉
     */
    public void setUserInfoCache(String token, Long adminId) throws Exception {
        try {
            String key = keyPrefix + adminId; // redis key
            redisUtil.set(key, token, cacheExpire);
        } catch (Exception e) {
            throw new Exception(MsgEnum.REDIS_ERROR.VALUE);
        }
    }
    /**
     * 检查用户是否被其他设备登录挤掉
     */
    public boolean checkUserExtrusion(String token, Long adminId) throws Exception {
        try {
            String key = keyPrefix + adminId; // redis key
            String originalToken = (String) redisUtil.get(key); // 原本存储的token
            return token.equals(originalToken); // false = 被挤掉线
        } catch (Exception e) {
            throw new Exception(MsgEnum.REDIS_ERROR.VALUE);
        }
    }
}
