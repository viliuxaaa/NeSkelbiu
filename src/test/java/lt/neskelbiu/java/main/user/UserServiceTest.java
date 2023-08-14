package lt.neskelbiu.java.main.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;


@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserServiceTest {

    @Mock
    UserRepository mockUserRepository;

    @InjectMocks
    UserService userService;

    Long id = 1L;
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
                .id(id)
                .username(username)
                .role(role)
                .firstname(firstname)
                .lastname(lastname)
                .email(email)
                .isNotLocked(isNotLocked)
                .build();
    }

    @Test
    void shouldCheckForNullReferenceUserService() {
        Assertions.assertNotNull(userService);
    }

    @Test
    public void GetAllUsers_GetListOfUsers_ReturnsList() {
        //Arrange
        User user = new User();

        when(mockUserRepository.save(Mockito.any(User.class)))
                .thenAnswer(e -> {
                    User savedUser = e.getArgument(0);
                    savedUser.setId(id);
                    savedUser.setUsername(username);
                    savedUser.setPassword(password);
                    savedUser.setFirstname(firstname);
                    savedUser.setLastname(lastname);
                    savedUser.setEmail(email);
                    savedUser.setRole(role);
                    savedUser.setNotLocked(isNotLocked);
                    return savedUser;
                });

        when(mockUserRepository.findAll()).thenReturn(List.of(user));

        //Act
        mockUserRepository.save(user);
        List<User> expectedList = userService.getAllUsers();

        //Assert
        Assertions.assertNotNull(expectedList);
        Assertions.assertEquals(id, expectedList.get(0).getId());
        Assertions.assertEquals(username, expectedList.get(0).getUsername());
        Assertions.assertEquals(password, expectedList.get(0).getPassword());
        Assertions.assertEquals(role, expectedList.get(0).getRole());
        Assertions.assertEquals(firstname, expectedList.get(0).getFirstname());
        Assertions.assertEquals(lastname, expectedList.get(0).getLastname());
        Assertions.assertEquals(email, expectedList.get(0).getEmail());
        Assertions.assertTrue(expectedList.get(0).isNotLocked());
    }

    @Test
    public void GetResponseAllUsers_GetListOfUsersForResponse_ReturnsListUserResponse() {
        //Arrange
        List<User> userList = List.of(user);

        List<UserResponse> expectedUserResponseList = userList.stream()
                .map(user -> UserResponse.builder()
                        .id(user.getId())
                        .username(user.getUsername())
                        .firstname(user.getFirstname())
                        .lastname(user.getLastname())
                        .email(user.getEmail())
                        .role(user.getRole())
                        .build())
                .toList();

        //Act
        List<UserResponse> userResponseList = userService.getResponseAllUsers(userList);

        //Assert
        Assertions.assertEquals(expectedUserResponseList, userResponseList);
    }

    @Test
    public void ChangeUserLockdownState_LocksOrUnlocksAccount_ChangesAccountToLockedState() {
        //Arrange
        User user = new User();
        String expectedMessage = "unlocked";

        when(mockUserRepository.save(Mockito.any(User.class)))
                .thenAnswer(e -> {
                    User savedUser = e.getArgument(0);
                    savedUser.setId(id);
                    savedUser.setUsername(username);
                    savedUser.setPassword(password);
                    savedUser.setFirstname(firstname);
                    savedUser.setLastname(lastname);
                    savedUser.setEmail(email);
                    savedUser.setRole(role);
                    savedUser.setNotLocked(isNotLocked);
                    return savedUser;
                });
        when(mockUserRepository.findById(Mockito.any(Long.class))).thenReturn(Optional.of(user));

        //Act
        mockUserRepository.save(user);
        String message = userService.changeUserLockdownState(id);

        //Assertion
        Assertions.assertEquals(expectedMessage, message);
    }

    @Test
    public void DeleteUser_DeleteSingleUser_DeletesUser() {
        //Arrange
        User user = new User();

        when(mockUserRepository.save(Mockito.any(User.class)))
                .thenAnswer(e -> {
                    User savedUser = e.getArgument(0);
                    savedUser.setId(id);
                    savedUser.setUsername(username);
                    savedUser.setPassword(password);
                    savedUser.setFirstname(firstname);
                    savedUser.setLastname(lastname);
                    savedUser.setEmail(email);
                    savedUser.setRole(role);
                    savedUser.setNotLocked(isNotLocked);
                    return savedUser;
                });
        when(mockUserRepository.findById(Mockito.any(Long.class))).thenReturn(Optional.of(user));

        //Act
        mockUserRepository.save(user);
        userService.deleteUser(id);
    }

    @Test
    public void FindByUsername_FindsUser_FindsUserByItsUsername() {
        //Arrange
        User user = new User();

        when(mockUserRepository.save(Mockito.any(User.class)))
                .thenAnswer(e -> {
                    User savedUser = e.getArgument(0);
                    savedUser.setId(id);
                    savedUser.setUsername(username);
                    savedUser.setPassword(password);
                    savedUser.setFirstname(firstname);
                    savedUser.setLastname(lastname);
                    savedUser.setEmail(email);
                    savedUser.setRole(role);
                    savedUser.setNotLocked(isNotLocked);
                    return savedUser;
                });
        when(mockUserRepository.findById(Mockito.any(Long.class))).thenReturn(Optional.of(user));
        when(mockUserRepository.findByUsername(Mockito.any(String.class))).thenReturn(Optional.of(user));
        //Act
        mockUserRepository.save(user);
        User expectedUser = userService.findByUsername(email);

        //Assertion
        Assertions.assertEquals(expectedUser.getUsername(), username);
    }

    @Test
    public void FindByEmail_FindsUser_FindsUserByItsEmail() {
        //Arrange
        User user = new User();

        when(mockUserRepository.save(Mockito.any(User.class)))
                .thenAnswer(e -> {
                    User savedUser = e.getArgument(0);
                    savedUser.setId(id);
                    savedUser.setUsername(username);
                    savedUser.setPassword(password);
                    savedUser.setFirstname(firstname);
                    savedUser.setLastname(lastname);
                    savedUser.setEmail(email);
                    savedUser.setRole(role);
                    savedUser.setNotLocked(isNotLocked);
                    return savedUser;
                });
        when(mockUserRepository.findById(Mockito.any(Long.class))).thenReturn(Optional.of(user));
        when(mockUserRepository.findByEmail(Mockito.any(String.class))).thenReturn(Optional.of(user));

        //Act
        mockUserRepository.save(user);
        User expectedUser = userService.findByEmail(email);

        //Assertion
        Assertions.assertEquals(expectedUser.getEmail(), email);
    }

    @Test
    public void ChangeName_ChangesFirstOrLastName_ChangeFirstName() {
        //Arrange
        User user = new User();
        String firstname = "test";
        String lastname = "test";

        when(mockUserRepository.save(Mockito.any(User.class)))
                .thenAnswer(e -> {
                    User savedUser = e.getArgument(0);
                    savedUser.setId(id);
                    savedUser.setUsername(username);
                    savedUser.setPassword(password);
                    savedUser.setFirstname(firstname);
                    savedUser.setLastname(lastname);
                    savedUser.setEmail(email);
                    savedUser.setRole(role);
                    savedUser.setNotLocked(isNotLocked);
                    return savedUser;
                });
        when(mockUserRepository.findById(Mockito.any(Long.class))).thenReturn(Optional.of(user));

        //Act
        User expectedUser = userService.changeName(id, new ChangeNameRequest("test", "test"));

        //Assertion
        Assertions.assertEquals(expectedUser.getFirstname(), firstname);
        Assertions.assertEquals(expectedUser.getLastname(), lastname);
    }

    @Test
    public void GetResponseUser_GetUserResponse_ReturnsUserResponse() {
        //Arrange
        UserResponse expectedUserResponse = UserResponse.builder()
                        .id(user.getId())
                        .username(user.getUsername())
                        .firstname(user.getFirstname())
                        .lastname(user.getLastname())
                        .email(user.getEmail())
                        .role(user.getRole())
                        .build();

        //Act
        UserResponse userResponse = userService.getResponseUser(user);

        //Assert
        Assertions.assertEquals(expectedUserResponse, userResponse);
    }
}
