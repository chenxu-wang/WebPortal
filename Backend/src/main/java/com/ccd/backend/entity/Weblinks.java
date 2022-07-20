package com.ccd.backend.entity;

public class Weblinks {

    private Integer id;
    private String link;
    private String title;
    private String description;
    private String category;

    public Weblinks(Integer id, String link, String title) {
        this.id = id;
        this.link = link;
        this.title = title;
    }

    public Weblinks(Integer id, String link, String title, String description, String category) {
        this.id = id;
        this.link = link;
        this.title = title;
        this.description = description;
        this.category = category;
    }
    public Weblinks(String link, String title, String description, String category) {
        this.link = link;
        this.title = title;
        this.description = description;
        this.category = category;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

}
