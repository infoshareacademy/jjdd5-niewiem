package com.infoshareacademy.niewiem.halls.dto;

public class HallDTO {
    private Integer id;
    private String name;
    private String imageURL;

    public HallDTO() {
        // empty constructor
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
