package com.example.meetyou.Messager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.meetyou.OptionsActivity;
import com.example.meetyou.R;
import com.example.meetyou.databinding.ActivityMessengerBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MessengerActivity extends AppCompatActivity {
    ActivityMessengerBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMessengerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);

        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.main));
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference messagesRef = database.getReference("Chats");

        ArrayList<ChatItem> chatItems = new ArrayList<>();

        messagesRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot chatSnapshot : dataSnapshot.getChildren()) {
                    String chatUID = chatSnapshot.child("chatUID").getValue(String.class);
                    if(chatUID.startsWith(getUID()+"_")||chatUID.endsWith("_"+getUID()))
                    {
                        String photo1 = chatSnapshot.child("photo1").getValue(String.class);
                        String photo2 = chatSnapshot.child("photo2").getValue(String.class);
                        String name1 = chatSnapshot.child("name1").getValue(String.class);
                        String name2 = chatSnapshot.child("name2").getValue(String.class);
                        String message1 = chatSnapshot.child("message1").getValue(String.class);
                        String message2 = chatSnapshot.child("message2").getValue(String.class);
                        chatItems.add(new ChatItem(chatUID, photo1, photo2, name1, name2, message1, message2));
                    }
                }

                ChatAdapter chatAdapter = new ChatAdapter(MessengerActivity.this, chatItems);
                ListView chatsListView = findViewById(R.id.chats_list_view);
                chatsListView.setAdapter(chatAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        binding.profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.settingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MessengerActivity.this, OptionsActivity.class);
                startActivity(intent);
            }
        });

        binding.chatsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                ChatItem selectedChat = chatItems.get(position);
                String chatUID = selectedChat.getChatUID();
                String chatName;
                getUserName(getUID(), new OnNameReceivedListener() {
                    @Override
                    public void onNameReceived(String name) {
                        if(name.equals(selectedChat.getName1())){
                            editor.putString("chatName", selectedChat.getName2());
                        }else{
                            editor.putString("chatName", selectedChat.getName1());
                        }
                    }
                });

                // Сохранение chatUID в SharedPreferences
                editor.putString("chatUID", chatUID);
                editor.apply();

                // Запуск ChatActivity с передачей chatUID
                Intent intent = new Intent(MessengerActivity.this, ChatActivity.class);
                startActivity(intent);
            }
        });

    }


    private void getUserName(String UID, final OnNameReceivedListener listener) {
        DatabaseReference nameRef = FirebaseDatabase.getInstance().getReference("Users").child(UID).child("name");
        nameRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name = snapshot.getValue(String.class);
                if (name != null) {
                    listener.onNameReceived(name);
                } else {
                    listener.onNameReceived(" ");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                listener.onNameReceived(" ");
            }
        });
    }

    interface OnNameReceivedListener {
        void onNameReceived(String name);
    }

    private String getChatUID(){
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        return sharedPreferences.getString("chatUID", "");
    }

    private String getUID() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        return sharedPreferences.getString("UID", "");
    }
}