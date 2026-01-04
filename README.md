📚 图书馆管理系统 (Library Management System)
一个基于Spring Boot 3.2.5的现代化、功能完整的图书馆管理系统，实现了图书管理、用户管理、借阅/归还流程及数据统计等核心功能。

🚀 快速开始
环境要求
确保你的开发环境满足以下要求：

JDK: 17 或更高版本 (推荐JDK 17或21)

构建工具: Apache Maven 3.6+

数据库: MySQL 8.0+ (用于生产环境) 或 H2 Database (用于内嵌测试)

IDE (可选): Eclipse, IntelliJ IDEA 或 VS Code

1. 获取代码
bash
git clone [你的GitHub仓库地址]
cd LibraryManagementSystem
2. 配置数据库
启动你的MySQL服务。

创建数据库：

sql
CREATE DATABASE library_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
根据项目中的 init_database.sql 文件初始化表结构（可选，JPA可自动建表）。

3. 修改应用配置
打开 src/main/resources/application.yml，根据你的环境修改数据库连接等配置，关键部分如下：

yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/library_db?useSSL=false&serverTimezone=Asia/Shanghai
    username: [你的MySQL用户名] # 例如: root
    password: [你的MySQL密码]   # 例如: 123456
4. 编译与运行
方式一：使用Maven命令直接运行（推荐用于开发）

bash
# 在项目根目录下执行
mvn spring-boot:run[citation:4]
应用启动后，控制台会打印访问地址。

方式二：打包后运行

bash
# 1. 使用Maven打包项目[citation:10]
mvn clean package

# 2. 运行生成的JAR文件（通常在 `target/` 目录下）
java -jar target/LibraryManagementSystem-1.0.0.jar[citation:10]
方式三：在IDE中运行

使用Eclipse或IntelliJ IDEA导入为Maven项目。

找到主启动类 com.library.LibraryApplication.java。

右键选择 Run As -> Java Application 或 Spring Boot App。

📦 项目依赖清单 (Project Dependencies)
本项目采用Maven构建，核心依赖如下表所示：

依赖组 (GroupId)	工件 (ArtifactId)	说明 (Description)	类型
org.springframework.boot	spring-boot-starter-parent	Spring Boot父项目，提供依赖管理和默认配置。	Parent POM
org.springframework.boot	spring-boot-starter-web	Web应用启动器，包含Tomcat和Spring MVC。	Starter
org.springframework.boot	spring-boot-starter-data-jpa	数据访问启动器，集成Spring Data JPA和Hibernate。	Starter
org.springframework.boot	spring-boot-starter-validation	数据校验启动器。	Starter
com.mysql	mysql-connector-j	MySQL数据库驱动。	Runtime
org.springdoc	springdoc-openapi-starter-webmvc-ui	API文档，用于生成Swagger UI界面。	Library
org.projectlombok	lombok	代码简化工具，自动生成Getter/Setter等方法。	Provided
org.springframework.boot	spring-boot-starter-test	测试启动器（包含JUnit）。	Test
com.h2database	h2	H2内存数据库，用于快速测试。	Test
完整的依赖树和版本定义请查看项目根目录下的 pom.xml 文件。Spring Boot的BOM（Bill of Materials）机制已自动管理了大部分依赖的兼容版本。

🌐 访问应用
应用成功启动后（默认端口8080），你可以通过以下链接访问：

系统首页：http://localhost:8080/

提供项目介绍和功能导航。

RESTful API 文档 (Swagger UI)：http://localhost:8080/swagger-ui/index.html

核心功能：在此页面可以查看、测试所有后台API接口，这是与系统交互的主要方式。

健康检查端点：http://localhost:8080/api/library/health

用于检查服务状态。

🔧 常见问题 (Troubleshooting)
端口冲突：如果8080端口被占用，在 application.yml 中修改 server.port 属性。

数据库连接失败：请确认 application.yml 中的数据库IP、端口、用户名和密码是否正确，并确保MySQL服务已启动。

依赖下载慢或失败：可考虑配置Maven使用国内镜像源（如阿里云镜像）。

Lombok 注解在IDE中不生效：请在Eclipse/IntelliJ IDEA中安装Lombok插件并启用注解处理。
