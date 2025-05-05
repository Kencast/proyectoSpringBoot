package bd2.proyecto1.signup;

import bd2.proyecto1.GlobalAttributes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/signup")
public class SignUpController {

    @Autowired
    private GlobalAttributes globalAttributes;
    @Autowired
    private RestTemplate restTemplate;
    @Value("${app.urlDatabase}")
    private String apiUrl;

    @GetMapping()
    public String signUp(Model model) {
        model.addAttribute("error", false);
        model.addAttribute("register", new SignUpInfo());
        return "SignUp";
    }

    public boolean isValidData(SignUpInfo signUpInfo) {
        try {
            ResponseEntity<Long> res = restTemplate.postForEntity(apiUrl + "user/create", signUpInfo, Long.class);
            globalAttributes.setUserId(res.getBody());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @PostMapping()
    public String register(@Valid @ModelAttribute("register") SignUpInfo signUpInfo,
            BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()){
            return "SignUp";
        }
        System.out.println("linea 49");
        if (!isValidData(signUpInfo)) {
            model.addAttribute("error", true);
            model.addAttribute("register", new SignUpInfo());
            return "SignUp";
        }
        return "redirect:/home";
    }
}