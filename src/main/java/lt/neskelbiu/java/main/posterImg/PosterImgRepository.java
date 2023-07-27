package lt.neskelbiu.java.main.posterImg;

import java.util.List;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PosterImgRepository extends JpaRepository<PosterImg, String>{

	List<PosterImg> findAllByPosterId(Long id);

	@Transactional
	@Modifying
	@Query("""
            DELETE FROM PosterImg p WHERE p.id = :id
            """)
	void deleteById(@Param("id") String id);

}
