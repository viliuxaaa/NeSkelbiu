package lt.neskelbiu.java.main.posterImg;

import com.fasterxml.jackson.databind.ObjectMapper;
import lt.neskelbiu.java.main.config.JwtAuthenticationFilter;
import lt.neskelbiu.java.main.poster.*;
import lt.neskelbiu.java.main.poster.categories.CategoryA;
import lt.neskelbiu.java.main.poster.categories.CategoryB;
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(value = PosterImgController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtAuthenticationFilter.class))
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc(addFilters = false)
public class PosterImgControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PosterService posterService;

    @MockBean
    private PosterImgService posterImgService;

    PosterImg posterImg = null;
    byte[] bytes = {};
    Long imagePosition = 0L;
    Long userId = 1L;

    Long posterId = 1L;
    String postName = "test";
    CategoryA categoryA = CategoryA.KOMPIUTERIAI;
    CategoryB categoryB = CategoryB.KOMPIUTERIAI_KITA;
    String description = "description";
    Status status = Status.ACTIVE;
    String phoneNumber = "37061111111";
    City city = City.ALYTUS;
    String website = "website";
    String videoLink = "videoLink";
    LocalDateTime createdAt = LocalDateTime.of(2023,2,1,1,1,1,10);
    LocalDateTime updatedAt = null;
    Long price = 1L;

    @BeforeAll
    public void fakeDataInit() throws IOException {
        File file = ResourceUtils.getFile("classpath:static/cup/default1.png");
        InputStream inputStream = new FileInputStream(file);
        bytes = inputStream.readAllBytes();

        posterImg = PosterImg.builder()
                .id("uuid")
                .position(0L)
                .name("default1.png")
                .data(bytes)
                .type("image/png")
                .build();

    }

    @Test
    public void GetImage_GetImage_ReturnsStatusOkByteArray() throws Exception {
        // Arrange
        when(posterImgService.getImage(Mockito.any(Long.class), Mockito.any(Long.class))).thenReturn(posterImg);

        // Perform the request and verify the response
        mockMvc.perform(get("/api/v1/images/poster/get/{posterId}/{imagePosition}", posterId, imagePosition)
                        .contentType(MediaType.IMAGE_PNG))
                .andExpect(status().isOk())
                .andExpect(content().bytes(bytes));
    }

    @Test
    public void UploadImages_UploadListOfImages_ReturnsStatusOkAndString() throws Exception {
        // Arrange
        MockMultipartFile file = new MockMultipartFile("image", "default1.txt", "image/png", bytes);

        Poster expectedPoster = Poster.builder()
                .id(posterId)
                .postName(postName)
                .categoryA(categoryA)
                .categoryB(categoryB)
                .description(description)
                .status(status)
                .phoneNumber(phoneNumber)
                .city(city)
                .website(website)
                .videoLink(videoLink)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .price(price)
                .build();

        String message = "Ikeltos 1 nuotraukos";

        when(posterService.findById(Mockito.any(Long.class))).thenReturn(expectedPoster);
        when(posterImgService.store(Mockito.any(List.class), Mockito.any(Poster.class))).thenReturn(message);

        // Perform the request and verify the response
        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/v1/images/poster/{userId}/{posterId}/upload", userId, posterId)
                        .file(file))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(message));
    }

    @Test
    public void UploadImages_UploadListOfImagesFailed_ReturnsStatusISEAndString() throws Exception {
        // Arrange
        MockMultipartFile file = new MockMultipartFile("image", "default1.txt", "image/png", bytes);

        Poster expectedPoster = Poster.builder()
                .id(posterId)
                .postName(postName)
                .categoryA(categoryA)
                .categoryB(categoryB)
                .description(description)
                .status(status)
                .phoneNumber(phoneNumber)
                .city(city)
                .website(website)
                .videoLink(videoLink)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .price(price)
                .build();

        String message = "Failed to upload images.";

        when(posterService.findById(Mockito.any(Long.class))).thenReturn(expectedPoster);
        when(posterImgService.store(Mockito.any(List.class), Mockito.any(Poster.class))).thenThrow(new IOException());

        // Perform the request and verify the response
        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/v1/images/poster/{userId}/{posterId}/upload", userId, posterId)
                        .file(file))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.content().string(message));
    }

    @Test
    public void DeleteImage_DeleteSingleImage_ReturnsStatusOk() throws Exception {
        // Arrange
        String message = "Images deleted successfully.";

        // Perform the request and verify the response
        mockMvc.perform(delete("/api/v1/images/poster/{userId}/{posterId}/delete", userId, posterId))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(message));
    }

    @Test
    public void DeleteImage_DeleteImageFailed_ReturnsStatusInternalServerError() throws Exception {
        // Arrange
        String message = "Failed to delete images.";

        doThrow(new IllegalArgumentException()).when(posterImgService).delete(Mockito.any(Long.class));

        // Perform the request and verify the response
        mockMvc.perform(delete("/api/v1/images/poster/{userId}/{posterId}/delete", userId, posterId))
                .andExpect(status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.content().string(message));
    }

}
