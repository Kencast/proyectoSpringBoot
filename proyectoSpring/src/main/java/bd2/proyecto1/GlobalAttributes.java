package bd2.proyecto1;


import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@Component
@ControllerAdvice
public class GlobalAttributes {

    private Long userId = 0L;

    @ModelAttribute("userId")
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
