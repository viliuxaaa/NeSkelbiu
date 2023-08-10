package lt.neskelbiu.java.main.poster;

import com.fasterxml.jackson.databind.ObjectMapper;
import lt.neskelbiu.java.main.auth.AuthenticationController;
import lt.neskelbiu.java.main.auth.AuthenticationService;
import lt.neskelbiu.java.main.config.JwtAuthenticationFilter;
import lt.neskelbiu.java.main.config.JwtService;
import lt.neskelbiu.java.main.poster.categories.CategoryA;
import lt.neskelbiu.java.main.poster.categories.CategoryB;
import lt.neskelbiu.java.main.posterImg.PosterImg;
import lt.neskelbiu.java.main.token.TokenRepository;
import lt.neskelbiu.java.main.user.Role;
import lt.neskelbiu.java.main.user.User;
import lt.neskelbiu.java.main.user.UserResponse;
import lt.neskelbiu.java.main.user.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
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
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(value = PosterController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtAuthenticationFilter.class))
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc(addFilters = false)
public class PosterControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PosterService posterService;

    ObjectMapper objectMapper = new ObjectMapper();

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
    List<PosterImg> posterImg = null;

    Poster expectedPoster = null;
    PosterResponse expectedResponse = null;

    @BeforeAll
    public void fakeDataInit() {
        expectedPoster = Poster.builder()
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
            .posterImg(posterImg)
            .build();

        expectedResponse = PosterResponse.builder()
                .userId(userId)
                .posterId(posterId)
                .postName(postName)
                .categoryA(categoryA)
                .categoryB(categoryB)
                .description(description)
                .status(status)
                .phoneNumber(phoneNumber)
                .city("Alytus")
                .website(website)
                .videoLink(videoLink)
                .createdAt(String.valueOf(createdAt))
                .updatedAt(null)
                .price(price)
                .images(null)
                .build();

    }

    @Test
    public void GetAllUsersPosters_GetListOfPosters_ReturnsStatusOkListPosterResponse() throws Exception {
        // Arrange
        List<Poster> expectedList = new ArrayList<>();
        expectedList.add(expectedPoster);

        List<PosterResponse> posterResponseList = new ArrayList<>();
        posterResponseList.add(expectedResponse);

        when(posterService.findAllUsersPosters(userId)).thenReturn(expectedList);
        when(posterService.posterListResponse(Mockito.any(List.class))).thenReturn(posterResponseList);

        // Perform the request and verify the response
        mockMvc.perform(get("/api/v1/poster/get/1/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].userId").value(userId))
                .andExpect(jsonPath("$[0].posterId").value(posterId))
                .andExpect(jsonPath("$[0].postName").value(postName))
                .andExpect(jsonPath("$[0].description").value(description))
                .andExpect(jsonPath("$[0].price").value(price))
                .andExpect(jsonPath("$[0].city").value("Alytus"));
    }

    @Test
    public void GetPoster_GetSinglePoster_ReturnsStatusOkPosterResponse() throws Exception {
        // Arrange
        when(posterService.findById(Mockito.any(Long.class))).thenReturn(expectedPoster);
        when(posterService.buildPosterResponse(Mockito.any(Poster.class))).thenReturn(expectedResponse);

        // Perform the request and verify the response
        mockMvc.perform(get("/api/v1/poster/get/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.userId").value(userId))
                .andExpect(jsonPath("$.posterId").value(posterId))
                .andExpect(jsonPath("$.postName").value(postName))
                .andExpect(jsonPath("$.description").value(description))
                .andExpect(jsonPath("$.price").value(price))
                .andExpect(jsonPath("$.city").value("Alytus"));
    }

    @Test
    public void CreatePoster_CreateSinglePoster_ReturnsStatusOkPosterResponse() throws Exception {
        // Arrange
        PosterRequest posterRequest = PosterRequest.builder()
                .postName(postName)
                .categoryA(categoryA)
                .categoryB(categoryB)
                .description(description)
                .status(status)
                .phoneNumber(phoneNumber)
                .city(city)
                .website(website)
                .videoLink(videoLink)
                .price(price)
                .build();
        when(posterService.buildPosterNoId(Mockito.any(Long.class), Mockito.any(PosterRequest.class))).thenReturn(expectedPoster);
        when(posterService.buildPosterResponse(Mockito.any(Poster.class))).thenReturn(expectedResponse);

        // Perform the request and verify the response
        mockMvc.perform(post("/api/v1/poster/1/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(posterRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.userId").value(userId))
                .andExpect(jsonPath("$.posterId").value(posterId))
                .andExpect(jsonPath("$.postName").value(postName))
                .andExpect(jsonPath("$.description").value(description))
                .andExpect(jsonPath("$.price").value(price))
                .andExpect(jsonPath("$.city").value("Alytus"));
    }

    @Test
    public void UpdatePoster_UpdateSinglePoster_ReturnsStatusOkPosterResponse() throws Exception {
        // Arrange
        PosterRequest posterRequest = PosterRequest.builder()
                .postName(postName)
                .categoryA(categoryA)
                .categoryB(categoryB)
                .description(description)
                .status(status)
                .phoneNumber(phoneNumber)
                .city(city)
                .website(website)
                .videoLink(videoLink)
                .price(price)
                .build();
        when(posterService.buildPosterWithId(Mockito.any(Long.class), Mockito.any(Long.class), Mockito.any(PosterRequest.class))).thenReturn(expectedPoster);
        when(posterService.buildPosterResponse(Mockito.any(Poster.class))).thenReturn(expectedResponse);

        // Perform the request and verify the response
        mockMvc.perform(put("/api/v1/poster/1/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(posterRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.userId").value(userId))
                .andExpect(jsonPath("$.posterId").value(posterId))
                .andExpect(jsonPath("$.postName").value(postName))
                .andExpect(jsonPath("$.description").value(description))
                .andExpect(jsonPath("$.price").value(price))
                .andExpect(jsonPath("$.city").value("Alytus"));
    }

    @Test
    public void GetLatest_GetPosters_ReturnsStatusOkListPosterResponse() throws Exception {
        // Arrange
        List<Poster> expectedList = new ArrayList<>();
        expectedList.add(expectedPoster);

        List<PosterResponse> posterResponseList = new ArrayList<>();
        posterResponseList.add(expectedResponse);

        when(posterService.getLatest()).thenReturn(posterResponseList);

        // Perform the request and verify the response
        mockMvc.perform(get("/api/v1/poster/get/latest")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].userId").value(userId))
                .andExpect(jsonPath("$[0].posterId").value(posterId))
                .andExpect(jsonPath("$[0].postName").value(postName))
                .andExpect(jsonPath("$[0].description").value(description))
                .andExpect(jsonPath("$[0].price").value(price))
                .andExpect(jsonPath("$[0].city").value("Alytus"));
    }
    @Test
    public void DeletePoster_DeleteSinglePoster_ReturnsStatusOk() throws Exception {
        // Perform the request and verify the response
        mockMvc.perform(delete("/api/v1/poster/1/delete/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void GetSearch_SearchPostersByAttributes_ReturnsStatusOkListPosterResponse() throws Exception {
        // Arrange
        List<PosterResponse> posterResponseList = new ArrayList<>();
        posterResponseList.add(expectedResponse);

        when(posterService.searchEngine(null, null, null, null, null, null, null)).thenReturn(posterResponseList);

        // Perform the request and verify the response
        mockMvc.perform(get("/api/v1/poster/get/search")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
