package lt.neskelbiu.java.main.poster;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PosterImgRepository extends JpaRepository<PosterImg, Long>{

}
