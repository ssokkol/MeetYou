package com.example.meetyou.Messager;

public class Chat {
    String userName;
    String profilePhoto;
    String firstMessage;

    public Chat() {
    }

    public Chat(String userName, String profilePhoto, String firstMessage) {
        this.userName = userName;
        this.profilePhoto = profilePhoto;
        this.firstMessage = firstMessage;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public String getFirstMessage() {
        return firstMessage;
    }

    public void setFirstMessage(String firstMessage) {
        this.firstMessage = firstMessage;
    }
}
