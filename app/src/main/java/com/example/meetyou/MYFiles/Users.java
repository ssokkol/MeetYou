package com.example.meetyou.MYFiles;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.example.meetyou.Messenger.ChatItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Users {
    String UID, email, name, bio, height, weight, gender, findGender, findHeight, findWeight, hobbies, target, color, status, photo1, photo2, photo3, photo4, photo5;
    int age, boosts, likes, megasymps;

    boolean verified;

    public Users() {
    }

    public Users(String UID, String name, String bio, String height, String weight, String gender, String findGender, String findHeight, String findWeight, String hobbies, String target,
                 String color, String status,
                 String photo1, String photo2, String photo3, String photo4, String photo5, int age, int boosts, int likes, int megasymps, boolean verified)
    {
        this.UID = UID;
        this.name = name;
        this.bio = bio;
        this.height = height;
        this.weight = weight;
        this.gender = gender;
        this.findGender = findGender;
        this.findHeight = findHeight;
        this.findWeight = findWeight;
        this.hobbies = hobbies;
        this.target = target;
        this.color = color;
        this.status = status;
        this.photo1 = photo1;
        this.photo2 = photo2;
        this.photo3 = photo3;
        this.photo4 = photo4;
        this.photo5 = this.photo5;
        this.age = age;
        this.boosts = boosts;
        this.likes = likes;
        this.megasymps = megasymps;
        this.verified = verified;
    }

    public String getFindHeight() {
        return findHeight;
    }

    public void setFindHeight(String findHeight) {
        this.findHeight = findHeight;
    }

    public String getFindWeight() {
        return findWeight;
    }

    public void setFindWeight(String findWeight) {
        this.findWeight = findWeight;
    }

    public String getPhoto1() {
        return photo1;
    }

    public void setPhoto1(String photo1) {
        this.photo1 = photo1;
    }

    public String getPhoto2() {
        return photo2;
    }

    public void setPhoto2(String photo2) {
        this.photo2 = photo2;
    }

    public String getPhoto3() {
        return photo3;
    }

    public void setPhoto3(String photo3) {
        this.photo3 = photo3;
    }

    public String getPhoto4() {
        return photo4;
    }

    public void setPhoto4(String photo4) {
        this.photo4 = photo4;
    }

    public String getPhoto5() {
        return photo5;
    }

    public void setPhoto5(String photo5) {
        this.photo5 = photo5;
    }

    public int getBoosts() {
        return boosts;
    }

    public void setBoosts(int boosts) {
        this.boosts = boosts;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getMegasymps() {
        return megasymps;
    }

    public void setMegasymps(int megasymps) {
        this.megasymps = megasymps;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
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

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
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

    public static void addLikedUser(String currentUserUID, String likedUserUID) {
        DatabaseReference likedRef = FirebaseDatabase.getInstance().getReference("Liked").child(currentUserUID);
        likedRef.child(likedUserUID).setValue(true);
    }

    public static void updateUID(String UID){
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users").child(UID).child("UID");
        userRef.setValue(UID);
    }

    public static void updateUserName(String UID, String newName) {
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users").child(UID).child("name");
        userRef.setValue(newName);
    }

    public static void updateUserBio(String UID, String newBio) {
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users").child(UID).child("bio");
        userRef.setValue(newBio);
    }

    public static void updateUserHeight(String UID, String newHeight) {
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users").child(UID).child("height");
        userRef.setValue(newHeight);
    }

    public static void updateUserWeight(String UID, String newWeight) {
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users").child(UID).child("weight");
        userRef.setValue(newWeight);
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

    public static void updateUserColor(String UID, String newColor) {
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users").child(UID).child("color");
        userRef.setValue(newColor);
    }

    public static void updateUserStatus(String UID, String newStatus) {
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users").child(UID).child("status");
        userRef.setValue(newStatus);
    }

    public static void updateUserAge(String UID, int newAge) {
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users").child(UID).child("age");
        userRef.setValue(newAge);
    }

    public static void updateVerification(String UID, boolean newVerification){
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users").child(UID).child("verified");
        userRef.setValue(newVerification);
    }


    public static void updateUserPhoto(String UID, String photoName,String photoURL){
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users").child(UID).child(photoName);
        userRef.setValue(photoURL);
    }


    public static void updateUserFindHeight(String UID, String newFindHeight) {
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users").child(UID).child("findHeight");
        userRef.setValue(newFindHeight);
    }

    public static void updateUserFindWeight(String UID, String newFindWeight) {
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users").child(UID).child("findWeight");
        userRef.setValue(newFindWeight);
    }

    public static void getUserDataFromFirebase(String UID, final OnUserDataListener listener) {
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users").child(UID);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Users user = dataSnapshot.getValue(Users.class);
                if (user != null) {
                    listener.onDataLoaded(user);
                } else {
                    listener.onDataNotAvailable();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onDataNotAvailable();
            }
        });
    }

    public interface OnUserDataListener {

        void onDataLoaded(String color, String userName, String userBio, String photo1, String photo2, String photo3, String photo4,String photo5, String UID, int Age);

        void onDataLoaded(Users user);
        void onDataNotAvailable();
    }

    public static void saveUserDataToSharedPreferences(Context context, String UID) {
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users").child(UID);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Users user = dataSnapshot.getValue(Users.class);

                if (user != null) {
                    SharedPreferences sharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("name", user.getName());
                    editor.putString("bio", user.getBio());
                    editor.putString("height", user.getHeight());
                    editor.putString("weight", user.getWeight());
                    editor.putString("gender", user.getGender());
                    editor.putString("findGender", user.getFindGender());
                    editor.putString("findHeight", user.getFindHeight());
                    editor.putString("findWeight", user.getFindWeight());
                    editor.putString("hobbies", user.getHobbies());
                    editor.putString("target", user.getTarget());
                    editor.putString("color", user.getColor());
                    editor.putString("status", user.getStatus());
                    editor.putString("photo1", user.getPhoto1());
                    editor.putString("photo2", user.getPhoto2());
                    editor.putString("photo3", user.getPhoto3());
                    editor.putString("photo4", user.getPhoto4());
                    editor.putString("photo5", user.getPhoto5());
                    editor.putInt("age", user.getAge());
                    editor.putInt("boosts", user.getBoosts());
                    editor.putInt("likes", user.getLikes());
                    editor.putInt("megasymps", user.getMegasymps());
                    editor.putBoolean("verified", user.isVerified());
                    editor.apply();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }


    public static Users getUserDataFromSharedPreferences(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        String UID = sharedPreferences.getString("UID", "");
        String name = sharedPreferences.getString("name", "");
        String bio = sharedPreferences.getString("bio", "");
        String height = sharedPreferences.getString("height", "");
        String weight = sharedPreferences.getString("weight", "");
        String gender = sharedPreferences.getString("gender", "");
        String findGender = sharedPreferences.getString("findGender", "");
        String findHeight = sharedPreferences.getString("findHeight", "");
        String findWeight = sharedPreferences.getString("findWeight", "");
        String hobbies = sharedPreferences.getString("hobbies", "");
        String target = sharedPreferences.getString("target", "");
        String color = sharedPreferences.getString("color", "");
        String status = sharedPreferences.getString("status", "");
        String photo1 = sharedPreferences.getString("photo1", "");
        String photo2 = sharedPreferences.getString("photo2", "");
        String photo3 = sharedPreferences.getString("photo3", "");
        String photo4 = sharedPreferences.getString("photo4", "");
        String photo5 = sharedPreferences.getString("photo5", "");
        int age = sharedPreferences.getInt("age", 0);
        int boosts = sharedPreferences.getInt("boosts", 0);
        int likes = sharedPreferences.getInt("likes", 0);
        int megasymps = sharedPreferences.getInt("megasymps", 0);
        boolean verified = sharedPreferences.getBoolean("verified", false);

        return new Users(UID, name, bio, height, weight, gender, findGender, findHeight, findWeight, hobbies, target, color, status, photo1, photo2, photo3, photo4, photo5, age, boosts, likes, megasymps, verified);
    }

    private static String getRandomGender() {
        Random random = new Random();
        int randomValue = random.nextInt(2);

        if (randomValue == 0) {
            return "male";
        } else {
            return "female";
        }
    }

    public static void getRandomUserFromPool(Context context, float minAge, float maxAge, String gender, String findGender, String findHeight, String findWeight, String targetUID, final OnUserDataListener listener) {
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("Users");
        Query query;

        if (findGender.equals("any")) {
            query = usersRef;
        } else {
            String genderToQuery = findGender.equals("random") ? getRandomGender() : findGender;
            query = usersRef.orderByChild("gender").equalTo(genderToQuery);
        }

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Users> matchingUsers = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Users user = snapshot.getValue(Users.class);
                    if (user != null && user.getHeight().equals(findHeight) && user.getWeight().equals(findWeight) && user.getAge() >= minAge && user.getAge() <= maxAge) {
                        matchingUsers.add(user);
                    }
                }

                if (!matchingUsers.isEmpty()) {
                    Users randomUser = null;
                    do {
                        int randomIndex = new Random().nextInt(matchingUsers.size());
                        randomUser = matchingUsers.get(randomIndex);
                    } while (randomUser != null && randomUser.getUID().equals(targetUID));

                    if (randomUser != null) {
                        listener.onDataLoaded(randomUser.getColor(), randomUser.getName(), randomUser.getBio(),
                                randomUser.getPhoto1(), randomUser.getPhoto2(), randomUser.getPhoto3(), randomUser.getPhoto4(), randomUser.getPhoto5(), randomUser.getUID(), randomUser.getAge());
                    } else {
                        listener.onDataNotAvailable();
                        NotificationHelper.showCustomNotification(context, null, "According to your parameters, no users were found", null, 0,0,0,0);
                    }
                } else {
                    listener.onDataNotAvailable();
                    NotificationHelper.showCustomNotification(context, null, "According to your parameters, no users were found", null, 0,0,0,0);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onDataNotAvailable();
            }
        });
    }



    //CHATTING с коментариями для эмирки, хотя, я надеюсь, что он сюды суваться не будет
    public static void updateUserLikes(String currentUserPhoto, String likedUserPhoto,String currentUserUID, String likedUserUID, Context context) {
        String chatName = currentUserUID + "_" + likedUserUID;
        DatabaseReference currentUserRef = FirebaseDatabase.getInstance().getReference("Users").child(currentUserUID);
        DatabaseReference likedUserRef = FirebaseDatabase.getInstance().getReference("Users").child(likedUserUID);

        // Проверяем, был ли пользователь уже лайкнут
        currentUserRef.child("likedUsers").child(likedUserUID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Пользователь уже был лайкнут, ничего не делаем
                    return;
                }
                likedUserRef.child("likesCount").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Integer currentLikesCount = snapshot.getValue(Integer.class);
                        if (currentLikesCount != null) {
                            int newLikesCount = currentLikesCount + 1;
                            likedUserRef.child("likesCount").setValue(newLikesCount);
                        } else{
                            likedUserRef.child("likesCount").setValue(1);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });

                // Если пользователь еще не лайкал этого человека, добавляем лайк
                currentUserRef.child("likedUsers").child(likedUserUID).setValue(true);

                likedUserRef.child("likedUsers").child(currentUserUID).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot likedUserSnapshot) {
                        if (likedUserSnapshot.exists()) {
                            likedUserRef.child("userToken").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (snapshot.exists()) {
                                        String likedUserToken = snapshot.getValue(String.class);
                                        sendLikeNotification(likedUserToken, "Name");
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                            // Оба пользователей лайкнули друг друга, проверяем наличие чата
                            DatabaseReference chatCheckRef = FirebaseDatabase.getInstance().getReference("Chats");

                            chatCheckRef.orderByChild(currentUserUID + "/" + likedUserUID).equalTo(true).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot chatSnapshot) {
                                    if (!chatSnapshot.exists()) {
                                        // Чата еще нет, создаем чат
                                        DatabaseReference chatRef = FirebaseDatabase.getInstance().getReference("Chats").child(chatName);
                                        String chatUID = chatRef.getKey();

                                        ChatItem currentUserChat = new ChatItem(chatName,currentUserPhoto, likedUserPhoto, currentUserUID, likedUserUID, "Hello there!", "Hello there!");
//                                        ChatItem likedUserChat = new ChatItem(R.drawable.photo_1, currentUserUID, null);
                                        chatRef.setValue(currentUserChat);
//                                        chatRef.child(likedUserUID).setValue(likedUserChat);

                                        NotificationHelper.showMatchNotification(context, chatName,null, null, 0, 0, 0, 0);
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    // Обработка ошибок, если не удалось проверить наличие чата
                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Обработка ошибок, если не удалось получить информацию о пользователях
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Обработка ошибок, если не удалось проверить наличие лайка
            }
        });
    }



    public static void updateUserMegasymps(String currentUserPhoto, String likedUserPhoto,String currentUserUID, String sympedUserUID, Context context) {
        String chatName = currentUserUID + "_" + sympedUserUID;
        DatabaseReference currentUserRef = FirebaseDatabase.getInstance().getReference("Users").child(currentUserUID);
        DatabaseReference likedUserRef = FirebaseDatabase.getInstance().getReference("Users").child(sympedUserUID);

        // Проверяем, был ли пользователь уже лайкнут
        currentUserRef.child("sympedUsers").child(sympedUserUID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Пользователь уже был лайкнут, ничего не делаем
                    return;
                }
                likedUserRef.child("sympsCount").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Integer currentLikesCount = snapshot.getValue(Integer.class);
                        if (currentLikesCount != null) {
                            int newLikesCount = currentLikesCount + 1;
                            likedUserRef.child("sympsCount").setValue(newLikesCount);
                        } else{
                            likedUserRef.child("sympsCount").setValue(1);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });

                // Если пользователь еще не лайкал этого человека, добавляем лайк
                currentUserRef.child("sympedUsers").child(sympedUserUID).setValue(true);

                likedUserRef.child("likedUsers").child(currentUserUID).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot likedUserSnapshot) {
                        if (likedUserSnapshot.exists()) {
                            // Оба пользователей лайкнули друг друга, проверяем наличие чата
                            DatabaseReference chatCheckRef = FirebaseDatabase.getInstance().getReference("Chats");

                            chatCheckRef.orderByChild(currentUserUID + "/" + sympedUserUID).equalTo(true).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot chatSnapshot) {
                                    if (!chatSnapshot.exists()) {
                                        // Чата еще нет, создаем чат
                                        DatabaseReference chatRef = FirebaseDatabase.getInstance().getReference("Chats").child(chatName);

                                        ChatItem currentUserChat = new ChatItem(chatName,currentUserPhoto, likedUserPhoto, currentUserUID, sympedUserUID, "Hello there!", "Hello there!");
                                        chatRef.setValue(currentUserChat);

                                        NotificationHelper.showMatchNotification(context, chatName,null, null, 0, 0, 0, 0);
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    // Обработка ошибок, если не удалось проверить наличие чата
                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Обработка ошибок, если не удалось получить информацию о пользователях
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Обработка ошибок, если не удалось проверить наличие лайка
            }
        });
    }

    public static void sendLikeNotification(String likedUserToken, String currentUserDisplayName) {
        JSONObject notification = new JSONObject();
        JSONObject notificationBody = new JSONObject();

        try {
            notificationBody.put("title", "New Like!");
            notificationBody.put("body", currentUserDisplayName + " liked you back!");
            notification.put("to", likedUserToken);
            notification.put("data", notificationBody);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        FirebaseMessaging.getInstance().send(new RemoteMessage.Builder("YOUR_SENDER_ID@gcm.googleapis.com")
                .setMessageId(Integer.toString(0))
                .addData("notification", notification.toString())
                .build());
    }
}
