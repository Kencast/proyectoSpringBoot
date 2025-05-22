package bd2.proyecto1.signup;

import bd2.proyecto1.GlobalAttributes;
import bd2.proyecto1.login.LoginController;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SignUpControllerTest {

    @InjectMocks
    private SignUpController signUpController;

    @Mock
    private GlobalAttributes globalAttributes;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private Model model;

    private String apiUrl = "http://localhost:8080";

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        signUpController.apiUrl = apiUrl;
    }

    @Test
    void testSignUpAddsAttributesAndReturnsView() {
        String viewName = signUpController.signUp(model);
        verify(model).addAttribute("error", false);
        verify(model).addAttribute(eq("register"), any(SignUpInfo.class));
        assertEquals("SignUp", viewName);
    }

    @Test
    void validSignUpDataSuccess(){
        SignUpInfo signUpInfo = new SignUpInfo("pedro@gmail.com","Bolivia",
                "Pedro403","Fernandez","Garcia",
                "Pedro","https://s1.qwant.com/thumbr/474x237/a/9/42a2ec2c559479ce86d86066198fdda6ad666572f754d54428550694375d3b/th.jpg?u=https%3A%2F%2Ftse.mm.bing.net%2Fth%3Fid%3DOIP.Fpo7kzvOV_W1BpSZ_xjb3QHaDt%26pid%3DApi&q=0&b=1&p=0&a=0");
        ResponseEntity<Long> response = ResponseEntity.ok(1L);
        when(restTemplate.postForEntity(eq(apiUrl+"user/create"),any(SignUpInfo.class),eq(Long.class)))
                .thenReturn(response);
        boolean answer = signUpController.isValidData(signUpInfo);
        assertTrue(answer);
    }

    @Test
    void validSignUpDataFail(){
        SignUpInfo signUpInfo = new SignUpInfo("pedro@gmail.com","Bolivia",
                "Pedro403","Fernandez","Garcia",
                "Pedro","https://s1.qwant.com/thumbr/474x237/a/9/42a2ec2c559479ce86d86066198fdda6ad666572f754d54428550694375d3b/th.jpg?u=https%3A%2F%2Ftse.mm.bing.net%2Fth%3Fid%3DOIP.Fpo7kzvOV_W1BpSZ_xjb3QHaDt%26pid%3DApi&q=0&b=1&p=0&a=0");
        ResponseEntity<Long> response = ResponseEntity.ok(1L);
        when(restTemplate.postForEntity(eq(apiUrl+"user/create"),any(SignUpInfo.class),eq(Long.class)))
                .thenThrow(new RuntimeException("Auth failed"));
        boolean answer = signUpController.isValidData(signUpInfo);
        assertFalse(answer);
    }

    @Test
    void returnSignUpIfBindingErrors() {
        SignUpInfo signUpInfo = new SignUpInfo();
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(true);
        String view = signUpController.register(signUpInfo, bindingResult, model);
        assertEquals("SignUp", view);
    }

    @Test
    void returnSignUpIfNotValidData() {
        SignUpInfo signUpInfo = new SignUpInfo();
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(false);
        SignUpController spyController = Mockito.spy(signUpController);
        doReturn(false).when(spyController).isValidData(eq(signUpInfo));
        String view = spyController.register(signUpInfo, bindingResult, model);
        verify(model).addAttribute("error", true);
        verify(model).addAttribute(eq("register"), any(SignUpInfo.class));
        assertEquals("SignUp", view);
    }

    @Test
    void SuccessSignUp(){
        SignUpInfo signUpInfo = new SignUpInfo();
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(false);
        SignUpController spyController = Mockito.spy(signUpController);
        doReturn(true).when(spyController).isValidData(eq(signUpInfo));
        String view = spyController.register(signUpInfo, bindingResult, model);
        assertEquals("redirect:/home", view);
    }
}
