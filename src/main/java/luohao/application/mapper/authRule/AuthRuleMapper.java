/**
 * tb_auth_rule 权限表
 * @author 1874
 */
package luohao.application.mapper.authRule;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import luohao.application.model.dataobject.authRule.AuthRuleDO;
import luohao.application.model.vo.authRule.AuthRuleListReqVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.Arrays;
import java.util.List;

@Mapper
public interface AuthRuleMapper extends BaseMapper<AuthRuleDO> {
    /** 查询权限分页 **/
    default IPage<AuthRuleDO> findAuthRulePage(Page<AuthRuleDO> page, AuthRuleListReqVo params) {
        QueryWrapper<AuthRuleDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(ObjectUtils.isNotNull(params.getArName()), "ar_name", params.getArName());
        queryWrapper.eq(ObjectUtils.isNotNull(params.getArMethod()), "ar_method", params.getArMethod());
        queryWrapper.eq(ObjectUtils.isNotNull(params.getArStatus()), "ar_status", params.getArStatus());
        queryWrapper.orderByDesc("ar_id");
        return selectPage(page, queryWrapper);
    }
    /** 查询权限列表  */
    default List<AuthRuleDO> findAuthRuleList(AuthRuleListReqVo params) {
        QueryWrapper<AuthRuleDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(ObjectUtils.isNotNull(params.getArName()), "ar_name", params.getArName());
        queryWrapper.eq(ObjectUtils.isNotNull(params.getArMethod()), "ar_method", params.getArMethod());
        queryWrapper.eq(ObjectUtils.isNotNull(params.getArStatus()), "ar_status", params.getArStatus());
        return selectList(queryWrapper);
    }
    /** 根据权限ids查询权限信息 */
    default List<AuthRuleDO> findAuthRuleListForIds(String ruleIds, Long roleId) {
        QueryWrapper<AuthRuleDO> queryWrapper = new QueryWrapper<>();
        if (roleId != 0) queryWrapper.in("ar_id", Arrays.asList(ruleIds.split(","))); // 如果不是超级管理员
        queryWrapper.eq("ar_status", 1);
        queryWrapper.orderByAsc("ar_id");
        return selectList(queryWrapper);
    }
}
