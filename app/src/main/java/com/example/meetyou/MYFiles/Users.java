package com.example.meetyou.MYFiles;

public class Users {
    String email, name;
    int age;

    public Users() {
    }

    public Users(String email, String name, int age) {
        this.email = email;
        this.name = name;
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
