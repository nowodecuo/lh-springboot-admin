/**
 * 角色service
 * @author 1874
 */
package luohao.application.service.role;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import luohao.application.annotation.LogAnnotation;
import luohao.application.common.enums.MsgEnum;
import luohao.application.common.utils.ConvertUtils;
import luohao.application.common.utils.RuleUtil;
import luohao.application.common.utils.Utils;
import luohao.application.mapper.admin.AdminMapper;
import luohao.application.model.dataobject.authRule.AuthRuleDO;
import luohao.application.model.vo.role.RoleListRespVO;
import luohao.application.model.vo.role.RoleActionReqVo;
import luohao.application.handler.UserInfoHandler;
import luohao.application.mapper.authRule.AuthRuleMapper;
import luohao.application.mapper.backMenu.BackMenuMapper;
import luohao.application.mapper.role.RoleMapper;
import luohao.application.model.dataobject.backMenu.BackMenuDO;
import luohao.application.model.dataobject.role.RoleDO;
import luohao.application.model.vo.role.RoleMenuRespVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RoleService {
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private AdminMapper adminMapper;
    @Resource
    private AuthRuleMapper authRuleMapper;
    @Resource
    private BackMenuMapper backMenuMapper;
    @Resource
    private RuleUtil ruleUtil;
    @Resource
    private UserInfoHandler userInfoHandler;
    @Resource
    private ConvertUtils convertUtils;

    /** 查询角色列表 */
    public List<RoleListRespVO> queryRoleList() {
        // 获取当前登录管理员角色ID
        Long adminRoleId = userInfoHandler.getUserRoleId();
        // 查询当前登录管理员角色及下级的所有角色
        List<RoleDO> roleDOList = roleMapper.findRoleList(adminRoleId, true);
        // do转vo
        return convertUtils.convertList(roleDOList, RoleListRespVO.class);
    }
    /** 查询我的下级角色列表 */
    public List<RoleListRespVO> queryMySubordinatesList() {
        // 获取当前登录管理员角色ID
        Long adminRoleId = userInfoHandler.getUserRoleId();
        // 查询数据
        List<RoleDO> roleDOList = roleMapper.findRoleList(adminRoleId, false);
        // do转vo
        return convertUtils.convertList(roleDOList, RoleListRespVO.class);
    }
    /** 新增角色 */
    @LogAnnotation(title = "新增角色")
    public void insertRole(RoleActionReqVo params) throws Exception {
        // 前端pids是单数字，需要查询目标上级对应的pids拼接上前端pids
        RoleActionReqVo roleActionReqVo = this.rolePidConcatData(params);
        // vo转do
        RoleDO roleDO = convertUtils.convert(roleActionReqVo, RoleDO.class);
        // 新增数据
        if (roleMapper.insert(roleDO) <= 0) {
            throw new Exception(MsgEnum.ADD_FAIL.VALUE);
        }
    }
    /** 修改角色 */
    @LogAnnotation(title = "修改角色")
    public void updateRole(RoleActionReqVo params) throws Exception {
        // 查询目标角色是否是我的下级
        this.roleIsMySubordinate(params.getRoId());
        // vo转do
        RoleDO roleDO = convertUtils.convert(params, RoleDO.class);
        // 修改数据
        if (roleMapper.updateById(roleDO) <= 0){
            throw new Exception(MsgEnum.UPDATE_FAIL.VALUE);
        }
    }
    /** 删除角色 */
    @LogAnnotation(title = "删除角色")
    public void deleteRole(Long id) throws Exception {
        // 查询目标角色是否是我的下级角色
        this.roleIsMySubordinate(id);
        // 查询目标角色是否还有下级角色
        if (roleMapper.findRoleIsSubordinate(id) >= 1) {
            throw new Exception(MsgEnum.ROLE_IS_SUBORDINATE.VALUE);
        }
        // 查询目标角色是否还有所属管理员
        if (adminMapper.findAdminIsSubordinate(id) >= 1) {
            throw new Exception(MsgEnum.ADMIN_IS_SUBORDINATE.VALUE);
        }
        // 删除数据
        if (roleMapper.deleteById(id) <= 0) {
            throw new Exception(MsgEnum.DELETE_FAIL.VALUE);
        }
    }
    /** 更新角色后台菜单授权 */
    @LogAnnotation(title = "更新角色后台菜单授权")
    public void updateRoleBackMenu(Long roId, String menuIds) throws Exception {
        // 查询目标角色是否是我的下级
        this.roleIsMySubordinate(roId);
        // 更新数据
        UpdateWrapper<RoleDO> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("ro_back_menu_ids", menuIds);
        updateWrapper.eq("ro_id", roId);
        if (roleMapper.update(updateWrapper) <= 0) {
            throw new Exception(MsgEnum.UPDATE_FAIL.VALUE);
        }
    }
    /** 更新角色权限授权 */
    @LogAnnotation(title = "更新角色权限授权")
    public void updateRoleAuthRule(Long roId, String ruleIds) throws Exception {
        // 查询目标角色是否是我的下级
        this.roleIsMySubordinate(roId);
        // 更新数据
        UpdateWrapper<RoleDO> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("ro_rule_ids", ruleIds);
        updateWrapper.eq("ro_id", roId);
        if (roleMapper.update(updateWrapper) <= 0) {
            throw new Exception(MsgEnum.UPDATE_FAIL.VALUE);
        }
    }
    /** 查询登录角色菜单和权限 */
    public RoleMenuRespVO queryRoleMenuAndRule() throws Exception {
        String menuIds = "";
        Long roleId = userInfoHandler.getUserRoleId(); // 管理员角色id
        // 如果角色不是超级管理员，则查询管理员拥有的权限ids、菜单ids
        if (!roleId.equals(userInfoHandler.SUPER_ADMIN_ROLE_ID)) {
            RoleDO roleDO = roleMapper.findRoleData(roleId);
            menuIds = roleDO.getRoBackMenuIds() != null ? roleDO.getRoBackMenuIds() : "";
        }
        Long adminId = userInfoHandler.getUserId(); // 管理员id
        // 查询缓存中对应的权限信息
        List<AuthRuleDO> authRuleList = ruleUtil.getUserRule(adminId);
        // 查询对应的菜单信息
        List<BackMenuDO> backMenuList = backMenuMapper.findBackMenuListForIds(menuIds, roleId);
        // 组合后返回
        RoleMenuRespVO roleMenuRespVO = new RoleMenuRespVO();
        roleMenuRespVO.setAuthRuleList(authRuleList);
        roleMenuRespVO.setBackMenuList(backMenuList);
        return roleMenuRespVO;
    }
    /** 设置用户权限并存入缓存 */
    public void setUserRuleSaveCache() throws Exception {
        Long adminId = userInfoHandler.getUserId(); // 管理员id
        Long roleId = userInfoHandler.getUserRoleId(); // 管理员角色id
        String ruleIds = "";
        // 如果角色不是超级管理员，则查询管理员拥有的权限ids、菜单ids
        if (!roleId.equals(userInfoHandler.SUPER_ADMIN_ROLE_ID)) {
            RoleDO roleDO = roleMapper.findRoleData(roleId);
            ruleIds = roleDO.getRoRuleIds() != null ? roleDO.getRoRuleIds() : "";
        }
        // 查询对应的权限信息
        List<AuthRuleDO> authRuleList = authRuleMapper.findAuthRuleListForIds(ruleIds, roleId);
        // 将权限信息存储到redis中
        ruleUtil.saveCacheUserRule(authRuleList, adminId);
    }
    /** 查询目标角色是否是我或我的下级角色 */
    private void roleIsMySubordinate(Long targetRoleId) throws Exception {
        // 我的角色id
        Long myRoleId = userInfoHandler.getUserRoleId();
        // 如果操作的角色id != 我的角色id && 我 != 超管，则查询判断是否是我的下级，相等则通过
        if (!myRoleId.equals(targetRoleId) && !myRoleId.equals(userInfoHandler.SUPER_ADMIN_ROLE_ID)) {
            // 查询目标角色是否是我的下级
            if (roleMapper.findRoleIsMySubordinate(myRoleId, targetRoleId) <= 0) {
                throw new Exception(MsgEnum.ROLE_NO_SUBORDINATE.VALUE);
            }
        }
    }
    /** 角色父id组合数据 */
    private RoleActionReqVo rolePidConcatData(RoleActionReqVo params) {
        // 此时为单数字
        String roPid = params.getRoPids();
        // 如果是pids != 0
        String superAdminRoleId = String.valueOf(userInfoHandler.SUPER_ADMIN_ROLE_ID);
        if (!roPid.equals(superAdminRoleId)) {
            // 前端pids是单数字，需要查询目标上级对应的pids拼接上前端pids
            RoleDO parentRoleDO = roleMapper.selectById(roPid); // 查询该角色的上级信息
            String pids = Utils.pidConcatData(parentRoleDO.getRoPids(), roPid); // 上级pids与pid组合
            params.setRoPids(pids); // 设置值
        }
        return params;
    }
}
