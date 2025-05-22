package bd2.proyecto1.login;

import bd2.proyecto1.GlobalAttributes;
import bd2.proyecto1.signup.SignUpInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LoginControllerTest {

    @InjectMocks
    private LoginController loginController;

    @Mock
    private GlobalAttributes globalAttributes;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private Model model;

    private String apiUrl = "http://localhost:8080";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        loginController.apiUrl = apiUrl;
    }

    @Test
    void validLoginSuccess() {
        LoginInfo loginInfo = new LoginInfo();
        ResponseEntity<Long> response = ResponseEntity.ok(1L);

        when(restTemplate.postForEntity(
                eq(apiUrl + "user/login"),
                any(LoginInfo.class),
                eq(Long.class))
        ).thenReturn(response);

        boolean result = loginController.validLogin(loginInfo, model);

        assertTrue(result);
    }

    @Test
    void validLoginFails() {
        LoginInfo loginInfo = new LoginInfo();

        when(restTemplate.postForEntity(
                eq(apiUrl + "user/login"),
                any(LoginInfo.class),
                eq(Long.class))
        ).thenThrow(new RuntimeException("Auth failed"));

        boolean result = loginController.validLogin(loginInfo, model);

        assertFalse(result);
    }

    @Test
    void returnsLoginIfValidationFails() {
        LoginInfo loginInfo = new LoginInfo();
        BindingResult bindingResult = mock(BindingResult.class);

        when(bindingResult.hasErrors()).thenReturn(true);

        String view = loginController.login(loginInfo, bindingResult, model);

        assertEquals("Login", view);
    }

    @Test
    void returnsLoginIfLoginFails() {
        LoginInfo loginInfo = new LoginInfo();
        BindingResult bindingResult = mock(BindingResult.class);

        when(bindingResult.hasErrors()).thenReturn(false);

        LoginController spyController = Mockito.spy(loginController);
        doReturn(false).when(spyController).validLogin(eq(loginInfo), any());

        String view = spyController.login(loginInfo, bindingResult, model);

        assertEquals("Login", view);
        verify(model).addAttribute("error", true);
    }

    @Test
    void redirectIfLoginSuccesfull() {
        LoginInfo loginInfo = new LoginInfo();
        BindingResult bindingResult = mock(BindingResult.class);

        when(bindingResult.hasErrors()).thenReturn(false);

        LoginController spyController = Mockito.spy(loginController);
        doReturn(true).when(spyController).validLogin(eq(loginInfo), any());

        String view = spyController.login(loginInfo, bindingResult, model);

        assertEquals("redirect:/home", view);
    }

    @Test
    void testLoginAddAtributesAndReturnView(){
        String viewName = loginController.login(model);
        verify(model).addAttribute("error", false);
        verify(model).addAttribute(eq("login"), any(LoginInfo.class));
        assertEquals("Login",viewName);
    }

}