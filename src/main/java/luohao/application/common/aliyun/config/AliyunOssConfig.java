/**
 * 阿里云OSS配置
 * @author 1874
 *
 * aliyun:
 *   oss:
 *      endpoint: https://oss-cn-chengdu.aliyuncs.com # 地域节点
 *      accessKeyId: xxx
 *      accessKeySecret: xxx
 *      bucketName: xxxx # 对象存储 bucket名称
 *      objectPath: xxx/ # 对象存储路径
 */
package luohao.application.common.aliyun.config;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(value = "aliyun.oss")
@Data
public class AliyunOssConfig {
    private String url; // 文件访问域名
    private String endpoint; // 阿里云 oss 地域节点
    private String accessKeyId; // 阿里云 oss accessKeyId
    private String accessKeySecret; // 阿里云 oss accessKeySecret
    private String objectPath; // 阿里云 oss 对象存储位置
    private String bucketName; // 阿里云 oss bucket名称
}
