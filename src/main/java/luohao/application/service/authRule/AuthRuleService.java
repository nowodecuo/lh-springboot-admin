/**
 * 权限service
 * @author 1874
 */
package luohao.application.service.authRule;

import luohao.application.annotation.LogAnnotation;
import luohao.application.common.enums.MsgEnum;
import luohao.application.common.utils.ConvertUtils;
import luohao.application.model.dataobject.authRule.AuthRuleDO;
import luohao.application.model.vo.authRule.AuthRuleListReqVo;
import luohao.application.model.vo.authRule.AuthRuleActionReqVo;
import luohao.application.handler.UserInfoHandler;
import luohao.application.mapper.authRule.AuthRuleMapper;
import luohao.application.mapper.role.RoleMapper;
import luohao.application.model.dataobject.role.RoleDO;
import luohao.application.model.vo.authRule.AuthRuleListRespVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class AuthRuleService {
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private ConvertUtils convertUtils;
    @Resource
    private AuthRuleMapper authRuleMapper;
    @Resource
    private UserInfoHandler userInfoHandler;
    /** 查询权限分页 */
    public List<AuthRuleListRespVo> queryAuthRuleList(AuthRuleListReqVo params) {
        // 查询数据库信息
        List<AuthRuleDO> authRuleDOList = authRuleMapper.findAuthRuleList(params);
        // do转vo
        return convertUtils.convertList(authRuleDOList, AuthRuleListRespVo.class);
    }
    /** 新增权限 */
    @LogAnnotation(title = "新增权限")
    public void insertAuthRule(AuthRuleActionReqVo params) throws Exception {
        // vo转do
        AuthRuleDO authRuleDO = convertUtils.convert(params, AuthRuleDO.class);
        // 新增数据
        if (authRuleMapper.insert(authRuleDO) <= 0) {
            throw new Exception(MsgEnum.ADD_FAIL.VALUE);
        }
    }
    /** 修改权限 */
    @LogAnnotation(title = "修改权限")
    public void updateAuthRule(AuthRuleActionReqVo params) throws Exception {
        // 判断操作的权限是否是当前管理员拥有的权限
        this.ruleIsMyRules(params.getArId());
        // vo转do
        AuthRuleDO authRuleDO = convertUtils.convert(params, AuthRuleDO.class);
        // 修改数据
        if (authRuleMapper.updateById(authRuleDO) <= 0){
            throw new Exception(MsgEnum.UPDATE_FAIL.VALUE);
        }
    }
    /** 删除权限 */
    @LogAnnotation(title = "删除权限")
    public void deleteBackMenu(Long id) throws Exception {
        // 判断操作的权限是否是当前管理员拥有的权限
        this.ruleIsMyRules(id);
        // 删除数据
        if (authRuleMapper.deleteById(id) <= 0) {
            throw new Exception(MsgEnum.DELETE_FAIL.VALUE);
        }
    }
    /** 判断操作的权限是否是当前管理员拥有的权限 */
    private void ruleIsMyRules(Long ruleId) throws Exception {
        // 当前管理员角色id
        Long roleId = userInfoHandler.getUserRoleId();
        // 如果不是超级管理员，查询当前管理员的权限ids
        if (!roleId.equals(userInfoHandler.SUPER_ADMIN_ROLE_ID)) {
            RoleDO roleDO = roleMapper.findRoleData(roleId); // 获取当前管理员角色拥有的权限ids
            String[] ruleIds = roleDO.getRoRuleIds().split(","); // 权限ids转为数组
            // 判断指定权限id，是否在当前管理员拥有的权限中,，如果不在则报错
            ArrayList<String> ruleIdsArr = new ArrayList<>(Arrays.asList(ruleIds));
            if (!ruleIdsArr.contains(String.valueOf(ruleId))) {
                throw new Exception(MsgEnum.ROLE_NOT_RULE.VALUE);
            }
        }
    }
}
