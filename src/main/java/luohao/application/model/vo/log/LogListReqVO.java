/***
 * 日志分页入参
 * @author 1874
 */
package luohao.application.model.vo.log;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import luohao.application.common.pojo.PageParam;

@Data
@Schema(description = "管理后台-日志管理分页 Request VO")
public class LogListReqVO extends PageParam {
    @Schema(description = "日志内容")
    private String loContent;

    @Schema(description = "操作方法")
    private String loMethod;

    @Schema(description = "结果")
    private String loResult;

    @Schema(description = "IP")
    private String loIp;

    @Schema(description = "IP地址")
    private String loAddress;

    @Schema(description = "开始时间")
    private String startTime; // 开始时间

    @Schema(description = "结束数据")
    private String endTime; // 结束数据

    @Schema(description = "管理员姓名")
    private String adName; // 管理员姓名
}