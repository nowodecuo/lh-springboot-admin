/**
 * App端
 * @author 1874
 */
package luohao.application.controller.appService.index;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/index")
@Tag(name = "APP应用-创建CURD")
public class IndexController {
    @GetMapping("/hello")
    @Operation(summary = "欢迎使用")
    public String welcome() {
        return "WELCOME TO USE LH-SPRING-ADMIN";
    }
}
