package com.library;

/**
 * 应用常量定义
 */
public class AppConstants {
    
    // ==================== 角色常量 ====================
    public static final String ROLE_ADMIN = "ADMIN";
    public static final String ROLE_LIBRARIAN = "LIBRARIAN";
    public static final String ROLE_USER = "USER";
    
    // ==================== 状态常量 ====================
    public static final String STATUS_ACTIVE = "ACTIVE";
    public static final String STATUS_INACTIVE = "INACTIVE";
    public static final String STATUS_SUSPENDED = "SUSPENDED";
    
    // ==================== 图书状态 ====================
    public static final String BOOK_AVAILABLE = "AVAILABLE";
    public static final String BOOK_UNAVAILABLE = "UNAVAILABLE";
    public static final String BOOK_MAINTENANCE = "MAINTENANCE";
    public static final String BOOK_LOST = "LOST";
    
    // ==================== 借阅状态 ====================
    public static final String BORROW_BORROWED = "BORROWED";
    public static final String BORROW_RETURNED = "RETURNED";
    public static final String BORROW_OVERDUE = "OVERDUE";
    public static final String BORROW_LOST = "LOST";
    
    // ==================== 分页默认值 ====================
    public static final int DEFAULT_PAGE_SIZE = 10;
    public static final int MAX_PAGE_SIZE = 100;
    
    // ==================== 借阅相关 ====================
    public static final int DEFAULT_BORROW_DAYS = 30;
    public static final int DEFAULT_MAX_BORROW_LIMIT = 5;
    public static final double DEFAULT_FINE_PER_DAY = 0.5; // 每天罚款金额
    
    // ==================== 安全相关 ====================
    public static final String JWT_HEADER = "Authorization";
    public static final String JWT_PREFIX = "Bearer ";
    public static final long JWT_EXPIRATION = 24 * 60 * 60 * 1000; // 24小时
    public static final long JWT_REFRESH_EXPIRATION = 7 * 24 * 60 * 60 * 1000; // 7天
    
    // ==================== 缓存键 ====================
    public static final String CACHE_BOOKS = "books";
    public static final String CACHE_USERS = "users";
    public static final String CACHE_BOOK_AVAILABLE = "book_available";
    
    // ==================== 日期格式 ====================
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    
    // ==================== 文件上传 ====================
    public static final long MAX_FILE_SIZE = 10 * 1024 * 1024; // 10MB
    public static final String[] ALLOWED_IMAGE_TYPES = {"image/jpeg", "image/png", "image/gif"};
    
    // 私有构造方法，防止实例化
    private AppConstants() {
    }
}