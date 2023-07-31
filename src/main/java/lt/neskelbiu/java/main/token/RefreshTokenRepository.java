package lt.neskelbiu.java.main.token;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long>{
	@Query(value = """
	      select t from RefreshToken t inner join User u\s
	      on t.user.id = u.id\s
	      where u.id = :id and (t.expired = false)\s
	      """)
	List<RefreshToken> findAllValidTokenByUser(Long id);
}
