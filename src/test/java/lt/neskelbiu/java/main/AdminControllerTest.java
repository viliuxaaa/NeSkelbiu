//package lt.neskelbiu.java.main;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lt.neskelbiu.java.main.config.JwtAuthenticationFilter;
//import lt.neskelbiu.java.main.demo.DemoController;
//import lt.neskelbiu.java.main.user.UserService;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.FilterType;
//import org.springframework.test.web.servlet.MockMvc;
//
//@WebMvcTest(value = DemoController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtAuthenticationFilter.class))
//public class AdminControllerTest {
//
//    @Autowired private MockMvc mockMvc;
//    @Autowired private ObjectMapper objectMapper;
//    @MockBean
//    private UserService userService;
//
//    @Test
//    public void test
//}
