package com.example.meetyou.Messager;

public class ChatItem {
    private String profilePhoto1, profilePhoto2;
    private String name1, name2;
    private String message1, message2;

    protected String chatUID;

    public ChatItem(String chatUID, String profilePhoto1, String profilePhoto2, String name1, String name2, String message1, String message2) {
        this.chatUID = chatUID;
        this.profilePhoto1 = profilePhoto1;
        this.profilePhoto2 = profilePhoto2;
        this.name1 = name1;
        this.name2 = name2;
        this.message1 = message1;
        this.message2 = message2;
    }

    public String getChatUID() {
        return chatUID;
    }

    public void setChatUID(String chatUID) {
        this.chatUID = chatUID;
    }

    public String getProfilePhoto1() {
        return profilePhoto1;
    }

    public void setProfilePhoto1(String profilePhoto1) {
        this.profilePhoto1 = profilePhoto1;
    }

    public String getProfilePhoto2() {
        return profilePhoto2;
    }

    public void setProfilePhoto2(String profilePhoto2) {
        this.profilePhoto2 = profilePhoto2;
    }

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public String getName2() {
        return name2;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }

    public String getMessage1() {
        return message1;
    }

    public void setMessage1(String message1) {
        this.message1 = message1;
    }

    public String getMessage2() {
        return message2;
    }

    public void setMessage2(String message2) {
        this.message2 = message2;
    }
}
