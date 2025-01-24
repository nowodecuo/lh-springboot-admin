/**
 * 对象元数据 CURD拦截处理
 * @author 1874
 */
package luohao.application.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Objects;

@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Resource
    UserInfoHandler userInfoHandler;
    /** 新增 */
    @Override
    public void insertFill(MetaObject metaObject) {
        // 创建时间为空，则以当前时间为创建时间
        Object createTime = getFieldValByName("createTime", metaObject);
        if (Objects.isNull(createTime)) {
            setFieldValByName("createTime", LocalDateTime.now(), metaObject);
        }
        // 创建人id
        Object id = userInfoHandler.get(userInfoHandler.ID);
        if (!Objects.isNull(id)) {
            setFieldValByName("creator", id.toString(), metaObject);
        }
    }
    /** 更新 */
    @Override
    public void updateFill(MetaObject metaObject) {
        // 更新时间为空，则以当前时间为更新时间
        Object modifyTime = getFieldValByName("updateTime", metaObject);
        if (Objects.isNull(modifyTime)) {
            setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
        }
        // 更新人id
        Object id = userInfoHandler.get(userInfoHandler.ID);
        if (!Objects.isNull(id)) {
            setFieldValByName("updater", id.toString(), metaObject);
        }
    }
}
