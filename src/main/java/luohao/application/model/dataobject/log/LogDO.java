/**
 * tb_log 日志模型
 * @author 1874
 */
package luohao.application.model.dataobject.log;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import luohao.application.model.dataobject.base.BaseDO;

import java.time.LocalDateTime;

@Data
@TableName(value = "tb_log")
@JsonInclude(value = JsonInclude.Include.NON_NULL) // 空值不返回
public class LogDO extends BaseDO {
    @TableId(type = IdType.AUTO)
    private Long loId;

    private String loContent; // 日志内容

    private String loMethod; // 操作方法

    private String loParams; // 执行参数

    private String loResult; // 执行结果(1成功0失败)

    private String loReason; // 原因

    private String loAddress = "未知"; // ip地址

    private String loIp; // ip

    private Long adId;

    private String adName;
}
