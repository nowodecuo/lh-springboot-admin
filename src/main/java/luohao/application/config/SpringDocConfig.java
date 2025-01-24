/**
 * 接口文档信息配置
 * @author 1874
 */
package luohao.application.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().info(new Info().title("lh-springboot-admin").description("API文档").version("v1.0.0").contact(new Contact().name("1874").email("lh19951007@163.com")));
    }
}
