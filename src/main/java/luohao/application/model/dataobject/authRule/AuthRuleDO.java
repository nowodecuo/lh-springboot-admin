/**
 * 权限实体
 * @author 1874
 */
package luohao.application.model.dataobject.authRule;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import luohao.application.model.dataobject.base.BaseDO;

@Data
@TableName(value = "tb_auth_rule")
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class AuthRuleDO extends BaseDO {
    @TableId(type = IdType.AUTO)
    private Long arId;

    private Long arPid; // 父ID

    private String arMethod; // 方法名称

    private String arName; // 权限名称

    private String arStatus; /** {@link luohao.application.common.enums.StatusEnum} */
}
