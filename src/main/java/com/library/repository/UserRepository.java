package com.library.repository;

import com.library.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 用户数据访问接口
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    /**
     * 根据用户名查找用户
     */
    Optional<User> findByUsername(String username);
    
    /**
     * 根据邮箱查找用户
     */
    Optional<User> findByEmail(String email);
    
    /**
     * 根据用户名或邮箱查找用户
     */
    @Query("SELECT u FROM User u WHERE u.username = :usernameOrEmail OR u.email = :usernameOrEmail")
    Optional<User> findByUsernameOrEmail(@Param("usernameOrEmail") String usernameOrEmail);
    
    /**
     * 检查用户名是否存在
     */
    boolean existsByUsername(String username);
    
    /**
     * 检查邮箱是否存在
     */
    boolean existsByEmail(String email);
    
    /**
     * 根据角色查找用户
     */
    List<User> findByRole(User.Role role);
    
    /**
     * 根据状态查找用户
     */
    List<User> findByStatus(User.Status status);
    
    /**
     * 根据状态统计用户数量（新增）
     */
    Long countByStatus(User.Status status);
    
    /**
     * 根据角色和状态查找用户
     */
    List<User> findByRoleAndStatus(User.Role role, User.Status status);
    
    /**
     * 查找当前有罚款的用户
     */
    List<User> findByFineAmountGreaterThan(Double fineAmount);
    
    /**
     * 查找借阅数量达到上限的用户
     */
    @Query("SELECT u FROM User u WHERE u.currentBorrowed >= u.maxBorrowLimit")
    List<User> findUsersReachedBorrowLimit();
    
    /**
     * 统计不同角色的用户数量
     */
    @Query("SELECT u.role, COUNT(u) FROM User u GROUP BY u.role")
    List<Object[]> countUsersByRole();
    
    /**
     * 根据用户名模糊搜索
     */
    List<User> findByUsernameContainingIgnoreCase(String keyword);
    
    /**
     * 根据真实姓名模糊搜索
     */
    List<User> findByRealNameContainingIgnoreCase(String keyword);
    
    /**
     * 根据用户名或真实姓名模糊搜索并分页（新增）
     */
    Page<User> findByUsernameContainingIgnoreCaseOrRealNameContainingIgnoreCase(
            String username, String realName, Pageable pageable);
    
    /**
     * 根据用户名或真实姓名模糊搜索（不分页，新增）
     */
    List<User> findByUsernameContainingIgnoreCaseOrRealNameContainingIgnoreCase(
            String username, String realName);
}