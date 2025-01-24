/**
 * 创建curd service
 * @author 1874
 */
package luohao.application.service.create;

import lombok.extern.slf4j.Slf4j;
import luohao.application.common.enums.MsgEnum;
import luohao.application.common.pojo.CreatePackage;
import luohao.application.common.utils.Compress;
import luohao.application.common.utils.Config;
import luohao.application.common.utils.CreateTemplate;
import luohao.application.common.utils.Utils;
import luohao.application.model.vo.create.CreateReqVO;
import luohao.application.mapper.create.CreateMapper;
import luohao.application.model.dataobject.create.TableInfoDO;
import luohao.application.model.dataobject.create.TableDO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class CreateService {
    @Value("${create.file-path}")
    private String filePath; // 文件保存路径

    @Value("${upload-path}")
    private String uploadPath; // 下载文件保存路径
    @Resource
    private CreateTemplate createTemplate;
    @Resource
    private CreateMapper createMapper;
    @Resource
    private Compress compress;
    private final String backstagePath = "/backstage"; // 后端文件目录
    private final String reactPath = "/react"; // 前端文件目录
    /** 查询所有数据表列表 */
    public List<TableDO> queryTableList() {
        return createMapper.queryTableList();
    }
    /** 查询数据表详情 */
    public List<TableInfoDO> queryTableInfo(String tableName) throws Exception {
        Map<String, Object> exists = createMapper.queryTableExists(tableName);
        if (exists.size() == 0) {
            throw new Exception(MsgEnum.TABLE_NOT_EXISTS.VALUE);
        }
        return createMapper.queryTableInfo(tableName);
    }
    /** 创建curd方法 */
    public String createCurd(CreateReqVO params) throws IOException {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        // 创建后端
        this.createServiceFile(params, CreateTemplate.CONTROLLER, uuid,false);
        this.createServiceFile(params, CreateTemplate.SERVICE, uuid,false);
        this.createServiceFile(params, CreateTemplate.MAPPER, uuid,false);
        this.createServiceFile(params, CreateTemplate.MAPPER_XML, uuid,false);
        this.createServiceFile(params, CreateTemplate.DATA_OBJECT, uuid,true);
        this.createServiceFile(params, CreateTemplate.LIST_REQ_VO, uuid,true);
        this.createServiceFile(params, CreateTemplate.LIST_RESP_VO, uuid,true);
        this.createServiceFile(params, CreateTemplate.ACTION_VO, uuid,true);
        // 创建前端
        this.createReactFile(params, CreateTemplate.REACT_PAGE, uuid);
        this.createReactFile(params, CreateTemplate.REACT_SERVICE, uuid);
        this.createReactFile(params, CreateTemplate.REACT_INTERFACE, uuid);
        this.createReactFile(params, CreateTemplate.REACT_CONFIG, uuid);
        // 将创建的curd文件打包成zip
        String zipFilePath = filePath + "/" + uuid;
        String zipCreatePath = uploadPath + "/" + uuid + ".zip";
        compress.compressFolderRun(zipFilePath, zipCreatePath);
        // 设置60秒后执行任务
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        // 新任务执行的操作, 删除create目录和zip包
        Runnable task = () -> {
            Utils.deleteFolder(zipFilePath);
            File file = new File(zipCreatePath);
            if (file.exists()) file.delete();
        };
        // 延迟执行新任务
        scheduler.schedule(task, 60, TimeUnit.SECONDS);
        return zipCreatePath;
    }
    /**
     * 创建服务端文件
     * @param params 创建入参
     * @param type 创建文件类型
     * @param uuid uuid 用于多个请求创建区分
     * @param isModel 是否是模型
     */
    private void createServiceFile(CreateReqVO params, String type, String uuid, Boolean isModel) throws IOException {
        CreatePackage createPackage = createTemplate.getCreatePackageName(params.getCreateType());
        String className = createPackage.getControllerName() + params.getClassName();
        String cnClassName = params.getCnClassName();
        String methodName = params.getMethodName();
        String dirPath = filePath + "/" + uuid + backstagePath;
        String dir;
        if (isModel) {
            // 如果是VO
            String[] viewObjectArray = new String[]{ CreateTemplate.ACTION_VO, CreateTemplate.LIST_REQ_VO, CreateTemplate.LIST_RESP_VO };
            dir = Arrays.asList(viewObjectArray).contains(type) ? dirPath+"/model/vo/"+methodName : dirPath+"/model/"+type+"/"+methodName;
        } else {
            dir = dirPath+"/"+type+"/"+methodName;
        }
        // 没有目录则创建
        File file = new File(dir);
        if (!file.exists()) file.mkdirs();
        // 创建的文件路径、内容
        String createFileDir = "";
        String fileContent = "";
        switch (type) {
            case CreateTemplate.CONTROLLER:
                createFileDir = dir + "/" + className + "Controller.java";
                fileContent = createTemplate.createControllerTemplate(params);
                break;
            case CreateTemplate.SERVICE:
                createFileDir = dir + "/" + className + "Service.java";
                fileContent = createTemplate.createServiceTemplate(params);
                break;
            case CreateTemplate.MAPPER:
                createFileDir = dir + "/" + className + "Mapper.java";
                fileContent = createTemplate.createMapperTemplate(params);
                break;
            case CreateTemplate.MAPPER_XML:
                createFileDir = dir + "/" + className + "Mapper.xml";
                fileContent = createTemplate.createMapperXmlTemplate(params);
                break;
            case CreateTemplate.DATA_OBJECT:
                createFileDir = dir + "/" + className + "DO.java";
                fileContent = createTemplate.createDataObjectTemplate(params);
                break;
            case CreateTemplate.LIST_REQ_VO:
                createFileDir = dir + "/" + className + "ListReqVO.java";
                fileContent = createTemplate.createViewObjectTemplate(params, CreateTemplate.LIST_REQ_VO);
                break;
            case CreateTemplate.LIST_RESP_VO:
                createFileDir = dir + "/" + className + "ListRespVO.java";
                fileContent = createTemplate.createViewObjectTemplate(params, CreateTemplate.LIST_RESP_VO);
                break;
            case CreateTemplate.ACTION_VO:
                createFileDir = dir + "/" + className + "ActionVO.java";
                fileContent = createTemplate.createViewObjectTemplate(params, CreateTemplate.ACTION_VO);
                break;
        }
        // 生成文件内容并生成文件
        try (FileWriter fileWriter = new FileWriter(createFileDir)){
            fileWriter.write(fileContent);
        } catch (IOException e) {
            throw new IOException(cnClassName + "["+type+"]" + MsgEnum.CREATE_FAIL.VALUE);
        }
    }
    // ====================================================================[创建前端]========================================================================================= */
    /**
     * 创建前端React文件
     * @param params 创建入参
     * @param type 创建文件类型
     * @param uuid uuid 用于多个请求创建区分
     */
    private void createReactFile(CreateReqVO params, String type, String uuid) throws IOException {
        String cnClassName = params.getCnClassName();
        String methodName = params.getMethodName();
        String dir = filePath + "/" + uuid + reactPath + "/" + type + "/" + methodName; // 存放路径
        // 没有目录则创建
        File file = new File(dir);
        if (!file.exists()) file.mkdirs();
        // 创建模板
        String createFileDir = "";
        String fileContent = "";
        switch (type) {
            case CreateTemplate.REACT_PAGE:
                createFileDir = dir + "/index.tsx";
                fileContent = createTemplate.createReactPageTemplate(params);
                break;
            case CreateTemplate.REACT_SERVICE:
                createFileDir = dir + "/index.ts";
                fileContent = createTemplate.createReactServiceTemplate(params);
                break;
            case CreateTemplate.REACT_INTERFACE:
                createFileDir = dir + "/index.d.ts";
                fileContent = createTemplate.createReactInterfaceTemplate(params);
                break;
            case CreateTemplate.REACT_CONFIG:
                createFileDir = dir + "/index.ts";
                fileContent = createTemplate.createReactConfigTemplate(params);
                break;
        }
        // 生成文件内容并生成文件
        try (FileWriter fileWriter = new FileWriter(createFileDir)) {
            fileWriter.write(fileContent);
        } catch (IOException e) {
            throw new IOException(cnClassName + "["+type+"]" + MsgEnum.CREATE_FAIL.VALUE);
        }
    }
}
