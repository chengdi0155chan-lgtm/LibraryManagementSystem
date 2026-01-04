# 📚 图书馆管理系统

## 项目简介
基于Spring Boot 3.2.5的现代化图书馆管理系统，提供完整的图书管理、用户管理、借阅归还、统计分析等功能。

## 🚀 快速开始

### 环境要求
- **JDK**: 17+
- **MySQL**: 8.0+
- **Maven**: 3.6+
- **IDE**: Eclipse 2025-12 (推荐)

### 安装步骤

#### 1. 克隆项目
git clone https://github.com/yourusername/library-management-system.git
cd library-management-system

text

#### 2. 数据库配置
```sql
CREATE DATABASE library_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE library_db;
-- 可选：执行初始化脚本
source init_database.sql;
3. 修改配置文件
打开 src/main/resources/application.yml：

yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/library_db
    username: your_username
    password: your_password
4. 导入Eclipse
text
File → Import → Maven → Existing Maven Projects
选择项目目录 → Finish
5. 运行项目
text
右键 LibraryApplication.java → Run As → Java Application
📖 项目结构
text
src/main/java/com/library/
├── LibraryApplication.java      # 启动类
├── config/                      # 配置类
├── controller/                  # 控制器层
├── service/                     # 业务逻辑层
├── repository/                  # 数据访问层
├── entity/                      # 实体类
├── dto/                         # 数据传输对象
├── exception/                   # 异常处理
└── util/                        # 工具类
🔧 技术栈
组件	版本/名称
后端框架	Spring Boot 3.2.5
数据持久化	Spring Data JPA + MySQL 8.0
API文档	OpenAPI 3.0 (Swagger)
前端界面	HTML5 + Bootstrap 5
构建工具	Maven 3.6+
开发工具	Eclipse 2025-12
📊 功能模块
用户管理
✅ 用户注册、登录

✅ 角色权限控制

✅ 用户状态管理

图书管理
✅ 图书信息CRUD

✅ 库存管理

✅ 图书搜索

借阅管理
✅ 借阅、归还、续借

✅ 预约管理

✅ 超期罚款计算

统计分析
✅ 用户统计

✅ 借阅统计

✅ 系统监控

🔗 API文档
启动后访问：http://localhost:8080/swagger-ui/index.html

主要API端点
方法	端点	描述
GET	/api/users	获取用户列表
POST	/api/users	创建新用户
GET	/api/books	获取图书列表
POST	/api/books	添加新图书
POST	/api/borrow-records	借阅图书
POST	/api/borrow-records/{id}/return	归还图书
GET	/api/library/health	健康检查
🌐 前端界面
访问 http://localhost:8080/ 查看系统首页

🧪 测试
单元测试
text
mvn test
默认测试账号
角色	用户名	密码
管理员	admin	admin123
普通用户	user1	user123
图书管理员	librarian	lib123
📦 部署
打包
text
mvn clean package
运行
text
java -jar target/LibraryManagementSystem-1.0.0.jar
🔍 常见问题
1. 端口冲突
修改 application.yml：

yaml
server:
  port: 8081
2. 数据库连接失败
检查MySQL服务是否启动

验证用户名密码

确认数据库名称

3. 表结构未创建
确保配置：

yaml
spring:
  jpa:
    hibernate:
      ddl-auto: update
📄 相关文档
application.yml - 主配置文件

init_database.sql - 数据库初始化脚本

docs/ - 开发文档目录

👥 开发信息
项目名称: 图书馆管理系统

课程名称: 软件工程与AI辅助开发

开发者: [你的姓名]

学号: [你的学号]

版本: v1.0.0

📝 作业完成情况
✅ 软件工程流程执行

✅ AI辅助开发记录

✅ 代码质量评价

✅ 完整开发文档

✅ 可运行代码

最后更新: 2024年1月
状态: ✅ 已完成
