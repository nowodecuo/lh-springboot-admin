/**
 * tb_log 日志 mapper
 * @author 1874
 */
package luohao.application.mapper.log;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import luohao.application.model.vo.log.LogListReqVO;
import luohao.application.model.dataobject.log.LogDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface LogMapper extends BaseMapper<LogDO> {
    /** 日志列表分页 */
    IPage<LogDO> findPageData(Page<LogDO> page, @Param("reqVo") LogListReqVO params);
}
