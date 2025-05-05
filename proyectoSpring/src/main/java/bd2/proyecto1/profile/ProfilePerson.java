package bd2.proyecto1.profile;

public class ProfilePerson {
    private String name;
    private String firstName;
    private String secondName;
    private String userName;
    private String country;
    private String birthdate;
    private String email;
    private Long totalFollowers;
    private Long totalFollowing;
    private String profileUrl;
    private Long postAmount;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getTotalFollowers() {
        return totalFollowers;
    }

    public void setTotalFollowers(Long totalFollowers) {
        this.totalFollowers = totalFollowers;
    }

    public Long getTotalFollowing() {
        return totalFollowing;
    }

    public void setTotalFollowing(Long totalFollowing) {
        this.totalFollowing = totalFollowing;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public Long getPostAmount() {
        return postAmount;
    }

    public void setPostAmount(Long postAmount) {
        this.postAmount = postAmount;
    }
}
