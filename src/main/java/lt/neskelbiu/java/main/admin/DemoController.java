package lt.neskelbiu.java.main.admin;

import io.swagger.v3.oas.annotations.Hidden;
import lt.neskelbiu.java.main.message.ResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/v1/demo")
@Hidden
public class DemoController {
    @GetMapping("")
    public  ResponseEntity<ResponseMessage> getUserName() {
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(String.valueOf((new Date(System.currentTimeMillis() + 20000)).getTime())));
    }
}
