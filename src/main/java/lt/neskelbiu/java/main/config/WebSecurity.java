package lt.neskelbiu.java.main.config;

import lombok.RequiredArgsConstructor;
import lt.neskelbiu.java.main.user.User;
<<<<<<< HEAD
=======
import lt.neskelbiu.java.main.user.UserRepository;
>>>>>>> e788083e09d19bad4191a7db85458104ca7f65ad
import lt.neskelbiu.java.main.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.function.Supplier;

class WebSecurity {

    Logger logger = LoggerFactory.getLogger(WebSecurity.class);

    @Autowired
<<<<<<< HEAD
    UserService userService;
=======
    UserRepository userRepository;
>>>>>>> e788083e09d19bad4191a7db85458104ca7f65ad

    public boolean checkUserId(Supplier<Authentication> authentication, String id) {
        Long userId = Long.valueOf(id);
        logger.info(String.valueOf(userId));
<<<<<<< HEAD
        User user = userService.findByUsername(
                authentication.get().getName()
        );
=======
        User user = userRepository.findByUsername(
                authentication.get().getName()
        ).orElseThrow(() -> new UsernameNotFoundException("Not found"));
>>>>>>> e788083e09d19bad4191a7db85458104ca7f65ad
        logger.info(String.valueOf(user.getId()));
        return userId == user.getId();
    }
}