package com.library.repository;

import com.library.entity.BorrowRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * 借阅记录数据访问接口
 */
@Repository
public interface BorrowRecordRepository extends BaseRepository<BorrowRecord, Long> {
    
    /**
     * 根据用户ID查找借阅记录
     */
    List<BorrowRecord> findByUserId(Long userId);
    
    /**
     * 根据用户ID查找借阅记录（分页）
     */
    Page<BorrowRecord> findByUserId(Long userId, Pageable pageable);
    
    /**
     * 根据图书ID查找借阅记录
     */
    List<BorrowRecord> findByBookId(Long bookId);
    
    /**
     * 根据用户ID和图书ID查找未归还的记录
     */
    Optional<BorrowRecord> findByUserIdAndBookIdAndStatus(Long userId, Long bookId, BorrowRecord.BorrowStatus status);
    
    /**
     * 根据状态查找借阅记录
     */
    List<BorrowRecord> findByStatus(BorrowRecord.BorrowStatus status);
    
    /**
     * 根据应还日期范围查找借阅记录
     */
    List<BorrowRecord> findByDueDateBetween(LocalDate startDate, LocalDate endDate);
    
    /**
     * 查找超期的借阅记录（未归还且应还日期已过）
     */
    @Query("SELECT br FROM BorrowRecord br WHERE br.status = 'BORROWED' AND br.dueDate < :today")
    List<BorrowRecord> findOverdueRecords(@Param("today") LocalDate today);
    
    /**
     * 查找今日应还的记录
     */
    @Query("SELECT br FROM BorrowRecord br WHERE br.status = 'BORROWED' AND br.dueDate = :today")
    List<BorrowRecord> findDueTodayRecords(@Param("today") LocalDate today);
    
    /**
     * 统计用户的借阅次数
     */
    @Query("SELECT COUNT(br) FROM BorrowRecord br WHERE br.user.id = :userId")
    Long countByUserId(@Param("userId") Long userId);
    
    /**
     * 统计图书被借阅次数
     */
    @Query("SELECT COUNT(br) FROM BorrowRecord br WHERE br.book.id = :bookId")
    Long countByBookId(@Param("bookId") Long bookId);
    
    /**
     * 查找用户当前借阅中的记录
     */
    @Query("SELECT br FROM BorrowRecord br WHERE br.user.id = :userId AND br.status = 'BORROWED'")
    List<BorrowRecord> findCurrentBorrowsByUserId(@Param("userId") Long userId);
    
    /**
     * 查找用户历史借阅记录
     */
    @Query("SELECT br FROM BorrowRecord br WHERE br.user.id = :userId AND br.status != 'BORROWED'")
    List<BorrowRecord> findHistoryBorrowsByUserId(@Param("userId") Long userId);
    
    /**
     * 统计每日借阅数量
     */
    @Query("SELECT br.borrowDate, COUNT(br) FROM BorrowRecord br GROUP BY br.borrowDate ORDER BY br.borrowDate DESC")
    List<Object[]> countBorrowsByDate();
    
    /**
     * 统计每月借阅数量
     */
    @Query("SELECT FUNCTION('DATE_FORMAT', br.borrowDate, '%Y-%m'), COUNT(br) FROM BorrowRecord br GROUP BY FUNCTION('DATE_FORMAT', br.borrowDate, '%Y-%m') ORDER BY FUNCTION('DATE_FORMAT', br.borrowDate, '%Y-%m') DESC")
    List<Object[]> countBorrowsByMonth();
    
    /**
     * 查找有罚款的借阅记录
     */
    List<BorrowRecord> findByFineAmountGreaterThan(Double amount);
    
    /**
     * 根据多个条件搜索借阅记录（分页）
     */
    @Query("SELECT br FROM BorrowRecord br WHERE " +
           "(:userId IS NULL OR br.user.id = :userId) AND " +
           "(:bookId IS NULL OR br.book.id = :bookId) AND " +
           "(:status IS NULL OR br.status = :status) AND " +
           "(:startDate IS NULL OR br.borrowDate >= :startDate) AND " +
           "(:endDate IS NULL OR br.borrowDate <= :endDate)")
    Page<BorrowRecord> searchBorrowRecords(@Param("userId") Long userId,
                                          @Param("bookId") Long bookId,
                                          @Param("status") BorrowRecord.BorrowStatus status,
                                          @Param("startDate") LocalDate startDate,
                                          @Param("endDate") LocalDate endDate,
                                          Pageable pageable);
}