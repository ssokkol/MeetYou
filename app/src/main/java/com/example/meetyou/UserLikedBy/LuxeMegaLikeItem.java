package com.example.meetyou.UserLikedBy;

public class LuxeMegaLikeItem {
    private String name;
    private String photoName;
    private String message;

    public LuxeMegaLikeItem(String name, String photoName, String message) {
        this.name = name;
        this.photoName = photoName;
        this.message = message;
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

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message=message;
    }
}
