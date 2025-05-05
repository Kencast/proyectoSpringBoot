package bd2.proyecto1.profile;

public class UserSimple {
    private Long id;
    private String userName;
    private String profileUrl;
    private Long totalPosts;

    public UserSimple(Long id, String userName, String profileUrl, Long totalPosts) {
        this.id = id;
        this.userName = userName;
        this.profileUrl = profileUrl;
        this.totalPosts = totalPosts;
    }

    public UserSimple() {

    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setUserName(String userName){
        this.userName = userName;
    }

    public void setProfileUrl(String profileUrl){
        this.profileUrl = profileUrl;
    }

    public String getUserName(){
        return userName;
    }

    public String getProfileUrl(){
        return profileUrl;
    }

    public Long getTotalPosts(){
        return totalPosts;
    }
    public void setTotalPosts(Long totalPosts){
        this.totalPosts = totalPosts;
    }
}
