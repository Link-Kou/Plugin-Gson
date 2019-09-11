# TreeInfotip

### Plugin-Gson 能做什么？

> 基于Gson封装了TypeAdapterFactory与TypeAdapter。
> 基于注解，进行代码构建
---
### 使用环境

    JAVA1.8以上

### 在线安装

 ```xml：

 <dependency>
   <groupId>com.github.link-kou</groupId>
   <artifactId>gson</artifactId>
   <version>1.0.0</version>
 </dependency>

 ```

### 手动安装
   无
   
   
### 一、示列

```java：
public class TestDemo {

    @GsonAutowired
    private Gson gson;

    @GsonAutowired(group = "gson1")
    private Gson gson1;

    @GsonAutowired(group = "gson2")
    private Gson gson2;


    public void test() {
        System.out.println(123);
    }
}
```

### 二、说明

> ##### 说明文档：
1. 在项目根resources下创建gson.xml文件(文件名称不能改变)

2. 配置文件内容示列
```xml：
  <?xml version="1.0" encoding="UTF-8"?>
  <groups default="true">
      <!--<default bean="com.linkkou.gson.test.GsonBulidTest.getGson"/>-->
      <group title="gson1" bean="com.linkkou.gson.test.GsonBulidTest.getGson1"/>
      <group title="gson2" bean="com.linkkou.gson.test.GsonBulidTest.getGson2"/>
  </groups>
```

3. 标签说明
```标签说明文档：
         <groups/> 默认即可，所有子标签都在此标签里面
         <default/> 没有指定的默认分组
         <group/> 指定分组Gson
```

4. 属性说明
```属性说明文档：
         <bean/> 实现的gson的类路径与方法，方法必须为static类型并且返回Gson，否者报错哦！
         <title/> 分组表示
```