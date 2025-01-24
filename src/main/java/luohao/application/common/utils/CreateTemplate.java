/**
 * 创建curd集成方法
 * @author 1874
 */
package luohao.application.common.utils;

import cn.hutool.core.util.StrUtil;
import luohao.application.common.pojo.CreatePackage;
import luohao.application.model.vo.create.CreateReqVO;
import luohao.application.model.vo.create.FieldsConfigVO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CreateTemplate {
    public static final String CREATE_APP = "APP";
    public static final String CREATE_BACK = "BACK";
    public static final String CONTROLLER = "controller";
    public static final String SERVICE = "service";
    public static final String MAPPER = "mapper";
    public static final String MAPPER_XML = "mapperXml";
    public static final String DATA_OBJECT = "dataobject";
    public static final String ACTION_VO = "actionReqVo";
    public static final String LIST_REQ_VO = "listReqVo";
    public static final String LIST_RESP_VO = "listRespVo";
    public static final String REACT_PAGE = "pages";
    public static final String REACT_SERVICE = "service";
    public static final String REACT_INTERFACE = "typings";
    public static final String REACT_CONFIG = "config";
    /** 根据创建类型获取创建的文件名称(默认创建后端服务) */
    public CreatePackage getCreatePackageName(String createType) {
        CreatePackage createPackage = new CreatePackage();
        if (createType.equals(CreateTemplate.CREATE_APP)) {
            createPackage.setPackageName("appService");
            createPackage.setTagName("APP应用");
            createPackage.setControllerName("App");
        } else {
            createPackage.setPackageName("backService");
            createPackage.setTagName("管理后台");
            createPackage.setControllerName("");
        }
        return createPackage;
    }
    /** controller模板生成 */
    public String createControllerTemplate(CreateReqVO param) {
        CreatePackage createPackage = this.getCreatePackageName(param.getCreateType());
        String className = createPackage.getControllerName() + param.getClassName();
        String methodName = param.getMethodName();
        String cnClassName = param.getCnClassName();
        List<String> functions = param.getFunctions();
        // package
        String template = "package luohao.application.controller."+createPackage.getPackageName()+"."+methodName+";\n\n" +
                "import io.swagger.v3.oas.annotations.Operation;\n" +
                "import io.swagger.v3.oas.annotations.Parameter;\n" +
                "import io.swagger.v3.oas.annotations.tags.Tag;\n" +
                "import luohao.application.common.enums.MsgEnum;\n" +
                "import luohao.application.common.pojo.PageResult;\n" +
                "import luohao.application.common.utils.Config;\n" +
                "import luohao.application.model.vo."+methodName+".*;\n" +
                "import luohao.application.model.common.ActionAdd;\n" +
                "import luohao.application.model.common.ActionUpdate;\n" +
                "import luohao.application.common.pojo.FormValidator;\n" +
                "import luohao.application.common.pojo.CommonResult;\n" +
                "import luohao.application.service."+methodName+"."+className+"Service;\n" +
                "import luohao.application.common.utils.Utils;\n" +
                "import org.springframework.validation.annotation.Validated;\n" +
                "import org.springframework.web.bind.annotation.*;\n" +
                "import javax.annotation.Resource;\n" +
                "import java.util.Map;\n";
        // class start
        template += "@RestController\n" +
                "@RequestMapping(\"/"+methodName+"\")\n" +
                "@Tag(name = \""+createPackage.getTagName()+"-"+cnClassName+"\")\n" +
                "public class "+className+"Controller {\n";
        // 依赖
        template += "    @Resource\n" +
                "    private "+className+"Service "+methodName+"Service;\n";
        // 遍历创建的方法
        for (String item : functions) {
            switch (item) {
                case Config.LIST:
                    template += "    /** " + cnClassName + "列表 */\n" +
                            "    @PostMapping(\"/page\")\n" +
                            "    @Operation(summary = \""+cnClassName+"分页\")\n" +
                            "    public CommonResult<PageResult<"+className+"ListRespVO>> " + methodName + "Page(@RequestBody " +className+"ListReqVO params) {\n" +
                            "        PageResult<"+className+"ListRespVO> pageResult = "+methodName+"Service.queryTablePage(params);\n" +
                            "        return CommonResult.success(pageResult);\n" +
                            "    }\n";
                    break;
                case Config.ADD:
                    template += "    /** " + cnClassName + "新增 */\n" +
                            "    @PostMapping(\"/add\")\n" +
                            "    @Operation(summary = \""+cnClassName+"新增\")\n" +
                            "    public CommonResult<Boolean> " + methodName + "Add(@RequestBody @Validated({ActionAdd.class}) " +className+"ActionReqVO params) throws Exception {\n" +
                            "        "+methodName+"Service.insertData(params);\n" +
                            "        return CommonResult.success(true, MsgEnum.ADD_SUCCESS.VALUE);\n" +
                            "    }\n";
                    break;
                case Config.UPDATE:
                    template += "    /** " + cnClassName + "更新 */\n" +
                            "    @PostMapping(\"/update\")\n" +
                            "    @Operation(summary = \""+cnClassName+"更新\")\n" +
                            "    public CommonResult<Boolean> " + methodName + "Update(@RequestBody @Validated({ActionUpdate.class}) " +className+"ActionReqVO params) throws Exception {\n" +
                            "        "+methodName+"Service.updateData(params);\n" +
                            "        return CommonResult.success(true, MsgEnum.UPDATE_SUCCESS.VALUE);\n" +
                            "    }\n";
                    break;
                case Config.DELETE:
                    template += "    /** " + cnClassName + "删除 */\n" +
                            "    @PostMapping(\"/delete\")\n" +
                            "    @Operation(summary = \""+cnClassName+"删除\")\n" +
                            "    public CommonResult<Boolean> " + methodName + "Delete(@RequestBody Map<String, String> params) throws Exception {\n" +
                            "        Long id = Long.valueOf(map.get(\"id\"));\n" +
                            "        // 校验数据\n" +
                            "        Utils.formCheck(FormCheckDto.setFormCheck(id, \"ID不能为空\"));\n" +
                            "        // 验证通过后执行删除操作\n" +
                            "        "+methodName+"Service.deleteData(id);\n" +
                            "        return CommonResult.success(true, MsgEnum.DELETE_SUCCESS.VALUE);\n" +
                            "    }\n";
                    break;
                case Config.BATCH_DELETE:
                    template += "    /** " + cnClassName + "批量删除 */\n" +
                            "    @PostMapping(\"/BatchDelete\")\n" +
                            "    @Operation(summary = \""+cnClassName+"批量删除\")\n" +
                            "    public CommonResult<Boolean> " + methodName + "BatchDelete(@RequestBody Map<String, String> params) throws Exception {\n" +
                            "        String ids = String.valueOf(map.get(\"ids\"));\n" +
                            "        // 校验数据\n" +
                            "        Utils.formCheck(FormCheckDto.setFormCheck(ids, \"ID不能为空\"));\n" +
                            "        // 验证通过后执行删除操作\n" +
                            "        "+methodName+"Service.deleteBatchData(ids);\n" +
                            "        return CommonResult.success(true, MsgEnum.DELETE_SUCCESS.VALUE);\n" +
                            "    }\n";
                    break;
            }
        }
        // class end
        template += "}";
        return template;
    }
    /** service模板生成 */
    public String createServiceTemplate(CreateReqVO param) {
        CreatePackage createPackage = this.getCreatePackageName(param.getCreateType());
        String className = createPackage.getControllerName() + param.getClassName();
        String methodName = param.getMethodName();
        String cnClassName = param.getCnClassName();
        List<String> functions = param.getFunctions();
        // package
        String template = "package luohao.application.service."+methodName+";\n" +
                "\n" +
                "import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;\n" +
                "import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;\n" +
                "import com.baomidou.mybatisplus.extension.plugins.pagination.Page;\n" +
                "import luohao.application.annotation.LogAnnotation;\n" +
                "import luohao.application.common.enums.MsgEnum;\n" +
                "import luohao.application.common.utils.ConvertUtils;\n" +
                "import luohao.application.common.utils.Utils;\n" +
                "import luohao.application.model.vo."+methodName+".*;\n" +
                "import luohao.application.common.pojo.PageResult;\n" +
                "import luohao.application.mapper."+methodName+"."+className+"Mapper;\n" +
                "import luohao.application.model.dataobject."+methodName+"."+className+"DO;\n" +
                "import org.springframework.beans.factory.annotation.Value;\n" +
                "import org.springframework.stereotype.Service;\n" +
                "\n" +
                "import javax.annotation.Resource;\n" +
                "import java.util.Arrays;\n" +
                "import java.util.List;\n";
        // class start
        template += "@Service\n" +
                "public class "+className+"Service {\n";
        // 依赖
        template += "    @Resource\n" +
                "    private "+className+"Mapper "+methodName+"Mapper;\n" +
                "    @Resource\n" +
                "    private ConvertUtils convertUtils;\n";
        // 遍历创建的方法
        for (String item : functions) {
            switch (item) {
                case Config.LIST:
                    template += "    /** 查询"+cnClassName+"分页 */\n" +
                            "    public PageResult<AdminListRespVO> queryTablePage("+className+"ListReqVO params) {\n" +
                            "        Page<"+className+"DO> page = new Page<>(params.getPageNum(), params.getPageSize());\n" +
                            "        adminMapper.findPage(page, params);\n" +
                            "        // do转vo\n" +
                            "        List<"+className+"ListRespVO> list = convertUtils.convertList(page.getRecords(), "+className+"ListRespVO.class);\n" +
                            "        // 返回列表和分页信息\n" +
                            "        return PageResult.createPageRes(list, page.getTotal());\n" +
                            "    }\n";
                    break;
                case Config.ADD:
                    template += "    /** 新增"+cnClassName+" */\n" +
                            "    @LogAnnotation(title = \"新增"+cnClassName+"\")\n" +
                            "    public void insertData("+className+"ActionReqVO params) throws Exception {\n" +
                            "        "+className+"DO "+methodName+"DO = convertUtils.convert(params, "+className+"DO.class);\n" +
                            "        if ("+methodName+"Mapper.insert(params) <= 0) {\n" +
                            "            throw new Exception(MsgEnum.ADD_FAIL.VALUE);\n" +
                            "        }\n" +
                            "    }\n";
                    break;
                case Config.UPDATE:
                    template += "    /** 更新"+cnClassName+" */\n" +
                            "    @LogAnnotation(title = \"修改"+cnClassName+"\")\n" +
                            "    public void updateData("+className+"ActionReqVO params) throws Exception {\n" +
                            "        "+className+"DO "+methodName+"DO = convertUtils.convert(params, "+className+"DO.class);\n" +
                            "        if ("+methodName+"Mapper.updateById(params) <= 0) {\n" +
                            "            throw new Exception(MsgEnum.UPDATE_FAIL.VALUE);\n" +
                            "        }\n" +
                            "    }\n";
                    break;
                case Config.DELETE:
                    template += "    /** 删除"+cnClassName+" */\n" +
                            "    @LogAnnotation(title = \"删除"+cnClassName+"\")\n" +
                            "    public void deleteData(Long id) throws Exception {\n" +
                            "        if ("+methodName+"Mapper.deleteById(id) <= 0) {\n" +
                            "            throw new Exception(MsgEnum.DELETE_FAIL.VALUE);\n" +
                            "        }\n" +
                            "    }\n";
                    break;
                case Config.BATCH_DELETE:
                    template += "    /** 批量删除"+cnClassName+" */\n" +
                            "    @LogAnnotation(title = \"批量删除"+cnClassName+"\")\n" +
                            "    public void deleteBatchData(String ids) throws Exception {\n" +
                            "        String[] idsArr = ids.split(\",\");\n" +
                            "        if ("+methodName+"Mapper.deleteBatchIds(Arrays.asList(idsArray)) <= 0) {\n" +
                            "            throw new Exception(MsgEnum.DELETE_FAIL.VALUE);\n" +
                            "        }\n" +
                            "    }\n";
                    break;
            }
        }
        // class end
        template += "}";
        return template;
    }
    /** mapper模板生成 */
    public String createMapperTemplate(CreateReqVO param) {
        CreatePackage createPackage = this.getCreatePackageName(param.getCreateType());
        String className = createPackage.getControllerName() + param.getClassName();
        String cnClassName = param.getCnClassName();
        String methodName = param.getMethodName();
        List<String> functions = param.getFunctions();
        List<FieldsConfigVO> fieldsConfigVO = param.getFieldsConfig();
        // 主键
        String primaryKey = this.getPrimaryKey(fieldsConfigVO);
        // 遍历转换
        String queryWrapper = "";
        for (int i = 0; i < fieldsConfigVO.size(); i++) {
            FieldsConfigVO config = fieldsConfigVO.get(i);
            String fileName = config.getField(); // 字段名
            String fileCamelCase = StrUtil.toCamelCase(fileName); // 小驼峰字段名
            String fileUpperCaseStr = fileCamelCase.substring(0, 1).toUpperCase() + fileCamelCase.substring(1);; // 首字母转大写
            // 主键不生成条件
            if (!fileName.equals(primaryKey)) {
                queryWrapper += "        queryWrapper.eq(\""+fileName+"\", params.get"+fileUpperCaseStr+"())\n";
            }
        }
        // package
        String template = "package luohao.application.mapper."+methodName+";\n" +
                "\n" +
                "import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;\n" +
                "import com.baomidou.mybatisplus.core.mapper.BaseMapper;\n" +
                "import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;\n" +
                "import luohao.application.model.dataobject."+methodName+"."+className+"DO;\n" +
                "import org.apache.ibatis.annotations.Mapper;\n" +
                "\n" +
                "import java.util.Arrays;\n" +
                "import java.util.List;\n\n";
        // class start
        template += "@Mapper\n" +
                "public interface "+className+"Mapper extends BaseMapper<"+className+"DO> {\n";
        // 遍历创建的方法
        for (String item : functions) {
            if (item.equals(Config.LIST)) {
                template += "    /** " + cnClassName + "列表分页 */\n" +
                        "    default IPage<"+className+"DO> findPage(Page<"+className+"DO> page, AdminListReqVO params) {\n" +
                        "        QueryWrapper<AdminDO> queryWrapper = new QueryWrapper<>();\n" +queryWrapper+
                        "        queryWrapper.orderByDesc(\""+primaryKey+"\");\n" +
                        "        return selectPage(page, queryWrapper);\n" +
                        "    };\n";
            }
        }
        // class end
        template += "}";
        return template;
    }
    /** sql xml模板生成 */
    public String createMapperXmlTemplate(CreateReqVO param) {
        CreatePackage createPackage = this.getCreatePackageName(param.getCreateType());
        String className = createPackage.getControllerName() + param.getClassName();
        String methodName = param.getMethodName();
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">\n" +
            "<mapper namespace=\"luohao.application.mapper."+methodName+"."+className+"Mapper\">\n\n" +
            "</mapper>\n";
    }
    /** sql xml模板生成(sql语句) [暂时弃用] */
    public String createSqlXmlTemplate(CreateReqVO param) {
        String tableName = param.getTableName();
        String className = param.getClassName();
        String cnClassName = param.getCnClassName();
        List<String> functions = param.getFunctions();
        List<FieldsConfigVO> fieldsConfigVO = param.getFieldsConfig();
        // xml start
        String template = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">\n" +
                "<mapper namespace=\"luohao.application.mapper."+className+"Mapper\">\n";
        // 主键
        String primaryKey = this.getPrimaryKey(fieldsConfigVO);
        String primaryKeyCamelCase = StrUtil.toCamelCase(primaryKey);
        // 遍历转换
        String listWhereString = ""; // 列表 where 字段
        ArrayList<String> addValuesArray = new ArrayList<>(); // 新增 values 字段
        ArrayList<String> updateSetArray = new ArrayList<>(); // 新增 set 字段
        ArrayList<String> fileArray = new ArrayList<>(); // 字段名数组
        for (FieldsConfigVO config : fieldsConfigVO) {
            String fileName = config.getField(); // 字段名
            String fileCamelCase = StrUtil.toCamelCase(fileName); // 小驼峰字段名
            // 主键不生成条件
            if (!fileName.equals(primaryKey)) {
                listWhereString += "<if test=\""+fileCamelCase+" != null\">and "+fileName+"=#{"+fileCamelCase+"}</if>\n";
                addValuesArray.add("#{"+fileCamelCase+"}");
                updateSetArray.add(fileName+"=#{"+StrUtil.toCamelCase(fileName)+"}");
            }
            fileArray.add(config.getField());
        }
        // 逗号分隔字符串
        String fileString = String.join(",", fileArray);
        // include sql
        template += "<!-- 查询字段sql -->\n" +
                "    <sql id=\"selectSql\">\n" +
                "        select "+fileString+" from "+tableName+"\n" +
                "    </sql>\n";
        // 遍历创建的方法
        for (String item : functions) {
            switch (item) {
                case Config.LIST:
                    template += "    <!-- "+cnClassName+"分页 -->\n" +
                            "    <select id=\"findPageData\" resultType=\""+className+"Model\">\n" +
                            "        <include refid=\"selectSql\" />\n" +
                            "        <where>\n"+
                            "           "+listWhereString+
                            "        </where>\n" +
                            "        order by "+primaryKey+" desc\n" +
                            "    </select>\n";
                    break;
                case Config.ADD:
                    String valuesString = String.join(",", addValuesArray);
                    template += "    <!-- "+cnClassName+"新增 -->\n" +
                            "    <insert id=\"insertData\" parameterType=\""+className+"Model\">\n" +
                            "        insert into "+tableName+" ("+fileString+")\n" +
                            "        values ("+valuesString+")\n" +
                            "    </insert>\n";
                    break;
                case Config.UPDATE:
                    String updateSetString = String.join(",", updateSetArray);
                    template += "    <!-- "+cnClassName+"更新 -->\n" +
                            "    <update id=\"updateData\" parameterType=\""+className+"Model\">\n" +
                            "        update "+tableName+" set\n" +
                            "        "+updateSetString+"\n" +
                            "        where "+primaryKey+"=#{"+primaryKeyCamelCase+"}\n" +
                            "    </update>\n";
                    break;
                case Config.DELETE:
                    template += "    <!-- "+cnClassName+"删除 -->\n" +
                            "    <delete id=\"deleteData\" parameterType=\"integer\">\n" +
                            "        delete from "+tableName+" where "+primaryKey+"=#{id}\n" +
                            "    </delete>\n";
                    break;
                case Config.BATCH_DELETE:
                    template += "    <!-- "+cnClassName+"批量删除 -->\n" +
                            "    <delete id=\"deleteBatchData\" parameterType=\"arraylist\">\n" +
                            "        delete from "+tableName+" where "+primaryKey+" in\n" +
                            "        <foreach collection=\"ids\" index=\"index\" item=\"item\" open=\"(\" separator=\",\" close=\")\">#{item}</foreach>\n" +
                            "    </delete>\n";
                    break;
            }
        }
        // xml end
        template += "</mapper>";
        return template;
    }
    /** DO 模板生成 */
    public String createDataObjectTemplate(CreateReqVO param) {
        CreatePackage createPackage = this.getCreatePackageName(param.getCreateType());
        String className = createPackage.getControllerName() + param.getClassName();
        String methodName = param.getMethodName();
        String tableName = param.getTableName();
        List<FieldsConfigVO> fieldsConfigVO = param.getFieldsConfig();
        // package
        String template = "package luohao.application.model.dataobject."+methodName+";\n" +
                "import com.baomidou.mybatisplus.annotation.IdType;\n" +
                "import com.baomidou.mybatisplus.annotation.TableId;\n" +
                "import com.baomidou.mybatisplus.annotation.TableName;\n" +
                "import com.fasterxml.jackson.annotation.JsonInclude;\n" +
                "import lombok.Data;\n" +
                "import luohao.application.model.dataobject.base.BaseDO;\n";
        // class start
        template += "@Data\n" +
                "@TableName(value = \""+tableName+"\")\n" +
                "@JsonInclude(value = JsonInclude.Include.NON_NULL)\n" +
                "public class "+className+"DO extends BaseDO {\n";
        // 主键
        String primaryKey = this.getPrimaryKey(fieldsConfigVO);
        // 遍历字段配置
        for (FieldsConfigVO config : fieldsConfigVO) {
            String fileName = config.getField();
            String fileCamelCase = StrUtil.toCamelCase(fileName); // 小驼峰字段名
            String comment = config.getComment();
            // 主键为Long其他为string
            if (fileName.equals(primaryKey)) {
                template += "    @TableId(type = IdType.AUTO)\n" +
                        "    private Long "+fileCamelCase+"; // "+comment+"\n";
            } else {
                template += "    private String "+fileCamelCase+"; // "+comment+"\n";
            }
        }
        // class end
        template += "}";
        return template;
    }
    /** view object模板生成 */
    public String createViewObjectTemplate(CreateReqVO param, String type) {
        CreatePackage createPackage = this.getCreatePackageName(param.getCreateType());
        String className = createPackage.getControllerName() + param.getClassName();
        String methodName = param.getMethodName();
        List<FieldsConfigVO> fieldsConfigVO = param.getFieldsConfig();
        // class start
        String template = "package luohao.application.model.vo."+methodName+";\n" +
                "\n" +
                "import io.swagger.v3.oas.annotations.media.Schema;\n" +
                "import lombok.Data;\n";
        // 分页入参
        if (type.equals(CreateTemplate.LIST_REQ_VO)) {
            template += "import luohao.application.common.pojo.PageParam;\n" +
                    "@Data\n" +
                    "public class "+className+"ListReqVo extends PageParam {\n";
        // 分页返参
        } else if (type.equals(CreateTemplate.LIST_RESP_VO)) {
            template += "import luohao.application.common.pojo.PageParam;\n" +
                "@Data\n" +
                "public class "+className+"ListRespVo {\n";
        // 新增、编辑
        } else {
            template += "import luohao.application.model.common.ActionUpdate;\n" +
                    "import javax.validation.constraints.Min;\n" +
                    "import javax.validation.constraints.NotBlank;\n" +
                    "import javax.validation.constraints.NotNull;\n"+
                    "@Data\n" +
                    "public class "+className+"ActionReqVo {\n";
        }
        // 主键
        String primaryKey = this.getPrimaryKey(fieldsConfigVO);
        // 遍历字段配置
        String viewObjectFiles = "";
        for (FieldsConfigVO config : fieldsConfigVO) {
            String fileName = config.getField();
            String fileCamelCase = StrUtil.toCamelCase(fileName); // 小驼峰字段名
            String comment = config.getComment(); // 备注
            // 列表不需要validate， 新增、修改需要验证
            viewObjectFiles += "    @Schema(description = \""+comment+"\")\n";
            if (type.equals(Config.LIST)) {
                viewObjectFiles += fileName.equals(primaryKey) ? "    private Long "+fileCamelCase+";\n" : "    private String "+fileCamelCase+";\n";
            } else {
                if (fileName.equals(primaryKey)) {
                    viewObjectFiles += "    @NotNull(message = \""+comment+"不能为空\", groups = ActionUpdate.class)\n" +
                            "    @Min(value = 1, message = \""+comment+"不能小于1\", groups = ActionUpdate.class)\n" +
                            "    private Long "+fileCamelCase+";\n\n";
                } else {
                    viewObjectFiles += "    @NotBlank(message = \""+comment+"不能为空\")\n" +
                            "    private String "+fileCamelCase+";\n\n";
                }
            }
        }
        template += viewObjectFiles;
        // class end
        template += "}";
        return template;
    }
    /** 获取主键字段名 */
    private String getPrimaryKey(List<FieldsConfigVO> fieldsConfigVO) {
        for (FieldsConfigVO config : fieldsConfigVO) {
            if (config.getKey().equals(Config.PRIMARY_KEY)) {
                return config.getField();
            }
        }
        return Config.PRIMARY_VALUE;
    }
    // ====================================================================[创建前端模板]========================================================================================= */
    /** React Jsx页面生成 */
    public String createReactPageTemplate(CreateReqVO param) {
        String className = param.getClassName();
        String cnClassName = param.getCnClassName();
        String methodName = param.getMethodName();
        List<String> functions = param.getFunctions();
        String primaryKey = this.getPrimaryKey(param.getFieldsConfig());
        String primaryKeyCamelCase = StrUtil.toCamelCase(primaryKey); // 小驼峰字段名
        // package
        String template = "import React, { useRef, useState } from \"react\";\n" +
                "import { message } from \"antd\";\n" +
                "import Config from \"@/config\";\n" +
                "import { PageContainer, ProFormInstance } from \"@ant-design/pro-components\";\n" +
                "import { convertProTableData } from \"@/utils\";\n" +
                "import { ActionsEnum } from \"@/components/LComponents/enum/tableEnum\";\n" +
                "import { columns } from \"@/config/"+methodName+"\";\n" +
                "import { Access, useAccess } from \"@umijs/max\";\n" +
                "import { queryTableList, insertData, updateData, deleteData, batchDeleteData } from \"@/services/"+methodName+"\";\n" +
                "import LTable from \"@/components/LComponents/LTable\"; // 表格组件\n" +
                "import type { "+className+"ActionReqDto, "+className+"ListReqDto, "+className+"RespDto } from \"@/typings/"+methodName+"\";\n" +
                "import type { ActionsType, ParamType, TableRefType } from \"@/components/LComponents/typings/tableType\";\n";
        // start
        template += "const "+className+"List: React.FC = () => {\n";
        // 创建state
        template += "    const tableRef = useRef<TableRefType>(); // 表格ref\n" +
                "    const formRef = useRef<ProFormInstance>(); // 表单ref\n" +
                "    const [selectIds, setSelectIds] = useState<string>(); // ids数据\n"+
                "    const access = useAccess(); // 权限\n";
        // 创建操作方法
        String addString = "\n";
        String hideAddBtn = "true";
        String updateString = "\n";
        String hideEditBtn = "true";
        String deleteString = "\n";
        String hideDeleteBtn = "true";
        String batchDeleteString = "\n";
        String hideBatchDeleteBtn = "true";
        String rowSelectionString = "\n";
        String rowSelection = "\n";
        for (String item : functions) {
            switch (item) {
                case Config.ADD:
                    addString = "// 新增数据\n" +
                            "        if (actionType === ActionsEnum.ADD) {\n" +
                            "            const { response } = await insertData(params);\n" +
                            "            if (response) {\n" +
                            "                message.success(\"操作成功\");\n" +
                            "                tableRef.current?.reload();\n" +
                            "                return true;\n" +
                            "            }\n" +
                            "        }\n";
                    hideAddBtn = "!access."+methodName+"Add";
                    break;
                case Config.UPDATE:
                    updateString = "// 修改数据\n" +
                            "        if (actionType === ActionsEnum.EDIT) {\n" +
                            "            const { response } = await updateData(params);\n" +
                            "            if (response) {\n" +
                            "                message.success(\"操作成功\");\n" +
                            "                tableRef.current?.reload();\n" +
                            "                return true;\n" +
                            "            }\n" +
                            "        }\n";
                    hideEditBtn = "!access."+methodName+"Edit";
                    break;
                case Config.DELETE:
                    deleteString = "// 删除数据\n" +
                            "        if (actionType === ActionsEnum.DELETE) {\n" +
                            "            const { response } = await deleteData(row." + primaryKeyCamelCase + ");\n" +
                            "            if (response) {\n" +
                            "                message.success(\"删除成功\");\n" +
                            "                tableRef.current?.reload();\n" +
                            "            }\n" +
                            "        }\n";
                    hideDeleteBtn = "!access."+methodName+"Delete";
                    break;
                case Config.BATCH_DELETE:
                    batchDeleteString = "// 批量删除数据\n" +
                            "        if (actionType === ActionsEnum.BATCH_DELETE) {\n" +
                            "            if (!selectIds) {\n" +
                            "                message.error(\"请选择要删除的数据\");\n" +
                            "                return;\n" +
                            "            }\n" +
                            "            const { response } = await batchDeleteData(selectIds);\n" +
                            "            if (response) {\n" +
                            "                message.success(\"删除成功\");\n" +
                            "                tableRef.current?.clearSelected(); // 清空勾选\n" +
                            "                tableRef.current?.reload();\n" +
                            "            }\n" +
                            "        }\n";
                    rowSelectionString = "/** 多选事件 */\n" +
                            "    const handleRowSelection = (selectedRowKeyArr: React.Key[]) => {\n" +
                            "        setSelectIds(selectedRowKeyArr.join(\",\"));\n" +
                            "    }\n";
                    rowSelection = "rowSelection={{\n" +
                            "                        preserveSelectedRowKeys: true,\n" +
                            "                        onChange: handleRowSelection,\n" +
                            "                    }}\n";
                    hideBatchDeleteBtn = "!access."+methodName+"BatchDelete";
                    break;
            }
        }
        // 创建方法
        template += "    /** 获取表格数据 */\n" +
                "    const getTableList = async (params: ParamType<"+className+"ListReqDto>) => {\n" +
                "        const { response } = await queryTableList(params);\n" +
                "        return convertProTableData<"+className+"RespDto>({ response, keyName: \""+primaryKeyCamelCase+"\"});\n" +
                "    }\n" +
                "    /** 操作事件回调 */\n" +
                "    const handleActionCall = async (actionType: ActionsType, row: "+className+"RespDto) => {\n" +
                "        "+ deleteString +
                "        "+ batchDeleteString +
                "    }\n" +
                "    /** 操作事件请求提交 */\n" +
                "    const handleActionRequest = async (params: "+className+"ActionReqDto, actionType: ActionsType) => {  \n" +
                "        " + addString +
                "        " + updateString +
                "        return false;\n" +
                "    }\n" +
                "    "+ rowSelectionString +"\n";
        // 创建组件
        template += "    return (\n" +
                "        <PageContainer>\n" +
                "            <Access accessible={access."+methodName+"List} fallback={Config.RULE_CHECK_ERROR}>\n" +
                "                <LTable\n" +
                "                    title=\""+cnClassName+"\"\n" +
                "                    tableRef={tableRef}\n" +
                "                    formRef={formRef}\n" +
                "                    tableColumns={columns}\n" +
                "                    visible={{\n" +
                "                        hideAddBtn: "+hideAddBtn+",\n" +
                "                        hideEditBtn: () => "+hideEditBtn+",\n" +
                "                        hideDelBtn: () => "+hideDeleteBtn+",\n" +
                "                        hideBatchDelBtn: "+hideBatchDeleteBtn+",\n" +
                "                    }}\n" +
                "                    request={getTableList}\n" +
                "                    handleActionCall={handleActionCall}\n" +
                "                    modalFormRequest={handleActionRequest}\n" +
                "                    "+ rowSelection +
                "                />\n" +
                "            </Access>\n" +
                "       </PageContainer>\n" +
                "    )\n";
        // end
        template += "}\n" +
                "export default "+className+"List;";
        return template;
    }
    /** React service生成 */
    public String createReactServiceTemplate(CreateReqVO param) {
        String className = param.getClassName();
        String cnClassName = param.getCnClassName();
        String methodName = param.getMethodName();
        List<String> functions = param.getFunctions();
        // package
        String template = "import request from \"@/services/request\";\n" +
                "import type { ResPageDto } from \"@/typings/common/res\";\n" +
                "import type { "+className+"ActionReqDto, "+className+"ListReqDto, "+className+"RespDto } from \"@/typings/system/"+methodName+"\";\n";
        // start
        for (String item : functions) {
            switch (item) {
                case Config.LIST:
                    template += "/** 查询"+cnClassName+"列表 */\n" +
                            "export const queryTableList = (params: "+className+"ListReqDto) => {\n" +
                            "    return request<ResPageDto<"+className+"RespDto[]>>({ method: \"post\", url: \"/"+methodName+"/page\", data: params });\n" +
                            "}\n";
                    break;
                case Config.ADD:
                    template += "/** 新增"+cnClassName+" */\n" +
                            "export const insertData = (params: "+className+"ActionReqDto) => {\n" +
                            "    return request<boolean>({ method: \"post\", url: \"/"+methodName+"/add\", data: params });\n" +
                            "}\n";
                    break;
                case Config.UPDATE:
                    template += "/** 更新"+cnClassName+" */\n" +
                            "export const updateData = (params: "+className+"ActionReqDto) => {\n" +
                            "    return request<boolean>({ method: \"post\", url: \"/"+methodName+"/update\", data: params });\n" +
                            "}\n";
                    break;
                case Config.DELETE:
                    template += "/** 删除"+cnClassName+" */\n" +
                            "export const deleteData = (id: number) => {\n" +
                            "    return request<boolean>({ method: \"post\", url: \"/"+methodName+"/delete\", data: { id } });\n" +
                            "}\n";
                    break;
                case Config.BATCH_DELETE:
                    template += "/** 批量删除"+cnClassName+" */\n" +
                            "export const batchDeleteData = (ids: string) => {\n" +
                            "    return request<boolean>({ method: \"post\", url: \"/"+methodName+"/batchDelete\", data: { ids } });\n" +
                            "}\n";
                    break;
            }
        }
        // end
        return template;
    }
    /** React interface模板生成 */
    public String createReactInterfaceTemplate(CreateReqVO param) {
        String className = param.getClassName();
        List<FieldsConfigVO> fieldsConfigVO = param.getFieldsConfig();
        // start
        String template = "";
        String interfaceFiles = "";
        // 遍历字段配置
        for (FieldsConfigVO config : fieldsConfigVO) {
            String fileName = config.getField();
            String fileCamelCase = StrUtil.toCamelCase(fileName); // 小驼峰字段名
            interfaceFiles += "    "+fileCamelCase+": string; // "+config.getComment()+"\n";
        }
        // 列表入参
        template += "import { ParamType } from \"@/components/LComponents/typings/tableType\";\n\n" +
                "export interface "+className+"ListReqDto extends ParamType {\n" +
                "    "+interfaceFiles+"\n" +
                "}\n";
        // 新增、更新入参
        template += "export interface "+className+"ActionReqDto {\n" +
                "    "+interfaceFiles+"\n" +
                "}\n";
        // 查询返参
        template += "export interface "+className+"RespDto {\n" +
                "    "+interfaceFiles+"\n" +
                "}\n";
        // end
        return template;
    }
    /** React config模板生成 */
    public String createReactConfigTemplate(CreateReqVO param) {
        List<FieldsConfigVO> fieldsConfigVO = param.getFieldsConfig();
        // start
        String template = "import type { TableColumnsType } from \"@/components/LComponents/typings/tableType\";\n" +
                "/** 表格结构 */\n" +
                "export const columns: TableColumnsType[] = [\n";
        String interfaceFiles = "";
        // 主键
        String primaryKey = this.getPrimaryKey(fieldsConfigVO);
        // 遍历字段配置
        for (FieldsConfigVO config : fieldsConfigVO) {
            String fileName = config.getField();
            String fileCamelCase = StrUtil.toCamelCase(fileName); // 小驼峰字段名
            String comment = config.getComment();
            String componentType = config.getComponentType();
            // 主键为integer其他为string
            if (fileName.equals(primaryKey)) {
                interfaceFiles += "    { title: \""+comment+"\", dataIndex: \""+fileCamelCase+"\", hideInTable: true, hideInSearch: true, hiddenForm: true },\n";
            } else {
                interfaceFiles += this.getComponentTypeTemplate(componentType, config);
            }
        }
        template += interfaceFiles;
        // end
        template += "]";
        return template;
    }
    /** React 组件类型模板 */
    private String getComponentTypeTemplate(String componentType, FieldsConfigVO config) {
        String fileName = config.getField();
        String fileCamelCase = StrUtil.toCamelCase(fileName); // 小驼峰字段名
        String comment = config.getComment();
        switch (componentType) {
            case "password":
                return "    { title: \""+comment+"\", dataIndex: \""+fileCamelCase+"\", required: true, valueType: \"password\" },\n";
            case "select":
                return "    { title: \""+comment+"\", dataIndex: \""+fileCamelCase+"\", required: true, valueType: \"select\", valueEnum: {} },\n";
            case "date":
                return "    { title: \""+comment+"\", dataIndex: \""+fileCamelCase+"\", required: true, valueType: \"date\" },\n";
            case "dateRange":
                return "    { title: \""+comment+"\", dataIndex: \""+fileCamelCase+"\", required: true, valueType: \"dateRange\" },\n";
            case "cascader":
                return "    { title: \""+comment+"\", dataIndex: \""+fileCamelCase+"\", required: true, valueType: \"cascader\", fieldProps: { options: [], changeOnSelect: true } },\n";
            case "treeSelect":
                return "    { title: \""+comment+"\", dataIndex: \""+fileCamelCase+"\", required: true, valueType: \"treeSelect\", fieldProps: { options: [], changeOnSelect: true } },\n";
            case "digit":
                return "    { title: \""+comment+"\", dataIndex: \""+fileCamelCase+"\", required: true, valueType: \"digit\" },\n";
        }
        return "    { title: \""+comment+"\", dataIndex: \""+fileCamelCase+"\", required: true },\n";
    }
}
