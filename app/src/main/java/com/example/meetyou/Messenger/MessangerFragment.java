package com.example.meetyou.Messenger;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.meetyou.OptionsActivity;
import com.example.meetyou.R;
import com.example.meetyou.databinding.FragmentMessangerBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.Nullable;

import java.util.ArrayList;

public class MessangerFragment extends Fragment {
    private Context context;
    private FragmentMessangerBinding binding;

    public static MessangerFragment newInstance() {
        return new MessangerFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.context = null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMessangerBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadChatItems();
        binding = FragmentMessangerBinding.bind(view);
    }


    private void loadChatItems() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference messagesRef = database.getReference("Chats");

        ArrayList<ChatItem> chatItems = new ArrayList<>();

        messagesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String currentUID = getUID();
                for (DataSnapshot chatSnapshot : dataSnapshot.getChildren()) {
                    String chatUID = chatSnapshot.child("chatUID").getValue(String.class);
                    if (chatUID.startsWith(currentUID + "_") || chatUID.endsWith("_" + currentUID)) {
                        String photo1 = chatSnapshot.child("photo1").getValue(String.class);
                        String photo2 = chatSnapshot.child("photo2").getValue(String.class);
                        String name1 = chatSnapshot.child("name1").getValue(String.class);
                        String name2 = chatSnapshot.child("name2").getValue(String.class);
                        String message1 = chatSnapshot.child("message1").getValue(String.class);
                        String message2 = chatSnapshot.child("message2").getValue(String.class);
                        chatItems.add(new ChatItem(chatUID, photo1, photo2, name1, name2, message1, message2));
                    }
                }

                ChatAdapter chatAdapter = new ChatAdapter(context, chatItems);
                ListView chatsListView = binding.chatsListView;
                chatsListView.setAdapter(chatAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle database error
            }
        });

        binding.chatsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ChatItem selectedChat = chatItems.get(position);
                String chatUID = selectedChat.getChatUID();

                SharedPreferences sharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                if (selectedChat.getName1().equals(getUID())) {
                    editor.putString("chatName", selectedChat.getName2());
                } else {
                    editor.putString("chatName", selectedChat.getName1());
                }

                editor.putString("chatUID", chatUID);
                editor.apply();

                Intent intent = new Intent(context, ChatActivity.class);
                startActivity(intent);
            }
        });
    }

    private String getUID() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        return sharedPreferences.getString("UID", "");
    }
}
