/**
 * 后台菜单service
 * @author 1874
 */
package luohao.application.service.backMenu;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import luohao.application.annotation.LogAnnotation;
import luohao.application.common.enums.MsgEnum;
import luohao.application.common.utils.ConvertUtils;
import luohao.application.common.utils.Utils;
import luohao.application.model.vo.backMenu.BackMenuListRespVo;
import luohao.application.model.vo.backMenu.BackMenuActionReqVo;
import luohao.application.mapper.backMenu.BackMenuMapper;
import luohao.application.model.dataobject.backMenu.BackMenuDO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class BackMenuService {
    @Resource
    private ConvertUtils convertUtils;
    @Resource
    private BackMenuMapper backMenuMapper;
    /** 查询后台菜单列表 */
    public List<BackMenuListRespVo> queryBackMenuList(String bmStatus) {
        // 查询当前登录管理员角色所拥有的菜单列表
        List<BackMenuDO> backMenuDOList = backMenuMapper.findBackMenuList(bmStatus);
        // do转vo
        return convertUtils.convertList(backMenuDOList, BackMenuListRespVo.class);
    }
    /** 新增后台菜单 */
    @LogAnnotation(title = "新增后台菜单")
    public void insertBackMenu(BackMenuActionReqVo params) throws Exception {
        // 菜单父id组合数据
        BackMenuActionReqVo backMenuAction = this.backMenuPidConcatData(params);
        // vo转do
        BackMenuDO backMenuDO = convertUtils.convert(backMenuAction, BackMenuDO.class);
        // 新增数据
        if (backMenuMapper.insert(backMenuDO) <= 0) {
            throw new Exception(MsgEnum.ADD_FAIL.VALUE);
        }
    }
    /** 修改后台菜单 */
    @LogAnnotation(title = "修改后台菜单")
    public void updateBackMenu(BackMenuActionReqVo params) throws Exception {
        // vo转do
        BackMenuDO backMenuDO = convertUtils.convert(params, BackMenuDO.class);
        // 修改数据
        if (backMenuMapper.updateById(backMenuDO) <= 0) {
            throw new Exception(MsgEnum.UPDATE_FAIL.VALUE);
        }
    }
    /** 删除后台菜单 */
    @LogAnnotation(title = "删除后台菜单")
    public void deleteBackMenu(Long id) throws Exception {
        // 查询目标菜单是否还有下级菜单
        QueryWrapper<BackMenuDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.apply("FIND_IN_SET("+id+", bm_pids)");
        if (backMenuMapper.selectCount(queryWrapper) > 1) {
            throw new Exception(MsgEnum.BACK_MENU_IS_SUBORDINATE.VALUE);
        }
        // 删除数据
        if (backMenuMapper.deleteById(id) <= 0) {
            throw new Exception(MsgEnum.DELETE_FAIL.VALUE);
        }
    }
    /** 菜单父id组合数据 */
    private BackMenuActionReqVo backMenuPidConcatData(BackMenuActionReqVo params) {
        String menuPids = params.getBmPids(); // 此时为单数字
        // 如果是pids != 0时，查询上级菜单 (0为顶级)
        if (!menuPids.equals("0")) {
            // 前端pids是单数字，需要查询目标上级对应的pids拼接上前端pids，合成完整的pids (1,2,3,4)
            BackMenuDO parentMenu = backMenuMapper.selectById(menuPids); // 查询该菜单的上级信息
            String pids = Utils.pidConcatData(parentMenu.getBmPids(), menuPids); // 上级pids与pid组合
            params.setBmPids(pids); // 设置值
        }
        return params;
    }
}
