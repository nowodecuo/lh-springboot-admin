/**
 * tb_back_menu 后台菜单模型
 * @author 1874
 */
package luohao.application.model.dataobject.backMenu;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import luohao.application.model.dataobject.base.BaseDO;

@Data
@TableName(value = "tb_back_menu")
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class BackMenuDO extends BaseDO {
    @TableId(type = IdType.AUTO)
    private Long bmId;
    private String bmPids;
    private String bmTitle;
    private String bmIcon;
    private String bmPath;
    private String bmComponent;
    private Integer bmSort;
    private String bmStatus; /** {@link luohao.application.common.enums.StatusEnum} */
}
