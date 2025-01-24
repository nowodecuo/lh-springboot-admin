/**
 * tb_admin 管理员 mapper
 * @author 1874
 */
package luohao.application.mapper.admin;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import luohao.application.model.vo.admin.AdminListReqVO;
import luohao.application.model.dataobject.admin.AdminDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AdminMapper extends BaseMapper<AdminDO> {
    /**
     * 管理员列表分页
     * @param params 分页入参
     * @param adminRoleId 当前登录管理员角色id
     */
    IPage<AdminDO> findAdminDataPage(Page<AdminDO> page, @Param("reqVo") AdminListReqVO params, @Param("roleId") Long adminRoleId);
    /**
     * 账号密码查找某个管理员信息
     * @param account 账号
     * @param password 密码
     */
    AdminDO findAdminDataForLogin(@Param("account") String account, @Param("password") String password);
    /**
     * 查询目标管理员是否是我的下级
     * @param myRoleId 登录管理员id
     * @param targetId 查询目标管理员id
     */
    AdminDO findAdminIsMySubordinate(@Param("myRoleId") Long myRoleId, @Param("targetId") Long targetId);
    /**
     * 根据ID查询管理员信息
     * @param adminId 当前登录管理员id
     */
    AdminDO findAdminDataForId(@Param("adminId") Long adminId);
    /**
     * 查询目标角色是否还有所属的管理员
     * @param targetId 查询目标角色id
     */
    default Long findAdminIsSubordinate(@Param("targetId") Long targetId) {
        QueryWrapper<AdminDO> queryCountWrapper = new QueryWrapper<>();
        queryCountWrapper.eq("ad_role_id", targetId);
        return selectCount(queryCountWrapper);
    }
}
