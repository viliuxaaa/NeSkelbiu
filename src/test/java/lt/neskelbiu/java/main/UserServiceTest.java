//package lt.neskelbiu.java.main;
//
//import lt.neskelbiu.java.main.config.JwtAuthenticationFilter;
//import lt.neskelbiu.java.main.demo.DemoController;
//import lt.neskelbiu.java.main.exceptions.UserNotFoundException;
//import lt.neskelbiu.java.main.user.Role;
//import lt.neskelbiu.java.main.user.User;
//import lt.neskelbiu.java.main.user.UserRepository;
//import lt.neskelbiu.java.main.user.UserService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.FilterType;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.assertj.core.api.Assertions.assertThatThrownBy;
//import static org.mockito.Mockito.*;
//
//@WebMvcTest(value = DemoController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtAuthenticationFilter.class))
//class UserServiceTest {
//
//    @Mock
//    private UserRepository userRepository;
//
//    @InjectMocks
//    private UserService userService;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void testGetAllUsers() {
//        // Given
//        List<User> userList = new ArrayList<>();
//        userList.add(new User(1L, "testuser1", "John", "Doe", "john@example.com", Role.USER, true));
//        userList.add(new User(2L, "testuser2", "Jane", "Smith", "jane@example.com", Role.ADMIN, false));
//
//        when(userRepository.findAll()).thenReturn(userList);
//
//
//        // When
//        List<User> result = userService.getAllUsers();
//
//        // Then
//        assertThat(result).hasSize(2);
//        assertThat(result.get(0).getUsername()).isEqualTo("testuser1");
//        assertThat(result.get(1).getUsername()).isEqualTo("testuser2");
//    }
//
//    @Test
//    void testChangeUserLockdownState_UserFound() {
//        // Given
//        Long userId = 1L;
//        User user = new User(userId, "testuser", "John", "Doe", "john@example.com", Role.USER, true);
//        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
//        when(userRepository.save(user)).thenReturn(user);
//
//        // When
//        String result = userService.changeUserLockdownState(userId);
//
//        // Then
//        assertThat(result).isEqualTo("locked");
//        assertThat(user.isNotLocked()).isFalse();
//    }
//
//    @Test
//    void testChangeUserLockdownState_UserNotFound() {
//        // Given
//        Long userId = 1L;
//        when(userRepository.findById(userId)).thenReturn(Optional.empty());
//
//        // When & Then
//        assertThatThrownBy(() -> userService.changeUserLockdownState(userId))
//                .isInstanceOf(UserNotFoundException.class)
//                .hasMessageContaining("User not found by id:" + userId);
//    }
//
//    @Test
//    void testDeleteUser_UserFound() {
//        // Given
//        Long userId = 1L;
//        when(userRepository.existsById(userId)).thenReturn(true);
//
//        // When
//        userService.deleteUser(userId);
//
//        // Then
//        verify(userRepository, times(1)).deleteById(userId);
//    }
//
//    @Test
//    void testDeleteUser_UserNotFound() {
//        // Given
//        Long userId = 1L;
//        when(userRepository.existsById(userId)).thenReturn(false);
//
//        // When & Then
//        assertThatThrownBy(() -> userService.deleteUser(userId))
//                .isInstanceOf(UserNotFoundException.class)
//                .hasMessageContaining("User not found by id:" + userId);
//    }
//
//    // Similar tests can be written for findByUsername and findByEmail methods.
//
//}
