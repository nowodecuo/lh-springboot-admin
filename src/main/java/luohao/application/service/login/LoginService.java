/**
 * 登录service
 * @author 1874
 */
package luohao.application.service.login;

import luohao.application.annotation.LogAnnotation;
import luohao.application.common.enums.MsgEnum;
import luohao.application.common.enums.StatusEnum;
import luohao.application.common.utils.RedisUtil;
import luohao.application.common.utils.Utils;
import luohao.application.model.vo.login.LoginRespVO;
import luohao.application.handler.UserInfoHandler;
import luohao.application.mapper.admin.AdminMapper;
import luohao.application.model.dataobject.admin.AdminDO;
import luohao.application.service.role.RoleService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service
public class LoginService {
    @Value("${jwt-secret}")
    private String jwtSecret; // jwt秘钥

    @Value("${sm4-secret}")
    private String sm4Secret; // sm4秘钥

    @Value("${cache-expire}")
    private Integer cacheExpire; // 缓存时间/s

    @Value("${auth-rule.key-prefix}")
    private String ruleKeyPrefix; // 权限规则缓存key前缀

    @Value("${user.key-prefix}")
    private String userKeyPrefix; // 用户信息缓存key
    @Resource
    private AdminMapper adminMapper;
    @Resource
    private UserInfoHandler userInfoHandler;
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private RoleService roleService;
    /** 登录校验处理 */
    @LogAnnotation(title = "登录后台系统", writeParam = false)
    public LoginRespVO handleLoginCheck(String account, String password) throws Exception {
        // 将密码进行加密
        String enPassword = Utils.encryption(password);
        // 查询管理员
        AdminDO adminDO = adminMapper.findAdminDataForLogin(account, enPassword);
        if (adminDO == null) {
            throw new Exception(MsgEnum.LOGIN_CHECK_ERR.VALUE);
        }
        // 状态为0时管理员表示被禁用
        if (adminDO.getAdStatus().equals(StatusEnum.DISABLED.VALUE)) {
            throw new Exception(MsgEnum.ADMIN_DISABLED.VALUE);
        }
        // 存储信息并生成jwt令牌
        String sm4Token = this.createAdminJwtMap(adminDO);
        // 存入登录id、roleId，后续逻辑使用
        Map<String, Object> adminInfoMap = new HashMap<>();
        adminInfoMap.put(userInfoHandler.ID, adminDO.getAdId());
        adminInfoMap.put(userInfoHandler.ROLE_ID, adminDO.getAdRoleId());
        userInfoHandler.setUserInfoMap(adminInfoMap);
        // 将用户信息token存入缓存
        userInfoHandler.setUserInfoCache(sm4Token, adminDO.getAdId());
        // 设置用户权限并存入缓存
        roleService.setUserRuleSaveCache();
        // 管理员登录信息
        LoginRespVO loginRespVO = new LoginRespVO();
        loginRespVO.setId(adminDO.getAdId());
        loginRespVO.setAccount(adminDO.getAdAccount());
        loginRespVO.setName(adminDO.getAdName());
        loginRespVO.setHeaderImg(adminDO.getAdHeaderImg());
        loginRespVO.setRoleId(adminDO.getAdRoleId());
        loginRespVO.setRoleName(adminDO.getRoName() == null ? userInfoHandler.ROLE_NAME : adminDO.getRoName());
        loginRespVO.setToken(sm4Token);
        return loginRespVO;
    }
    /** 退出登录 */
    public void handleLogout() {
        Long adminId = (Long) userInfoHandler.get(userInfoHandler.ID);
        redisUtil.delete(ruleKeyPrefix + adminId); // 删除权限缓存
        redisUtil.delete(userKeyPrefix + adminId); // 删除用户缓存
    }
    /** 存储信息并生成jwt令牌，返回sm4加密后的token */
    private String createAdminJwtMap(AdminDO adminDO) throws Exception {
        HashMap<String, Object> adminInfo = new HashMap<>();
        adminInfo.put(userInfoHandler.ID, adminDO.getAdId());
        adminInfo.put(userInfoHandler.NAME, adminDO.getAdName());
        adminInfo.put(userInfoHandler.ROLE_ID, adminDO.getAdRoleId());
        String token = Utils.createJwt(adminInfo, jwtSecret, cacheExpire);
        return Utils.sm4Encryption(sm4Secret, token);
    }
}
