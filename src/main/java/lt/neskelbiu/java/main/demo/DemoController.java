package lt.neskelbiu.java.main.demo;

import io.swagger.v3.oas.annotations.Hidden;
import lt.neskelbiu.java.main.user.User;
import lt.neskelbiu.java.main.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/demo")
@Hidden
public class DemoController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/controller")
    public ResponseEntity<String> sayHello() {
        return ResponseEntity.ok("Hello from secure endpoint");
    }

    @GetMapping("/users")
    public ResponseEntity<List<String>> getUsers() {
        List<String> list = userRepository.findAll().stream().map(User::getUsername).collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }
}
