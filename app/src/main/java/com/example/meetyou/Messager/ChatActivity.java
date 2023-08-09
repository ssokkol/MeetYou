package com.example.meetyou.Messager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meetyou.MYFiles.NotificationHelper;
import com.example.meetyou.R;
import com.example.meetyou.StartActivity;
import com.example.meetyou.databinding.ActivityChatBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.Nullable;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private ActivityChatBinding binding;
    private MessageAdapter messageAdapter;
    private List<Message> messageList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setStatusBarColor(getColor(R.color.main));

        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            NotificationHelper.showCustomNotification(ChatActivity.this, null, getString(R.string.you_are_not_signed_in_message), null, 0, 0, 0, 0);
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(ChatActivity.this, StartActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        } else {
            initRecyclerView();
            binding.sendMessageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String messageText = binding.messageText.getText().toString().trim();
                    if (messageText.equals("")) return;
                    FirebaseDatabase.getInstance().getReference().child("Users").child(getUID()).child("Messages").child(getUID()+"_to_").push().setValue(new Message(getUID(), messageText));
                    binding.messageText.setText("");
                }
            });

            binding.goBackButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        }
    }

    private void initRecyclerView() {
        RecyclerView messagesRecyclerView = findViewById(R.id.messages_list);
        messagesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        messageAdapter = new MessageAdapter(messageList, getUID());
        messagesRecyclerView.setAdapter(messageAdapter);
        displayAllMessages();
    }

    private void displayAllMessages() {
        FirebaseDatabase.getInstance().getReference().child("Users").child(getUID()).child("Messages").child(getUID()+"_to_").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Message message = dataSnapshot.getValue(Message.class);
                if (message != null) {
                    messageList.add(message);
                    messageAdapter.notifyDataSetChanged();

                    RecyclerView messagesRecyclerView = findViewById(R.id.messages_list);
                    messagesRecyclerView.scrollToPosition(messageList.size() - 1);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        messageList.clear();
    }

    private void customPhotoLoadingToClient(String photoName, ImageView imageView) {
        DatabaseReference Images = FirebaseDatabase.getInstance().getReference("Users").child(getUID()).child(photoName);
        Images.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String value = snapshot.getValue(String.class);
                Picasso.get().load(value).into(imageView);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private String getUID() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        return sharedPreferences.getString("UID", "");
    }
}
