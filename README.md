
## 0 项目开始准备
### 0.1 新建工程
![img_1.png](img/img_1.png)

![img.png](img/img.png)

### 0.2 新建数据库dd-mall后使用idea连接

![img.png](img/img9.png)

![img_1.png](img/img10.png)

![img_2.png](img/img_2.png)

使用`dd_mall_local.sql`导入表

### 0.3 导入pom依赖
在原有基础上添加如下依赖
- mybatis依赖
- mysql依赖
- mybatis.generator插件

```
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.6.7</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
    <groupId>net.kokwind</groupId>
    <artifactId>mall</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>mall</name>
    <description>mall</description>
    <properties>
        <java.version>1.8</java.version>
    </properties>
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <!-- mybatis依赖 -->
        <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
            <version>2.2.2</version>
        </dependency>
        <!-- mysql依赖 -->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
        </dependency>

    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
            <!-- mybatis.generator插件 -->
            <plugin>
                <groupId>org.mybatis.generator</groupId>
                <artifactId>mybatis-generator-maven-plugin</artifactId>
                <version>1.4.1</version>
                <configuration>
                    <verbose>true</verbose>
                    <overwrite>true</overwrite>
                </configuration>
            </plugin>
        </plugins>
    </build>

</project>

```

### 0.4 新建配置文件
#### 0.4.1 创建application.properties
配置mysql连接
配置log
配置mybatis mapper位置，好让spring boot自动扫描

```
spring.application.name=dd_mall
spring.datasource.name=dd_mall_datasource
spring.datasource.url=jdbc:mysql://139.186.157.42:28001/dd_mall?useUnicode=true&characterEncoding=utf8&autoReconnect=true&useSSL=false&serverTimezone=Asia/Shanghai
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.username=root
spring.datasource.password=ibm99ibm
logging.config=classpath:logback-spring.xml
logging.level.root=INFO
logging.level.org.mybatis=debug
logging.level.dao=debug
logging.file.path=d:/logs

mybatis.mapper-locations=classpath:mappers/*.xml

```
#### 0.4.2 创建logback-spring.xml
logback位置，设置日志记录规则并保存在本地，位置在logs文件夹下
修改`application.properties`中的`logging.file.path`参数，相关参数是`logging`开头的字段。

```
```xml
<?xml version="1.0" encoding="UTF-8"?>
<!-- 日志级别从低到高分为TRACE < DEBUG < INFO < WARN < ERROR < FATAL，如果设置为WARN，则低于WARN的信息都不会输出 -->
<!-- scan:当此属性设置为true时，配置文档如果发生改变，将会被重新加载，默认值为true -->
<!-- scanPeriod:设置监测配置文档是否有修改的时间间隔，如果没有给出时间单位，默认单位是毫秒。
       当scan为true时，此属性生效。默认的时间间隔为1分钟。 -->
<!-- debug:当此属性设置为true时，将打印出logback内部日志信息，实时查看logback运行状态。默认值为false。 -->
<configuration scan="true" scanPeriod="10 seconds">
<!--    <include resource="org/springframework/boot/logging/logback/base.xml" />-->
    <contextName>logback-spring</contextName>

    <!-- name的值是变量的名称，value的值时变量定义的值。通过定义的值会被插入到logger上下文中。定义后，可以使“${}”来使用变量。 -->

    <!--   *******************需要更改该，写自己的保存路径***************************    -->
    <!--    这里用来指定地址   Value= ********** ,这种格式的配置信息，下面有很多，比如日志格式等-->
<!--     <property name="logging.file.path" value="${logging.file.path}" />-->
    <springProperty scope="context" name="logging.file.path" source="logging.file.path"/>

    <!--0. 日志格式和颜色渲染 -->
    <!-- 彩色日志依赖的渲染类 -->
    <conversionRule conversionWord="clr" converterClass="org.springframework.boot.logging.logback.ColorConverter" />
    <conversionRule conversionWord="wex" converterClass="org.springframework.boot.logging.logback.WhitespaceThrowableProxyConverter" />
    <conversionRule conversionWord="wEx" converterClass="org.springframework.boot.logging.logback.ExtendedWhitespaceThrowableProxyConverter" />
    <!-- 彩色日志格式 -->
    <property name="CONSOLE_LOG_PATTERN" value="${CONSOLE_LOG_PATTERN:-%clr(%d{yyyy-MM-dd HH:mm:ss.SSS}){faint} %clr(${LOG_LEVEL_PATTERN:-%5p}) %clr(${PID:- }){magenta} %clr(---){faint} %clr([%15.15t]){faint} %clr(%-40.40logger{39}){cyan} %clr(:){faint} %m%n${LOG_EXCEPTION_CONVERSION_WORD:-%wEx}}"/>

    <!--1. 输出到控制台-->
    <appender name="CONSOLE" class="ch.qos.logback.core.ConsoleAppender">
        <!--此日志appender是为开发使用，只配置最底级别，控制台输出的日志级别是大于或等于此级别的日志信息-->
        <!--<filter class="ch.qos.logback.classic.filter.ThresholdFilter">-->
        <!--<level>INFO</level>-->
        <!--</filter>-->
        <encoder>
            <Pattern>${CONSOLE_LOG_PATTERN}</Pattern>
            <!-- 设置字符集 -->
            <charset>UTF-8</charset>
        </encoder>
    </appender>

    <!--2. 输出到文档-->
    <!-- 2.1 level为 DEBUG 日志，时间滚动输出 -->
    <appender name="DEBUG_FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <!-- 正在记录的日志文档的路径及文档名 -->
        <file>${logging.file.path}/web_debug.log</file>
        <!--日志文档输出格式-->
        <encoder>
            <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{50} - %msg%n</pattern>
            <charset>UTF-8</charset> <!-- 设置字符集 -->
        </encoder>
        <!-- 日志记录器的滚动策略，按日期，按大小记录 -->
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <!-- 日志归档 -->
            <fileNamePattern>${logging.file.path}/debug/web-debug-%d{yyyy-MM-dd}.%i.log</fileNamePattern>
            <timeBasedFileNamingAndTriggeringPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP">
                <maxFileSize>10MB</maxFileSize>
            </timeBasedFileNamingAndTriggeringPolicy>
            <!--日志文档保留天数-->
            <maxHistory>15</maxHistory>
        </rollingPolicy>
        <!-- 此日志文档只记录debug级别的 -->
        <filter class="ch.qos.logback.classic.filter.LevelFilter">
            <level>debug</level>
            <onMatch>ACCEPT</onMatch>
            <onMismatch>DENY</onMismatch>
        </filter>
    </appender>

    <!-- 2.2 level为 INFO 日志，时间滚动输出 -->
    <appender name="INFO_FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <!-- 正在记录的日志文档的路径及文档名 -->
        <file>${logging.file.path}/web_info.log</file>
        <!--日志文档输出格式-->
        <encoder>
            <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{50} - %msg%n</pattern>
            <charset>UTF-8</charset>
        </encoder>
        <!-- 日志记录器的滚动策略，按日期，按大小记录 -->
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <!-- 每天日志归档路径以及格式 -->
            <fileNamePattern>${logging.file.path}/info/web-info-%d{yyyy-MM-dd}.%i.log</fileNamePattern>
            <timeBasedFileNamingAndTriggeringPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP">
                <maxFileSize>10MB</maxFileSize>
            </timeBasedFileNamingAndTriggeringPolicy>
            <!--日志文档保留天数-->
            <maxHistory>15</maxHistory>
        </rollingPolicy>
        <!-- 此日志文档只记录info级别的 -->
        <filter class="ch.qos.logback.classic.filter.LevelFilter">
            <level>info</level>
            <onMatch>ACCEPT</onMatch>
            <onMismatch>DENY</onMismatch>
        </filter>
    </appender>

    <!-- 2.3 level为 WARN 日志，时间滚动输出 -->
    <appender name="WARN_FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <!-- 正在记录的日志文档的路径及文档名 -->
        <file>${logging.file.path}/web_warn.log</file>
        <!--日志文档输出格式-->
        <encoder>
            <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{50} - %msg%n</pattern>
            <charset>UTF-8</charset> <!-- 此处设置字符集 -->
        </encoder>
        <!-- 日志记录器的滚动策略，按日期，按大小记录 -->
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <fileNamePattern>${logging.file.path}/warn/web-warn-%d{yyyy-MM-dd}.%i.log</fileNamePattern>
            <timeBasedFileNamingAndTriggeringPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP">
                <maxFileSize>10MB</maxFileSize>
            </timeBasedFileNamingAndTriggeringPolicy>
            <!--日志文档保留天数-->
            <maxHistory>15</maxHistory>
        </rollingPolicy>
        <!-- 此日志文档只记录warn级别的 -->
        <filter class="ch.qos.logback.classic.filter.LevelFilter">
            <level>warn</level>
            <onMatch>ACCEPT</onMatch>
            <onMismatch>DENY</onMismatch>
        </filter>
    </appender>

    <!-- 2.4 level为 ERROR 日志，时间滚动输出 -->
    <appender name="ERROR_FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <!-- 正在记录的日志文档的路径及文档名 -->
        <file>${logging.file.path}/web_error.log</file>
        <!--日志文档输出格式-->
        <encoder>
            <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{50} - %msg%n</pattern>
            <charset>UTF-8</charset> <!-- 此处设置字符集 -->
        </encoder>
        <!-- 日志记录器的滚动策略，按日期，按大小记录 -->
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <fileNamePattern>${logging.file.path}/error/web-error-%d{yyyy-MM-dd}.%i.log</fileNamePattern>
            <timeBasedFileNamingAndTriggeringPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP">
                <maxFileSize>10MB</maxFileSize>
            </timeBasedFileNamingAndTriggeringPolicy>
            <!--日志文档保留天数-->
            <maxHistory>15</maxHistory>
        </rollingPolicy>
        <!-- 此日志文档只记录ERROR级别的 -->
        <filter class="ch.qos.logback.classic.filter.LevelFilter">
            <level>ERROR</level>
            <onMatch>ACCEPT</onMatch>
            <onMismatch>DENY</onMismatch>
        </filter>
    </appender>

    <!--
      <logger>用来设置某一个包或者具体的某一个类的日志打印级别、
      以及指定<appender>。<logger>仅有一个name属性，
      一个可选的level和一个可选的addtivity属性。
      name:用来指定受此logger约束的某一个包或者具体的某一个类。
      level:用来设置打印级别，大小写无关：TRACE, DEBUG, INFO, WARN, ERROR, ALL 和 OFF，
         还有一个特俗值INHERITED或者同义词NULL，代表强制执行上级的级别。
         如果未设置此属性，那么当前logger将会继承上级的级别。
      addtivity:是否向上级logger传递打印信息。默认是true。
      <logger name="org.springframework.web" level="info"/>
      <logger name="org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor" level="INFO"/>
    -->

    <!--
      使用mybatis的时候，sql语句是debug下才会打印，而这里我们只配置了info，所以想要查看sql语句的话，有以下两种操作：
      第一种把<root level="info">改成<root level="DEBUG">这样就会打印sql，不过这样日志那边会出现很多其他消息
      第二种就是单独给dao下目录配置debug模式，代码如下，这样配置sql语句会打印，其他还是正常info级别：
      【logging.level.org.mybatis=debug logging.level.dao=debug】
     -->

    <!--
      root节点是必选节点，用来指定最基础的日志输出级别，只有一个level属性
      level:用来设置打印级别，大小写无关：TRACE, DEBUG, INFO, WARN, ERROR, ALL 和 OFF，
      不能设置为INHERITED或者同义词NULL。默认是DEBUG
      可以包含零个或多个元素，标识这个appender将会添加到这个logger。
    -->

    <!-- 4. 最终的策略 -->
    <!-- 4.1 开发环境:打印控制台-->
    <!--  <springProfile name="dev">-->
    <logger name="net.kokwind.mall" level="debug"/><!-- 修改此处扫描包名 -->
    <!--  </springProfile>-->

    <root level="info">
        <appender-ref ref="CONSOLE" />
        <appender-ref ref="DEBUG_FILE" />
        <appender-ref ref="INFO_FILE" />
        <appender-ref ref="WARN_FILE" />
        <appender-ref ref="ERROR_FILE" />
    </root>

    <!--   4.2 生产环境:输出到文档-->
    <springProfile name="pro">
        <root level="info">
            <appender-ref ref="CONSOLE" />
            <appender-ref ref="DEBUG_FILE" />
            <appender-ref ref="INFO_FILE" />
            <appender-ref ref="ERROR_FILE" />
            <appender-ref ref="WARN_FILE" />
        </root>
    </springProfile>
</configuration>
```

输出如下：
![img_3.png](img/img_3.png)



### 0.5 新建`generatorConfig.xml`
配置mybatis-generator
修改点：
- 数据库驱动个人配置，驱动的位置
- 数据库链接地址账号密码
- 修改生成对应表及类名即可

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE generatorConfiguration
  PUBLIC "-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN"
  "http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd">
<generatorConfiguration>
  <!-- 配置文件，放在resource目录下即可 -->
  <!--数据库驱动个人配置-->
  <classPathEntry
    location="src/main/resources/mysql-connector-java-8.0.28.jar"/>
  <context id="MysqlTables" targetRuntime="MyBatis3">
    <property name="autoDelimitKeywords" value="true"/>
    <!--可以使用``包括字段名，避免字段名与sql保留字冲突报错-->
    <property name="beginningDelimiter" value="`"/>
    <property name="endingDelimiter" value="`"/>
    <!-- optional，旨在创建class时，对注释进行控制 -->
    <commentGenerator>
      <property name="suppressDate" value="true"/>
      <property name="suppressAllComments" value="true"/>
    </commentGenerator>
    <!--数据库链接地址账号密码-->
    <jdbcConnection driverClass="com.mysql.cj.jdbc.Driver"
      connectionURL="jdbc:mysql://139.186.157.42:28001/dd_mall?useUnicode=true&amp;characterEncoding=UTF-8&amp;zeroDateTimeBehavior=convertToNull"
      userId="root"
      password="ibm99ibm">
      <property name="nullCatalogMeansCurrent" value="true"/>
    </jdbcConnection>
    <!-- 非必需，类型处理器，在数据库类型和java类型之间的转换控制-->
    <javaTypeResolver>
      <property name="forceBigDecimals" value="false"/>
    </javaTypeResolver>
    <!--生成Model类存放位置-->
    <javaModelGenerator targetPackage="net.kokwind.mall.model.entity"
      targetProject="src/main/java">
      <!-- 是否允许子包，即targetPackage.schemaName.tableName -->
      <property name="enableSubPackages" value="true"/>
      <!-- 是否对类CHAR类型的列的数据进行trim操作 -->
      <property name="trimStrings" value="true"/>
      <!-- 建立的Model对象是否 不可改变  即生成的Model对象不会有 setter方法，只有构造方法 -->
      <property name="immutable" value="false"/>
    </javaModelGenerator>
    <!--生成mapper映射文件存放位置-->
    <sqlMapGenerator targetPackage="mappers" targetProject="src/main/resources">
      <property name="enableSubPackages" value="true"/>
    </sqlMapGenerator>
    <!--生成Dao类存放位置-->
    <javaClientGenerator type="XMLMAPPER" targetPackage="net.kokwind.mall.model.dao"
      targetProject="src/main/java">
      <property name="enableSubPackages" value="true"/>
    </javaClientGenerator>
    <!--生成对应表及类名-->
    <table schema="root" tableName="dd_mall_cart" domainObjectName="Cart"
      enableCountByExample="false"
      enableUpdateByExample="false" enableDeleteByExample="false" enableSelectByExample="false"
      selectByExampleQueryId="false">
    </table>
    <table tableName="dd_mall_category" domainObjectName="Category" enableCountByExample="false"
      enableUpdateByExample="false" enableDeleteByExample="false" enableSelectByExample="false"
      selectByExampleQueryId="false">
    </table>
    <table tableName="dd_mall_order" domainObjectName="Order" enableCountByExample="false"
      enableUpdateByExample="false" enableDeleteByExample="false" enableSelectByExample="false"
      selectByExampleQueryId="false">
    </table>
    <table tableName="dd_mall_order_item" domainObjectName="OrderItem"
      enableCountByExample="false"
      enableUpdateByExample="false" enableDeleteByExample="false" enableSelectByExample="false"
      selectByExampleQueryId="false">
    </table>
    <table tableName="dd_mall_product" domainObjectName="Product" enableCountByExample="false"
      enableUpdateByExample="false" enableDeleteByExample="false" enableSelectByExample="false"
      selectByExampleQueryId="false">
    </table>
    <table tableName="dd_mall_user" domainObjectName="User" enableCountByExample="false"
      enableUpdateByExample="false" enableDeleteByExample="false" enableSelectByExample="false"
      selectByExampleQueryId="false">
    </table>

  </context>
</generatorConfiguration>

```

### 0.6 使用mybatis-generator生成代码
点击`mybatis-generator：generate`生成

![img_4.png](img/img_4.png)

生成代码如下

![img_5.png](img/img_5.png)

此时用idea写一个实现类引用mapper类来调用dao层的处理，使用@Autowired注解时被标红线，找不到bean。

![img_6.png](img/img_6.png)

![img_7.png](img/img_7.png)

因为我们在`application.properties`里配置了扫描包地址`mybatis.mapper-locations=classpath:mappers/*.xml`,并在程序入口配置了@MapperScan
```java
package net.kokwind.mall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("net.kokwind.mall.model.dao")
public class MallApplication {
    public static void main(String[] args) {
        SpringApplication.run(MallApplication.class, args);
    }
}

```
所以程序可以正常运行，但是idea提示找不到bean。

所以还需要加上@Respository注解，这样就不标红了。

也可以加@Mapper注解，这两种注解的区别是@Mapper注解是指定mapper类，而@Respository注解是指定mapper类的实现类。
1. 使用@Mapper注解后，不需要在Spring配置中设置扫描地址，通过mapper.xml里面的namesapce属性对应相关的mapper类，spring将动态的生成Bean后注入到Service层。
2. 使用@Repository注解则需要在Spring中配置扫描包地址，然后生成dao层的bean之后，再注入到Service层。
例如：
```java
package net.kokwind.mall.model.dao;

import net.kokwind.mall.model.entity.User;
import org.springframework.stereotype.Repository;
@Repository
public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User row);

    int insertSelective(User row);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User row);

    int updateByPrimaryKey(User row);
}
```

### 0.7 测试是否正常运行
#### 0.7.1 编写测试UserService
```java
package net.kokwind.mall.service;

import net.kokwind.mall.model.dao.UserMapper;
import net.kokwind.mall.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public User getUser(){
        return userMapper.selectByPrimaryKey(1);
    }
}
```

#### 0.7.2 编写测试UserController

```java
package net.kokwind.mall.controller;

import net.kokwind.mall.model.entity.User;
import net.kokwind.mall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/user")
    public User getUser() {
        return userService.getUser();
    }
}
```

#### 0.7.3 测试是否正常运行

![img_8.png](img/img_8.png)

### 0.8 AOP同意处理Web请求日志
用filter把每个请求都打印出来，可以看到请求的url和参数。可以提高开发和调试的效率
创建filter把请求的信息和返回的信息都打印出来。

添加AOP依赖
```
        <!-- AOP依赖 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-aop</artifactId>
        </dependency>
```
新建实现截取请求响应的AOP类
```java
package net.kokwind.mall.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * 描述：打印请求和响应信息
 */
@Aspect
@Component
public class WebLogAspect {
    private final Logger log = LoggerFactory.getLogger(WebLogAspect.class);

    @Pointcut("execution(public * net.kokwind.mall.controller.*.*(..)))")
    public void webLog() {

    }

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) {
        //收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        log.info("URL : " + request.getRequestURL().toString());
        log.info("HTTP_METHOD :" + request.getMethod());
        log.info("IP : " + request.getRemoteAddr());
        log.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "."
                + joinPoint.getSignature().getName());
        log.info("ARGS : " + Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(returning = "res", pointcut = "webLog()")
    public void doAfterReturning(Object res) throws JsonProcessingException {
        //处理完请求，返回内容
        log.info("RESPONSE : " + new ObjectMapper().writeValueAsString(res));
    }
}

```
重新启动，再次请求user接口
![img.png](img/img11.png)


## 1 用户模块

![img_1.png](img/img12.png)

![img_2.png](img/img13.png)



### 1.1 接口文档

#### 1.1.1 注册新用户

| 注册新用户 |           |
| ---------- | --------- |
| 请求地址   | /register |
| 请求方式   | POST      |

| 参数     | 参数含义 |
| -------- | -------- |
| UserName | 用户名   |
| password | 密码     |

请求示例

```
/register?userName=onezos&password=12345678
```

返回示例

```
{
  "status": 10000,
  "msg": "SUCCESS",
  "data": null
}
```



#### 1.1.2 登录

| 请求地址 | /login |
| -------- | ------ |
| 请求方式 | POST   |

参数

| 参数     | 参数含义 | 示例     | 备注 |
| -------- | -------- | -------- | ---- |
| userName | 用户名   | onezos   |      |
| password | 密码     | 12345678 |      |

返回示例

```
{
  "status": 10000,
  "msg": "SUCCESS",
  "data": {
    "id": 9,
    "username": "onezos",
    "password": null,
    "personalizedSignature": "祝你今天好心情",
    "role": 2,
    "createTime": "2022-04-27T12:39:47.000+0000",
    "updateTime": "2022-04-27T16:56:02.000+0000"
  }
}
```



#### 1.1.3 更新个性签名 

| 请求地址 | /user/update |
| -------- | ------------ |
| 请求方式 | POST         |

参数

| 参数      | 参数含义       | 示例           | 备注 |
| --------- | -------------- | -------------- | ---- |
| signature | 更新的签名内容 | 更新了我的签名 |      |

请求示例

```
/user/update?signature=更新了我的签名
```

返回示例

```
{    "status": 10000,    "msg": "SUCCESS",    "data": null}
```



#### 1.1.4 退出登录

| 请求地址 | /user/logout |
| -------- | ------------ |
| 请求方式 | POST         |

参数

| 参数 | 参数含义 | 示例 | 备注 |
| ---- | -------- | ---- | ---- |
| 无   |          |      |      |

请求示例

```
/user/logout
```

返回示例

```
{    "status": 10000,    "msg": "SUCCESS",    "data": null}
```



#### 1.1.5 管理员登录

| 请求地址 | /adminLogin |
| -------- | ----------- |
| 请求方式 | POST        |

参数

| 参数     | 参数含义 | 示例     | 备注 |
| -------- | -------- | -------- | ---- |
| userName | 用户名   | onezos   |      |
| password | 密码     | 12345678 |      |

请求示例

```
/adminLogin?userName=onezos&password=12345678
```

返回示例

```
{
  "status": 10000,
  "msg": "SUCCESS",
  "data": {
    "id": 9,
    "username": "onezos",
    "password": null,
    "personalizedSignature": "祝你今天好心情",
    "role": 2,
    "createTime": "2022-04-26T12:39:47.000+0000",
    "updateTime": "2022-04-27T16:56:02.000+0000"
  }
}
```



### 1.2 编写注册新用户

#### 1.2.1 编写service

注册新用户时，我们先插入两个字段，一个是用户名`userNmae`，一个是用户密码`password`，可以使用`mybatis-generator`自动生成的`insertSelective`的sql语句即可。

插入新用户的逻辑是要先进行判断用户名是否存在，如果存在则提示，然后才能写入数据表中。

```java
package net.kokwind.mall.service;

import net.kokwind.mall.exception.DdMallException;
import net.kokwind.mall.exception.DdMallExceptionEnum;
import net.kokwind.mall.model.dao.UserMapper;
import net.kokwind.mall.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public User getUser(){
        return userMapper.selectByPrimaryKey(1);
    }

    public void register(String userName, String password) throws DdMallException {
        //查询用户名是否存在，不允许重名
        User result = userMapper.selectByName(userName);
        if(result != null){
            throw new DdMallException(DdMallExceptionEnum.NAME_EXISTED);
        }
        //写到数据库
        User user = new User();
        user.setUsername(userName);
        user.setPassword(password);
        //只给选中的字段赋值用insertSelective，给每个字段赋值用insert
        int count = userMapper.insertSelective(user);
        if(count == 0){
            throw new DdMallException(DdMallExceptionEnum.REGISTER_FAILED);
        }
    }
}
```

#### 1.2.2 增加用用户名查询的mapper

`UserMapper`增加`selectByName`

```java
package net.kokwind.mall.model.dao;

import net.kokwind.mall.model.entity.User;
import org.springframework.stereotype.Repository;
@Repository
public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User row);

    int insertSelective(User row);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User row);

    int updateByPrimaryKey(User row);

    User selectByName(String userName);
```

`UserMapper.xml`增加`selectByName`

```
  <select id="selectByName" parameterType="java.lang.String" resultMap="BaseResultMap">
    select
    <include refid="Base_Column_List"/>
    from dd_mall_user
    where username = #{userName,jdbcType=VARCHAR}
  </select>
```



#### 1.2.3 编写统一异常

抛出异常时，看到新建了`DdMallException`类和`DdMallExceptionEnum`枚举。

代码如下

`DdMallExceptionEnum`放常用的消息枚举

```java
package net.kokwind.mall.exception;

public enum DdMallExceptionEnum {
    NEED_USER_NAME(10001, "用户名不能为空"),
    NEED_PASSWORD(10002, "密码不能为空"),
    PASSWORD_TOO_SHORT(10003, "密码长度不能小于8位"),
    NAME_EXISTED(10004, "用户名已存在"),
    REGISTER_FAILED(10005, "创建失败,请稍后重试");
    Integer code;
    String message;

    DdMallExceptionEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
```

`DdMallException`统一的异常调用

```java
package net.kokwind.mall.exception;

/**
 * 描述： 统一异常
 */
public class DdMallException extends Exception {
    private final Integer code;
    private final String message;

    public DdMallException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public DdMallException(DdMallExceptionEnum exceptionEnum) {
        this(exceptionEnum.getCode(), exceptionEnum.getMessage());
    }

    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
```

#### 1.2.4 编写通用的统一返回类

返回示例

```
{
  "status": 10000,
  "msg": "SUCCESS",
  "data": null
}
```

新建类·

```java
package net.kokwind.mall.common;

import net.kokwind.mall.exception.DdMallExceptionEnum;

public class ApiRestResponse<T> {
    private Integer status;
    private String message;
    private T data;

    private static final int OK_CODE = 10000;
    private static final String OK_MSG = "SUCCESS";

    public ApiRestResponse(Integer status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public ApiRestResponse(Integer status, String message) {
        this.status = status;
        this.message = message;
    }

    public ApiRestResponse() {
        this(OK_CODE, OK_MSG);
    }

    public static <T> ApiRestResponse<T> success() {
        return new ApiRestResponse<>();
    }

    public static <T> ApiRestResponse<T> success(T result) {

        ApiRestResponse<T> response = new ApiRestResponse<>();
        response.setData(result);
        return response;
    }

    public static <T> ApiRestResponse<T> error(Integer code, String msg) {
        return new ApiRestResponse<>(code,msg);
    }

    public static <T> ApiRestResponse<T> error(DdMallExceptionEnum ex) {
        return new ApiRestResponse<>(ex.getCode(), ex.getMessage());
    }

    @Override
    public String toString() {
        return "ApiRestResponse{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
```

#### 1.2.5 编写注册接口controller

传入`userName`和`password`，并对其进行基础判断。

```java
package net.kokwind.mall.controller;

import net.kokwind.mall.common.ApiRestResponse;
import net.kokwind.mall.exception.DdMallException;
import net.kokwind.mall.exception.DdMallExceptionEnum;
import net.kokwind.mall.model.entity.User;
import net.kokwind.mall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/user")
    public User getUser() {
        return userService.getUser();
    }

    @PostMapping("/register")
    public ApiRestResponse register(@RequestParam("userName") String userName,
                                    @RequestParam("password") String password) throws DdMallException {
        if(ObjectUtils.isEmpty(userName)) {
            return ApiRestResponse.error(DdMallExceptionEnum.NEED_USER_NAME);
        }
        if(ObjectUtils.isEmpty(password)) {
            return ApiRestResponse.error(DdMallExceptionEnum.NEED_PASSWORD);
        }
        if(password.length()<8) {
            return ApiRestResponse.error(DdMallExceptionEnum.PASSWORD_TOO_SHORT);
        }
        userService.register(userName, password);
        return ApiRestResponse.success();
    }
}
```

#### 1.2.6 启动测试

![img_3.png](img/img14.png)

![img_6.png](img/img17.png)

![img_4.png](img/img15.png)

![img_5.png](img/img16.png)

![img_7.png](img/img18.png)

![img_8.png](img/img19.png)

#### 1.2.7 对异常进行拦截统一处理

最后一次抛出异常的信息跟之前的不一样。这种并不是我之前想要的结果，所以要对异常进行统一处理，出于对前端结构统一和安全考虑。

```java
package net.kokwind.mall.exception;

import ch.qos.logback.classic.Logger;
import net.kokwind.mall.common.ApiRestResponse;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {
    private final Logger logger = (Logger) LoggerFactory.getLogger(GlobalExceptionHandler.class);
    //处理Exception异常
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Object handleException(Exception e) {
        logger.error("Default Exception", e);
        return ApiRestResponse.error(DdMallExceptionEnum.SYSTEM_ERROR);
    }
    //处理DdMallException异常
    @ExceptionHandler(DdMallException.class)
    @ResponseBody
    public Object handleDdMallException(DdMallException e) {
        logger.error("DdMallException Exception", e);
        return ApiRestResponse.error(e.getCode(), e.getMessage());
    }
}

```
再次启动

![img_9.png](img/img20.png)

#### 1.2.8 java的异常体系

![img_10.png](img/img21.png)

对于`Error`我们会遇到，但是不需要处理的，重点是在处理`IOException`

- Error：是java运行时系统的内部错误或者资源耗尽，一旦发生这样的情况，除了java会告诉我们这些，它并没有什么能力来帮助我们处理它。
- Exception
  - `RuntimeException`一旦发生就是我们程序员的问题。这种异常是编译器无法事先报错的，是需要我们进行check。
  - `IOException`这种是受检查异常，编译器是可以提示我们的，我们可以及时的进行处理。



#### 1.2.9 对密码进行MD5加密
进入用户表可以看到目前密码是明文保存的，为了安全，我们需要对密码进行加密。
先删除掉用户`kokwind`
![img_11.png](img/img22.png)

```java
package net.kokwind.mall.util;


import net.kokwind.mall.common.Constant;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.util.DigestUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 描述：   MD5工具
 */
public class MD5Utils {
    public static String getMD5Str(String str) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        //对原始字符串+盐值进行MD5加密，然后再进行base64编码，盐值保存在Constant类中。
        return Base64.encodeBase64String(md5.digest((str + Constant.SALT).getBytes()));
    }

    public static void main(String[] args) {
        String md5 = null;
        try {
            md5 = getMD5Str("123456");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        System.out.println(md5);

    }
}
```

```java
package net.kokwind.mall.common;

/**
 * 描述： 存放常量值
 */
public class Constant {
    public static final String SALT = "KOKWIND";
}
```

修改service
```java
package net.kokwind.mall.service;

import net.kokwind.mall.exception.DdMallException;
import net.kokwind.mall.exception.DdMallExceptionEnum;
import net.kokwind.mall.model.dao.UserMapper;
import net.kokwind.mall.model.entity.User;
import net.kokwind.mall.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public User getUser(){
        return userMapper.selectByPrimaryKey(1);
    }

    public void register(String userName, String password) throws DdMallException {
        //查询用户名是否存在，不允许重名
        User result = userMapper.selectByName(userName);
        if(result != null){
            throw new DdMallException(DdMallExceptionEnum.NAME_EXISTED);
        }
        //写到数据库
        User user = new User();
        user.setUsername(userName);
        //user.setPassword(password);
        try {
            user.setPassword(MD5Utils.getMD5Str(password));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        //只给选中的字段赋值用insertSelective，给每个字段赋值用insert
        int count = userMapper.insertSelective(user);
        if(count == 0){
            throw new DdMallException(DdMallExceptionEnum.REGISTER_FAILED);
        }
    }
}
```
重启测试

![img_12.png](img/img23.png)

![img_13.png](img/img24.png)



### 1.3 编写登录功能

- 登录状态需要保持
- session的实现方案：登陆后，会保存用户信息到session
- 之后的访问，会先从session中获取用户信息，然后再执行业务逻辑

在UserService里添加`login`方法

```java
    public User login(String userName, String password) throws DdMallException {
        String md5Password = null;
        try {
            md5Password = MD5Utils.getMD5Str(password);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        User user = userMapper.selectLogin(userName, md5Password);
        if(user == null){
            throw new DdMallException(DdMallExceptionEnum.WRONG_PASSWORD);
        }
        return user;
    }
```

对应的在UserController里添加`login`方法, 把登录User信息存入session
```java
    @PostMapping("/login")
    public ApiRestResponse login(@RequestParam("userName") String userName,
                                 @RequestParam("password") String password,
                                 HttpSession session) throws DdMallException {
        if(ObjectUtils.isEmpty(userName)) {
            return ApiRestResponse.error(DdMallExceptionEnum.NEED_USER_NAME);
        }
        if(ObjectUtils.isEmpty(password)) {
            return ApiRestResponse.error(DdMallExceptionEnum.NEED_PASSWORD);
        }
        User user = userService.login(userName, password);
        //保存用户信息时，不保存密码
        user.setPassword(null);
        session.setAttribute(Constant.DD_MALL_USER, user);
        return ApiRestResponse.success(user);
    }
```

![img_1.png](img/img26.png)


### 1.4 编写用户更新功能

在UserService里添加`updateUserInfo`方法
```java
    public void updateUserInfo(User user) throws DdMallException {
        int updateCount = userMapper.updateByPrimaryKeySelective(user);
        if(updateCount > 1){
            throw new DdMallException(DdMallExceptionEnum.UPDATE_FAILED);
        }
    }
```
对应的在UserController里添加`updateUserInfo`方法, 把登录User信息从session中取出，然后更新数据库
```java
    @PostMapping("/user/update")
    public ApiRestResponse updateUserInfo(HttpSession session,
                                          @RequestParam String signature) throws DdMallException {
        User currentUser = (User) session.getAttribute(Constant.DD_MALL_USER);
        if(ObjectUtils.isEmpty(currentUser)) {
            return ApiRestResponse.error(DdMallExceptionEnum.NEED_LOGIN);
        }
        User user = new User();
        user.setPersonalizedSignature(signature);
        user.setId(currentUser.getId());
        userService.updateUserInfo(user);
        return ApiRestResponse.success();
    }
```

![img_2.png](img/img27.png)

![img.png](img/img25.png)

### 1.5 编写用户退出功能

```java
    //用户登出接口
    @PostMapping("/user/logout")
    public ApiRestResponse logout(HttpSession session){
        session.removeAttribute(Constant.DD_MALL_USER);
        return ApiRestResponse.success();
    }
```
启动测试

![img.png](img/img28.png)

### 1.6 编写管理员接口
在`controller`中增加`adminLogin`方法
```java
    //管理员接口
    @PostMapping("/admin/login")
    public ApiRestResponse adminLogin(@RequestParam("userName") String userName,
                                 @RequestParam("password") String password,
                                 HttpSession session) throws DdMallException {
        if(ObjectUtils.isEmpty(userName)) {
            return ApiRestResponse.error(DdMallExceptionEnum.NEED_USER_NAME);
        }
        if(ObjectUtils.isEmpty(password)) {
            return ApiRestResponse.error(DdMallExceptionEnum.NEED_PASSWORD);
        }
        User user = userService.login(userName, password);
        //校验是否是管理员
        if (userService.checkAdminRole(user)) {
            user.setPassword(null);
            session.setAttribute(Constant.DD_MALL_USER, user);
            return ApiRestResponse.success(user);
        }else {
            return ApiRestResponse.error(DdMallExceptionEnum.NEED_ADMIN_ROLE);
        }
    }
```
在`service`中增加`checkAdminRole`方法
```java
    //检查是否是管理员服务
    public boolean checkAdminRole(User user) {
        //1是普通用户，2是管理员
        return user.getRole().equals(2);
    }
```

启动测试

![img_1.png](img/img29.png)



## 2 商品分类模块

### 2.1 接口文档

#### 2.1.1 后台管理：

##### 2.1.1.1 增加目录分类

| 请求地址 | /admin/category/add |
| -------- | ------------------- |
| 请求方式 | POST                |

参数

| 参数     | 参数含义   | 示例     | 备注                 |
| -------- | ---------- | -------- | -------------------- |
| name     | 目录名     | 新鲜水果 |                      |
| type     | 目录层级   | 1        | 不超过3级            |
| parentId | 父目录的ID | 2        | 1级目录的parentId为0 |
| orderNum | 排序       | 5        | 同级目录的排序       |

请求示例

```
/admin/category/add
```

body：

```
{"name":"食品","type":1,"parentId":0,"orderNum":1}
```

返回示例

```
{

  "status": 10000,

  "msg": "SUCCESS",

  "data": null

}
```



##### 2.1.1.2 更新目录分类

| 请求地址 | /admin/category/update |
| -------- | ---------------------- |
| 请求方式 | POST                   |

参数

| 参数     | 参数含义   | 示例     | 备注                 |
| -------- | ---------- | -------- | -------------------- |
| id       | 目录的id   | 4        |                      |
| name     | 目录名     | 新鲜水果 |                      |
| type     | 目录层级   | 1        | 不超过3级            |
| parentId | 父目录的ID | 2        | 1级目录的parentId为0 |
| orderNum | 排序       | 5        | 同级目录的排序       |

请求示例

```
/admin/category/update
```

body：

```
{"id":"1","name":"食品品品品","type":1,"parentId":0,"orderNum":1}
```

返回示例

```
{

  "status": 10000,

  "msg": "SUCCESS",

  "data": null

}
```



##### 2.2.1.3 删除分类

| 请求地址 | /admin/category/delete |
| -------- | ---------------------- |
| 请求方式 | POST                   |

参数

| 参数 | 参数含义 | 示例 | 备注 |
| ---- | -------- | ---- | ---- |
| id   | 目录的id | 4    |      |

请求示例

```
/admin/category/delete?id=1
```

返回示例

```
{

  "status": 10000,

  "msg": "SUCCESS",

  "data": null

}
```



##### 2.1.1.4 分类列表（平铺） 

| 请求地址 | /admin/category/list?pageNum=1&pageSize=10 |
| -------- | ------------------------------------------ |
| 请求方式 | GET                                        |

参数

| 参数     | 参数含义 | 示例 | 备注 |
| -------- | -------- | ---- | ---- |
| pageNum  | 页数     | 1    |      |
| pageSize | 每页条数 | 10   |      |

请求示例

```
/admin/category/list?pageNum=1&pageSize=10
```

返回示例

```
{
  "status": 10000,
  "msg": "SUCCESS",
  "data": {
    "total": 19,
    "list": [
      {
        "id": 3,
        "name": "新鲜水果",
        "type": 1,
        "parentId": 0,
        "orderNum": 1,
        "createTime": "2019-12-17T17:17:00.000+0000",
        "updateTime": "2019-12-28T09:11:26.000+0000"
      }]
}
```

#### 2.1.2 前台：分类列表（递归） 

| 参数 | 参数含义 | 示例 | 备注 |
| ---- | -------- | ---- | ---- |
| 无   |          |      |      |

请求示例

```
/category/list
```

返回示例

```
{
  "status": 10000,
  "msg": "SUCCESS",
  "data": [
    {
      "id": 3,
      "name": "新鲜水果",
      "type": 1,
      "parentId": 0,
      "orderNum": 1,
      "childCategory": [
        {
          "id": 4,
          "name": "橘子橙子",
          "type": 2,
          "parentId": 3,
          "orderNum": 1,
          "childCategory": [
            {
              "id": 19,
              "name": "果冻橙",
              "type": 3,
              "parentId": 4,
              "orderNum": 1,
              "childCategory": []
            }
          ]
      }
  ]
}
```



### 2.2 表设计



![img.png](img/img30.png)



### 2.3 编写商品分类模块

#### 2.3.1 dao中增加selectByName

```java
package net.kokwind.mall.model.dao;

import net.kokwind.mall.model.entity.Category;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Category row);

    int insertSelective(Category row);

    Category selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Category row);

    int updateByPrimaryKey(Category row);

    Category selectByName(String name);
}
```

#### 2.3.2 Mapper中增加selectByName

```xml
  <select id="selectByName" parameterType="java.lang.String" resultMap="BaseResultMap">
    select
    <include refid="Base_Column_List"/>
    from dd_mall_category
    where name = #{name,jdbcType=VARCHAR}
  </select>
```

#### 2.3.3 新建请求的参数类AddCategoryReq

@Size等注解参见2.3.5.1

```java
package net.kokwind.mall.model.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 描述:
 */
public class AddCategoryReq {
    @Size(min = 2,max = 5)
    @NotNull(message = "name不能为null")
    private String name;

    @NotNull(message = "type不能为null")
    @Max(3)
    private Integer type;
    @NotNull(message = "parentId不能为null")
    private Integer parentId;
    @NotNull(message = "orderNum不能为null")
    private Integer orderNum;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }
}
```

#### 2.3.4 新建请求的服务

```java
package net.kokwind.mall.service;

import net.kokwind.mall.exception.DdMallException;
import net.kokwind.mall.exception.DdMallExceptionEnum;
import net.kokwind.mall.model.dao.CategoryMapper;
import net.kokwind.mall.model.entity.Category;
import net.kokwind.mall.model.request.AddCategoryReq;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 描述: 商品分类服务
 */
@Service
public class CategoryService {
    @Autowired
    CategoryMapper categoryMapper;

    public void add(AddCategoryReq addCategoryReq) {
        Category category = new Category();
        BeanUtils.copyProperties(addCategoryReq, category);
        Category oldCategory = categoryMapper.selectByName(addCategoryReq.getName());
        if(oldCategory != null) {
            throw new DdMallException(DdMallExceptionEnum.NAME_EXISTED);
        }
        int count = categoryMapper.insertSelective(category);
        if(count == 0) {
            throw new DdMallException(DdMallExceptionEnum.CREATE_FAILED);
        }
    }
}
```

#### 2.3.5 新建请求的控制器

##### 2.3.5.1 @Valid校验

在平常通过 Spring 框架写代码时候，会经常写接口类，相信大家对该类的写法非常熟悉。在写接口时经常要写效验请求参数逻辑，这时候我们会常用做法是写大量的 if 与 if else 类似这样的代码来做判断

```
        if(addCategoryReq.getName() == null || addCategoryReq.getName()== null ||
        addCategoryReq.getOrderNum() ==null || addCategoryReq.getParentId() == null){
            return ApiRestResponse.error(DdMallExceptionEnum.NAME_NOT_NULL);
        }
```

使用@Valid 参数校验

注解 @Valid 的主要作用是用于数据效验，可以在定义的实体中的属性上，添加不同的注解来完成不同的校验规则，而在接口类中的接收数据参数中添加 @valid 注解，这时你的实体将会开启一个校验的功能。

首先添加依赖

```
		<!-- Valid依赖 -->
        <dependency>
            <groupId>jakarta.validation</groupId>
            <artifactId>jakarta.validation-api</artifactId>
            <version>2.0.2</version>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-validation</artifactId>
        </dependency>

        <dependency>
            <groupId>org.glassfish</groupId>
            <artifactId>jakarta.el</artifactId>
            <version>3.0.3</version>
        </dependency>
```



| 参数           | 说明               |
| -------------- | ------------------ |
| @Valid         | 需要验证           |
| @NotNull       | 非空               |
| @Max(value)    | 最大值             |
| @Size(max,min) | 字符串长度范围限制 |

请求示例

```java
package net.kokwind.mall.controller;

import net.kokwind.mall.common.ApiRestResponse;
import net.kokwind.mall.common.Constant;
import net.kokwind.mall.exception.DdMallExceptionEnum;
import net.kokwind.mall.model.entity.User;
import net.kokwind.mall.model.request.AddCategoryReq;
import net.kokwind.mall.service.CategoryService;
import net.kokwind.mall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * 描述：目录Controller
 */
@RestController
public class CategoryController {
    @Autowired
    UserService userService;
    @Autowired
    CategoryService categoryService;

    @PostMapping("admin/categoty/add")
    public ApiRestResponse addCategory(HttpSession session,
                                       @Valid @RequestBody  AddCategoryReq addCategoryReq) {
        //判断是否登录
        User currentUser = (User) session.getAttribute(Constant.DD_MALL_USER);
        if (currentUser == null) {
            return ApiRestResponse.error(DdMallExceptionEnum.NEED_LOGIN);
        }
        //校验是否是管理员
        boolean adminRole = userService.checkAdminRole(currentUser);
        if(adminRole){
            categoryService.add(addCategoryReq);
            return ApiRestResponse.success();
        }else{
            return ApiRestResponse.error(DdMallExceptionEnum.NEED_ADMIN_ROLE);
        }
    }
}
```

##### 2.3.5.2 增加统一异常处理方法

```java
package net.kokwind.mall.exception;

import ch.qos.logback.classic.Logger;
import net.kokwind.mall.common.ApiRestResponse;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {
    private final Logger logger = (Logger) LoggerFactory.getLogger(GlobalExceptionHandler.class);
    //处理Exception异常
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Object handleException(Exception e) {
        logger.error("Default Exception", e);
        return ApiRestResponse.error(DdMallExceptionEnum.SYSTEM_ERROR);
    }
    //处理DdMallException异常
    @ExceptionHandler(DdMallException.class)
    @ResponseBody
    public Object handleDdMallException(DdMallException e) {
        logger.error("DdMallException Exception", e);
        return ApiRestResponse.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ApiRestResponse handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e) {
        logger.error("MethodArgumentNotValidException: ", e);
        return handleBindingResult(e.getBindingResult());
    }

    private ApiRestResponse handleBindingResult(BindingResult result) {
        //把异常处理为对外暴露的提示
        List<String> list = new ArrayList<>();
        if (result.hasErrors()) {
            List<ObjectError> allErrors = result.getAllErrors();
            for (ObjectError objectError : allErrors) {
                String message = objectError.getDefaultMessage();
                list.add(message);
            }
        }
        if (list.size() == 0) {
            return ApiRestResponse.error(DdMallExceptionEnum.REQUEST_PARAM_ERROR);
        }
        return ApiRestResponse
                .error(DdMallExceptionEnum.REQUEST_PARAM_ERROR.getCode(), list.toString());
    }
}
```



#### 2.4 使用swagger编写API文档

引入依赖

```
        <!-- 自动生成API文档依赖 -->
        <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-swagger2</artifactId>
            <version>2.9.2</version>
        </dependency>

        <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-swagger-ui</artifactId>
            <version>2.9.2</version>
        </dependency>
```

启动类加注解@EnableSwagger2

```java
package net.kokwind.mall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@MapperScan("net.kokwind.mall.model.dao")
@EnableSwagger2
public class MallApplication {
    public static void main(String[] args) {
        SpringApplication.run(MallApplication.class, args);
    }
}
```

编写配置类

```java
package net.kokwind.mall.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 描述：     配置地址映射
 */
@Configuration
@EnableWebMvc
public class DdMallWebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/statics/**").addResourceLocations("classpath:/statics/");
        // 解决 SWAGGER2 404报错
        registry.addResourceHandler("/swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
```

```java
package net.kokwind.mall.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SpringFoxConfig {

    //访问http://localhost:8080/swagger-ui.html可以看到API文档
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("DD生鲜")
                .description("")
                .termsOfServiceUrl("")
                .build();
    }
}
```

启动

![img_1.png](img/img31.png)



#### 2.5 编写未登录和管理员检查过滤器

新建过滤器配置

```java
package net.kokwind.mall.config;

import net.kokwind.mall.filter.AdminFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 描述：     Admin过滤器的配置
 */
@Configuration
public class AdminFilterConfig {

    @Bean
    public AdminFilter adminFilter() {
        return new AdminFilter();
    }

    @Bean(name = "adminFilterConf")
    public FilterRegistrationBean adminFilterConfig() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(adminFilter());
        filterRegistrationBean.addUrlPatterns("/admin/category/*");
        filterRegistrationBean.addUrlPatterns("/admin/product/*");
        filterRegistrationBean.addUrlPatterns("/admin/order/*");
        filterRegistrationBean.setName("adminFilterConf");
        return filterRegistrationBean;
    }
}
```

新建过滤器规则

```java
package net.kokwind.mall.filter;

/**
 * 描述：管理员校验过滤器
 */

import net.kokwind.mall.common.ApiRestResponse;
import net.kokwind.mall.common.Constant;
import net.kokwind.mall.exception.DdMallExceptionEnum;
import net.kokwind.mall.model.entity.User;
import net.kokwind.mall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class AdminFilter implements Filter {
    @Autowired
    UserService userService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession();
        //判断是否登录
        User currentUser = (User) session.getAttribute(Constant.DD_MALL_USER);
        if (currentUser == null) {
            PrintWriter out = new HttpServletResponseWrapper(
                    (HttpServletResponse) servletResponse).getWriter();
            out.write("{\n"
                    + "    \"status\": 10007,\n"
                    + "    \"msg\": \"NEED_LOGIN\",\n"
                    + "    \"data\": null\n"
                    + "}");
            out.flush();
            out.close();
            return;
        }
        //校验是否是管理员
        boolean adminRole = userService.checkAdminRole(currentUser);
        if (adminRole) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            PrintWriter out = new HttpServletResponseWrapper(
                    (HttpServletResponse) servletResponse).getWriter();
            out.write("{\n"
                    + "    \"status\": 10009,\n"
                    + "    \"msg\": \"NEED_ADMIN\",\n"
                    + "    \"data\": null\n"
                    + "}");
            out.flush();
            out.close();
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
```



#### 2.6 添加分页

首先添加依赖，注意`springboot pagehelper`插件启动报错 `com.github.pagehelper.autoconfigure.PageHelperAutoConfiguration`

降低`springboot`版本到2.5.x

```
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.5.5</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
```



```
        <dependency>
            <groupId>com.github.pagehelper</groupId>
            <artifactId>pagehelper-spring-boot-starter</artifactId>
            <version>1.2.13</version>
        </dependency>
```

多层递归分类列表

```java
package net.kokwind.mall.model.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CategoryVO implements Serializable {
    private Integer id;

    private String name;

    private Integer type;

    private Integer parentId;

    private Integer orderNum;

    private Date createTime;

    private Date updateTime;

    private List<CategoryVO> childCategory = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public List<CategoryVO> getChildCategory() {
        return childCategory;
    }

    public void setChildCategory(List<CategoryVO> childCategory) {
        this.childCategory = childCategory;
    }
}
```

Mapper中增加

```
  <select id="selectList" resultMap="BaseResultMap">
    select
    <include refid="Base_Column_List"/>
    from dd_mall_category
  </select>
  <select id="selectCategoriesByParentId" resultMap="BaseResultMap" parameterType="int">
    select
    <include refid="Base_Column_List"/>
    from dd_mall_category
    where parent_id = #{parentId}
  </select>
```

Dao中增加

```
    List<Category> selectList();

    List<Category> selectCategoriesByParentId(Integer parentId);
```

CategoryService中增加管理员列表和用户列表

```java
    public List<CategoryVO> listCategoryForCustomer(Integer parentId) {
        ArrayList<CategoryVO> categoryVOList = new ArrayList<>();
        recursivelyFindCategories(categoryVOList, parentId);
        return categoryVOList;
    }

    private void recursivelyFindCategories(List<CategoryVO> categoryVOList, Integer parentId) {
        //递归获取所有子类别，并组合成为一个“目录树”
        List<Category> categoryList = categoryMapper.selectCategoriesByParentId(parentId);
        if (!CollectionUtils.isEmpty(categoryList)) {
            for (int i = 0; i < categoryList.size(); i++) {
                Category category = categoryList.get(i);
                CategoryVO categoryVO = new CategoryVO();
                BeanUtils.copyProperties(category, categoryVO);
                categoryVOList.add(categoryVO);
                recursivelyFindCategories(categoryVO.getChildCategory(), categoryVO.getId());
            }
        }
    }
```

CategoryController中增加

```java
    @ApiOperation("后台目录列表")
    @GetMapping("admin/category/list")
    public ApiRestResponse listCategoryForAdmin(@RequestParam Integer pageNum,@RequestParam Integer pageSize) {
        PageInfo pageInfo = categoryService.listForAdmin(pageNum, pageSize);
        return ApiRestResponse.success(pageInfo);
    }
    @ApiOperation("前台目录列表")
    @GetMapping("category/list")
    public ApiRestResponse listCategoryForCustomer() {
        List<CategoryVO> categoryVOS = categoryService.listCategoryForCustomer(0);
        return ApiRestResponse.success(categoryVOS);
    }
```

#### 2.7 使用本地redis缓存

##### 2.7.1 首先添加依赖

```xml
        <!-- redis缓存  -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-redis</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-cache</artifactId>
        </dependency>
```

##### 2.7.2 本机安装redis

[Release 3.0.504 · microsoftarchive/redis (github.com)](https://github.com/microsoftarchive/redis/releases/tag/win-3.0.504)

正常安装勾选设置环境变量

测试是否成功，如下：

```
C:\Users\gdd93>redis-cli
127.0.0.1:6379> ping
PONG
127.0.0.1:6379>
```

##### 2.7.3 添加配置

**application.properties**

```
spring.redis.host=localhost
spring.redis.port=6379
spring.redis.password=
```

##### 2.7.4 启动类加注解@EnableCaching

```java
package net.kokwind.mall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@MapperScan("net.kokwind.mall.model.dao")
@EnableSwagger2
@EnableCaching
public class MallApplication {
    public static void main(String[] args) {
        SpringApplication.run(MallApplication.class, args);
    }
}
```

##### 2.7.5 在哪个方法上使用cache

```java
    @Cacheable(value = "listCategoryForCustomer")
    public List<CategoryVO> listCategoryForCustomer(Integer parentId) {
        ArrayList<CategoryVO> categoryVOList = new ArrayList<>();
        recursivelyFindCategories(categoryVOList, parentId);
        return categoryVOList;
    }
```

##### 2.7.6 创建缓存的配置类

```java
package net.kokwind.mall.config;

import java.time.Duration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;

/**
 * 描述：     缓存的配置类
 */
@Configuration
@EnableCaching
public class CachingConfig {

    @Bean
    public RedisCacheManager redisCacheManager(RedisConnectionFactory connectionFactory) {

        RedisCacheWriter redisCacheWriter = RedisCacheWriter
                .lockingRedisCacheWriter(connectionFactory);
        RedisCacheConfiguration cacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();
        cacheConfiguration = cacheConfiguration.entryTtl(Duration.ofSeconds(30));

        RedisCacheManager redisCacheManager = new RedisCacheManager(redisCacheWriter,
                cacheConfiguration);
        return redisCacheManager;
    }
}
```

#### 2.4 测试

![img.png](img/img32.png)

![img_1.png](img/img33.png)

![img_2.png](img/img34.png)

![img_3.png](img/img35.png)

![img_4.png](img/img36.png)

![img_5.png](img/img37.png)

![img_6.png](img/img38.png)



## 3 商品模块

### 3.1 后台管理：

#### 3.1.1 增加商品

| 请求地址 | /admin/product/add |
| -------- | ------------------ |
| 请求方式 | POST               |

参数

| 参数       | 参数含义 | 示例                                                         | 备注     |
| ---------- | -------- | ------------------------------------------------------------ | -------- |
| name       | 商品名称 | 猕猴桃                                                       |          |
| categoryId | 目录ID   | 5                                                            |          |
| price      | 价格     | 1000                                                         | 单位是分 |
| stock      | 库存     | 10                                                           |          |
| detail     | 商品描述 | 新西兰黄心，黄金奇异果                                       |          |
| image      | 商品图片 | http://127.0.0.1:8083/images/6037baf8-5251-4560-be5a-32b8ee3823cf.png |          |

请求示例

```
/admin/product/add
```

body：

```
{"name":"猕猴桃","categoryId":5,"price":1000,"stock":10,"status":1,"detail":"新西兰黄心，黄金奇异果","image":"http://127.0.0.1:8083/images/6037baf8-5251-4560-be5a-32b8ee3823cf.png"}
```

返回示例

```
{
  "status": 10000,
  "msg": "SUCCESS",
  "data": null
}
```

#### 3.1.2 上传图片

| 请求地址 | /admin/upload/file |
| -------- | ------------------ |
| 请求方式 | POST               |

参数：

body 的类型是 form-data，key 是 file，value 是里传 file 类型的文件：

返回:

```
{
  "status": 10000,
  "msg": "SUCCESS",
  "data": "http://127.0.0.1:8082/upload/b899f512-3467-4c71-8d2d-2d491b21f429.png"
}
```

#### 3.1.3 更新商品

| 请求地址 | /admin/product/update |
| -------- | --------------------- |
| 请求方式 | POST                  |

参数

| 参数       | 参数含义 | 示例                                                         | 备注     |
| ---------- | -------- | ------------------------------------------------------------ | -------- |
| id         | 商品ID   | 1                                                            |          |
| name       | 商品名称 | 猕猴桃                                                       |          |
| categoryId | 目录ID   | 5                                                            |          |
| price      | 价格     | 1000                                                         | 单位是分 |
| stock      | 库存     | 10                                                           |          |
| detail     | 商品描述 | 新西兰黄心，黄金奇异果                                       |          |
| image      | 商品图片 | http://127.0.0.1:8083/images/6037baf8-5251-4560-be5a-32b8ee3823cf.png |          |

请求示例

```
/admin/product/add
```

body：

```
{"id":3,"name":"早餐小面包 3","categoryId":5,"price":1000,"stock":10,"status":1,"detail":"好吃的小面包","image":"http://127.0.0.1:8083/images/6037baf8-5251-4560-be5a-32b8ee3823cf.png"}

```



返回示例

```
{
  "status": 10000,
  "msg": "SUCCESS",
  "data": null
}
```



#### 3.1.4 删除商品

| 请求地址 | /admin/product/delete |
| -------- | --------------------- |
| 请求方式 | POST                  |

参数

| 参数 | 参数含义 | 示例 | 备注 |
| ---- | -------- | ---- | ---- |
| id   | 商品的id | 1    |      |

请求示例

```
/admin/product/delete?id=1
```

返回示例

```
{
  "status": 10000,
  "msg": "SUCCESS",
  "data": null
}
```



#### 3.1.5 批量上下架商品

| 请求地址 | /admin/product/delete |
| -------- | --------------------- |
| 请求方式 | POST                  |

参数

| 参数       | 参数含义         | 示例  | 备注             |
| ---------- | ---------------- | ----- | ---------------- |
| ids        | 要更改的商品的id | 1,2,4 | 数组             |
| sellStatus | 上下架状态       | 1     | 1是上架，0是下架 |

请求示例

```
/admin/product/batchUpdateSellStatus?ids=1,2,4&sellStatus=1
```

返回示例

```
{
  "status": 10000,
  "msg": "SUCCESS",
  "data": null
}

```



#### 3.1.6 商品列表(后台）

| 请求地址 | /admin/product/list |
| -------- | ------------------- |
| 请求方式 | GET                 |

参数

| 参数     | 参数含义 | 示例 | 备注                   |
| -------- | -------- | ---- | ---------------------- |
| pageNum  | 页数     | 1    | 默认会按照修改时间倒序 |
| pageSize | 每页条数 | 10   |                        |

请求示例

```
/admin/product/list?pageSize=5&pageNum=1 
```



返回示例

```
{
  "status": 10000,
  "msg": "SUCCESS",
  "data": {
    "total": 20,
    "list": [
      {
        "id": 40,
        "name": "胡萝卜",
        "image": "http://127.0.0.1:8083/images/huluobo.jpg",
        "detail": "商品名称：绿鲜知胡萝卜商品编号：4116192商品毛重：1.07kg商品产地：北京包装：简装分类：萝卜烹饪建议：火锅，炒菜，炖菜",
        "categoryId": 18,
        "price": 222,
        "stock": 222,
        "status": 1,
        "createTime": "2019-12-28T08:06:34.000+0000",
        "updateTime": "2020-02-10T16:53:25.000+0000"
      }
    ],
    "pageNum": 1,
    "pageSize": 1,
    "size": 1,
    "startRow": 1,
    "endRow": 1,
    "pages": 20,
    "prePage": 0,
    "nextPage": 2,
    "isFirstPage": true,
    "isLastPage": false,
    "hasPreviousPage": false,
    "hasNextPage": true,
    "navigatePages": 8,
    "navigatepageNums": [
      1,
      2,
      3,
      4,
      5,
      6,
      7,
      8
    ],
    "navigateFirstPage": 1,
    "navigateLastPage": 8
  }
}
```



### 3.2 前台管理：

#### 3.2.1 商品列表

| 请求地址 | /product/list |
| -------- | ------------- |
| 请求方式 | GET           |

参数

| 参数       | 参数含义     | 示例       | 备注                      |
| ---------- | ------------ | ---------- | ------------------------- |
| orderBy    | 排序方式     | price desc | price desc 或者 price asc |
| categoryId | 商品分类 Id  | 1          |                           |
| keyword    | 搜索的关键词 | 桃         |                           |
| pageNum    | 页数         | 1          |                           |
| pageSize   | 每页条数     | 10         |                           |

请求示例

```
/product/list?orderBy=price desc&categoryId=4
```



返回示例

```
{
  "status": 10000,
  "msg": "SUCCESS",
  "data": {
    "total": 20,
    "list": [
      {
        "id": 2,
        "name": "澳洲进口大黑车厘子大樱桃包甜黑樱桃大果多汁 500g 特大果",
        "image": "http://127.0.0.1:8083/images/chelizi2.jpg",
        "detail": "商品毛重：1.0kg货号：608323093445原产地：智利类别：美早热卖时间：1月，11月，12月国产/进口：进口售卖方式：单品",
        "categoryId": 14,
        "price": 50,
        "stock": 100,
        "status": 1,
        "createTime": "2019-12-18T08:08:15.000+0000",
        "updateTime": "2020-02-10T16:08:25.000+0000"
      },
      {
        "id": 3,
        "name": "茶树菇 美味菌菇 东北山珍 500g",
        "image": "http://127.0.0.1:8083/images/chashugu.jpg",
        "detail": "商品名：茶树菇 商品特点：美味菌菇 东北山珍 500g",
        "categoryId": 15,
        "price": 1000,
        "stock": 6,
        "status": 1,
        "createTime": "2019-12-18T08:10:50.000+0000",
        "updateTime": "2020-02-10T16:42:42.000+0000"
      },
      {
        "id": 14,
        "name": "Zespri佳沛 新西兰阳光金奇异果 6个装",
        "image": "http://127.0.0.1:8083/images/mihoutao2.jpg",
        "detail": "商品编号：4635056商品毛重：0.71kg商品产地：新西兰类别：金果包装：简装国产/进口：进口原产地：新西兰",
        "categoryId": 12,
        "price": 39,
        "stock": 77,
        "status": 1,
        "createTime": "2019-12-18T08:11:13.000+0000",
        "updateTime": "2020-02-10T15:36:48.000+0000"
      },
      {
        "id": 17,
        "name": "红颜奶油草莓 约重500g/20-24颗 新鲜水果",
        "image": "http://127.0.0.1:8083/images/caomei2.jpg",
        "detail": "商品毛重：0.58kg商品产地：丹东/南通/武汉类别：红颜草莓包装：简装国产/进口：国产",
        "categoryId": 11,
        "price": 99,
        "stock": 84,
        "status": 1,
        "createTime": "2019-12-18T08:11:13.000+0000",
        "updateTime": "2020-02-10T15:37:48.000+0000"
      },
      {
        "id": 21,
        "name": "智利原味三文鱼排（大西洋鲑）240g/袋 4片装",
        "image": "http://127.0.0.1:8083/images/sanwenyu2.jpg",
        "detail": "商品毛重：260.00g商品产地：中国大陆保存状态：冷冻国产/进口：进口包装：简装类别：三文鱼海水/淡水：海水烹饪建议：煎炸，蒸菜，烧烤原产地：智利",
        "categoryId": 8,
        "price": 499,
        "stock": 1,
        "status": 1,
        "createTime": "2019-12-28T07:13:07.000+0000",
        "updateTime": "2020-02-10T15:38:46.000+0000"
      },
      {
        "id": 22,
        "name": "即食海参大连野生辽刺参 新鲜速食 特级生鲜海产 60~80G",
        "image": "http://127.0.0.1:8083/images/haishen.jpg",
        "detail": "商品毛重：1.5kg商品产地：中国大陆贮存条件：冷冻重量：50-99g国产/进口：国产适用场景：养生滋补包装：袋装原产地：辽宁年限：9年以上等级：特级食品工艺：冷冻水产热卖时间：9月类别：即食海参固形物含量：70%-90%特产品类：大连海参售卖方式：单品",
        "categoryId": 13,
        "price": 699,
        "stock": 3,
        "status": 1,
        "createTime": "2019-12-28T07:16:29.000+0000",
        "updateTime": "2020-02-10T16:04:29.000+0000"
      },
      {
        "id": 23,
        "name": "澳大利亚直采鲜橙 精品澳橙12粒 单果130-180g",
        "image": "http://127.0.0.1:8083/images/chengzi.jpg",
        "detail": "商品毛重：2.27kg商品产地：澳大利亚类别：脐橙包装：简装国产/进口：进口原产地：澳大利亚",
        "categoryId": 4,
        "price": 12,
        "stock": 12,
        "status": 1,
        "createTime": "2019-12-28T08:02:13.000+0000",
        "updateTime": "2020-02-10T16:40:15.000+0000"
      },
      {
        "id": 24,
        "name": "智利帝王蟹礼盒装4.4-4.0斤/只 生鲜活鲜熟冻大螃蟹",
        "image": "http://127.0.0.1:8083/images/diwangxie.jpg",
        "detail": "商品毛重：3.0kg商品产地：智利大闸蟹售卖方式：公蟹重量：2000-4999g套餐份量：5人份以上国产/进口：进口海水/淡水：海水烹饪建议：火锅，炒菜，烧烤，刺身，加热即食包装：简装原产地：智利保存状态：冷冻公单蟹重：5.5两及以上分类：帝王蟹特产品类：其它售卖方式：单品",
        "categoryId": 7,
        "price": 222,
        "stock": 222,
        "status": 1,
        "createTime": "2019-12-28T08:06:34.000+0000",
        "updateTime": "2020-02-10T16:05:05.000+0000"
      },
      {
        "id": 25,
        "name": "新疆库尔勒克伦生无籽红提 国产新鲜红提葡萄 提子 5斤装",
        "image": "http://127.0.0.1:8083/images/hongti.jpg",
        "detail": "商品毛重：2.5kg商品产地：中国大陆货号：XZL201909002重量：2000-3999g套餐份量：2人份国产/进口：国产是否有机：非有机单箱规格：3个装，4个装，5个装类别：红提包装：简装原产地：中国大陆售卖方式：单品",
        "categoryId": 28,
        "price": 222,
        "stock": 222,
        "status": 1,
        "createTime": "2019-12-28T08:06:34.000+0000",
        "updateTime": "2020-02-10T16:44:05.000+0000"
      },
      {
        "id": 26,
        "name": "越南进口红心火龙果 4个装 红肉中果 单果约330-420g",
        "image": "http://127.0.0.1:8083/images/hongxinhuolongguo.jpg",
        "detail": "商品毛重：1.79kg商品产地：越南重量：1000-1999g类别：红心火龙果包装：简装国产/进口：进口",
        "categoryId": 28,
        "price": 222,
        "stock": 222,
        "status": 1,
        "createTime": "2019-12-28T08:06:34.000+0000",
        "updateTime": "2020-02-10T16:44:11.000+0000"
      }
    ],
    "pageNum": 1,
    "pageSize": 10,
    "size": 10,
    "startRow": 1,
    "endRow": 10,
    "pages": 2,
    "prePage": 0,
    "nextPage": 2,
    "isFirstPage": true,
    "isLastPage": false,
    "hasPreviousPage": false,
    "hasNextPage": true,
    "navigatePages": 8,
    "navigatepageNums": [
      1,
      2
    ],
    "navigateFirstPage": 1,
    "navigateLastPage": 2
  }
}
```



#### 3.2.2 商品详情

| 请求地址 | /product/detail |
| -------- | --------------- |
| 请求方式 | GET             |

参数

| 参数 | 参数含义 | 示例 | 备注 |
| ---- | -------- | ---- | ---- |
| id   | 商品ID   | 2    |      |

请求示例

```
/product/detail?id=2
```

返回示例

```
{
  "status": 10000,
  "msg": "SUCCESS",
  "data": {
    "id": 2,
    "name": "澳洲进口大黑车厘子大樱桃包甜黑樱桃大果多汁 500g 特大果",
    "image": "http://127.0.0.1:8083/images/chelizi2.jpg",
    "detail": "商品毛重：1.0kg货号：608323093445原产地：智利类别：美早热卖时间：1月，11月，12月国产/进口：进口售卖方式：单品",
    "categoryId": 14,
    "price": 50,
    "stock": 100,
    "status": 1,
    "createTime": "2019-12-18T08:08:15.000+0000",
    "updateTime": "2020-02-10T16:08:25.000+0000"
  }
}
```



### 3.3 代码和测试：

#### 3.3.1 代码

参见github

#### 3.3.2 测试

![img.png](img/img39.png)

![img_1.png](img/img40.png)

![img_2.png](img/img41.png)

![img_3.png](img/img42.png)

![img_4.png](img/img43.png)

![img_5.png](img/img44.png)

![img_6.png](img/img45.png)

![img_7.png](img/img46.png)





## 4 购物车模块

### 4.1 接口文档

#### 4.1.1  购物车列表

| 请求地址 | /cart/list |
| -------- | ---------- |
| 请求方式 | GET        |

参数

| 参数 | 参数含义 | 示例 | 备注 |
| ---- | -------- | ---- | ---- |
| 无   |          |      |      |



请求示例

```
/cart/list
```

返回示例

```
{
  "status": 10000,
  "msg": "SUCCESS",
  "data": [
   {
      "id": 5,
      "productId": 3,
      "userId": 9,
      "quantity": 1,
      "selected": 1,
      "price": 1000,
      "totalPrice": 1000,
      "productName": "茶树菇 美味菌菇 东北山珍 500g",
      "productImage": "http://127.0.0.1:8083/images/chashugu.jpg"
    }
  ]
}
```



#### 4.1.2  添加商品到购物车

| 请求地址 | /cart/add |
| -------- | --------- |
| 请求方式 | POST      |

参数

| 参数      | 参数含义 | 示例 | 备注 |
| --------- | -------- | ---- | ---- |
| productId | 商品ID   | 5    |      |
| count     | 数量     | 1    |      |

请求示例

```
/cart/add?productId=5&count=1
```

返回的是“购物车列表”

#### 4.1.3  更新购物车某个商品的数量

| 请求地址 | /cart/update |
| -------- | ------------ |
| 请求方式 | POST         |

参数

| 参数      | 参数含义 | 示例 | 备注 |
| --------- | -------- | ---- | ---- |
| productId | 商品ID   | 5    |      |
| count     | 数量     | 1    |      |

请求示例

```
/cart/update?productId=5&count=1
```

返回的是“购物车列表”

####  4.1.4  删除购物车的某个商品

| 请求地址 | /cart/delete |
| -------- | ------------ |
| 请求方式 | POST         |

参数

| 参数      | 参数含义 | 示例 | 备注 |
| --------- | -------- | ---- | ---- |
| productId | 商品ID   | 5    |      |

请求示例

```
/cart/delete?productId=5
```

返回的是“购物车列表”

#### 4.1.5  选中/不选中购物车的某个商品

| 请求地址 | /cart/select |
| -------- | ------------ |
| 请求方式 | POST         |

参数

| 参数      | 参数含义 | 示例 | 备注                 |
| --------- | -------- | ---- | -------------------- |
| productId | 商品ID   | 5    |                      |
| selected  | 是否选中 | 1    | 0 是不选中，1 是选中 |

请求示例

```
/cart/select?productId=2&selected=1
```

返回的是“购物车列表”

#### 4.1.6  全选/全不选购物车的某个商品

| 请求地址 | /cart/selectAll |
| -------- | --------------- |
| 请求方式 | POST            |

参数

| 参数     | 参数含义 | 示例 | 备注                 |
| -------- | -------- | ---- | -------------------- |
| selected | 是否选中 | 1    | 0 是不选中，1 是选中 |

请求示例

```
/cart/selectAll?selected=1
```



#### 4.2 代码和测试

代码部分参见github

![img.png](img/img47.png)

![img_1.png](img/img48.png)

![img_2.png](img/img49.png)

![img_3.png](img/img50.png)

![img_4.png](img/img51.png)

![img_5.png](img/img52.png)




