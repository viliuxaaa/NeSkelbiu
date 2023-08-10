package lt.neskelbiu.java.main.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import lt.neskelbiu.java.main.config.JwtAuthenticationFilter;
import lt.neskelbiu.java.main.exceptions.UserAlreadyExistsExceptionEmail;
import lt.neskelbiu.java.main.exceptions.UserAlreadyExistsExceptionUsername;
import lt.neskelbiu.java.main.message.ResponseMessage;
import lt.neskelbiu.java.main.user.Role;
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
import lt.neskelbiu.java.main.config.JwtService;
import lt.neskelbiu.java.main.token.TokenRepository;
import lt.neskelbiu.java.main.poster.PosterService;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(value = AuthenticationController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtAuthenticationFilter.class))
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc(addFilters = false)
public class AuthenticationControllerTest {
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
    public void Register_RegisterSuccessful_ReturnsStatusOkAndAuthenticationResponse() throws Exception {
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
        expectedResponse.setRole(Role.USER);
        expectedResponse.setFirstName(firstName);
        expectedResponse.setLastName(lastName);
        expectedResponse.setAccessToken("abc123");

        when(authenticationService.register(Mockito.any(RegisterRequest.class), Mockito.eq(Role.USER))).thenReturn(expectedResponse); //75line

        // Perform the request and verify the response
        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.user_id").value(1L))
                .andExpect(jsonPath("$.username").value(username))
                .andExpect(jsonPath("$.email").value(email))
                .andExpect(jsonPath("$.role").value("USER"))
                .andExpect(jsonPath("$.first_name").value(firstName))
                .andExpect(jsonPath("$.last_name").value(lastName))
                .andExpect(jsonPath("$.access_token").value("abc123"));
    }

    @Test
    public void Register_RegisterFailed_ThrowsExceptionUsernameExists() throws Exception {
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

        String message = "This username already exists";

        when(authenticationService.register(Mockito.any(RegisterRequest.class), Mockito.eq(Role.USER))).thenThrow(new UserAlreadyExistsExceptionUsername(message));

        // Perform the request and verify the response
        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(message));
    }

    @Test
    public void Register_RegisterFailed_ThrowsExceptionEmailExists() throws Exception {
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

        String message = "This email already exists";

        when(authenticationService.register(Mockito.any(RegisterRequest.class), Mockito.eq(Role.USER))).thenThrow(new UserAlreadyExistsExceptionEmail(message));

        // Perform the request and verify the response
        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(message));
    }

    @Test
    public void Authenticate_AuthenticateSuccessful_ReturnsAuthenticationResponse() throws Exception {
        // Arrange
        String username = "test";
        String password = "test";
        String firstName = "testFirst";
        String lastName = "testLast";
        String email = "test@email.com";
        String role = "USER";
        String token = "abc123";

        AuthenticationRequest request = new AuthenticationRequest();
        request.setUsername(username);
        request.setPassword(password);

        AuthenticationResponse expectedResponse = new AuthenticationResponse();
        expectedResponse.setId(1L);
        expectedResponse.setUsername(username);
        expectedResponse.setEmail(email);
        expectedResponse.setRole(Role.USER);
        expectedResponse.setFirstName(firstName);
        expectedResponse.setLastName(lastName);
        expectedResponse.setAccessToken(token);

        when(authenticationService.authenticate(Mockito.any(AuthenticationRequest.class))).thenReturn(expectedResponse);

        // Perform the request and verify the response
        mockMvc.perform(post("/api/v1/auth/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.user_id").value(1L))
                .andExpect(jsonPath("$.username").value(username))
                .andExpect(jsonPath("$.email").value(email))
                .andExpect(jsonPath("$.role").value(role))
                .andExpect(jsonPath("$.first_name").value(firstName))
                .andExpect(jsonPath("$.last_name").value(lastName))
                .andExpect(jsonPath("$.access_token").value(token));
    }

    @Test
    public void Refresh_RefreshSuccessful_ReturnsAuthenticationResponse() throws Exception {
        // Arrange
        String username = "test";
        String password = "test";
        String firstName = "testFirst";
        String lastName = "testLast";
        String email = "test@email.com";
        String role = "USER";
        String token = "abc123";

        RefreshTokenRequest request = new RefreshTokenRequest();
        request.setUserId(1L);
        request.setUsername(username);
        request.setUserEmail(email);

        AuthenticationResponse expectedResponse = new AuthenticationResponse();
        expectedResponse.setId(1L);
        expectedResponse.setUsername(username);
        expectedResponse.setEmail(email);
        expectedResponse.setRole(Role.USER);
        expectedResponse.setFirstName(firstName);
        expectedResponse.setLastName(lastName);
        expectedResponse.setAccessToken(token);

        when(authenticationService.refresh(Mockito.any(RefreshTokenRequest.class))).thenReturn(expectedResponse);

        // Perform the request and verify the response
        mockMvc.perform(post("/api/v1/auth/refresh")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.user_id").value(1L))
                .andExpect(jsonPath("$.username").value(username))
                .andExpect(jsonPath("$.email").value(email))
                .andExpect(jsonPath("$.role").value(role))
                .andExpect(jsonPath("$.first_name").value(firstName))
                .andExpect(jsonPath("$.last_name").value(lastName))
                .andExpect(jsonPath("$.access_token").value(token));
    }

    @Test
    public void PasswordChange_ChangeSuccessful_ReturnsMessageResponse() throws Exception {
        // Arrange
        String oldPass = "test1";
        String newPass = "test2";

        PasswordRequest request = new PasswordRequest();
        request.setOldPassword(oldPass);
        request.setNewPassword(newPass);

        ResponseMessage expectedResponse = new ResponseMessage();
        expectedResponse.setMessage("Password has been changed");

        // Perform the request and verify the response
        mockMvc.perform(post("/api/v1/auth/change-password/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(expectedResponse.getMessage()));
    }
}
