package com.library.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 * 简化版 Spring Security 配置
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // 禁用 CSRF
            .csrf(AbstractHttpConfigurer::disable)
            
            // 授权配置
            .authorizeHttpRequests(auth -> auth
                // 允许访问的端点
                .requestMatchers(
                    "/swagger-ui/**", "/v3/api-docs/**", "/api-docs/**",  // Swagger
                    "/h2-console/**",  // H2 控制台
                    "/test", "/api/test/**",  // 测试端点
                    "/api/library/health"  // 健康检查
                ).permitAll()
                
                // 所有 API 端点都需要认证（但允许所有角色）
                .requestMatchers("/api/**").authenticated()
                
                // 其他所有请求
                .anyRequest().permitAll()
            )
            
            // 启用 HTTP Basic 认证
            .httpBasic(basic -> {})
            
            // 禁用表单登录（我们使用 HTTP Basic）
            .formLogin(AbstractHttpConfigurer::disable)
            
            // 允许 H2 控制台的 iframe
            .headers(headers -> headers
                .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));
        
        return http.build();
    }
    
    /**
     * 创建测试用户
     */
    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails admin = User.withUsername("admin")
            .password(passwordEncoder.encode("admin123"))
            .roles("ADMIN")
            .build();
        
        UserDetails user = User.withUsername("user")
            .password(passwordEncoder.encode("user123"))
            .roles("USER")
            .build();
        
        return new InMemoryUserDetailsManager(admin, user);
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}