package lt.neskelbiu.java.main.userImg;

import lombok.RequiredArgsConstructor;
import lt.neskelbiu.java.main.user.User;
import lt.neskelbiu.java.main.user.UserService;
import lt.neskelbiu.java.main.userImg.message.ResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

// https://www.bezkoder.com/spring-boot-upload-file-database/

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserImgController {

    private final UserImgService userImgService;
    private final UserService userService;

    @PostMapping("/{id}/image-upload")
    public ResponseEntity<ResponseMessage> uploadFile(@PathVariable Long id, @RequestParam("file") MultipartFile file) throws InterruptedException {
        var user = userService.findById(id);
        var userImg = userImgService.getUserImage(user);

//        if (userImg != null) {
//            userImgService.deleteById(userImg.getId());
//            Thread.sleep(1500);
//        }

        String message = "";
        try {
            userImgService.store(file, user);
            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    @DeleteMapping("/{id}/image-upload")
    public ResponseEntity<ResponseMessage> DeleteFile(@PathVariable Long id) throws InterruptedException {
        var user = userService.findById(id);
        var userImg = userImgService.getUserImage(user);

//        if (userImg != null) {
//            userImgService.delete(userImg.getId());
//            Thread.sleep(1500);
//        }

        String message = "";
        userImgService.deleteById(userImg.getId());
        message = "Deleted the file successfully: ";
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
    }
}
