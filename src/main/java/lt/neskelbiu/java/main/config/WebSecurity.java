package lt.neskelbiu.java.main.config;

import lombok.RequiredArgsConstructor;
import lt.neskelbiu.java.main.user.User;
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
    UserService userService;

    public boolean checkUserId(Supplier<Authentication> authentication, String id) {
        Long userId = Long.valueOf(id);
        logger.info(String.valueOf(userId));
        User user = userService.findByUsername(
                authentication.get().getName()
        );
        logger.info(String.valueOf(user.getId()));
        return userId == user.getId();
    }
}