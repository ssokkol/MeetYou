package com.example.meetyou.Messenger;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
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
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);

        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.main));


        getUserNameFromDatabase(getChatName(), new OnNameReceivedListener() {
            @Override
            public void onNameReceived(String name) {
                binding.chatNameTextView.setText(name);
            }
        });

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
                    getName(new OnNameReceivedListener() {
                        @Override
                        public void onNameReceived(String name) {
                            getChatName1(new OnChatName1Received() {
                                @Override
                                public void onChatNameReceived(String name1) {
                                    getChatName2(new OnChatName2Received() {
                                        @Override
                                        public void onChatNameReceived(String name2) {
                                            Log.e("NAME DEBUG CHAT PIZDA", getChatName());
                                            if (getUID().equals(name1)) {
                                                FirebaseDatabase.getInstance().getReference().child("Chats").child(getChatUID()).child("Messages").push().setValue(new Message(name, messageText));
                                                binding.messageText.setText("");
                                                if(messageText.length()>35){
                                                    String trimmedMessage = messageText.substring(0, Math.min(messageText.length(), 25)) + "...";
                                                    FirebaseDatabase.getInstance().getReference().child("Chats").child(getChatUID()).child("message1").setValue(trimmedMessage);
                                                }
                                                else {
                                                    FirebaseDatabase.getInstance().getReference().child("Chats").child(getChatUID()).child("message1").setValue(messageText);
                                                }
                                            }
                                            else {
                                                FirebaseDatabase.getInstance().getReference().child("Chats").child(getChatUID()).child("Messages").push().setValue(new Message(name, messageText));
                                                binding.messageText.setText("");
                                                if(messageText.length()>35){
                                                    String trimmedMessage = messageText.substring(0, Math.min(messageText.length(), 25)) + "...";
                                                    FirebaseDatabase.getInstance().getReference().child("Chats").child(getChatUID()).child("message2").setValue(trimmedMessage);
                                                }
                                                else {
                                                    FirebaseDatabase.getInstance().getReference().child("Chats").child(getChatUID()).child("message2").setValue(messageText);
                                                }
                                            }
                                        }
                                    });
                                }
                            });
                        }
                    });
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
        getName(new OnNameReceivedListener() {
            @Override
            public void onNameReceived(String name) {
                messageAdapter = new MessageAdapter(ChatActivity.this,messageList, name);
                messagesRecyclerView.setAdapter(messageAdapter);
                displayAllMessages();
            }
        });
    }

    private void displayAllMessages() {
        FirebaseDatabase.getInstance().getReference().child("Chats").child(getChatUID()).child("Messages").addChildEventListener(new ChildEventListener() {
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

    private void customPhotoLoadingToClient(String currentChat, String photoName, ImageView imageView) {
        DatabaseReference imagesRef = FirebaseDatabase.getInstance().getReference("Chats").child(currentChat).child(photoName);
        imagesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String imageUrl = snapshot.getValue(String.class);
                if (imageUrl != null) {
                    // Загрузка изображения с помощью библиотеки Picasso
                    Picasso.get().load(imageUrl).into(imageView);
                } else {
                    // Обработка ошибки, если изображение не удалось загрузить
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Обработка ошибки, если загрузка изображения не удалась
            }
        });
    }

    private String getChatUID(){
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        return sharedPreferences.getString("chatUID", "");
    }

    private String getChatName(){
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        return sharedPreferences.getString("chatName", "");
    }
    private void getName(final OnNameReceivedListener listener) {
        DatabaseReference nameRef = FirebaseDatabase.getInstance().getReference("Users").child(getUID()).child("name");
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
    public interface OnNameReceivedListener {
        void onNameReceived(String name);
    }

    private void getChatName1(final OnChatName1Received listener) {
        DatabaseReference nameRef = FirebaseDatabase.getInstance().getReference("Chats").child(getChatUID()).child("name1");
        nameRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name = snapshot.getValue(String.class);
                if (name != null) {
                    listener.onChatNameReceived(name);
                } else {
                    listener.onChatNameReceived(" ");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                listener.onChatNameReceived(" ");
            }
        });
    }

    public interface OnChatName1Received{
        void onChatNameReceived(String name);
    }

    private void getChatName2(final OnChatName2Received listener) {
        DatabaseReference nameRef = FirebaseDatabase.getInstance().getReference("Chats").child(getChatUID()).child("name2");
        nameRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name = snapshot.getValue(String.class);
                if (name != null) {
                    listener.onChatNameReceived(name);
                } else {
                    listener.onChatNameReceived(" ");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                listener.onChatNameReceived(" ");
            }
        });
    }

    public interface OnChatName2Received{
        void onChatNameReceived(String name);
    }

    private void getUserNameFromDatabase(String UID, final OnNameReceivedListener listener) {
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

    private String getUID() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        return sharedPreferences.getString("UID", "");
    }
}
