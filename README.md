# 📚 图书馆管理系统

基于Spring Boot的现代化图书馆管理系统，提供完整的图书管理、借阅归还、用户管理等功能。

## 🚀 技术栈

- **后端**: Spring Boot 3.2.5 + Spring Data JPA + Spring Security
- **数据库**: MySQL 8.0
- **API文档**: OpenAPI 3.0 (Swagger)
- **前端**: HTML + Bootstrap 5 + JavaScript
- **构建工具**: Maven
- **开发工具**: Eclipse 2025-12 + JDK 25

## ✨ 功能特性

### 📖 核心功能
- ✅ 用户管理（注册、登录、权限控制）
- ✅ 图书管理（增删改查、库存管理）
- ✅ 借阅管理（借阅、归还、续借）
- ✅ 预约管理（预约、取消预约）
- ✅ 罚款管理（超期自动计算）

### 📊 统计功能
- 📈 用户统计（活跃用户、借阅排行）
- 📈 图书统计（库存统计、借阅排行）
- 📈 借阅统计（月度趋势、超期统计）

### 🔧 辅助功能
- 🔔 借阅提醒（到期提醒、超期通知）
- 🔒 安全认证（基于角色的访问控制）
- 📋 操作日志（用户行为记录）

## 🛠️ 快速开始

### 环境要求
- JDK 17或更高版本
- MySQL 8.0或更高版本
- Maven 3.6+
- Eclipse 2025-12（推荐）

### 数据库配置
1. 创建数据库：
   ```sql
   CREATE DATABASE library_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
   
### 项目配置
1. 修改数据库连接配置（src/main/resources/application.yml）：
   ```yaml
   spring:
   datasource:
    url: jdbc:mysql://localhost:3306/library_db
    username: your_username
    password: your_password

3. 修改配置文件 `src/main/resources/application.yml`

### 🖥️ 在 Eclipse 中运行项目
1. 导入项目：
- File → Import → Maven → Existing Maven Projects
- 选择项目根目录

2. 更新 Maven 依赖：
- 右键项目 → Maven → Update Project
- 勾选 Force Update of Snapshots/Releases

3. 运行应用程序：
- 找到 `src/main/java/com/library/LibraryApplication.java`
- 右键 → Run As → Java Application

4. 观察控制台输出，确保看到"图书馆管理系统启动成功！"

### 🌐 访问应用
- 系统首页: [http://localhost:8080/](http://localhost:8080/)
- API根路径: [http://localhost:8080/api](http://localhost:8080/api)
- API文档 (Swagger UI): [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
- 健康检查: [http://localhost:8080/api/library/health](http://localhost:8080/api/library/health)
- 用户管理: [http://localhost:8080/api/users](http://localhost:8080/api/users)
- 图书管理: [http://localhost:8080/api/books](http://localhost:8080/api/books)

### 👤 默认测试账号
- 管理员: admin / admin123
- 普通用户: user1 / user123
- 图书管理员: librarian / lib123

## 📦 依赖清单

### 主要依赖
| 依赖                | 版本    | 说明                     |
|---------------------|---------|--------------------------|
| Spring Boot         | 3.2.5   | 应用框架                 |
| Spring Data JPA     | 3.2.5   | 数据持久化               |
| MySQL Connector     | 8.3.0   | MySQL数据库驱动          |
| Spring Security     | 3.2.5   | 安全认证（可选）         |
| SpringDoc OpenAPI   | 2.5.0   | API文档生成              |
| Lombok              | 1.18.32 | 代码简化工具             |
| H2 Database         | 2.2.224 | 测试数据库               |

### 完整的 pom.xml 依赖（关键部分）
```xml
<!-- Spring Boot Starters -->
<dependency>
 <groupId>org.springframework.boot</groupId>
 <artifactId>spring-boot-starter-web</artifactId>
</dependency>
<dependency>
 <groupId>org.springframework.boot</groupId>
 <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
<dependency>
 <groupId>org.springframework.boot</groupId>
 <artifactId>spring-boot-starter-validation</artifactId>
</dependency>
<dependency>
 <groupId>org.springframework.boot</groupId>
 <artifactId>spring-boot-starter-security</artifactId>
 <optional>true</optional>
</dependency>

<!-- 数据库 -->
<dependency>
 <groupId>com.mysql</groupId>
 <artifactId>mysql-connector-j</artifactId>
 <version>8.3.0</version>
 <scope>runtime</scope>
</dependency>

<!-- 开发工具 -->
<dependency>
 <groupId>org.projectlombok</groupId>
 <artifactId>lombok</artifactId>
 <optional>true</optional>
</dependency>
<dependency>
 <groupId>org.springframework.boot</groupId>
 <artifactId>spring-boot-devtools</artifactId>
 <optional>true</optional>
</dependency>

<!-- API文档 -->
<dependency>
 <groupId>org.springdoc</groupId>
 <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
 <version>2.5.0</version>
</dependency>

<!-- 测试依赖 -->
<dependency>
 <groupId>org.springframework.boot</groupId>
 <artifactId>spring-boot-starter-test</artifactId>
 <scope>test</scope>
</dependency>
<dependency>
 <groupId>com.h2database</groupId>
 <artifactId>h2</artifactId>
 <scope>test</scope>
</dependency>
   
