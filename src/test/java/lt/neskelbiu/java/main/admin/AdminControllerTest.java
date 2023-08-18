package lt.neskelbiu.java.main.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import lt.neskelbiu.java.main.auth.AuthenticationResponse;
import lt.neskelbiu.java.main.auth.AuthenticationService;
import lt.neskelbiu.java.main.auth.RegisterRequest;
import lt.neskelbiu.java.main.config.JwtAuthenticationFilter;
import lt.neskelbiu.java.main.config.JwtService;
import lt.neskelbiu.java.main.poster.PosterService;
import lt.neskelbiu.java.main.token.TokenRepository;
import lt.neskelbiu.java.main.user.Role;
import lt.neskelbiu.java.main.user.User;
import lt.neskelbiu.java.main.user.UserResponse;
import lt.neskelbiu.java.main.user.UserService;
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

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(value = AdminController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtAuthenticationFilter.class))
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc(addFilters = false)
public class AdminControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private UserService userService;

    @MockBean
    private PosterService posterService;

    @MockBean
    private TokenRepository tokenRepository;

    @MockBean
    private AuthenticationService authenticationService;

    ObjectMapper objectMapper = new ObjectMapper();
    @Test
    public void RegisterAdmin_RegisterAdminSuccessful_ReturnsStatusOkAndAuthenticationResponse() throws Exception {
        // Arrange
        String username = "test";
        String password = "test";
        String firstName = "testFirst";
        String lastName = "testLast";
        String email = "test@email.com";

        RegisterRequest request = new RegisterRequest();
        request.setUsername(username);
        request.setPassword(password);
        request.setFirstName(firstName);
        request.setLastName(lastName);
        request.setEmail(email);

        AuthenticationResponse expectedResponse = new AuthenticationResponse();
        expectedResponse.setId(1L);
        expectedResponse.setUsername(username);
        expectedResponse.setEmail(email);
        expectedResponse.setRole(Role.ADMIN);
        expectedResponse.setFirstName(firstName);
        expectedResponse.setLastName(lastName);
        expectedResponse.setAccessToken("abc123");

        when(authenticationService.register(Mockito.any(RegisterRequest.class), Mockito.eq(Role.ADMIN))).thenReturn(expectedResponse);

        // Perform the request and verify the response
        mockMvc.perform(post("/api/v1/admin/register/admin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.user_id").value(1L))
                .andExpect(jsonPath("$.username").value(username))
                .andExpect(jsonPath("$.email").value(email))
                .andExpect(jsonPath("$.role").value("ADMIN"))
                .andExpect(jsonPath("$.first_name").value(firstName))
                .andExpect(jsonPath("$.last_name").value(lastName))
                .andExpect(jsonPath("$.access_token").value("abc123"));
    }

    @Test
    public void ChangeLock_UserAccLockChange_ReturnsStatusOkResponseMessage() throws Exception {
        // Arrange
        Long userId = 2L;
        String username = "test";
        String password = "test";
        String firstName = "testFirst";
        String lastName = "testLast";
        String email = "test@email.com";
        Boolean isNotLocked = true;
        Role role = Role.USER;

        User expectedUser = User.builder()
                .id(userId)
                .username(username)
                .password(password)
                .firstname(firstName)
                .lastname(lastName)
                .email(email)
                .isNotLocked(isNotLocked)
                .role(role)
                .build();

        when(userService.findById(Mockito.any(Long.class))).thenReturn(expectedUser);
        when(userService.changeUserLockdownState(Mockito.any(Long.class))).thenReturn("locked");

        //Act
        String message = userService.changeUserLockdownState(userId);

        // Perform the request and verify the response
        mockMvc.perform(put("/api/v1/admin/get/2/change-lock")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("User with id " + userId + " is " + message));
    }

    @Test
    public void ChangeLock_UserAccLockChange_ReturnsStatusBadRequestResponseMessage() throws Exception {
        // Arrange
        Long userId = 2L;
        String username = "test";
        String password = "test";
        String firstName = "testFirst";
        String lastName = "testLast";
        String email = "test@email.com";
        Boolean isNotLocked = true;
        Role role = Role.ADMIN;

        User expectedUser = User.builder()
                .id(userId)
                .username(username)
                .password(password)
                .firstname(firstName)
                .lastname(lastName)
                .email(email)
                .isNotLocked(isNotLocked)
                .role(role)
                .build();

        when(userService.findById(Mockito.any(Long.class))).thenReturn(expectedUser);

        // Perform the request and verify the response
        mockMvc.perform(put("/api/v1/admin/get/2/change-lock")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Admin cannot be locked"));
    }

    @Test
    public void DeleteUser_DeleteUserSuccessfully_ReturnsStatusOkResponseMessage() throws Exception {
        // Arrange
        Long userId = 2L;
        String username = "test";
        String password = "test";
        String firstName = "testFirst";
        String lastName = "testLast";
        String email = "test@email.com";
        Boolean isNotLocked = true;
        Role role = Role.USER;

        User expectedUser = User.builder()
                .id(userId)
                .username(username)
                .password(password)
                .firstname(firstName)
                .lastname(lastName)
                .email(email)
                .isNotLocked(isNotLocked)
                .role(role)
                .build();

        when(userService.findById(Mockito.any(Long.class))).thenReturn(expectedUser);

        // Perform the request and verify the response
        mockMvc.perform(delete("/api/v1/admin/get/2/delete")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("User with id " + userId + " is deleted"));
    }

    @Test
    public void DeleteUser_DeleteUserFailed_ReturnsStatusBadRequestResponseMessage() throws Exception {
        // Arrange
        Long userId = 2L;
        String username = "test";
        String password = "test";
        String firstName = "testFirst";
        String lastName = "testLast";
        String email = "test@email.com";
        Boolean isNotLocked = true;
        Role role = Role.ADMIN;

        User expectedUser = User.builder()
                .id(userId)
                .username(username)
                .password(password)
                .firstname(firstName)
                .lastname(lastName)
                .email(email)
                .isNotLocked(isNotLocked)
                .role(role)
                .build();

        when(userService.findById(Mockito.any(Long.class))).thenReturn(expectedUser);

        // Perform the request and verify the response
        mockMvc.perform(delete("/api/v1/admin/get/2/delete")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Admin cannot be deleted"));
    }

    @Test
    public void GetAllUsers_GetListOfUsers_ReturnsStatusOkListUserResponse() throws Exception {
        // Arrange
        Long userId = 2L;
        String username = "test";
        String password = "test";
        String firstName = "testFirst";
        String lastName = "testLast";
        String email = "test@email.com";
        Boolean isNotLocked = true;
        Role role = Role.ADMIN;

        List<User> expectedList = new ArrayList<>();
        User expectedUser = User.builder()
                .id(userId)
                .username(username)
                .password(password)
                .firstname(firstName)
                .lastname(lastName)
                .email(email)
                .isNotLocked(isNotLocked)
                .role(role)
                .build();
        expectedList.add(expectedUser);

        List<UserResponse> userResponseList = new ArrayList<>();
        UserResponse expectedResponseUser = UserResponse.builder()
                .id(userId)
                .username(username)
                .firstname(firstName)
                .lastname(lastName)
                .email(email)
                .role(role)
                .build();
        userResponseList.add(expectedResponseUser);

        when(userService.getAllUsers()).thenReturn(expectedList);
        when(userService.getResponseAllUsers(Mockito.any(List.class))).thenReturn(userResponseList);

        // Perform the request and verify the response
        mockMvc.perform(get("/api/v1/admin/get/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(userId))
                .andExpect(jsonPath("$[0].username").value(username))
                .andExpect(jsonPath("$[0].email").value(email))
                .andExpect(jsonPath("$[0].role").value("ADMIN"))
                .andExpect(jsonPath("$[0].firstname").value(firstName))
                .andExpect(jsonPath("$[0].lastname").value(lastName));
    }

    @Test
    public void DeletePoster_DeletePosterSuccessfully_ReturnsStatusOkResponseMessage() throws Exception {
        // Arrange
        Long posterId = 1L;

        // Perform the request and verify the response
        mockMvc.perform(delete("/api/v1/admin/get/1/delete-poster")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Poster with id " + posterId + " is deleted"));
    }
}
