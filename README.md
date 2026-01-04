📚 图书馆管理系统
🎯 项目简介
基于 Spring Boot 3.2.5 + MySQL 8.0 的现代化图书馆管理系统，提供完整的图书管理、借阅归还、用户管理、统计报表等功能。本项目为《软件工程与AI辅助开发》课程作业，展示了如何结合AI工具完成软件开发全流程。

✨ 主要特性
🔧 核心功能
用户管理：注册、登录、权限控制（管理员/图书管理员/普通用户）

图书管理：增删改查、库存管理、分类搜索

借阅管理：借阅、归还、续借、预约

统计分析：用户统计、图书统计、借阅统计

罚款管理：自动计算超期罚款、罚款支付

提醒系统：借阅到期提醒、超期提醒

🚀 技术特性
RESTful API：规范的API设计，前后端分离

JPA/Hibernate：对象关系映射，自动生成表结构

Spring Security：安全认证与权限控制

Swagger/OpenAPI：完整的API文档

缓存机制：提升系统性能

统一异常处理：规范的错误响应

分页查询：大数据量高效处理

🏗️ 技术栈
后端技术
技术	版本	说明
Spring Boot	3.2.5	快速开发框架
Java	17+	编程语言
MySQL	8.0+	数据库
Spring Data JPA	3.2.5	数据持久层
Spring Security	6.2.0	安全框架
SpringDoc OpenAPI	2.5.0	API文档生成
Maven	3.6+	项目管理工具
前端技术
技术	版本	说明
Bootstrap	5.3.0	CSS框架
Font Awesome	6.4.0	图标库
JavaScript	ES6+	客户端脚本
📁 项目结构
text
LibraryManagementSystem/
├── src/
│   ├── main/
│   │   ├── java/com/library/
│   │   │   ├── LibraryApplication.java      # 启动类
│   │   │   ├── ApiResponse.java            # 统一响应格式
│   │   │   ├── AppConstants.java           # 应用常量
│   │   │   ├── config/                     # 配置类
│   │   │   │   ├── ApplicationConfig.java
│   │   │   │   ├── SecurityConfig.java
│   │   │   │   └── OpenApiConfig.java
│   │   │   ├── controller/                 # 控制器层
│   │   │   │   ├── UserController.java
│   │   │   │   ├── BookController.java
│   │   │   │   ├── BorrowController.java
│   │   │   │   ├── LibraryController.java
│   │   │   │   └── HomeController.java
│   │   │   ├── service/                    # 业务逻辑层
│   │   │   │   ├── UserService.java
│   │   │   │   ├── BookService.java
│   │   │   │   ├── BorrowRecordService.java
│   │   │   │   ├── LibraryService.java
│   │   │   │   └── impl/                   # 实现类
│   │   │   ├── repository/                 # 数据访问层
│   │   │   │   ├── UserRepository.java
│   │   │   │   ├── BookRepository.java
│   │   │   │   └── BorrowRecordRepository.java
│   │   │   ├── entity/                     # 实体类
│   │   │   │   ├── BaseEntity.java
│   │   │   │   ├── User.java
│   │   │   │   ├── Book.java
│   │   │   │   └── BorrowRecord.java
│   │   │   ├── dto/                        # 数据传输对象
│   │   │   │   ├── UserDTO.java
│   │   │   │   ├── BookDTO.java
│   │   │   │   └── BorrowRequestDTO.java
│   │   │   └── exception/                  # 异常处理
│   │   │       ├── GlobalExceptionHandler.java
│   │   │       └── BusinessException.java
│   │   └── resources/
│   │       ├── application.yml             # 主配置文件
│   │       ├── application-test.yml        # 测试配置
│   │       └── static/
│   │           └── index.html              # 前端首页
│   └── test/
│       └── java/com/library/               # 测试代码
├── pom.xml                                 # Maven配置
├── init_database.sql                       # 数据库初始化脚本
├── README.md                               # 项目说明（本文件）
└── .gitignore                              # Git忽略文件
🚀 快速开始
环境要求
JDK: 17 或更高版本

MySQL: 8.0 或更高版本

Maven: 3.6 或更高版本

Eclipse/IDEA: 推荐使用 Eclipse 2025-12 或 IntelliJ IDEA

1. 数据库配置
sql
-- 创建数据库
CREATE DATABASE library_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 使用数据库
USE library_db;

-- 执行初始化脚本
-- 或者使用项目根目录下的 init_database.sql 文件
2. 修改配置文件
编辑 src/main/resources/application.yml：

yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/library_db?useSSL=false&serverTimezone=Asia/Shanghai&characterEncoding=UTF-8
    username: your_username      # 修改为你的MySQL用户名
    password: your_password      # 修改为你的MySQL密码
3. 导入项目到 Eclipse
打开 Eclipse

File → Import → Maven → Existing Maven Projects

选择项目目录

点击 Finish

4. 运行项目
方式一：在 Eclipse 中运行
找到 LibraryApplication.java

右键 → Run As → Java Application

观察控制台输出，确认启动成功

方式二：使用 Maven 命令
bash
# 进入项目目录
cd LibraryManagementSystem

# 编译项目
mvn clean compile

# 运行项目
mvn spring-boot:run
5. 访问系统
访问地址	说明
http://localhost:8080/	系统首页
http://localhost:8080/api	API根路径
http://localhost:8080/swagger-ui/index.html	API文档
http://localhost:8080/api/library/health	健康检查
📚 默认账号
角色	用户名	密码	说明
管理员	admin	admin123	拥有所有权限
图书管理员	librarian	lib123	管理图书和借阅
普通用户	user1	user123	普通读者
🔧 API 接口
用户管理 API
方法	端点	说明
GET	/api/users	获取用户列表
POST	/api/users	创建新用户
GET	/api/users/{id}	获取用户详情
PUT	/api/users/{id}	更新用户信息
DELETE	/api/users/{id}	删除用户
创建用户示例：

bash
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "username": "testuser",
    "passwordHash": "password123",
    "email": "test@example.com",
    "realName": "测试用户",
    "role": "USER"
  }'
图书管理 API
方法	端点	说明
GET	/api/books	获取图书列表
POST	/api/books	添加新图书
GET	/api/books/{id}	获取图书详情
PUT	/api/books/{id}	更新图书信息
GET	/api/books/search	搜索图书
创建图书示例：

bash
curl -X POST http://localhost:8080/api/books \
  -H "Content-Type: application/json" \
  -d '{
    "isbn": "978-7-111-55674-7",
    "title": "Spring Boot实战",
    "author": "张三",
    "publisher": "机械工业出版社",
    "category": "计算机",
    "totalCopies": 10,
    "availableCopies": 5
  }'
借阅管理 API
方法	端点	说明
POST	/api/borrow-records	创建借阅记录
POST	/api/borrow-records/{id}/return	归还图书
POST	/api/borrow-records/{id}/renew	续借图书
GET	/api/borrow-records/user/{userId}	获取用户借阅记录
借阅图书示例：

bash
curl -X POST http://localhost:8080/api/borrow-records \
  -H "Content-Type: application/json" \
  -d '{
    "userId": 1,
    "bookId": 1,
    "borrowDays": 30
  }'
综合功能 API
方法	端点	说明
GET	/api/library/health	健康检查
GET	/api/library/overview	系统概览
POST	/api/library/register	用户注册
POST	/api/library/login	用户登录
🧪 测试
单元测试
bash
# 运行所有测试
mvn test

# 运行特定测试类
mvn test -Dtest=ServiceTest
API 测试
使用 Swagger UI：http://localhost:8080/swagger-ui/index.html

使用 Postman 或 curl

使用 前端测试页面：http://localhost:8080/

数据库测试
sql
-- 查看用户表
SELECT * FROM users;

-- 查看图书表
SELECT id, isbn, title, author, available_copies FROM books;

-- 查看借阅记录
SELECT * FROM borrow_records;
🐛 常见问题
1. 端口被占用
错误：Address already in use
解决：修改 application.yml 中的 server.port

yaml
server:
  port: 8081  # 改为其他端口
2. 数据库连接失败
错误：Communications link failure
解决：

确认MySQL服务已启动

检查 application.yml 中的数据库配置

确认数据库权限

3. 表结构未创建
错误：Table not found
解决：检查JPA配置

yaml
spring:
  jpa:
    hibernate:
      ddl-auto: update  # 确保为update或create
4. 依赖下载失败
解决：

bash
# 清理并重新下载依赖
mvn clean compile -U
🤖 AI辅助开发记录
使用的AI工具
工具	使用场景	效果评价
ChatGPT	代码生成、问题解答、设计建议	优秀
GitHub Copilot	代码补全、快速实现	良好
其他AI工具	调试优化、文档编写	良好
AI辅助开发过程
需求分析阶段

使用AI分析项目需求

生成数据库设计建议

制定开发计划

系统设计阶段

AI生成系统架构图

提供技术选型建议

生成API设计文档

编码实现阶段

AI辅助编写实体类、Repository、Service、Controller

生成单元测试代码

调试和优化代码

测试部署阶段

生成测试用例

提供部署方案

编写文档

AI使用评价
优点：

提高效率：代码生成速度快，减少重复工作

学习辅助：提供最佳实践和代码示例

问题解决：快速定位和解决技术问题

不足：

上下文限制：有时需要多次提示才能得到理想结果

准确性：生成的代码可能需要人工调整

创造性：复杂业务逻辑仍需人工设计

📊 项目统计
指标	数量
实体类	4个
Repository接口	3个
Service接口/实现	8个
Controller	5个
API端点	40+个
测试用例	20+个
代码行数	3000+行
📝 作业要求完成情况
任务一：软件工程核心流程
需求分析：完成图书馆管理系统需求文档

系统设计：完成数据库设计和API设计

编码实现：完成所有核心功能开发

测试：完成单元测试和集成测试

部署：提供完整的部署说明

任务二：AI辅助开发过程记录
AI对话记录：记录与AI的交互过程

AI应用场景：记录AI在各个环节的应用

AI产生文档：记录AI生成的文档和设计

任务三：AI工具评价
全面性评价：从准确性、效率、可维护性等维度评价

实例分析：结合具体代码片段分析

客观性：提供客观可验证的评价依据

👥 贡献指南
Fork 项目

创建分支：git checkout -b feature/your-feature

提交更改：git commit -m 'Add some feature'

推送分支：git push origin feature/your-feature

提交 Pull Request

📄 许可证
本项目仅用于课程作业学习，不用于商业用途。

📞 联系方式
姓名：[你的姓名]

学号：[你的学号]

课程：《软件工程与AI辅助开发》

提交时间：第18周星期天24:00之前

最后更新：2024年5月
项目状态：已完成
