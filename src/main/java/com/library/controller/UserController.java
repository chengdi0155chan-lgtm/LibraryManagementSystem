package com.library.controller;

import com.library.ApiResponse;
import com.library.dto.UserDTO;
import com.library.entity.User;
import com.library.exception.BusinessException;
import com.library.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 用户管理控制器
 */
@RestController
@RequestMapping("/api/users")
@Tag(name = "用户管理", description = "用户相关的CRUD操作")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @PostMapping
    @Operation(summary = "创建新用户", description = "创建一个新的用户账号")
    public ResponseEntity<ApiResponse<User>> createUser(
            @Valid @RequestBody User user) {
        
        try {
            User createdUser = userService.createUser(user);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success("用户创建成功", createdUser));
        } catch (IllegalArgumentException e) {
            throw BusinessException.badRequest(e.getMessage());
        }
    }
    
    @GetMapping("/{userId}")
    @Operation(summary = "获取用户信息", description = "根据用户ID获取用户详细信息")
    public ResponseEntity<ApiResponse<UserDTO>> getUser(
            @Parameter(description = "用户ID", required = true, example = "1")
            @PathVariable Long userId) {
        
        UserDTO userDTO = userService.getUserDTOById(userId);
        if (userDTO == null) {
            throw BusinessException.notFound("用户");
        }
        
        return ResponseEntity.ok(ApiResponse.success(userDTO));
    }
    
    @PutMapping("/{userId}")
    @Operation(summary = "更新用户信息", description = "更新指定用户的信息")
    public ResponseEntity<ApiResponse<User>> updateUser(
            @Parameter(description = "用户ID", required = true, example = "1")
            @PathVariable Long userId,
            @Valid @RequestBody User user) {
        
        try {
            User updatedUser = userService.updateUser(userId, user);
            return ResponseEntity.ok(ApiResponse.success("用户更新成功", updatedUser));
        } catch (IllegalArgumentException e) {
            throw BusinessException.badRequest(e.getMessage());
        }
    }
    
    @DeleteMapping("/{userId}")
    @Operation(summary = "删除用户", description = "软删除指定用户（标记为已删除）")
    public ResponseEntity<ApiResponse<Void>> deleteUser(
            @Parameter(description = "用户ID", required = true, example = "1")
            @PathVariable Long userId) {
        
        userService.deleteUser(userId);
        return ResponseEntity.ok(ApiResponse.success("用户删除成功"));
    }
    
    @GetMapping
    @Operation(summary = "获取用户列表", description = "分页获取所有用户，支持排序")
    public ResponseEntity<ApiResponse<Page<User>>> getUsers(
            @Parameter(description = "页码，从0开始", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "每页大小", example = "10")
            @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "排序字段，如：createdAt,desc", example = "id,desc")
            @RequestParam(defaultValue = "id,desc") String sort) {
        
        String[] sortParams = sort.split(",");
        Sort.Direction direction = sortParams.length > 1 && "desc".equalsIgnoreCase(sortParams[1]) 
                ? Sort.Direction.DESC : Sort.Direction.ASC;
        
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortParams[0]));
        Page<User> users = userService.getUsers(pageable);
        
        return ResponseEntity.ok(ApiResponse.success(users));
    }
    
    @GetMapping("/search")
    @Operation(summary = "搜索用户", description = "根据关键词搜索用户（用户名或真实姓名）")
    public ResponseEntity<ApiResponse<Page<User>>> searchUsers(
            @Parameter(description = "搜索关键词")
            @RequestParam(required = false) String keyword,
            @Parameter(description = "页码", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "每页大小", example = "10")
            @RequestParam(defaultValue = "10") int size) {
        
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
        Page<User> users = userService.searchUsers(keyword, pageable);
        
        return ResponseEntity.ok(ApiResponse.success(users));
    }
    
    @GetMapping("/all-dto")
    @Operation(summary = "获取所有用户DTO", description = "获取所有用户的DTO（不包含敏感信息）")
    public ResponseEntity<ApiResponse<List<UserDTO>>> getAllUserDTOs() {
        List<UserDTO> userDTOs = userService.getAllUserDTOs();
        return ResponseEntity.ok(ApiResponse.success(userDTOs));
    }
    
    @PutMapping("/{userId}/status")
    @Operation(summary = "更新用户状态", description = "更新用户的状态（ACTIVE/INACTIVE/SUSPENDED）")
    public ResponseEntity<ApiResponse<User>> updateUserStatus(
            @Parameter(description = "用户ID", required = true, example = "1")
            @PathVariable Long userId,
            @Parameter(description = "用户状态", required = true, example = "ACTIVE")
            @RequestParam User.Status status) {
        
        User updatedUser = userService.updateUserStatus(userId, status);
        return ResponseEntity.ok(ApiResponse.success("用户状态更新成功", updatedUser));
    }
    
    @PutMapping("/{userId}/role")
    @Operation(summary = "更新用户角色", description = "更新用户的角色（ADMIN/LIBRARIAN/USER）")
    public ResponseEntity<ApiResponse<User>> updateUserRole(
            @Parameter(description = "用户ID", required = true, example = "1")
            @PathVariable Long userId,
            @Parameter(description = "用户角色", required = true, example = "USER")
            @RequestParam User.Role role) {
        
        User updatedUser = userService.updateUserRole(userId, role);
        return ResponseEntity.ok(ApiResponse.success("用户角色更新成功", updatedUser));
    }
    
    @PostMapping("/{userId}/reset-password")
    @Operation(summary = "重置用户密码", description = "重置指定用户的密码")
    public ResponseEntity<ApiResponse<Void>> resetPassword(
            @Parameter(description = "用户ID", required = true, example = "1")
            @PathVariable Long userId,
            @Parameter(description = "新密码", required = true)
            @RequestParam String newPassword) {
        
        userService.resetPassword(userId, newPassword);
        return ResponseEntity.ok(ApiResponse.success("密码重置成功"));
    }
    
    @GetMapping("/check-username")
    @Operation(summary = "检查用户名是否可用", description = "检查用户名是否已被使用")
    public ResponseEntity<ApiResponse<Boolean>> checkUsername(
            @Parameter(description = "用户名", required = true)
            @RequestParam String username) {
        
        boolean available = userService.isUsernameAvailable(username);
        return ResponseEntity.ok(ApiResponse.success(available));
    }
    
    @GetMapping("/check-email")
    @Operation(summary = "检查邮箱是否可用", description = "检查邮箱是否已被使用")
    public ResponseEntity<ApiResponse<Boolean>> checkEmail(
            @Parameter(description = "邮箱地址", required = true)
            @RequestParam String email) {
        
        boolean available = userService.isEmailAvailable(email);
        return ResponseEntity.ok(ApiResponse.success(available));
    }
    
    @GetMapping("/statistics/count-by-role")
    @Operation(summary = "按角色统计用户数量", description = "统计不同角色的用户数量")
    public ResponseEntity<ApiResponse<Object[]>> countUsersByRole() {
        Object[] stats = userService.countUsersByRole();
        return ResponseEntity.ok(ApiResponse.success(stats));
    }
    
    @GetMapping("/with-fines")
    @Operation(summary = "获取有罚款的用户", description = "获取所有有未支付罚款的用户")
    public ResponseEntity<ApiResponse<List<User>>> getUsersWithFines() {
        List<User> users = userService.getUsersWithFines();
        return ResponseEntity.ok(ApiResponse.success(users));
    }
    
    @GetMapping("/statistics/overview")
    @Operation(summary = "用户统计概览", description = "获取用户相关的统计概览")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getUserStatistics() {
        long totalUsers = userService.getAllUsers().size();
        long activeUsers = userService.countActiveUsers();
        long usersWithFines = userService.getUsersWithFines().size();
        
        Map<String, Object> stats = Map.of(
            "totalUsers", totalUsers,
            "activeUsers", activeUsers,
            "usersWithFines", usersWithFines
        );
        
        return ResponseEntity.ok(ApiResponse.success(stats));
    }
}