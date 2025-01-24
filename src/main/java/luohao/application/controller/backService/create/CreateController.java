/**
 * 创建CURD
 * @author 1874
 */
package luohao.application.controller.backService.create;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import luohao.application.common.pojo.FormValidator;
import luohao.application.common.pojo.CommonResult;
import luohao.application.common.enums.MsgEnum;
import luohao.application.common.utils.Config;
import luohao.application.common.utils.Utils;
import luohao.application.model.vo.create.CreateReqVO;
import luohao.application.model.dataobject.create.TableInfoDO;
import luohao.application.model.dataobject.create.TableDO;
import luohao.application.service.create.CreateService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/create")
@Tag(name = "管理后台-创建CURD")
public class CreateController {
    // curd创建路径
    @Value("${upload-path}")
    private String uploadPath;
    @Resource
    private CreateService createService;
    /** 查询可创建的数据表列表 */
    @PostMapping("/createTableList")
    @Operation(summary = "查询可创建的数据表列表")
    public CommonResult<List<TableDO>> createTableList() {
        List<TableDO> list = createService.queryTableList();
        return CommonResult.success(list);
    }
    /** 查询数据表详情信息 */
    @PostMapping("/createTableInfo")
    @Operation(summary = "查询数据表详情信息")
    @Parameter(name = "tableName", description = "表名")
    public CommonResult<List<TableInfoDO>> createTableInfo(@RequestBody Map<String, String> map) throws Exception {
        String tableName = map.get("tableName");
        Utils.formCheck(FormValidator.setFormCheck(tableName, "表名不能为空"));
        List<TableInfoDO> list = createService.queryTableInfo(tableName);
        return CommonResult.success(list);
    }
    /** 发起创建curd */
    @PostMapping("/createCurd")
    @Operation(summary = "发起创建curd")
    public CommonResult<HashMap<String, String>> createCurd(@RequestBody @Validated CreateReqVO params) throws IOException {
        String filePath = createService.createCurd(params);
        HashMap<String, String> resultMap = new HashMap<>();
        resultMap.put("filePath", filePath);
        return CommonResult.success(resultMap, MsgEnum.CREATE_SUCCESS.VALUE);
    }
    /** 下载已创建的curd文件 */
    @PostMapping("/createFileDownload")
    @Operation(summary = "下载已创建的curd文件")
    public void createFileDownload(HttpServletResponse response, @RequestBody Map<String, String> map) throws Exception {
        String filePath = map.get("filePath");
        // 校验
        Utils.formCheck(FormValidator.setFormCheck(filePath, "下载文件不能为空"));
        // 判断文件是否存在
        File file = new File(filePath);
        if (!file.exists()) {
            throw new IOException("文件已失效或未找到文件，请重新生成下载");
        }
        // 设置响应类型
        response.setContentType("application/force-download");//应用程序强制下载
        // 读取文件
        InputStream inputStream = Files.newInputStream(file.toPath());
        //设置响应头，对文件进行url编码
        response.setHeader("Content-Disposition", "attachment;filename="+Config.CREATE_ZIP_NAME);
        response.setContentLength(inputStream.available());
        // 读文件写入http响应
        OutputStream outputStream = response.getOutputStream();
        int len;
        byte[] b = new byte[1024];
        while((len = inputStream.read(b)) != -1){
            outputStream.write(b, 0, len);
        }
        outputStream.flush();
        outputStream.close();
        inputStream.close();
    }
}
