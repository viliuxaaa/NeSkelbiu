package lt.neskelbiu.java.main.userImg;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lt.neskelbiu.java.main.user.UserRepository;
import lt.neskelbiu.java.main.user.UserService;
import lt.neskelbiu.java.main.message.ResponseMessage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

// https://www.bezkoder.com/spring-boot-upload-file-database/

@RestController
@RequiredArgsConstructor
@Tag(name = "User Image Controller")
@RequestMapping("/api/v1/user")
public class UserImgController {

    private final UserImgService userImgService;
    private final UserService userService;
    private final UserRepository userRepository;

    @Operation(
            summary = "Used for getting user's image",
            description = "With this endpoint you get user image. Need to provide user id."
    )
    @GetMapping("/get/{userId}/image")
    public ResponseEntity<byte[]> getImage(@PathVariable Long userId) throws InterruptedException {
        var user = userService.findById(userId);
        var userImg = userImgService.getUserImage(user);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);

        return ResponseEntity.status(HttpStatus.OK)
                .headers(headers)
                .body(userImg.getData());
    }

    @Operation(
            summary = "Used for uploading new user's image",
            description = "With this endpoint you upload new or overwrite old user's image. In request param you " +
                    "need to provide image param called \"image\" and give image as a file. This is protected path, " +
                    "for authenticated users only."
    )
    @PostMapping("/{userId}/image-upload")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<ResponseMessage> uploadImage(@PathVariable Long userId, @RequestParam("image") MultipartFile file) throws InterruptedException {
        var user = userService.findById(userId);
        var userImg = userImgService.getUserImage(user);

        if (userImg != null) {
            userImgService.deleteById(userImg.getId());
        }

        String message = "";
        try {
            userImgService.store(file, user);
            message = "Uploaded the upload successfully: " + file.getOriginalFilename();
            user.setUpdatedAt(LocalDateTime.now());
            userRepository.save(user);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not upload the image: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    @Operation(
            summary = "Used for deleting user's image",
            description = "With this endpoint you delete user's image. This is protected path, for authenticated users only."
    )
    @DeleteMapping("/{userId}/image-delete")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<ResponseMessage> deleteImage(@PathVariable Long userId) {
        var user = userService.findById(userId);
        var userImg = userImgService.getUserImage(user);

        String message = "Deleted the image successfully: " + userImg.getName();
        userImgService.deleteById(userImg.getId());
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
    }
}
