/**
 * web配置处理
 * @author 1874
 */
package luohao.application.config;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import lombok.extern.slf4j.Slf4j;
import luohao.application.handler.InterceptorAppHandler;
import luohao.application.handler.InterceptorHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@EnableWebMvc
@Configuration
@Slf4j
public class MyWebMvcConfigurer implements WebMvcConfigurer {
    // 本地上传路径配置
    @Value("${upload-path}")
    private String uploadPath;
    @Resource
    private WebApiConfig webApiConfig;
    @Resource
    private InterceptorHandler interceptorHandler; // 注入管理端拦截器
    @Resource
    private InterceptorAppHandler interceptorAppHandler; // 注入APP端拦截器
    /**
     * 参数转换配置
     */
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(
                SerializerFeature.DisableCircularReferenceDetect,  // 防止循环引用
                SerializerFeature.WriteNullListAsEmpty, // 空集合返回[],不返回null
                SerializerFeature.WriteMapNullValue, // 保留空值字段
                SerializerFeature.PrettyFormat, // 格式化输出
                SerializerFeature.WriteNullBooleanAsFalse, // Boolean字段如果为null，输出为false，而非null
                SerializerFeature.WriteNullStringAsEmpty // String字段如果为null，输出为""，而非null
        );
        // Long转String
        SerializeConfig serializeConfig = SerializeConfig.globalInstance;
        serializeConfig.put(Long.class, ToStringSerializer.instance);
        serializeConfig.put(Long.TYPE, ToStringSerializer.instance);
        // 日期格式
        fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");
        fastJsonConfig.setSerializeConfig(serializeConfig);
        // 创建FastJsonHttpMessageConverter
        FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
        fastJsonHttpMessageConverter.setFastJsonConfig(fastJsonConfig);
        converters.add(converters.size() - 1, fastJsonHttpMessageConverter);
    }
    /**
     * 静态资源注册
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 将/static/** 路径下所有文件静态映射访问到uploadPath目录下
        registry.addResourceHandler("/static/**").addResourceLocations(ResourceUtils.FILE_URL_PREFIX + uploadPath);
        // knife4j swagger
        registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
    /**
     * 拦截器注册
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        ArrayList<String> pattern = new ArrayList<>();
        pattern.add("/admin-api/login/**");
        pattern.add("/admin-api/file/download/**");
        registry.addInterceptor(interceptorHandler).addPathPatterns("/admin-api/**").excludePathPatterns(pattern);
        registry.addInterceptor(interceptorAppHandler).addPathPatterns("/app-api/**");
    }
    /**
     * api前缀配置
     */
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurePathMatch(configurer, webApiConfig.getAppApi());
        configurePathMatch(configurer, webApiConfig.getAdminApi());
    }
    private void configurePathMatch(PathMatchConfigurer configurer, WebApiConfig.Api api) {
        // 创建路径匹配，以"."分隔
        AntPathMatcher antPathMatcher = new AntPathMatcher(".");
        // 指定匹配前缀
        configurer.addPathPrefix(api.getPrefix(), item ->
            item.isAnnotationPresent(RestController.class) && antPathMatcher.match(api.getControllerPath(), item.getPackage().getName())
        );
    }
}
