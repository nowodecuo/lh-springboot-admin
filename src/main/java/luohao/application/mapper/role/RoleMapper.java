/**
 * tb_role 角色表
 * @author 1874
 */
package luohao.application.mapper.role;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import luohao.application.model.dataobject.role.RoleDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RoleMapper extends BaseMapper<RoleDO> {
    /**
     * 查询角色列表
     * @param adminRoleId 管理员角色id
     * @param isMySelf 是否查询自身角色
     */
    default List<RoleDO> findRoleList(Long adminRoleId, boolean isMySelf) {
        QueryWrapper<RoleDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.apply("FIND_IN_SET("+adminRoleId+", ro_pids)");
        // isMySelf = true 查询自身角色及下级角色，false 则只查询下级角色
        if (isMySelf) queryWrapper.or().eq("ro_id", adminRoleId);
        return selectList(queryWrapper);
    }
    /** 查询角色信息 */
    default RoleDO findRoleData(Long roId) {
        QueryWrapper<RoleDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("ro_id", "ro_name", "ro_pids", "ro_back_menu_ids", "ro_rule_ids");
        queryWrapper.eq("ro_id", roId);
        return selectOne(queryWrapper);
    }
    /**
     * 查询目标角色是否是我的下级
     * @param myRoleId 登录管理员角色id
     * @param targetRoleId 查询目标角色id
     */
    default Long findRoleIsMySubordinate(Long myRoleId, Long targetRoleId) {
        QueryWrapper<RoleDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.apply("FIND_IN_SET("+myRoleId+", ro_pids)");
        queryWrapper.eq("ro_id", targetRoleId);
        return selectCount(queryWrapper);
    }
    /**
     * 查询目标角色是否还有下级角色
     * @param targetId 查询目标角色id
     */
    default Long findRoleIsSubordinate(Long targetId) {
        QueryWrapper<RoleDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.apply("FIND_IN_SET("+targetId+", ro_pids)");
        return selectCount(queryWrapper);
    }
}
