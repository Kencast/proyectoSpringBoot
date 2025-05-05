package bd2.proyecto1.login;

import jakarta.validation.constraints.*;

public class LoginInfo {
    @NotEmpty(message = "The email cannot be empty")
    @Email(message = "You must provide a valid email")
    private String email;

    @NotEmpty(message = "The password cannot be empty")
    @Size(min = 3, message = "The password must be between 3 and 20 characters long")
    private String password;

    public LoginInfo() {
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
