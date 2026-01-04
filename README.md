# 📚 图书馆管理系统 (Library Management System)

基于Spring Boot 3.2.5的现代化图书馆管理系统，提供完整的图书管理、借阅归还、用户管理等功能。

![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.5-brightgreen)
![Java](https://img.shields.io/badge/Java-17-orange)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue)
![License](https://img.shields.io/badge/License-MIT-yellow)

## ✨ 功能特性

### 📊 核心功能
- ✅ **用户管理**：用户注册、登录、权限控制（管理员、图书管理员、普通用户）
- ✅ **图书管理**：图书信息CRUD、库存管理、分类管理
- ✅ **借阅管理**：借阅、归还、续借、预约功能
- ✅ **罚款管理**：自动计算超期罚款、罚款支付
- ✅ **统计分析**：图书借阅统计、用户活跃度分析

### 🛠️ 技术特性
- 🚀 **后端框架**：Spring Boot 3.2.5 + Spring Data JPA
- 🗄️ **数据库**：MySQL 8.0 + H2（测试环境）
- 📄 **API文档**：OpenAPI 3.0 (Swagger UI)
- 🔐 **安全认证**：Spring Security + JWT（可选）
- 📱 **前端界面**：响应式Bootstrap 5页面

## 🚀 快速开始

### 环境要求
- JDK 17 或更高版本
- MySQL 8.0 或更高版本
- Maven 3.6+ 或 Gradle
- Git

### 步骤1：克隆项目
```bash
git clone https://github.com/chengdi0155chan-lgtm/LibraryManagementSystem.git
cd LibraryManagementSystem
