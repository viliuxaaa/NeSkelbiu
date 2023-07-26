package lt.neskelbiu.java.main.userImg;

import lt.neskelbiu.java.main.user.User;
import lt.neskelbiu.java.main.userImg.UserImg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserImgRepository extends JpaRepository<UserImg, Long>{
    UserImg findByUser(User user);
    void deleteById(String id);

}
