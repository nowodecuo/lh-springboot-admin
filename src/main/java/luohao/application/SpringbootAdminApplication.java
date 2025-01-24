/**
 * 引导类，springboot项目入口
 */
package luohao.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan // 开启servlet组件支持
@SpringBootApplication
public class SpringbootAdminApplication {
	/** 启动springboot的应用，返回spring的IOC容器 */
	public static void main(String[] args) {
		SpringApplication.run(SpringbootAdminApplication.class, args);
	}
}
