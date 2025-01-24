/**
 * 日志service
 * @author 1874
 */
package luohao.application.service.log;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import luohao.application.annotation.LogAnnotation;
import luohao.application.common.enums.MsgEnum;
import luohao.application.common.utils.ConvertUtils;
import luohao.application.model.vo.log.LogListReqVO;
import luohao.application.handler.UserInfoHandler;
import luohao.application.mapper.log.LogMapper;
import luohao.application.model.dataobject.log.LogDO;
import luohao.application.common.pojo.PageResult;
import luohao.application.model.vo.log.LogListRespVO;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LogService {
    @Resource
    private LogMapper logMapper;
    @Resource
    private UserInfoHandler userInfoHandler;
    @Resource
    private ConvertUtils convertUtils;
    @Resource
    private HttpServletRequest request;
    /** 查询日志列表分页 */
    public PageResult<LogListRespVO> queryTablePage(LogListReqVO params) {
        // 分页起始计算
        Page<LogDO> page = new Page<>(params.getPageNum(), params.getPageSize());
        // 查询数据库信息
        logMapper.findPageData(page, params);
        // do转vo
        List<LogListRespVO> logListRes = convertUtils.convertList(page.getRecords(), LogListRespVO.class);
        // 返回列表和分页信息
        return PageResult.createPageRes(logListRes, page.getTotal());
    }
    /** 新增日志 */
    @Transactional(propagation = Propagation.REQUIRES_NEW) // 事务回滚注解
    public void insertLog(String content, String method, String params, String result, String reason) {
        // 获取管理员id、ip
        String ipAddress = request.getHeader("X-Forwarded-For");
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
        }
        // 新增日志数据
        LogDO logDO = new LogDO();
        logDO.setLoContent(content);
        logDO.setLoMethod(method);
        logDO.setLoParams(params);
        logDO.setLoResult(result);
        logDO.setLoReason(reason);
        logDO.setLoIp(ipAddress);
        logMapper.insert(logDO);
    }
    /** 批量删除日志 */
    @LogAnnotation(title = "批量删除日志")
    public void deleteBatchData(String ids) throws Exception {
        String[] idsArray = ids.split(",");
        if (logMapper.deleteBatchIds(Arrays.asList(idsArray)) <= 0) {
            throw new Exception(MsgEnum.DELETE_FAIL.VALUE);
        }
    }
}
