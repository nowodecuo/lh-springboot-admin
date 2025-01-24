package luohao.application;

import luohao.application.service.admin.AdminService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class SpringbootAdminApplicationTests {
	@Resource
	private AdminService adminService;

	@Test
	void getJwt() {

	}
}
