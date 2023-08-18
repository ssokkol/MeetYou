package com.example.meetyou.UserLikedBy;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LikedUsersManager {

    public interface LikedUsersCallback {
        void onLikedUsersLoaded(String[] userUids);
    }

    public static void loadLikedUsers(String currentUserUid, LikedUsersCallback callback) {
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("Users");
        DatabaseReference currentUserRef = usersRef.child(currentUserUid);

        currentUserRef.child("likedUsers").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    int likedUsersCount = (int) dataSnapshot.getChildrenCount();
                    String[] likedUserUids = new String[likedUsersCount];
                    int index = 0;

                    for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                        String likedUserUid = childSnapshot.getKey();
                        likedUserUids[index] = likedUserUid;
                        index++;
                    }

                    callback.onLikedUsersLoaded(likedUserUids);
                } else {
                    callback.onLikedUsersLoaded(new String[0]);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}
