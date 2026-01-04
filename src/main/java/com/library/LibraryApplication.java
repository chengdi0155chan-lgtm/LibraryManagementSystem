package com.library;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 图书馆管理系统 - 主启动类
 * 
 * @author 开发者
 * @version 1.0.0
 * @created 2024年
 * 
 * 项目说明：
 * 基于Spring Boot的图书馆管理系统，提供图书管理、借阅、归还、用户管理等核心功能。
 * 
 * 技术栈：
 * - Spring Boot 3.2.5
 * - Spring Data JPA
 * - MySQL 8.0
 * - Spring Security (可选)
 * - Spring Cache
 * - OpenAPI 3 (Swagger)
 * 
 * 主要功能：
 * 1. 用户管理（注册、登录、权限控制）
 * 2. 图书管理（增删改查、库存管理）
 * 3. 借阅管理（借阅、归还、续借）
 * 4. 预约管理（预约、取消预约）
 * 5. 统计报表（用户统计、图书统计、借阅统计）
 * 6. 罚款管理（超期罚款计算和支付）
 * 
 * 启动步骤：
 * 1. 安装MySQL 8.0+，创建数据库：library_db
 * 2. 修改application.yml中的数据库连接信息
 * 3. 运行此启动类
 * 4. 访问 http://localhost:8080/api/swagger-ui/index.html 查看API文档
 * 
 * 默认账号：
 * - 管理员：admin / admin123
 * - 普通用户：user1 / user123
 * 
 * 项目结构：
 * com.library
 * ├── LibraryApplication.java      # 启动类 (当前文件)
 * ├── ApiResponse.java            # 统一API响应格式
 * ├── AppConstants.java           # 应用常量
 * ├── config/                     # 配置类
 * ├── controller/                 # 控制器层
 * ├── service/                    # 业务逻辑层
 * ├── repository/                 # 数据访问层
 * ├── entity/                     # 实体类
 * ├── dto/                        # 数据传输对象
 * ├── exception/                  # 异常处理
 * └── util/                       # 工具类
 */
@SpringBootApplication
@EnableJpaAuditing                // 启用JPA审计功能（自动填充创建时间/更新时间）
@EnableCaching                    // 启用缓存功能
@EnableTransactionManagement      // 启用事务管理
@EnableScheduling                 // 启用定时任务（可选，用于定时发送提醒等）
public class LibraryApplication {
    
    /**
     * 应用启动入口
     * 
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        // 启动Spring Boot应用
        SpringApplication.run(LibraryApplication.class, args);
        
        // 打印启动成功信息
        printStartupInfo();
    }
    
    /**
     * 打印启动信息
     */
    private static void printStartupInfo() {
        String line = "=".repeat(60);
        
        System.out.println("\n" + line);
        System.out.println("📚 图书馆管理系统启动成功！");
        System.out.println(line);
        
        // 系统信息
        System.out.println("🏷️  系统名称: 图书馆管理系统");
        System.out.println("🚀 启动时间: " + new java.util.Date());
        System.out.println("💻 Java版本: " + System.getProperty("java.version"));
        System.out.println("📦 Spring Boot版本: 3.2.5");
        
        // 访问地址
        System.out.println("\n🌐 访问地址:");
        System.out.println("   API文档: http://localhost:8080/api/swagger-ui/index.html");
        System.out.println("   API接口: http://localhost:8080/api");
        System.out.println("   健康检查: http://localhost:8080/api/library/health");
        
        // 数据库信息
        System.out.println("\n🗃️  数据库:");
        System.out.println("   数据库: library_db");
        System.out.println("   驱动: MySQL 8.0+");
        
        // 主要API端点
        System.out.println("\n🔗 主要API端点:");
        System.out.println("   GET    /api/users              - 获取用户列表");
        System.out.println("   POST   /api/users              - 创建新用户");
        System.out.println("   GET    /api/books              - 获取图书列表");
        System.out.println("   POST   /api/books              - 添加新图书");
        System.out.println("   POST   /api/borrow-records     - 借阅图书");
        System.out.println("   POST   /api/borrow-records/{id}/return - 归还图书");
        
        // 默认账号
        System.out.println("\n👤 默认测试账号:");
        System.out.println("   管理员: admin / admin123");
        System.out.println("   普通用户: user1 / user123");
        System.out.println("   图书管理员: librarian / lib123");
        
        // 提示信息
        System.out.println("\n💡 提示:");
        System.out.println("   1. 首次启动请确保MySQL服务已启动");
        System.out.println("   2. 数据库会自动创建表结构（spring.jpa.hibernate.ddl-auto=update）");
        System.out.println("   3. 初始数据在 init_database.sql 中");
        System.out.println("   4. 修改 application.yml 配置数据库连接");
        
        System.out.println(line);
        System.out.println("🎉 启动完成，系统正在运行...\n");
    }
}