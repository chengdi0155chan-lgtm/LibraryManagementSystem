📚 图书馆管理系统 - 运行说明与依赖清单
🚀 快速开始
环境要求
JDK: 17 或更高版本（建议使用 JDK 17）

MySQL: 8.0 或更高版本

Maven: 3.6+（用于构建）

Eclipse IDE for Enterprise Java: 2025-12 或更高版本（推荐）

第一步：克隆项目
bash
git clone https://github.com/yourusername/library-management-system.git
cd library-management-system
第二步：数据库配置
启动 MySQL 服务

创建数据库：

sql
CREATE DATABASE library_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

第三步：配置应用
修改数据库连接配置（src/main/resources/application.yml）：

yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/library_db
    username: your_username  # 改为你的MySQL用户名
    password: your_password  # 改为你的MySQL密码
第四步：运行项目
方式一：使用 Eclipse IDE
导入项目：

text
File → Import → Maven → Existing Maven Projects
更新 Maven 依赖：

text
右键项目 → Maven → Update Project
运行启动类：

text
找到 LibraryApplication.java → 右键 → Run As → Java Application
方式二：使用 Maven 命令行
bash
# 编译项目
mvn clean compile

# 运行项目
mvn spring-boot:run

# 或打包后运行
mvn clean package
java -jar target/LibraryManagementSystem-1.0.0.jar
第五步：访问应用
项目启动后，访问以下地址：

系统首页: http://localhost:8080/

API 文档 (Swagger UI): http://localhost:8080/swagger-ui/index.html

健康检查: http://localhost:8080/api/library/health

用户管理 API: http://localhost:8080/api/users

图书管理 API: http://localhost:8080/api/books

第六步：测试账号
系统预置了以下测试账号：

用户名	密码	角色	权限
admin	admin123	ADMIN	系统管理员，拥有所有权限
librarian	lib123	LIBRARIAN	图书管理员，可以管理图书和借阅
user1	user123	USER	普通用户，可以借阅图书
📦 依赖清单
核心依赖（pom.xml 配置）
xml
<!-- Spring Boot 父项目 -->
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>3.2.5</version>
</parent>

<!-- ==================== 主要依赖 ==================== -->

<!-- Web开发 -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>

<!-- 数据访问 (JPA) -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>

<!-- 数据验证 -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
</dependency>

<!-- 安全认证 -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>

<!-- 缓存支持 -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-cache</artifactId>
</dependency>

<!-- ==================== 数据库 ==================== -->

<!-- MySQL 驱动 -->
<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
    <scope>runtime</scope>
</dependency>

<!-- H2 数据库（测试环境） -->
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <scope>test</scope>
</dependency>

<!-- ==================== 工具库 ==================== -->

<!-- Lombok（减少样板代码） -->
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <optional>true</optional>
</dependency>

<!-- 对象映射 -->
<dependency>
    <groupId>org.mapstruct</groupId>
    <artifactId>mapstruct</artifactId>
    <version>1.5.5.Final</version>
</dependency>
<dependency>
    <groupId>org.mapstruct</groupId>
    <artifactId>mapstruct-processor</artifactId>
    <version>1.5.5.Final</version>
    <scope>provided</scope>
</dependency>

<!-- ==================== API文档 ==================== -->

<!-- OpenAPI 3 / Swagger -->
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    <version>2.5.0</version>
</dependency>

<!-- ==================== 安全与认证 ==================== -->

<!-- JWT 令牌 -->
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-api</artifactId>
    <version>0.12.5</version>
</dependency>
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-impl</artifactId>
    <version>0.12.5</version>
    <scope>runtime</scope>
</dependency>
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-jackson</artifactId>
    <version>0.12.5</version>
    <scope>runtime</scope>
</dependency>

<!-- ==================== 测试 ==================== -->

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
</dependency>
<dependency>
    <groupId>org.springframework.security</groupId>
    <artifactId>spring-security-test</artifactId>
    <scope>test</scope>
</dependency>

<!-- JUnit 5 -->
<dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter-api</artifactId>
    <scope>test</scope>
</dependency>
<dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter-engine</artifactId>
    <scope>test</scope>
</dependency>

<!-- ==================== 开发工具 ==================== -->

<!-- 开发热部署 -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
    <scope>runtime</scope>
    <optional>true</optional>
</dependency>

<!-- ==================== 其他工具 ==================== -->

<!-- Apache Commons -->
<dependency>
    <groupId>org.apache.commons</groupId>
    <artifactId>commons-lang3</artifactId>
</dependency>
<dependency>
    <groupId>commons-io</groupId>
    <artifactId>commons-io</artifactId>
    <version>2.15.1</version>
</dependency>
环境变量配置（可选）
如果需要，可以在 application.yml 中配置以下环境变量：

yaml
# 数据库连接
DATABASE_URL: jdbc:mysql://localhost:3306/library_db
DATABASE_USERNAME: root
DATABASE_PASSWORD: your_password

# JWT 配置
JWT_SECRET: library-management-secret-key
JWT_EXPIRATION: 86400000  # 24小时

# 服务器配置
SERVER_PORT: 8080
SERVER_CONTEXT_PATH: /api
🔧 常见问题解决
1. 端口冲突
yaml
# 修改 application.yml
server:
  port: 8081  # 改为其他可用端口
2. 数据库连接失败
确保 MySQL 服务已启动

检查用户名和密码是否正确

验证数据库是否存在

3. Maven 依赖下载失败
bash
# 使用阿里云镜像加速
mvn clean install -DskipTests -Denforcer.skip=true
4. 测试运行失败
bash
# 跳过测试
mvn clean package -DskipTests
📊 项目结构说明
text
src/
├── main/
│   ├── java/com/library/        # Java 源代码
│   │   ├── config/              # 配置类
│   │   ├── controller/          # REST API 控制器
│   │   ├── service/             # 业务逻辑层
│   │   ├── repository/          # 数据访问层
│   │   ├── entity/              # JPA 实体类
│   │   ├── dto/                 # 数据传输对象
│   │   ├── exception/           # 异常处理
│   │   └── util/                # 工具类
│   └── resources/               # 资源文件
│       ├── static/              # 静态文件（HTML, CSS, JS）
│       └── application.yml      # 应用配置文件
└── test/                        # 测试代码
📞 技术支持
如有问题，请：

查看控制台错误日志

检查数据库连接配置

验证依赖是否完整下载

参考 API 文档：http://localhost:8080/swagger-ui/index.html

项目状态: ✅ 运行正常
最后更新: 2026年
开发者: [刘孙文[
课程: 《软件工程与AI辅助开发》
