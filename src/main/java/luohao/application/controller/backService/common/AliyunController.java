/**
 * 阿里云集成服务
 * @auter 1874
 */
package luohao.application.controller.backService.common;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.PutObjectRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import luohao.application.common.aliyun.config.AliyunOssConfig;
import luohao.application.common.utils.Utils;
import luohao.application.common.pojo.CommonResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/aliyun")
@Tag(name = "通用-阿里云OSS")
public class AliyunController {
    @Value("${upload-path}")
    private String uploadPath; // 本地上传路径配置
    @Resource
    private AliyunOssConfig ossConfig; // 阿里云OSS配置注入
    /** 阿里云oss上传 */
    @PostMapping("/aliyunOssUpload")
    @Operation(summary = "阿里云oss上传")
    @Parameter(name = "file", description = "文件", required = true)
    public CommonResult<Map<String, String>> aliyunOssUpload(MultipartFile file) throws IOException, OSSException {
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(ossConfig.getEndpoint(), ossConfig.getAccessKeyId(), ossConfig.getAccessKeySecret());
        try {
            if (uploadPath == null) throw new IOException("请配置上传路径upload-path");
            // 上传文件到本地并返回uuid文件名称
            String newFileName = Utils.fileUpload(file, uploadPath);
            // 文件存储到oss的完整路径
            String objectFilePath = ossConfig.getObjectPath() + newFileName;
            // 转为输入文件流
            InputStream inputStream = Files.newInputStream(Paths.get(uploadPath + newFileName));
            // 创建PutObjectRequest对象。
            PutObjectRequest putObjectRequest = new PutObjectRequest(ossConfig.getBucketName(), objectFilePath, inputStream);
            // 创建PutObject请求。
            ossClient.putObject(putObjectRequest);
            // 返回信息
            HashMap<String, String> resMap = new HashMap<>();
            resMap.put("fileName", newFileName);
            resMap.put("url", ossConfig.getUrl() + newFileName);
            return CommonResult.success(resMap, "上传成功");
        } catch (OSSException oe) {
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } finally {
            if (ossClient != null) ossClient.shutdown();
        }
        throw new IOException("上传失败");
    }
}
