package bd2.proyecto1.home;

import java.util.List;

public class PostInfoDTO {
    private Long postId;
    private Long userId;
    private String postTitle;
    private String userName;
    private String imageLink;
    private Long totalComments;
    private List<String> tags;

    public PostInfoDTO(Long postId, String postTitle, Long userId, String userName, String imageLink, Long totalComments) {
        this.postId = postId;
        this.userId = userId;
        this.postTitle = postTitle;
        this.userName = userName;
        this.imageLink = imageLink;
        this.totalComments = totalComments;
        tags=null;
    }

    // Getters
    public Long getPostId() {
        return postId;
    }

    public Long getUserId() {
        return userId;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public String getUserName() {
        return userName;
    }

    public String getImageLink() {
        return imageLink;
    }

    public Long getTotalComments() {
        return totalComments;
    }

    // Setters
    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public void setTotalComments(Long totalComments) {
        this.totalComments = totalComments;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<String> getTags() {
        return tags;
    }
}

