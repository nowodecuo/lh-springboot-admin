/**
 * Api前缀配置
 * @author 1874
 */
package luohao.application.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@Component
public class WebApiConfig {
    Api appApi = new Api("app-api", "**.controller.appService.**"); // 管理端应用
    Api adminApi = new Api("admin-api", "**.controller.backService.**"); // APP端应用

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Api {
        private String prefix;
        private String controllerPath;
    }
}
