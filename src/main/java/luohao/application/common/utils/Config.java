/**
 * 通常配置常量
 * @author 1874
 */
package luohao.application.common.utils;

public class Config {
    public static final String PASSWORD_PATTERN = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9]).{8,30}"; // 密码校验正则
    public static final String PASSWORD_ERROR_MSG = "密码复杂度太低（密码中必须包含字母、数字、特殊字符）"; // 密码校验错误提示
    public static final String ACCOUNT_PATTERN = "^[A-Za-z0-9]+$"; // 账号校验正则
    public static final String ACCOUNT_ERROR_MSG = "账号格式应为字母或数字"; // 账号校验错误提示
    public static final String PAGE = "page";
    public static final String LIST = "list";
    public static final String ADD = "add";
    public static final String UPDATE = "update";
    public static final String DELETE = "delete";
    public static final String BATCH_DELETE = "batchDelete";
    public static final String PRIMARY_KEY = "PRI"; // 主键KEY类型
    public static final String PRIMARY_VALUE = "id"; // 主键默认值
    public static final String CREATE_ZIP_NAME = "create-curd.zip"; // curd文件压缩包名
}
