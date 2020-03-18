# TreeInfotip

### Plugin-Gson 能做什么？

> 实现了Gson的TypeAdapterFactory与TypeAdapter自定义

- Duuble精度、Float精度、Enum自定义转换
- Data、Time、Timestamp需要为NUll的情况

> Java的编译时注解;继承AbstractProcessor进行代码构建
> 解决GSON的全局使用降低耦合度

- @GsonAutowired注解注入
- 使用XML进行配置

---
### 使用环境

    JAVA1.8
    Maven

### 示列

> ##### 图片示列教程：（国内有些网络啊！tmd图片看不了的）

 ![样列](https://raw.githubusercontent.com/Link-Kou/Plugin-Gson/master/image/2020-03-18_16-04-22.gif "样列")
 
### Maven仓库

 ```xml：

 <dependency>
   <groupId>com.github.link-kou</groupId>
   <artifactId>gson</artifactId>
   <version>1.0.2</version>
 </dependency>

 ```
   
### 使用教程

1. @GsonAutowired会实现构建，与框架无关的实现，非Spring环境中也可以使用

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

2.如何实现替换自己的实现

```bash：
    在项目根resources下创建gson.xml文件(文件名称不能改变)。
    此非必须步骤,默认情况下会自动实现一个默认的GsonBuilder
    XML文件如下示列
```

![样列](https://raw.githubusercontent.com/Link-Kou/Plugin-Gson/master/image/2020-03-18_16-24-46.jpg "样列")
 
```xml：
  <?xml version="1.0" encoding="UTF-8"?>
  <groups default="true">
      <!--<default bean="com.linkkou.gson.test.GsonBulidTest.getGson"/>-->
      <group title="gson1" bean="com.linkkou.gson.test.GsonBulidTest.getGson"/>
      <group title="gson2" bean="com.linkkou.gson.test.GsonBulidTest.getGson"/>
  </groups>
```

3. 标签说明
```xml：

   //默认即可，所有子标签都在此标签里面
   //default属性表示是否对默认情况下的注解@GsonAutowired替换;默认为True
   <groups default="true"/> 

   //默认分组指定之后,所有实现了 @GsonAutowired都会被替换
   <default/> 

   //指定分组Gson, @GsonAutowired加上group的优先级最高
   //在没有找到group的情况下，采用default的实现
   <group/> 

```

4.bean的使用构建方式

```java：
//com.linkkou.gson.test.GsonBulidTest.getGson
package com.linkkou.gson.test;

import .....

public class GsonBulidTest {

    public static Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            //当Map的key为复杂对象时,需要开启该方法
            .enableComplexMapKeySerialization()
            //当字段值为空或null时，依然对该字段进行转换
            .serializeNulls()
            //防止特殊字符出现乱码
            .disableHtmlEscaping()
            .create();
     /**
      * 必须使用静态
      * @return Gson
     */
    public static Gson getGson() {
        return gson;
    }
}

```

