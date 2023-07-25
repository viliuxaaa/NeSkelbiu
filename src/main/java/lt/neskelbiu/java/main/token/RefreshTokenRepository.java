package lt.neskelbiu.java.main.token;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long>{
	List<RefreshToken> findAllValidTokenByUser(Integer id);

	  Optional<RefreshToken> findByToken(String token);
}
