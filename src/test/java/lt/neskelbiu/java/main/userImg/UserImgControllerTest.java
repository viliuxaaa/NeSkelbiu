package lt.neskelbiu.java.main.userImg;

import com.fasterxml.jackson.databind.ObjectMapper;
import lt.neskelbiu.java.main.config.JwtAuthenticationFilter;
import lt.neskelbiu.java.main.posterImg.PosterImg;
import lt.neskelbiu.java.main.posterImg.PosterImgController;
import lt.neskelbiu.java.main.user.User;
import lt.neskelbiu.java.main.user.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(value = UserImgController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtAuthenticationFilter.class))
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc(addFilters = false)
public class UserImgControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    UserImgService userImgService;

    @MockBean
    UserService userService;

    UserImg userImg = null;
    byte[] bytes = {};
    Long userId = 1L;

    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeAll
    public void fakeDataInit() throws IOException {
        File file = ResourceUtils.getFile("classpath:static/cup/default1.png");
        InputStream inputStream = new FileInputStream(file);
        bytes = inputStream.readAllBytes();

        userImg = UserImg.builder()
                .id("uuid")
                .name("default1.png")
                .data(bytes)
                .type("image/png")
                .build();
    }

    @Test
    public void GetImage_GetImage_ReturnsStatusOkByteArray() throws Exception {
        // Arrange
        when(userService.findById(Mockito.any(Long.class))).thenReturn(new User());
        when(userImgService.getUserImage(Mockito.any(User.class))).thenReturn(userImg);

        // Perform the request and verify the response
        mockMvc.perform(get("/api/v1/user/get/{userId}/image", userId)
                        .contentType(MediaType.IMAGE_PNG))
                .andExpect(status().isOk())
                .andExpect(content().bytes(bytes));
    }

    @Test
    public void UploadImage_UploadUserImage_ReturnsStatusOkResponseMessage() throws Exception {
        // Arrange
        MockMultipartFile file = new MockMultipartFile("image", "default1.txt", "image/png", bytes);

        when(userService.findById(Mockito.any(Long.class))).thenReturn(new User());
        when(userImgService.getUserImage(Mockito.any(User.class))).thenReturn(userImg);

        String message = "Uploaded the upload successfully: " + file.getOriginalFilename();

        // Perform the request and verify the response
        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/v1/user/{userId}/image-upload", userId)
                        .file(file))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(message));
    }

    @Test
    public void UploadImage_UploadUserImageFailed_ReturnsStatusExpectationFailedResponseMessage() throws Exception {
        // Arrange
        MockMultipartFile file = new MockMultipartFile("image", "default1.txt", "image/png", bytes);

        when(userService.findById(Mockito.any(Long.class))).thenReturn(new User());
        when(userImgService.getUserImage(Mockito.any(User.class))).thenReturn(userImg);
        when(userImgService.store(Mockito.any(MultipartFile.class), Mockito.any(User.class))).thenThrow(new IllegalArgumentException());

        String message = "Could not upload the image: " + file.getOriginalFilename() + "!";

        // Perform the request and verify the response
        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/v1/user/{userId}/image-upload", userId)
                        .file(file))
                .andExpect(status().isExpectationFailed())
                .andExpect(jsonPath("$.message").value(message));
    }

    @Test
    public void DeleteImage_DeleteUserImage_ReturnsStatusOkResponseMessage() throws Exception {
        // Arrange
        when(userService.findById(Mockito.any(Long.class))).thenReturn(new User());
        when(userImgService.getUserImage(Mockito.any(User.class))).thenReturn(userImg);

        String message = "Deleted the image successfully: " + userImg.getName();

        // Perform the request and verify the response
        mockMvc.perform(delete("/api/v1/user/{userId}/image-delete", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(message));
    }

}
