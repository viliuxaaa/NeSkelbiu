package lt.neskelbiu.java.main.posterImg;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PosterImgRepository extends JpaRepository<PosterImg, String>{

	List<PosterImg> findAllByPosterId(Long id);

}
