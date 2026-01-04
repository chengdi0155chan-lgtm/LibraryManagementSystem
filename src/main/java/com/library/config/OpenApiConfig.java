package com.library.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.tags.Tag;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * OpenAPI/Swagger配置类
 * 优化Swagger UI显示，添加中文支持
 */
@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "图书馆管理系统 API",
        version = "1.0.0",
        description = """
            ## 📚 图书馆管理系统 REST API 文档
            
            ### 功能概述
            提供完整的图书馆管理功能，包括：
            - 👥 用户管理（注册、登录、权限控制）
            - 📖 图书管理（增删改查、库存管理）
            - 🔄 借阅管理（借阅、归还、续借）
            - 📊 统计分析（数据报表、统计图表）
            - ⚠️  罚款管理（超期罚款计算）
            
            ### 技术栈
            - **后端框架**: Spring Boot 3.2.5
            - **数据库**: MySQL 8.0
            - **API文档**: OpenAPI 3.0
            - **安全框架**: Spring Security
            
            ### 使用说明
            1. 所有API请求都需要在请求头中包含 `Content-Type: application/json`
            2. 部分API可能需要认证（如需要，会在接口中注明）
            3. 参数验证错误会返回详细的错误信息
            4. 系统自动处理时区，所有时间均为北京时间
            
            ### 接口约定
            - ✅ 200: 请求成功
            - ✅ 201: 创建成功
            - ❌ 400: 请求参数错误
            - ❌ 401: 未授权访问
            - ❌ 403: 权限不足
            - ❌ 404: 资源不存在
            - ❌ 409: 资源冲突
            - ❌ 500: 服务器内部错误
            """,
        contact = @Contact(
            name = "开发团队",
            email = "dev@library.com",
            url = "https://github.com/library-system"
        ),
        license = @License(
            name = "MIT License",
            url = "https://opensource.org/licenses/MIT"
        )
    ),
    servers = {
        @Server(
            description = "本地开发环境",
            url = "http://localhost:8080"
        ),
        @Server(
            description = "测试环境",
            url = "https://test.library.com"
        ),
        @Server(
            description = "生产环境",
            url = "https://api.library.com"
        )
    },
    security = {
        @SecurityRequirement(name = "bearerAuth")
    }
)
@SecurityScheme(
    name = "bearerAuth",
    description = "JWT认证令牌",
    scheme = "bearer",
    type = SecuritySchemeType.HTTP,
    bearerFormat = "JWT",
    in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {
    
    /**
     * 图书馆API分组配置
     */
    @Bean
    public GroupedOpenApi libraryApi() {
        return GroupedOpenApi.builder()
                .group("图书馆API")
                .displayName("图书馆管理系统核心API")
                .pathsToMatch("/api/**")
                .packagesToScan("com.library.controller")
                .addOperationCustomizer(globalOperationCustomizer())
                .build();
    }
    
    /**
     * 用户管理API分组
     */
    @Bean
    public GroupedOpenApi userApi() {
        return GroupedOpenApi.builder()
                .group("用户管理")
                .displayName("用户管理相关API")
                .pathsToMatch("/api/users/**")
                .packagesToScan("com.library.controller")
                .build();
    }
    
    /**
     * 图书管理API分组
     */
    @Bean
    public GroupedOpenApi bookApi() {
        return GroupedOpenApi.builder()
                .group("图书管理")
                .displayName("图书管理相关API")
                .pathsToMatch("/api/books/**")
                .packagesToScan("com.library.controller")
                .build();
    }
    
    /**
     * 借阅管理API分组
     */
    @Bean
    public GroupedOpenApi borrowApi() {
        return GroupedOpenApi.builder()
                .group("借阅管理")
                .displayName("借阅管理相关API")
                .pathsToMatch("/api/borrow-records/**", "/api/library/borrow")
                .packagesToScan("com.library.controller")
                .build();
    }
    
    /**
     * 自定义OpenAPI配置
     */
    @Bean
    public OpenApiCustomizer openApiCustomizer() {
        return openApi -> {
            // 添加全局标签
            openApi.addTagsItem(new Tag()
                    .name("用户管理")
                    .description("用户注册、登录、信息管理等操作"));
            
            openApi.addTagsItem(new Tag()
                    .name("图书管理")
                    .description("图书信息管理、库存管理、搜索等操作"));
            
            openApi.addTagsItem(new Tag()
                    .name("借阅管理")
                    .description("图书借阅、归还、续借、罚款等操作"));
            
            openApi.addTagsItem(new Tag()
                    .name("统计分析")
                    .description("数据统计、报表生成、趋势分析等操作"));
            
            openApi.addTagsItem(new Tag()
                    .name("系统管理")
                    .description("系统配置、监控、健康检查等操作"));
            
            // 添加全局响应
            if (openApi.getComponents() == null) {
                openApi.setComponents(new io.swagger.v3.oas.models.Components());
            }
            
            openApi.getComponents()
                    .addResponses("Success", new ApiResponse()
                            .description("操作成功")
                            .content(new Content()
                                    .addMediaType("application/json", new MediaType()
                                            .schema(new Schema<>()
                                                    .$ref("#/components/schemas/ApiResponse")))));
            
            openApi.getComponents()
                    .addResponses("BadRequest", new ApiResponse()
                            .description("请求参数错误")
                            .content(new Content()
                                    .addMediaType("application/json", new MediaType()
                                            .schema(new Schema<>()
                                                    .$ref("#/components/schemas/ApiResponse")))));
            
            openApi.getComponents()
                    .addResponses("NotFound", new ApiResponse()
                            .description("资源不存在")
                            .content(new Content()
                                    .addMediaType("application/json", new MediaType()
                                            .schema(new Schema<>()
                                                    .$ref("#/components/schemas/ApiResponse")))));
            
            openApi.getComponents()
                    .addResponses("InternalServerError", new ApiResponse()
                            .description("服务器内部错误")
                            .content(new Content()
                                    .addMediaType("application/json", new MediaType()
                                            .schema(new Schema<>()
                                                    .$ref("#/components/schemas/ApiResponse")))));
            
            // 添加通用模型
            Schema<?> apiResponseSchema = new Schema<>()
                    .type("object")
                    .addProperty("success", new Schema<>()
                            .type("boolean")
                            .description("操作是否成功"))
                    .addProperty("message", new Schema<>()
                            .type("string")
                            .description("响应消息"))
                    .addProperty("data", new Schema<>()
                            .type("object")
                            .description("响应数据"))
                    .addProperty("timestamp", new Schema<>()
                            .type("string")
                            .format("date-time")
                            .description("响应时间"))
                    .addProperty("code", new Schema<>()
                            .type("integer")
                            .description("状态码"));
            
            Schema<?> pageableSchema = new Schema<>()
                    .type("object")
                    .addProperty("page", new Schema<>()
                            .type("integer")
                            .description("页码（从0开始）"))
                    .addProperty("size", new Schema<>()
                            .type("integer")
                            .description("每页大小"))
                    .addProperty("sort", new StringSchema()
                            .description("排序字段（格式：field,direction）"));
            
            openApi.getComponents()
                    .addSchemas("ApiResponse", apiResponseSchema);
            
            openApi.getComponents()
                    .addSchemas("Pageable", pageableSchema);
            
            // 添加扩展信息（使用Map而不是Links类）
            Map<String, Object> links = new HashMap<>();
            links.put("GitHub", Map.of(
                "description", "项目GitHub仓库",
                "url", "https://github.com/library-system"
            ));
            links.put("Postman", Map.of(
                "description", "Postman集合",
                "url", "https://www.postman.com/collection"
            ));
            links.put("健康检查", Map.of(
                "description", "系统健康检查",
                "operationId", "healthCheck"
            ));
            
            openApi.getInfo()
                    .addExtension("x-links", links);
        };
    }
    
    /**
     * 全局操作自定义器
     */
    @Bean
    public OperationCustomizer globalOperationCustomizer() {
        return (operation, handlerMethod) -> {
            // 为所有操作添加全局参数
            operation.addParametersItem(new Parameter()
                    .in("header")
                    .name("Accept-Language")
                    .description("语言设置")
                    .schema(new StringSchema()
                            .addEnumItem("zh-CN")
                            .addEnumItem("en-US")
                            ._default("zh-CN"))
                    .example("zh-CN")
                    .required(false));
            
            // 添加全局响应
            operation.getResponses()
                    .addApiResponse("400", new ApiResponse()
                            .$ref("#/components/responses/BadRequest"));
            
            operation.getResponses()
                    .addApiResponse("404", new ApiResponse()
                            .$ref("#/components/responses/NotFound"));
            
            operation.getResponses()
                    .addApiResponse("500", new ApiResponse()
                            .$ref("#/components/responses/InternalServerError"));
            
            // 为POST/PUT/PATCH方法添加请求示例说明
            if (operation.getRequestBody() != null) {
                operation.getRequestBody().setDescription("""
                        请求体说明：
                        1. 所有字段都需要符合JSON格式
                        2. 必填字段已在模型中标明
                        3. 字符串类型字段需要进行适当的验证
                        """);
            }
            
            return operation;
        };
    }
    
    /**
     * 创建OpenAPI Bean进行更多配置
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new io.swagger.v3.oas.models.info.Info()
                        .title("图书馆管理系统 API 文档")
                        .version("1.0.0")
                        .description("""
                                <div style="font-family: 'Microsoft YaHei', sans-serif;">
                                    <h2>📚 图书馆管理系统 API 文档</h2>
                                    
                                    <h3>📖 使用说明</h3>
                                    <ul>
                                        <li>点击右侧的接口分组查看不同模块的API</li>
                                        <li>点击具体的接口可以查看详细信息和测试功能</li>
                                        <li>使用"Try it out"按钮可以直接在页面上测试接口</li>
                                        <li>需要认证的接口请先配置认证信息</li>
                                    </ul>
                                    
                                    <h3>🚀 快速开始</h3>
                                    <ol>
                                        <li>创建用户：使用POST /api/users接口</li>
                                        <li>添加图书：使用POST /api/books接口</li>
                                        <li>借阅图书：使用POST /api/borrow-records接口</li>
                                        <li>归还图书：使用POST /api/borrow-records/{id}/return接口</li>
                                    </ol>
                                    
                                    <h3>🔧 开发工具</h3>
                                    <p>推荐使用以下工具进行API测试：</p>
                                    <ul>
                                        <li><a href="https://www.postman.com/" target="_blank">Postman</a></li>
                                        <li><a href="https://insomnia.rest/" target="_blank">Insomnia</a></li>
                                        <li><a href="https://swagger.io/tools/swagger-ui/" target="_blank">Swagger UI</a></li>
                                    </ul>
                                </div>
                                """)
                        .termsOfService("https://library.com/terms")
                        .contact(new io.swagger.v3.oas.models.info.Contact()
                                .name("技术支持")
                                .email("support@library.com")
                                .url("https://help.library.com"))
                        .license(new io.swagger.v3.oas.models.info.License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .addTagsItem(new Tag()
                        .name("首页")
                        .description("系统首页和导航"))
                .addTagsItem(new Tag()
                        .name("用户管理")
                        .description("用户注册、登录、信息管理"))
                .addTagsItem(new Tag()
                        .name("图书管理")
                        .description("图书信息管理、库存管理"))
                .addTagsItem(new Tag()
                        .name("借阅管理")
                        .description("借阅、归还、续借、罚款"))
                .addTagsItem(new Tag()
                        .name("统计分析")
                        .description("数据统计、报表生成"))
                .externalDocs(new ExternalDocumentation()
                        .description("详细开发文档")
                        .url("https://docs.library.com"));
    }
}