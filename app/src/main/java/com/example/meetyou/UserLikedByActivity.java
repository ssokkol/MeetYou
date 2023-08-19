package com.example.meetyou;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TableLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.meetyou.Messenger.MessengerActivity;
import com.example.meetyou.UserLikedBy.NonMegaLikeAdapter;
import com.example.meetyou.UserLikedBy.NonMegaLikeItem;
import com.example.meetyou.databinding.ActivityUserLikedByBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserLikedByActivity extends AppCompatActivity {

    ActivityUserLikedByBinding binding;
    private ArrayList<NonMegaLikeItem> nonMegaLikeItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserLikedByBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loadUsersWhoLikedMe();

        binding.profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.messageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserLikedByActivity.this, MessengerActivity.class);
                startActivity(intent);
                finish();
            }
        });


        binding.shopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserLikedByActivity.this, ShopActivity.class);
                startActivity(intent);
                finish();
            }
        });


        binding.findButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserLikedByActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    private void loadLikedUsers() {
        DatabaseReference currentUserLikedRef = FirebaseDatabase.getInstance().getReference("Users")
                .child(getUID())
                .child("likedUsers");

        currentUserLikedRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<NonMegaLikeItem> likedUsersList = new ArrayList<>();

                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                    String likedUserID = userSnapshot.getKey();
                    if (userSnapshot.getValue(Boolean.class) != null && userSnapshot.getValue(Boolean.class)) {
                        likedUsersList.add(new NonMegaLikeItem(likedUserID, "photo1"));
                    }
                }

                GridLayout likedByTable = findViewById(R.id.liked_by_table);
                NonMegaLikeAdapter adapter = new NonMegaLikeAdapter(UserLikedByActivity.this, R.layout.nonmegalike, likedUsersList);

                for (int i = 0; i < adapter.getCount(); i++) {
                    View itemRow = adapter.getView(i, null, likedByTable);
                    likedByTable.addView(itemRow);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void loadUsersWhoLikedMe() {
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("Users");

        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<NonMegaLikeItem> likedByUsersList = new ArrayList<>();

                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                    String userID = userSnapshot.getKey();
                    DataSnapshot likedUsersSnapshot = userSnapshot.child("likedUsers");

                    if (likedUsersSnapshot.hasChild(getUID()) && likedUsersSnapshot.child(getUID()).getValue(Boolean.class)) {
                        likedByUsersList.add(new NonMegaLikeItem(userID, "photo1"));
                    }
                }

                GridLayout likedByTable = findViewById(R.id.liked_by_table);
                NonMegaLikeAdapter adapter = new NonMegaLikeAdapter(UserLikedByActivity.this, R.layout.nonmegalike, likedByUsersList);

                for (int i = 0; i < adapter.getCount(); i++) {
                    View itemRow = adapter.getView(i, null, likedByTable);
                    likedByTable.addView(itemRow);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                finish();
            }
        });
    }


    private String getUID() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        return sharedPreferences.getString("UID", "");
    }
}