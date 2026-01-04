package com.library.repository;

import com.library.entity.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

/**
 * 基础Repository接口
 * @param <T> 实体类型
 * @param <ID> ID类型
 */
@NoRepositoryBean
public interface BaseRepository<T extends BaseEntity, ID> extends JpaRepository<T, ID> {
    
    /**
     * 根据ID列表查找实体
     */
    List<T> findByIdIn(List<ID> ids);
    
    /**
     * 根据是否删除标志查找
     */
    List<T> findByIsDeleted(Boolean isDeleted);
    
    /**
     * 查找未删除的记录
     */
    default List<T> findActive() {
        return findByIsDeleted(false);
    }
    
    /**
     * 查找已删除的记录
     */
    default List<T> findDeleted() {
        return findByIsDeleted(true);
    }
    
    /**
     * 软删除（标记为已删除）
     */
    default void softDelete(ID id) {
        findById(id).ifPresent(entity -> {
            entity.setIsDeleted(true);
            save(entity);
        });
    }
    
    /**
     * 批量软删除
     */
    default void softDeleteAll(List<ID> ids) {
        List<T> entities = findByIdIn(ids);
        entities.forEach(entity -> entity.setIsDeleted(true));
        saveAll(entities);
    }
    
    /**
     * 恢复软删除的记录
     */
    default void restore(ID id) {
        findById(id).ifPresent(entity -> {
            entity.setIsDeleted(false);
            save(entity);
        });
    }
    
    /**
     * 统计未删除的记录数量
     */
    Long countByIsDeletedFalse();
    
    /**
     * 统计已删除的记录数量
     */
    Long countByIsDeletedTrue();
}