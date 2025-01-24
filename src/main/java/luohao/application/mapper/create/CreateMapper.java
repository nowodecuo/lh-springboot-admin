/**
 * 创建CURD mapper
 * @author 1874
 */
package luohao.application.mapper.create;

import luohao.application.model.dataobject.create.TableInfoDO;
import luohao.application.model.dataobject.create.TableDO;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface CreateMapper {
    /** 查询可创建数据表列表 */
    List<TableDO> queryTableList();
    /** 查询表是否存在 */
    @MapKey("index")
    Map<String, Object> queryTableExists(String tableName);
    /** 查询数据表详情信息 */
    List<TableInfoDO> queryTableInfo(String tableName);
}
