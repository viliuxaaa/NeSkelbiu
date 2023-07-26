package lt.neskelbiu.java.main.userImg;

import lombok.RequiredArgsConstructor;
import lt.neskelbiu.java.main.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class UserImgService {

    private final UserImgRepository userImgRepository;

    public UserImg store(MultipartFile file, User user) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        UserImg userImg = UserImg.builder()
                .name(fileName)
                .type(file.getContentType())
                .data(file.getBytes())
                .user(user)
                .build();

        return userImgRepository.save(userImg);
    }

    public UserImg getUserImage(User user) {
        return userImgRepository.findByUser(user);
    }

    public void deleteById(String id) {
        userImgRepository.deleteById(id);
    }
}
