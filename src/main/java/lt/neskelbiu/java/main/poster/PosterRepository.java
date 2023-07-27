package lt.neskelbiu.java.main.poster;

import lt.neskelbiu.java.main.poster.categories.CategoryA;
import lt.neskelbiu.java.main.poster.categories.CategoryB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PosterRepository extends JpaRepository<Poster, Long>{
    @Query("""
            SELECT t FROM Poster t\s
            WHERE (:categoryA is NULL OR t.categoryA = :categoryA)\s
            AND (:categoryB is NULL OR t.categoryB = :categoryB)\s
            AND (:cityEnum is NULL OR t.city = :cityEnum)\s
            ORDER BY price asc
            """)
    List<Poster> searchByAndSortByPriceAcsending(CategoryA categoryA, CategoryB categoryB, City cityEnum);

    @Query("""
            SELECT t FROM Poster t\s
            WHERE (:categoryA is NULL OR t.categoryA = :categoryA)\s
            AND (:categoryB is NULL OR t.categoryB = :categoryB)\s
            AND (:cityEnum is NULL OR t.city = :cityEnum)\s
            ORDER BY price desc
            """)
    List<Poster> searchByAndSortByPriceDescending(CategoryA categoryA, CategoryB categoryB, City cityEnum);

    @Query("""
            SELECT t FROM Poster t\s
            WHERE (:categoryA is NULL OR t.categoryA = :categoryA)\s
            AND (:categoryB is NULL OR t.categoryB = :categoryB)\s
            AND (:cityEnum is NULL OR t.city = :cityEnum)\s
            ORDER BY t.createdAt DESC
            """)
    List<Poster> searchByAndSortByCreatedAt(CategoryA categoryA, CategoryB categoryB, City cityEnum);

    @Query("""
            SELECT t FROM Poster t\s
            WHERE (:categoryA is NULL OR t.categoryA = :categoryA)\s
            AND (:categoryB is NULL OR t.categoryB = :categoryB)\s
            AND (:cityEnum is NULL OR t.city = :cityEnum)\s
            ORDER BY t.updatedAt DESC
            """)
    List<Poster> searchByAndSortByUpdatedAt(CategoryA categoryA, CategoryB categoryB, City cityEnum);

    @Query("""
            SELECT t FROM Poster t\s
            WHERE (:categoryA is NULL OR t.categoryA = :categoryA)\s
            AND (:categoryB is NULL OR t.categoryB = :categoryB)\s
            AND (:cityEnum is NULL OR t.city = :cityEnum)\s
            """)
    List<Poster> searchBy(CategoryA categoryA, CategoryB categoryB, City cityEnum);
}
