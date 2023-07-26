package lt.neskelbiu.java.main.user;

import lombok.RequiredArgsConstructor;
import lt.neskelbiu.java.main.exceptions.UserNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    final UserRepository userRepository;
    public User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found by id:" + userId));
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User with such username not found:" + username));
    }
}
