/**
 * 对象转换
 * @author 1874
 */
package luohao.application.common.utils;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ConvertUtils {
    @Resource
    private ModelMapper modelMapper;
    /** 列表转换 */
    public <T, R> List<R> convertList(List<T> sourceList, Class<R> targetClass) {
        return sourceList.stream().map(source -> modelMapper.map(source, targetClass)).collect(Collectors.toList());
    }
    /** 对象转换 */
    public <T, R> R convert(T source, Class<R> targetClass) {
        return modelMapper.map(source, targetClass);
    }
}
