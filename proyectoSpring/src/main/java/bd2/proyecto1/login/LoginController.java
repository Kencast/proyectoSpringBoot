package bd2.proyecto1.login;

import bd2.proyecto1.GlobalAttributes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import jakarta.validation.Valid;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private GlobalAttributes globalAttributes;
    @Autowired
    private RestTemplate restTemplate;
    @Value("${app.urlDatabase}")
    private String apiUrl;

    @GetMapping()
    public String login(Model model) {
        model.addAttribute("error", false);
        model.addAttribute("login", new LoginInfo());
        return "Login";
    }

    public boolean validLogin(LoginInfo loginInfo, Model model) {
        try {
            ResponseEntity<Long> res = restTemplate.postForEntity(apiUrl + "user/login", loginInfo, Long.class);
            globalAttributes.setUserId(res.getBody());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @PostMapping()
    public String login(@Valid @ModelAttribute("login") LoginInfo loginInfo,
            BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors())
            return "Login";
        if (validLogin(loginInfo, model) == false) {
            model.addAttribute("error", true);
            return "Login";
        }
        return "redirect:/home";
    }
}
