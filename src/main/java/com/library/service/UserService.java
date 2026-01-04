package com.library.service;

import com.library.dto.UserDTO;
import com.library.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * 用户服务接口
 */
public interface UserService {
    
    /**
     * 创建新用户
     */
    User createUser(User user);
    
    /**
     * 更新用户信息
     */
    User updateUser(Long userId, User user);
    
    /**
     * 删除用户（软删除）
     */
    void deleteUser(Long userId);
    
    /**
     * 根据ID获取用户
     */
    Optional<User> getUserById(Long userId);
    
    /**
     * 根据用户名获取用户
     */
    Optional<User> getUserByUsername(String username);
    
    /**
     * 根据邮箱获取用户
     */
    Optional<User> getUserByEmail(String email);
    
    /**
     * 获取所有用户
     */
    List<User> getAllUsers();
    
    /**
     * 分页获取用户
     */
    Page<User> getUsers(Pageable pageable);
    
    /**
     * 搜索用户
     */
    Page<User> searchUsers(String keyword, Pageable pageable);
    
    /**
     * 获取用户DTO（不包含敏感信息）
     */
    UserDTO getUserDTOById(Long userId);
    
    /**
     * 获取用户DTO列表
     */
    List<UserDTO> getAllUserDTOs();
    
    /**
     * 更新用户状态
     */
    User updateUserStatus(Long userId, User.Status status);
    
    /**
     * 更新用户角色
     */
    User updateUserRole(Long userId, User.Role role);
    
    /**
     * 验证用户凭据
     */
    boolean validateUserCredentials(String username, String password);
    
    /**
     * 检查用户名是否可用
     */
    boolean isUsernameAvailable(String username);
    
    /**
     * 检查邮箱是否可用
     */
    boolean isEmailAvailable(String email);
    
    /**
     * 重置用户密码
     */
    void resetPassword(Long userId, String newPassword);
    
    /**
     * 更新最后登录时间
     */
    void updateLastLogin(Long userId);
    
    /**
     * 获取活跃用户数量
     */
    long countActiveUsers();
    
    /**
     * 获取按角色统计的用户数量
     */
    Object[] countUsersByRole();
    
    /**
     * 获取有罚款的用户
     */
    List<User> getUsersWithFines();
}