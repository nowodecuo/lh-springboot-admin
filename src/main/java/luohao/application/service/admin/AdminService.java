/**
 * 管理员service
 * @author 1874
 */
package luohao.application.service.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import luohao.application.annotation.LogAnnotation;
import luohao.application.common.enums.MsgEnum;
import luohao.application.common.utils.ConvertUtils;
import luohao.application.common.utils.Utils;
import luohao.application.model.vo.admin.*;
import luohao.application.common.pojo.PageResult;
import luohao.application.handler.UserInfoHandler;
import luohao.application.mapper.admin.AdminMapper;
import luohao.application.model.dataobject.admin.AdminDO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.util.Arrays;
import java.util.List;

@Service
public class AdminService {
    // 本地上传路径配置
    @Value("${upload-path}")
    private String uploadPath;
    @Resource
    private ConvertUtils convertUtils;
    @Resource
    private AdminMapper adminMapper;
    @Resource
    private UserInfoHandler userInfoHandler;
    /** 查询管理员分页 */
    public PageResult<AdminListRespVO> queryAdminPage(AdminListReqVO params) {
        Page<AdminDO> page = new Page<>(params.getPageNum(), params.getPageSize());
        // 获取当前登录管理员角色ID
        Long roleId = userInfoHandler.getUserRoleId();
        // 查询数据库信息, 查询当前登录管理员角色下级的所有管理员
        adminMapper.findAdminDataPage(page, params, roleId);
        // do转vo
        List<AdminListRespVO> adminListRes = convertUtils.convertList(page.getRecords(), AdminListRespVO.class);
        // 返回列表和分页信息
        return PageResult.createPageRes(adminListRes, page.getTotal());
    }
    /** 新增管理员 */
    @LogAnnotation(title = "新增管理员")
    public void insertAdmin(AdminActionReqVO params) throws Exception {
        // 检查账号是否存在
        QueryWrapper<AdminDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ad_account", params.getAdAccount());
        if (adminMapper.selectCount(queryWrapper) >= 1) {
            throw new Exception(MsgEnum.ACCOUNT_EXISTS.VALUE);
        }
        // vo转do
        AdminDO adminDO = convertUtils.convert(params, AdminDO.class);
        String enPassword = Utils.encryption(adminDO.getAdPassword()); // 密码加密
        adminDO.setAdPassword(enPassword);
        // 插入数据库
        if (adminMapper.insert(adminDO) <= 0) {
            throw new Exception(MsgEnum.ADD_FAIL.VALUE);
        }
    }
    /** 修改管理员 */
    @LogAnnotation(title = "修改管理员")
    public void updateAdmin(AdminActionReqVO params) throws Exception {
        // 查询目标管理员是否是我的下级
        this.adminIsMySubordinate(params.getAdId());
        // vo转do
        AdminDO adminDO = convertUtils.convert(params, AdminDO.class);
        // 修改数据库
        if (adminMapper.updateById(adminDO) <= 0) {
            throw new Exception(MsgEnum.UPDATE_FAIL.VALUE);
        }
    }
    /** 删除管理员 */
    @LogAnnotation(title = "删除管理员")
    public void deleteAdmin(Long id) throws Exception {
        // 查询目标管理员是否是我的下级
        this.adminIsMySubordinate(id);
        // 执行删除
        if (adminMapper.deleteById(id) <= 0) {
            throw new Exception(MsgEnum.DELETE_FAIL.VALUE);
        }
    }
    /** 更新管理员密码 */
    @LogAnnotation(title = "更新管理员密码")
    public void updatePassword(Long id, String password, boolean isMyself) throws Exception {
        Long adminId = userInfoHandler.getUserId();
        // 如果修改的是我自己个人，则判断是否是本人
        if (isMyself && !adminId.equals(id)) {
            throw new Exception(MsgEnum.PASSWORD_NOT_MYSELF.VALUE);
        }
        // 查询目标管理员是否是我的下级
        this.adminIsMySubordinate(id);
        // 密码加密
        String enPassword = Utils.encryption(password);
        // 执行更新
        UpdateWrapper<AdminDO> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("ad_password", enPassword).eq("ad_id", id);
        if (adminMapper.update(updateWrapper) <= 0) {
            throw new Exception(MsgEnum.UPDATE_FAIL.VALUE);
        }
    }
    /** 批量迁移管理员角色 */
    @LogAnnotation(title = "批量迁移管理员角色")
    public void updateBatchRole(Integer roleId, String adminIds) throws Exception {
        UpdateWrapper<AdminDO> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("ad_role_id", roleId).in("ad_id", Arrays.asList(adminIds.split(",")));
        if(adminMapper.update(updateWrapper) <= 0){
            throw new Exception(MsgEnum.UPDATE_FAIL.VALUE);
        }
    }
    /** (个人设置)查询管理员个人信息 */
    public AdminMyRespVO queryMyAdminData() {
        Long adminId = userInfoHandler.getUserId();
        // 管理员信息
        AdminDO adminDO = adminMapper.findAdminDataForId(adminId);
        if (adminDO.getRoName() == null) adminDO.setRoName(userInfoHandler.ROLE_NAME);
        // do转vo
        return convertUtils.convert(adminDO, AdminMyRespVO.class);
    }
    /** (个人设置)管理员个人信息更新 */
    @LogAnnotation(title = "管理员个人信息更新")
    public void updateMyAdmin(AdminMyReqVO params) throws Exception {
        Long adminId = userInfoHandler.getUserId();
        // 查询个人信息
        AdminDO adminInfo = adminMapper.selectById(adminId);
        // vo转do
        AdminDO adminDO = convertUtils.convert(params, AdminDO.class);
        adminDO.setAdId(adminInfo.getAdId());
        // 原头像与新头像不相同则修改
        String newHeaderImg = params.getAdHeaderImg();
        String oldHeaderImg = adminInfo.getAdHeaderImg();
        if (!newHeaderImg.equals(oldHeaderImg)) {
            adminDO.setAdHeaderImg(newHeaderImg); // 加入更新
        }
        // 修改数据库
        if (adminMapper.updateById(adminDO) <= 0) {
            throw new Exception(MsgEnum.UPDATE_FAIL.VALUE);
        }
        // 更新成功后删除原来的头像
        if (adminDO.getAdHeaderImg() != null && oldHeaderImg != null) {
            File file = new File(uploadPath + "/" + oldHeaderImg);
            if(file.exists()) file.delete(); // 如果存在文件则删除
        }
    }
    /** 查询目标管理员是否是我的下级 */
    private void adminIsMySubordinate(Long targetId) throws Exception {
        Long roleId = userInfoHandler.getUserRoleId();
        // 0=超级管理员直接跳过
        if (!roleId.equals(userInfoHandler.SUPER_ADMIN_ROLE_ID)) {
            // 查询目标管理员是否是我的下级
            AdminDO adminDO = adminMapper.findAdminIsMySubordinate(roleId, targetId);
            // 如果adminModel == null 表示不是我的下级，则返回错误信息，
            if (adminDO == null) {
                throw new Exception(MsgEnum.ADMIN_NO_SUBORDINATE.VALUE);
            }
        }
    }
}
