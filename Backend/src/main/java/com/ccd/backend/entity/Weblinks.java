package com.ccd.backend.entity;

public class Weblinks {


    public Weblinks(Integer id, String link, String title) {
        this.id = id;
        this.link = link;
        this.title = title;
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
    private Integer id;
    private String link;
    private String title;
}