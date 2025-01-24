/**
 * 文件上传
 * @author 1874
 */
package luohao.application.controller.backService.common;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import luohao.application.common.utils.Utils;
import luohao.application.common.pojo.CommonResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/file")
@Tag(name = "通用-文件上传")
public class UploadController {
    // 本地上传路径配置
    @Value("${upload-path}")
    private String uploadPath;
    /** 文件上传 */
    @PostMapping("/upload")
    @Operation(summary = "文件上传")
    @Parameter(name = "file", description = "文件", required = true)
    public CommonResult<String> fileUpload(MultipartFile file) throws IOException {
        // 上传并返回uuid文件名称
        String newFileName = Utils.fileUpload(file, uploadPath);
        return CommonResult.success(newFileName, "上传成功");
    }
    /** 下载文件 */
    @GetMapping("/download/{filename:.+}")
    @Operation(summary = "下载文件")
    @Parameter(name = "filename", description = "文件名", required = true)
    public ResponseEntity<InputStreamResource> fileDownload(@PathVariable String filename) throws IOException {
        return Utils.fileDownload(uploadPath + "/" + filename);
    }
}
