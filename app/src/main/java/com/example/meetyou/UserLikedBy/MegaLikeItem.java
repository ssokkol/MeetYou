package com.example.meetyou.UserLikedBy;

public class MegaLikeItem {
    private String name;
    private String photoName;

    public MegaLikeItem(String name, String photoName) {
        this.name = name;
        this.photoName = photoName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhotoName() {
        return photoName;
    }

    public void setPhotoName(String photoName) {
        this.photoName = photoName;
    }
}
