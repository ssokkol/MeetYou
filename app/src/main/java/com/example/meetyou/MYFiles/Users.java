package com.example.meetyou.MYFiles;

import android.content.SharedPreferences;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Users {
    String email, name, bio, height, width, gender, findGender, hobbies, target;
    int age;

    public Users() {
    }

    public Users(String name, String bio, String height, String width, String gender, String findGender, String hobbies, String target, int age) {
        this.name = name;
        this.bio = bio;
        this.height = height;
        this.width = width;
        this.gender = gender;
        this.findGender = findGender;
        this.hobbies = hobbies;
        this.target = target;
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

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFindGender() {
        return findGender;
    }

    public void setFindGender(String findGender) {
        this.findGender = findGender;
    }

    public String getHobbies() {
        return hobbies;
    }

    public void setHobbies(String hobbies) {
        this.hobbies = hobbies;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public static void updateUserName(String UID, String newName) {
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users").child(UID).child("name");
        userRef.setValue(newName);
    }

    public static void updateUserBio(String UID, String newBio) {
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users").child(UID).child("bio");
        userRef.setValue(newBio);
    }

    public void updateUserHeight(String UID, String newHeight) {
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users").child(UID).child("height");
        userRef.setValue(newHeight);
    }

    public void updateUserWidth(String UID, String newWidth) {
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users").child(UID).child("width");
        userRef.setValue(newWidth);
    }

    public static void updateUserGender(String UID, String newGender) {
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users").child(UID).child("gender");
        userRef.setValue(newGender);
    }

    public static void updateUserFindGender(String UID, String newFindGender) {
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users").child(UID).child("findGender");
        userRef.setValue(newFindGender);
    }

    public static void updateUserHobbies(String UID, String newHobbies) {
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users").child(UID).child("hobbies");
        userRef.setValue(newHobbies);
    }

    public static void updateUserTarget(String UID, String newTarget) {
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users").child(UID).child("target");
        userRef.setValue(newTarget);
    }

    public static void updateUserAge(String UID, int newAge) {
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users").child(UID).child("age");
        userRef.setValue(newAge);
    }
}
