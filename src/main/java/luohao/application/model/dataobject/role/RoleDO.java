/**
 * 角色实体
 * @author 1874
 */
package luohao.application.model.dataobject.role;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import luohao.application.model.dataobject.base.BaseDO;

@Data
@TableName(value = "tb_role")
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class RoleDO extends BaseDO {
    @TableId(type = IdType.AUTO)
    private Long roId;

    private String roName; // 角色名

    private String roPids; // 父id

    private String roRuleIds; // 权限规则id

    private String roBackMenuIds; // 后台菜单id

    private String roStatus; // 状态(1启用0禁用)
}
