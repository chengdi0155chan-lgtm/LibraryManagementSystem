package com.library.service.impl;

import com.library.dto.UserDTO;
import com.library.entity.User;
import com.library.repository.UserRepository;
import com.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 用户服务实现类
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Override
    public User createUser(User user) {
        // 验证用户名和邮箱
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException("用户名已存在: " + user.getUsername());
        }
        
        if (StringUtils.hasText(user.getEmail()) && 
            userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("邮箱已存在: " + user.getEmail());
        }
        
        // 加密密码
        user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));
        
        // 设置默认值
        if (user.getMaxBorrowLimit() == null) {
            user.setMaxBorrowLimit(5);
        }
        if (user.getCurrentBorrowed() == null) {
            user.setCurrentBorrowed(0);
        }
        if (user.getFineAmount() == null) {
            user.setFineAmount(0.0);
        }
        if (user.getRole() == null) {
            user.setRole(User.Role.USER);
        }
        if (user.getStatus() == null) {
            user.setStatus(User.Status.ACTIVE);
        }
        
        return userRepository.save(user);
    }
    
    @Override
    public User updateUser(Long userId, User user) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("用户不存在: " + userId));
        
        // 更新基本信息
        if (StringUtils.hasText(user.getEmail()) && 
            !user.getEmail().equals(existingUser.getEmail())) {
            if (userRepository.existsByEmail(user.getEmail())) {
                throw new IllegalArgumentException("邮箱已存在: " + user.getEmail());
            }
            existingUser.setEmail(user.getEmail());
        }
        
        if (StringUtils.hasText(user.getPhone())) {
            existingUser.setPhone(user.getPhone());
        }
        
        if (StringUtils.hasText(user.getRealName())) {
            existingUser.setRealName(user.getRealName());
        }
        
        if (user.getMaxBorrowLimit() != null) {
            existingUser.setMaxBorrowLimit(user.getMaxBorrowLimit());
        }
        
        return userRepository.save(existingUser);
    }
    
    @Override
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("用户不存在: " + userId));
        
        user.setStatus(User.Status.INACTIVE);
        user.setIsDeleted(true);
        userRepository.save(user);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<User> getUserById(Long userId) {
        return userRepository.findById(userId);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<User> getUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<User> searchUsers(String keyword, Pageable pageable) {
        if (!StringUtils.hasText(keyword)) {
            return userRepository.findAll(pageable);
        }
        
        // 搜索用户名和真实姓名
        return userRepository.findByUsernameContainingIgnoreCaseOrRealNameContainingIgnoreCase(
                keyword, keyword, pageable);
    }
    
    @Override
    @Transactional(readOnly = true)
    public UserDTO getUserDTOById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("用户不存在: " + userId));
        
        return convertToDTO(user);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<UserDTO> getAllUserDTOs() {
        return userRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public User updateUserStatus(Long userId, User.Status status) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("用户不存在: " + userId));
        
        user.setStatus(status);
        return userRepository.save(user);
    }
    
    @Override
    public User updateUserRole(Long userId, User.Role role) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("用户不存在: " + userId));
        
        user.setRole(role);
        return userRepository.save(user);
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean validateUserCredentials(String username, String password) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isEmpty()) {
            return false;
        }
        
        User user = userOptional.get();
        return passwordEncoder.matches(password, user.getPasswordHash());
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean isUsernameAvailable(String username) {
        return !userRepository.existsByUsername(username);
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean isEmailAvailable(String email) {
        if (!StringUtils.hasText(email)) {
            return true;
        }
        return !userRepository.existsByEmail(email);
    }
    
    @Override
    public void resetPassword(Long userId, String newPassword) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("用户不存在: " + userId));
        
        user.setPasswordHash(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }
    
    @Override
    public void updateLastLogin(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("用户不存在: " + userId));
        
        user.setLastLoginAt(LocalDateTime.now());
        userRepository.save(user);
    }
    
    @Override
    @Transactional(readOnly = true)
    public long countActiveUsers() {
        return userRepository.countByStatus(User.Status.ACTIVE);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Object[] countUsersByRole() {
        return userRepository.countUsersByRole().toArray();
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<User> getUsersWithFines() {
        return userRepository.findByFineAmountGreaterThan(0.0);
    }
    
    // 辅助方法：转换为DTO
    private UserDTO convertToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());
        dto.setRealName(user.getRealName());
        dto.setRole(user.getRole().name());
        dto.setStatus(user.getStatus().name());
        dto.setMaxBorrowLimit(user.getMaxBorrowLimit());
        dto.setCurrentBorrowed(user.getCurrentBorrowed());
        dto.setFineAmount(user.getFineAmount());
        dto.setCreatedAt(user.getCreatedAt());
        
        return dto;
    }
    
    // 这个方法已经不需要了，因为我们在Repository中定义了对应的方法
    // 删除原来的 findByUsernameContainingIgnoreCaseOrRealNameContainingIgnoreCase 方法
}