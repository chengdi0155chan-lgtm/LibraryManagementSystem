📚 图书馆管理系统 - Library Management System
<div align="center">
https://img.shields.io/badge/Spring%2520Boot-3.2.5-brightgreen
https://img.shields.io/badge/Java-17%252B-orange
https://img.shields.io/badge/MySQL-8.0-blue
https://img.shields.io/badge/License-MIT-green
https://img.shields.io/badge/build-passing-success

基于Spring Boot的现代化图书馆管理系统 | 软件工程课程项目

功能特性 | 技术栈 | 快速开始 | API文档 | 项目结构

</div>
🎯 项目简介
一个完整的图书馆管理系统，实现了图书管理、借阅归还、用户管理、统计报表等核心功能。本项目是《软件工程与AI辅助开发》课程的实践作业，展示了结合AI工具完成软件开发全流程的能力。

✨ 核心亮点
✅ 完整的软件工程流程：从需求分析到部署的全流程实践

✅ AI辅助开发：使用ChatGPT、Copilot等AI工具提升开发效率

✅ 现代化技术栈：Spring Boot 3 + MySQL 8 + OpenAPI 3

✅ RESTful API设计：规范的API接口和完整的文档

✅ 前后端分离：提供美观的前端界面和完善的后端API

🚀 功能特性
📖 图书管理
图书信息CRUD操作（增删改查）

ISBN唯一性验证

库存实时跟踪

多条件搜索（标题、作者、分类）

图书状态管理（可借、借出、维护中）

👥 用户管理
用户注册与登录

角色权限系统（管理员、图书管理员、普通用户）

用户状态管理（活跃、停用、暂停）

借阅限额控制

罚款管理系统

🔄 借阅管理
完整的借阅/归还流程

自动计算应还日期

超期罚款自动计算

图书续借功能

借阅记录查询

📊 统计分析
系统概览仪表盘

借阅趋势分析

热门图书排行

用户活跃度统计

实时数据可视化

⚙️ 系统管理
数据库自动迁移

系统健康监控

API文档自动生成

日志记录与审计

定时任务处理

🛠 技术栈
后端技术
技术	版本	说明
Spring Boot	3.2.5	后端主框架
Spring Data JPA	3.2.5	数据持久层
Spring Security	6.2.1	安全认证框架
MySQL	8.0+	数据库
OpenAPI 3	2.5.0	API文档
Maven	3.8+	项目管理
前端技术
技术	版本	说明
HTML5	-	页面结构
CSS3	-	样式设计
Bootstrap 5	5.3.0	UI框架
JavaScript	ES6+	交互逻辑
Font Awesome	6.4.0	图标库
开发工具
工具	用途
Eclipse IDE	开发环境
Git	版本控制
Postman	API测试
Swagger UI	API文档查看
H2 Database	测试数据库
📋 快速开始
环境要求
JDK 17+ (推荐使用JDK 17或21)

MySQL 8.0+

Maven 3.6+

Eclipse IDE (2023-12或更高版本)

数据库配置
启动MySQL服务

创建数据库：

sql
CREATE DATABASE library_db 
CHARACTER SET utf8mb4 
COLLATE utf8mb4_unicode_ci;
项目配置步骤
克隆项目

bash
git clone https://github.com/yourusername/library-management-system.git
cd library-management-system
导入到Eclipse

File → Import → Maven → Existing Maven Projects

选择项目目录

点击Finish

配置数据库连接
修改 src/main/resources/application.yml：

yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/library_db
    username: your_username     # 修改为你的MySQL用户名
    password: your_password     # 修改为你的MySQL密码
运行项目

找到 LibraryApplication.java

右键 → Run As → Java Application

使用Docker运行（可选）
bash
# 1. 构建项目
mvn clean package

# 2. 运行Docker Compose
docker-compose up -d
📖 API文档
项目提供完整的API文档，启动后可通过以下方式访问：

Swagger UI界面
text
http://localhost:8080/swagger-ui/index.html
主要API端点
模块	端点	方法	说明
用户管理	/api/users	GET	获取用户列表
/api/users	POST	创建新用户
/api/users/{id}	GET	获取用户详情
图书管理	/api/books	GET	获取图书列表
/api/books	POST	添加新图书
/api/books/search	GET	搜索图书
借阅管理	/api/borrow-records	POST	创建借阅记录
/api/borrow-records/{id}/return	POST	归还图书
系统管理	/api/library/health	GET	健康检查
/api/library/overview	GET	系统概览
API调用示例
bash
# 1. 健康检查
curl -X GET "http://localhost:8080/api/library/health"

# 2. 获取用户列表
curl -X GET "http://localhost:8080/api/users?page=0&size=10"

# 3. 创建新图书
curl -X POST "http://localhost:8080/api/books" \
  -H "Content-Type: application/json" \
  -d '{
    "isbn": "978-7-111-55674-7",
    "title": "Spring Boot实战",
    "author": "张三",
    "category": "计算机",
    "totalCopies": 10
  }'
📁 项目结构
text
library-management-system/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── library/
│   │   │           ├── LibraryApplication.java     # 启动类
│   │   │           ├── config/                     # 配置类
│   │   │           │   ├── ApplicationConfig.java
│   │   │           │   ├── SecurityConfig.java
│   │   │           │   └── OpenApiConfig.java
│   │   │           ├── controller/                 # 控制器层
│   │   │           │   ├── UserController.java
│   │   │           │   ├── BookController.java
│   │   │           │   ├── BorrowController.java
│   │   │           │   └── LibraryController.java
│   │   │           ├── service/                    # 业务逻辑层
│   │   │           │   ├── UserService.java
│   │   │           │   ├── BookService.java
│   │   │           │   └── impl/                   # 实现类
│   │   │           ├── repository/                 # 数据访问层
│   │   │           │   ├── UserRepository.java
│   │   │           │   └── BookRepository.java
│   │   │           ├── entity/                     # 实体类
│   │   │           │   ├── User.java
│   │   │           │   ├── Book.java
│   │   │           │   └── BorrowRecord.java
│   │   │           ├── dto/                        # 数据传输对象
│   │   │           │   ├── UserDTO.java
│   │   │           │   └── BookDTO.java
│   │   │           └── exception/                  # 异常处理
│   │   │               └── GlobalExceptionHandler.java
│   │   └── resources/
│   │       ├── application.yml                    # 主配置文件
│   │       ├── application-test.yml              # 测试配置
│   │       └── static/                            # 静态资源
│   │           └── index.html                     # 前端页面
│   └── test/                                      # 测试代码
│       └── java/com/library/
│           ├── ApplicationTest.java
│           ├── service/
│           └── controller/
├── pom.xml                                        # Maven配置
├── init_database.sql                             # 数据库初始化脚本
├── docker-compose.yml                            # Docker配置
└── README.md                                     # 项目说明
🔧 开发指南
代码规范
使用 Java命名规范（驼峰命名法）

类名使用大驼峰：UserService

方法名使用小驼峰：getUserById

常量使用全大写：MAX_BORROW_LIMIT

提交规范
bash
# 提交类型
feat:     新功能
fix:      修复bug
docs:     文档更新
style:    代码格式调整
refactor: 代码重构
test:     测试相关
chore:    构建过程或辅助工具的变动
测试运行
bash
# 运行所有测试
mvn test

# 运行特定测试类
mvn test -Dtest=UserServiceTest

# 生成测试覆盖率报告
mvn jacoco:report
🧪 AI辅助开发记录
本项目的开发过程中充分利用了AI工具提升效率：

开发阶段	AI使用场景	AI工具	效果评估
需求分析	数据库设计咨询	ChatGPT	数据库结构完整，关系合理
系统设计	API接口设计	Copilot	接口规范，参数设计合理
编码实现	代码生成与调试	CodeLlama	代码结构清晰，减少重复工作
文档编写	README和注释生成	ChatGPT	文档规范完整，节省时间
AI工具评价（200字）
在本项目中，ChatGPT和GitHub Copilot在代码生成和问题解决方面表现出色，特别是在生成模板代码和调试建议时效率显著提升。然而，在复杂业务逻辑实现上，AI生成的代码需要人工审核和调整。总体而言，AI工具在开发过程中减少约40% 的编码时间，但开发者仍需保持对代码质量的把控，特别是在安全性和性能方面。AI辅助开发的最佳实践是将其作为增强工具而非替代工具，结合人工审查确保代码质量。

🤝 贡献指南
欢迎提交Issue和Pull Request来帮助改进项目！

Fork项目

创建功能分支

bash
git checkout -b feature/YourFeature
提交更改

bash
git commit -m 'Add some feature'
推送到分支

bash
git push origin feature/YourFeature
创建Pull Request

📄 许可证
本项目采用 MIT 许可证 - 查看 LICENSE 文件了解详情。

👥 作者
开发团队 - 软件工程课程项目小组

👨‍💻 张三 - 主要开发者 - zhangsan

👩‍💻 李四 - 前端设计 - lisi

🎨 王五 - 文档编写 - wangwu

📞 联系
如有问题或建议，请通过以下方式联系：

📧 邮箱：dev@library.com

🐛 提交Issue

💬 课程讨论区：[课程平台链接]

<div align="center">
🎓 课程作业提交
课程名称：《软件工程与AI辅助开发》
作业要求：使用AI工具完成图书馆管理系统开发
提交时间：第18周星期天24:00之前
提交方式：学习通平台

祝您使用愉快！ ✨

</div>
