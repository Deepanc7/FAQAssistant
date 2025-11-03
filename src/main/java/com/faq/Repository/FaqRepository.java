package com.faq.Repository;

import com.faq.Entity.Faq;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FaqRepository extends JpaRepository<Faq, UUID> {

    Page<Faq> findByCategory_Id(UUID categoryId, Pageable pageable);

    @Query("""
        SELECT DISTINCT f FROM Faq f
        LEFT JOIN f.tags t
        WHERE LOWER(f.question) LIKE LOWER(CONCAT('%', :keyword, '%'))
           OR LOWER(f.answer) LIKE LOWER(CONCAT('%', :keyword, '%'))
           OR LOWER(t.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
        """)
    Page<Faq> searchFaqs(@Param("keyword") String keyword, Pageable pageable);
}
