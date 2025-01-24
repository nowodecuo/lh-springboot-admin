package luohao.application.common.pojo;

import lombok.Data;

@Data
public class CreatePackage {
    private String packageName; // 创建的服务所在包名(appService|backService)
    private String tagName; // @Tag() 名称
    private String controllerName; // controller 名称
}
