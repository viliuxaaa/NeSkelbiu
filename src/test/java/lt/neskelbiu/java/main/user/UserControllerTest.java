package lt.neskelbiu.java.main.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import lt.neskelbiu.java.main.config.JwtAuthenticationFilter;
import lt.neskelbiu.java.main.poster.PosterService;
import lt.neskelbiu.java.main.posterImg.PosterImgController;
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
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(value = UserController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtAuthenticationFilter.class))
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    ObjectMapper objectMapper = new ObjectMapper();

    Long userId = 1L;
    String username = "username";
    String password = "password";
    Role role = Role.USER;
    String firstname = "John";
    String lastname = "Doe";
    String email = "johndoe@email.com";
    Boolean isNotLocked = true;
    User user = null;

    @BeforeAll
    public void initFakeData() {
        user = User.builder()
                .id(userId)
                .username(username)
                .role(role)
                .firstname(firstname)
                .lastname(lastname)
                .email(email)
                .isNotLocked(isNotLocked)
                .build();
    }

    @Test
    public void GetUserProfile_GetUSerProfileInfo_ReturnsStatusOkUserResponse() throws Exception {
        // Arrange
        UserResponse expectedUserResponse = UserResponse.builder()
                .id(userId)
                .username(username)
                .role(role)
                .firstname(firstname)
                .lastname(lastname)
                .email(email)
                .build();

        when(userService.findById(Mockito.any(Long.class))).thenReturn(user);
        when(userService.getResponseUser(Mockito.any(User.class))).thenReturn(expectedUserResponse);

        // Perform the request and verify the response
        mockMvc.perform(get("/api/v1/user/get/{userId}/profile", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.username").value(username))
                .andExpect(jsonPath("$.email").value(email))
                .andExpect(jsonPath("$.role").value("USER"))
                .andExpect(jsonPath("$.firstname").value(firstname))
                .andExpect(jsonPath("$.lastname").value(lastname));
    }

    @Test
    public void ChangeName_ChangeFirstOrLastName_ReturnsStatusOkResponseMessage() throws Exception {
        // Arrange
        when(userService.changeName(Mockito.any(Long.class), Mockito.any(ChangeNameRequest.class))).thenReturn(user);
        ChangeNameRequest request = new ChangeNameRequest("test", "test");


        // Perform the request and verify the response
        mockMvc.perform(post("/api/v1/user/{userId}/change-name", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("User updated successfully"));
    }

    @Test
    public void DeleteUser_DeleteSingleUser_ReturnsStatusOkResponseMessage() throws Exception {
        // Perform the request and verify the response
        mockMvc.perform(delete("/api/v1/user/{userId}/user-delete", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        )

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("User deleted successfully"));
    }
}
