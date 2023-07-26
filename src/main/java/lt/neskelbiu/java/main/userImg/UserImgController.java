package lt.neskelbiu.java.main.userImg;

import lombok.RequiredArgsConstructor;
import lt.neskelbiu.java.main.user.User;
import lt.neskelbiu.java.main.user.UserService;
import lt.neskelbiu.java.main.userImg.message.ResponseMessage;
import org.springframework.http.HttpHeaders;
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

    @GetMapping("/{id}/image")
    public ResponseEntity<byte[]> getImage(@PathVariable Long id) throws InterruptedException {
        var user = userService.findById(id);
        var userImg = userImgService.getUserImage(user);
        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + userImg.getName() + "\"")
                .body(userImg.getData());
    }

    @PostMapping("/{id}/image-upload")
    public ResponseEntity<ResponseMessage> uploadImage(@PathVariable Long id, @RequestParam("file") MultipartFile file) throws InterruptedException {
        var user = userService.findById(id);
        var userImg = userImgService.getUserImage(user);

        if (userImg != null) {
            userImgService.deleteById(userImg.getId());
        }

        String message = "";
        try {
            userImgService.store(file, user);
            message = "Uploaded the upload successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not upload the image: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    @DeleteMapping("/{id}/image-delete")
    public ResponseEntity<ResponseMessage> deleteImage(@PathVariable Long id) {
        var user = userService.findById(id);
        var userImg = userImgService.getUserImage(user);

        String message = "Deleted the image successfully: " + userImg.getName();
        userImgService.deleteById(userImg.getId());
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
    }
}
