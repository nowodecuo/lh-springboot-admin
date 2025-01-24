/***
 * tb_admin 管理员
 * @author 1874
 */
package luohao.application.model.dataobject.admin;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import luohao.application.model.dataobject.base.BaseDO;

@Data
@TableName(value = "tb_admin")
@JsonInclude(value = JsonInclude.Include.NON_NULL) // 空值不返回
public class AdminDO extends BaseDO {
    @TableId(type = IdType.AUTO)
    private Long adId;

    private String adName; // 姓名

    private Long adRoleId; // 角色ID

    private String adHeaderImg; // 头像

    private String adAccount; // 账号

    private String adPassword; // 密码

    private String adCity; // 城市

    private String adPhone; // 手机号

    private String adStatus; /** {@link luohao.application.common.enums.StatusEnum} */

    @TableField(exist = false)
    private Long roId; // 角色id

    @TableField(exist = false)
    private String roName; // 角色名

    @TableField(exist = false)
    private String roRuleIds; // 权限ids

    @TableField(exist = false)
    private String roBackMenuIds; // 菜单ids
}
