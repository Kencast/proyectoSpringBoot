package com.example.demo.tag;

public class TagPopularityDTO {
    private int popularity;
    private String tagName;

    public TagPopularityDTO(String tagName, int popularity) {
        this.popularity = popularity;
        this.tagName = tagName;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
}
