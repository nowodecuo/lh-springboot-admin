/***
 * 日志分页返参
 * @author 1874
 */
package luohao.application.model.vo.log;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "管理后台-日志管理分页 Response VO")
public class LogListRespVO {
    @Schema(description = "id")
    private Long loId;

    @Schema(description = "日志内容")
    private String loContent; // 日志内容

    @Schema(description = "操作方法")
    private String loMethod; // 操作方法

    @Schema(description = "执行参数")
    private String loParams; // 执行参数

    @Schema(description = "执行结果")
    private String loResult; // 执行结果(1成功0失败)

    @Schema(description = "原因")
    private String loReason; // 原因

    @Schema(description = "IP地址")
    private String loAddress; // ip地址

    @Schema(description = "IP")
    private String loIp; // ip

    @Schema(description = "创建人id")
    private Long creator;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "管理员id")
    private Long adId;

    @Schema(description = "管理员姓名")
    private String adName;
}
