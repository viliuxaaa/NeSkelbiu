package lt.neskelbiu.java.main.demo;

import io.swagger.v3.oas.annotations.Hidden;
import lt.neskelbiu.java.main.auth.AuthenticationResponse;
import lt.neskelbiu.java.main.auth.AuthenticationService;
import lt.neskelbiu.java.main.message.ResponseMessage;
import lt.neskelbiu.java.main.user.User;
import lt.neskelbiu.java.main.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/demo")
@Hidden
public class DemoController {
    @GetMapping("/users/{userId}/demo")
    public  ResponseEntity<ResponseMessage> getUserName(@PathVariable String userId) {
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Hello, this is" + userId));
    }
}
