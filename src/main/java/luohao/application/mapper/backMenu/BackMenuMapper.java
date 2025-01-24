/**
 * tb_back_menu 后台菜单表
 * @author 1874
 */
package luohao.application.mapper.backMenu;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import luohao.application.model.dataobject.backMenu.BackMenuDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Arrays;
import java.util.List;

@Mapper
public interface BackMenuMapper extends BaseMapper<BackMenuDO> {
    /** 查询后台菜单列表 */
    default List<BackMenuDO> findBackMenuList(String bmStatus) {
        QueryWrapper<BackMenuDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(ObjectUtils.isNotNull(bmStatus), "bm_status", bmStatus);
        queryWrapper.orderByAsc("bm_sort");
        return selectList(queryWrapper);
    }
    /**
     * 根据菜单ids查询菜单信息
     * @param menuIds 菜单id字符串
     * @param roleId 角色id
     */
    default List<BackMenuDO> findBackMenuListForIds(String menuIds, Long roleId) {
        QueryWrapper<BackMenuDO> queryWrapper = new QueryWrapper<>();
        if (roleId != 0) queryWrapper.in("bm_id", Arrays.asList(menuIds.split(","))); // 如果不是超级管理员
        queryWrapper.eq("bm_status", 1);
        queryWrapper.orderByAsc("bm_sort");
        return selectList(queryWrapper);
    }
}
