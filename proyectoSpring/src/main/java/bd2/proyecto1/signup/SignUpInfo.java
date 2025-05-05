package bd2.proyecto1.signup;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class SignUpInfo {
    @NotEmpty(message = "The name cannot be empty")
    @Size(min = 3, max = 20, message = "The name must be between 3 and 20 characters long")
    private String name;

    @NotEmpty(message = "The first name cannot be empty")
    @Size(min = 3, max = 20, message = "The first name must be between 3 and 20 characters long")
    private String firstName;

    @NotEmpty(message = "The second name cannot be empty")
    @Size(min = 3, max = 20, message = "The second name must be between 3 and 20 characters long")
    private String secondName;

    @NotEmpty(message = "The user name cannot be empty")
    @Size(min = 3, max = 40, message = "The user name must be between 3 and 40 characters long")
    private String userName;

    @NotEmpty(message = "The country cannot be empty")
    private String country;

    @NotEmpty(message = "The email cannot be empty")
    @Email(message = "You must provide a valid email")
    private String email;

    @NotEmpty(message = "The password cannot be empty")
    @Size(min = 8, message = "The password must be at least 8 characters long")
    private String password;

    private String birthdate;

    @JsonIgnore
    private MultipartFile profilePicture;

    private String imageLink;

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public MultipartFile getProfilePicture() {
        return profilePicture;
    }

    public SignUpInfo(String email, String country, String userName,String secondName, String firstName,
                      String name, String imageLink) {
        this.password = null;
        this.email = email;
        this.country = country;
        this.userName = userName;
        this.secondName = secondName;
        this.firstName = firstName;
        this.name = name;
        this.profilePicture = null;
        this.imageLink = imageLink;
    }

    public SignUpInfo(){}

    public void setProfilePicture(MultipartFile profilePicture) {
        this.profilePicture = profilePicture;
    }

    public @NotEmpty(message = "The name cannot be empty") @Size(min = 3, max = 20, message = "The name must be between 3 and 20 characters long") String getName() {
        return name;
    }

    public void setName(
            @NotEmpty(message = "The name cannot be empty") @Size(min = 3, max = 20, message = "The name must be between 3 and 20 characters long") String name) {
        this.name = name;
    }

    public @NotEmpty(message = "The first name cannot be empty") @Size(min = 3, max = 20, message = "The first name must be between 3 and 20 characters long") String getFirstName() {
        return firstName;
    }

    public void setFirstName(
            @NotEmpty(message = "The first name cannot be empty") @Size(min = 3, max = 20, message = "The first name must be between 3 and 20 characters long") String firstName) {
        this.firstName = firstName;
    }

    public @NotEmpty(message = "The second name cannot be empty") @Size(min = 3, max = 20, message = "The second name must be between 3 and 20 characters long") String getSecondName() {
        return secondName;
    }

    public void setSecondName(
            @NotEmpty(message = "The second name cannot be empty") @Size(min = 3, max = 20, message = "The second name must be between 3 and 20 characters long") String secondName) {
        this.secondName = secondName;
    }

    public @NotEmpty(message = "The user name cannot be empty") @Size(min = 3, max = 40, message = "The user name must be between 3 and 40 characters long") String getUserName() {
        return userName;
    }

    public void setUserName(
            @NotEmpty(message = "The user name cannot be empty") @Size(min = 3, max = 40, message = "The user name must be between 3 and 40 characters long") String userName) {
        this.userName = userName;
    }

    public @NotEmpty(message = "The country cannot be empty") String getCountry() {
        return country;
    }

    public void setCountry(@NotEmpty(message = "The country cannot be empty") String country) {
        this.country = country;
    }

    public @NotEmpty(message = "The email cannot be empty") @Email(message = "You must provide a valid email") String getEmail() {
        return email;
    }

    public void setEmail(
            @NotEmpty(message = "The email cannot be empty") @Email(message = "You must provide a valid email") String email) {
        this.email = email;
    }

    public @NotEmpty(message = "The password cannot be empty") @Size(min = 8, message = "The password must be at least 3 characters long") String getPassword() {
        return password;
    }

    public void setPassword(
            @NotEmpty(message = "The password cannot be empty") @Size(min = 8, message = "The password must be at least 3 characters long") String password) {
        this.password = password;
    }

//    public String getBirthdate() {
//        return  birthdate;
//    }
//
//    public void setBirthdate(LocalDate birthdate) {
//        this.birthdate = "" + birthdate.getYear();
//        if (birthdate.getMonthValue() < 10)
//            this.birthdate += "-0" + birthdate.getMonthValue();
//        else
//            this.birthdate += "-" + birthdate.getMonthValue();
//        if (birthdate.getDayOfMonth() < 10)
//            this.birthdate += "-0" + birthdate.getDayOfMonth();
//        else
//            this.birthdate += "-" + birthdate.getDayOfMonth();
//    }
    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }
}
