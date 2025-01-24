## 目录

```
|-- java
    |-- luohao.application
        |-- annotation 注解
        |-- aop 
        |-- common 公共
            |-- aliyun 阿里云
            |-- enums 公共枚举
            |-- pojo 数据对象
            |-- utils 工具类
            |-- validator 验证注解实现
            |-- wechat 微信
        |-- config 配置
            |-- KaptchaConfig 验证码
            |-- ModelMapperConfig 类型转换
            |-- MybatisPlusConfiguration mybatis-plus
            |-- MyWebMvcConfigurer 网站配置
            |-- SpringDocConfig 接口文档信息配置
        |-- controller 控制器
        |-- handler 统一处理
            |-- GlobalExceptionHandler 统一异常处理
            |-- GlobalFilterHandler 统一过滤处理
            |-- InterceptorHandler 统一拦截处理
            |-- UserInfoHandler 用户信息
        |-- mapper 映射器
        |-- model 数据模型实体
            |-- common 公共
            |-- dataobject 数据对象
            |-- vo 视图对象
        |-- service 服务
|-- resources
    |-- luohao.application.mapper // mappler xml sql
```

## 其他

* 静态资源地址：/static/xxx.xx

* 接口文档地址：/doc.html

## 说明

创建数据表时，请创建以下字段：

```sql
ALTER TABLE xxx ADD creator BIGINT NULL COMMENT '创建人id';
ALTER TABLE xxx ADD create_time datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间';
ALTER TABLE xxx ADD updater BIGINT NULL COMMENT '更新人id';
ALTER TABLE xxx ADD update_time datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间';
```
