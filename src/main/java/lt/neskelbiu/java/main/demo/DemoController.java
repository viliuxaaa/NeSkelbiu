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

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/demo")
@Hidden
public class DemoController {
    @GetMapping("")
    public  ResponseEntity<ResponseMessage> getUserName() {
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(String.valueOf((new Date(System.currentTimeMillis() + 20000)).getTime())));
    }
}
