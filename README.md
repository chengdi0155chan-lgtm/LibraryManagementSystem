📚 图书馆管理系统 - Library Management System
https://img.shields.io/badge/Spring%2520Boot-3.2.5-green.svg
https://img.shields.io/badge/MySQL-8.0-blue.svg
https://img.shields.io/badge/Java-17-orange.svg
https://img.shields.io/badge/License-MIT-yellow.svg

🌟 项目简介
基于 Spring Boot 的现代化图书馆管理系统，实现图书的入库、借阅、归还、查询，以及用户管理、借阅统计等基础功能。本项目为《软件工程与AI辅助开发》课程作业，展示了如何结合AI工具完成软件开发的完整流程。

✨ 核心功能
📖 图书管理
图书信息的增删改查（CRUD）

ISBN号唯一性验证

库存管理（总册数、可借册数）

图书分类与搜索

👥 用户管理
用户注册与登录

角色权限管理（管理员、图书管理员、普通用户）

借阅数量限制控制

用户状态管理

🔄 借阅管理
图书借阅与归还

续借功能

超期罚款自动计算

借阅记录查询

📊 统计分析
图书馆概览统计

借阅趋势分析

热门图书排行

用户活跃度统计

🛡️ 系统安全
Spring Security 安全框架

密码加密存储

权限访问控制

API访问日志

🛠️ 技术栈
后端技术
框架: Spring Boot 3.2.5

安全: Spring Security

数据访问: Spring Data JPA

数据库: MySQL 8.0

API文档: OpenAPI 3.0 (Swagger)

构建工具: Maven

Java版本: 17

前端技术
框架: Bootstrap 5

图标: Font Awesome 6

交互: JavaScript ES6+

API调用: Fetch API

开发工具
IDE: Eclipse IDE for Enterprise Java 2025-12

版本控制: Git

API测试: Postman / Swagger UI

🚀 快速开始
环境要求
JDK 17 或更高版本

MySQL 8.0 或更高版本

Maven 3.6+

Git

安装步骤
1. 克隆项目
bash
git clone https://github.com/yourusername/library-management-system.git
cd library-management-system
2. 数据库配置
sql
-- 创建数据库
CREATE DATABASE library_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 使用数据库
USE library_db;

-- 执行初始化脚本（可选）
-- source init_database.sql
3. 配置文件修改
编辑 src/main/resources/application.yml：

yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/library_db
    username: your_username  # 修改为你的MySQL用户名
    password: your_password  # 修改为你的MySQL密码
4. 构建项目
bash
# 使用Maven构建
mvn clean package

# 或者直接运行
mvn spring-boot:run
5. 启动应用
bash
# 运行Spring Boot应用
java -jar target/LibraryManagementSystem-1.0.0.jar
访问地址
应用启动后，可以通过以下地址访问：

服务	地址	说明
系统首页	http://localhost:8080/	图书馆管理系统门户
API文档	http://localhost:8080/swagger-ui/index.html	Swagger UI API文档
健康检查	http://localhost:8080/api/library/health	系统健康状态
用户管理	http://localhost:8080/api/users	用户管理API
图书管理	http://localhost:8080/api/books	图书管理API
默认测试账号
text
管理员:
  - 用户名: admin
  - 密码: admin123

普通用户:
  - 用户名: user1
  - 密码: user123

图书管理员:
  - 用户名: librarian
  - 密码: lib123
📖 API文档
完整的API文档可通过Swagger UI访问：http://localhost:8080/swagger-ui/index.html

主要API端点
用户管理
方法	端点	描述
GET	/api/users	获取用户列表
POST	/api/users	创建新用户
GET	/api/users/{id}	获取用户详情
PUT	/api/users/{id}	更新用户信息
DELETE	/api/users/{id}	删除用户
图书管理
方法	端点	描述
GET	/api/books	获取图书列表
POST	/api/books	添加新图书
GET	/api/books/search	搜索图书
POST	/api/books/{id}/borrow	借阅图书
POST	/api/books/{id}/return	归还图书
借阅管理
方法	端点	描述
GET	/api/borrow-records	获取借阅记录
POST	/api/borrow-records	创建借阅记录
POST	/api/borrow-records/{id}/return	归还图书
GET	/api/borrow-records/overdue	获取超期记录
📁 项目结构
text
library-management-system/
├── src/
│   ├── main/
│   │   ├── java/com/library/
│   │   │   ├── LibraryApplication.java      # 启动类
│   │   │   ├── ApiResponse.java             # 统一API响应
│   │   │   ├── AppConstants.java            # 应用常量
│   │   │   ├── config/                      # 配置类
│   │   │   │   ├── ApplicationConfig.java
│   │   │   │   ├── SecurityConfig.java
│   │   │   │   └── OpenApiConfig.java
│   │   │   ├── controller/                  # 控制器层
│   │   │   │   ├── UserController.java
│   │   │   │   ├── BookController.java
│   │   │   │   ├── BorrowController.java
│   │   │   │   └── LibraryController.java
│   │   │   ├── service/                     # 业务逻辑层
│   │   │   │   ├── UserService.java
│   │   │   │   ├── BookService.java
│   │   │   │   ├── BorrowRecordService.java
│   │   │   │   ├── LibraryService.java
│   │   │   │   └── impl/                    # 实现类
│   │   │   ├── repository/                  # 数据访问层
│   │   │   │   ├── UserRepository.java
│   │   │   │   ├── BookRepository.java
│   │   │   │   └── BorrowRecordRepository.java
│   │   │   ├── entity/                      # 实体类
│   │   │   │   ├── BaseEntity.java
│   │   │   │   ├── User.java
│   │   │   │   ├── Book.java
│   │   │   │   └── BorrowRecord.java
│   │   │   ├── dto/                         # 数据传输对象
│   │   │   │   ├── UserDTO.java
│   │   │   │   ├── BookDTO.java
│   │   │   │   └── BorrowRequestDTO.java
│   │   │   ├── exception/                   # 异常处理
│   │   │   │   ├── GlobalExceptionHandler.java
│   │   │   │   └── BusinessException.java
│   │   └── resources/
│   │       ├── application.yml              # 主配置文件
│   │       ├── application-test.yml         # 测试配置文件
│   │       ├── static/                      # 静态资源
│   │       │   └── index.html               # 系统首页
│   │       └── templates/                   # 模板文件（可选）
│   └── test/                                # 测试代码
│       └── java/com/library/
│           ├── ApplicationTest.java
│           ├── EntityTest.java
│           ├── repository/
│           │   └── RepositoryTest.java
│           ├── service/
│           │   └── ServiceTest.java
│           └── controller/
│               └── ControllerTest.java
├── init_database.sql                        # 数据库初始化脚本
├── pom.xml                                  # Maven配置文件
├── README.md                                # 项目说明（本文件）
└── .gitignore                               # Git忽略文件
🧪 测试
运行测试
bash
# 运行所有测试
mvn test

# 运行特定测试类
mvn test -Dtest=ApplicationTest

# 生成测试覆盖率报告
mvn jacoco:report
测试覆盖
单元测试：Service层、Repository层

集成测试：Controller层API

数据库测试：使用H2内存数据库

🤖 AI辅助开发记录
本项目在开发过程中使用了多种AI工具辅助开发：

使用工具
ChatGPT: 需求分析、数据库设计、代码生成

GitHub Copilot: 代码补全、方法建议

CodeLlama: 特定代码片段生成

辅助场景
数据库设计: AI提供ER图设计和表结构建议

API设计: AI生成RESTful API规范和接口文档

代码实现: AI生成基础CRUD代码模板

错误调试: AI分析错误日志并提供解决方案

测试用例: AI生成单元测试和集成测试用例

AI工具评价
在本项目中，AI工具在以下方面表现出色：

✅ 效率提升: 基础代码生成速度提升60%

✅ 文档辅助: 快速生成API文档和注释

✅ 错误排查: 快速定位常见错误原因

同时存在以下局限：

⚠️ 复杂逻辑: 复杂业务逻辑仍需人工设计

⚠️ 代码质量: 生成代码需要人工审查和优化

⚠️ 安全性: 安全相关代码需要专业审查

📈 部署指南
生产环境部署
1. 环境变量配置
bash
# 设置环境变量
export DB_HOST=your_database_host
export DB_PORT=3306
export DB_NAME=library_db
export DB_USER=your_username
export DB_PASSWORD=your_password
export SERVER_PORT=8080
2. 构建生产版本
bash
# 打包应用
mvn clean package -DskipTests

# 生成可执行JAR
java -jar target/LibraryManagementSystem-1.0.0.jar
3. Docker部署（可选）
dockerfile
# Dockerfile
FROM openjdk:17-jdk-slim
COPY target/LibraryManagementSystem-1.0.0.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
bash
# 构建Docker镜像
docker build -t library-system .

# 运行容器
docker run -p 8080:8080 --name library-system library-system
📝 开发指南
代码规范
遵循Google Java代码风格

使用Lombok简化代码

方法注释使用JavaDoc格式

提交信息遵循Conventional Commits规范

分支策略
main: 主分支，生产代码

develop: 开发分支

feature/*: 功能分支

bugfix/*: 修复分支

release/*: 发布分支

提交规范
bash
git commit -m "feat: 添加用户注册功能"
git commit -m "fix: 修复借阅图书库存问题"
git commit -m "docs: 更新API文档"
git commit -m "test: 添加用户服务测试"
🐛 故障排除
常见问题
1. 数据库连接失败
问题: java.sql.SQLException: Access denied for user
解决:

检查application.yml中的数据库配置

确认MySQL服务已启动

验证用户名密码是否正确

2. 端口冲突
问题: Web server failed to start. Port 8080 was already in use.
解决:

yaml
# 修改application.yml
server:
  port: 8081  # 使用其他端口
3. 表结构未创建
问题: 数据库表未自动创建
解决:

yaml
# 确认配置正确
spring:
  jpa:
    hibernate:
      ddl-auto: update  # 开发环境使用update
4. Swagger无法访问
问题: 404错误或空白页面
解决:

确认已添加springdoc-openapi依赖

检查Spring Security配置是否允许访问

🤝 贡献指南
欢迎贡献代码！请遵循以下步骤：

Fork 项目

创建功能分支 (git checkout -b feature/AmazingFeature)

提交更改 (git commit -m 'Add some AmazingFeature')

推送分支 (git push origin feature/AmazingFeature)

开启 Pull Request

开发规范
新功能需提供单元测试

更新代码需同步更新文档

保持代码风格一致

重大更改需先开启Issue讨论

📄 许可证
本项目采用 MIT 许可证 - 查看 LICENSE 文件了解详情。

🙏 致谢
感谢课程《软件工程与AI辅助开发》提供的实践机会

感谢所有AI工具（ChatGPT、Copilot等）在开发过程中的辅助

感谢开源社区提供的优秀工具和框架

📞 联系信息
开发者: [你的姓名]
学号: [你的学号]
邮箱: [你的邮箱]
课程: 软件工程与AI辅助开发

项目状态: ✅ 已完成
最后更新: 2024年5月
版本: 1.0.0

