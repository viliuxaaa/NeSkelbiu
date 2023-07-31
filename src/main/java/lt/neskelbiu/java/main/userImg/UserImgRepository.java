package lt.neskelbiu.java.main.userImg;

import jakarta.transaction.Transactional;
import lt.neskelbiu.java.main.user.User;
import lt.neskelbiu.java.main.userImg.UserImg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserImgRepository extends JpaRepository<UserImg, String>{
    UserImg findByUser(User user);

    @Transactional
    @Modifying
    @Query("""
            delete from UserImg u where u.id = :id
            """)
    void deleteById(@Param("id") String id);
}
