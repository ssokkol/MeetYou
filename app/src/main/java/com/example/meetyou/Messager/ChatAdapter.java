package com.example.meetyou.Messager;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.meetyou.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ChatAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<ChatItem> chatItems;

    public ChatAdapter(Context context, ArrayList<ChatItem> chatItems) {
        this.context = context;
        this.chatItems = chatItems;
    }

    @Override
    public int getCount() {
        return chatItems.size();
    }

    @Override
    public Object getItem(int position) {
        return chatItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.chat_layout, parent, false);
            holder = new ViewHolder();
            holder.profilePhoto = convertView.findViewById(R.id.profile_photo);
            holder.nameTextView = convertView.findViewById(R.id.name_text_view);
            holder.messageTextView = convertView.findViewById(R.id.message_text_view);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ChatItem chatItem = chatItems.get(position);
        View finalConvertView = convertView;
        getUserUID(new OnUIDReceivedListener() {
            @Override
            public void onUIDReceived(String UID) {
                if (UID != null){
                    SharedPreferences sharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    if(chatItem.getChatUID().startsWith(UID+"_") || chatItem.getChatUID().endsWith("_"+UID)) {
                        if (chatItem.getName1().equals(UID)) {
                            getUserName(chatItem.getName2(), new OnNameReceivedListener() {
                                @Override
                                public void onNameReceived(String name) {
                                    holder.nameTextView.setText(name);

                                    editor.putString("chatName", name);
                                    editor.apply();
                                }
                            });
                            holder.messageTextView.setText(chatItem.getMessage2());
                            customPhotoLoadingToClient(chatItem.getChatUID(), "profilePhoto2", holder.profilePhoto);
                        } else if (chatItem.getName2().equals(UID)) {
                            getUserName(chatItem.getName1(), new OnNameReceivedListener() {
                                @Override
                                public void onNameReceived(String name) {
                                    holder.nameTextView.setText(name);

                                    editor.putString("chatName", name);
                                    editor.apply();
                                }
                            });
//                        holder.nameTextView.setText(chatItem.getName1());
                            holder.messageTextView.setText(chatItem.getMessage1());
                            customPhotoLoadingToClient(chatItem.getChatUID(), "profilePhoto1", holder.profilePhoto);
                        }
                    }else {
                        finalConvertView.setVisibility(View.GONE);
                        finalConvertView.setLayoutParams(new ViewGroup.LayoutParams(0,0));
                    }
                }
            }
        });
        return convertView;
    }

    private static class ViewHolder {
        ImageView profilePhoto;
        TextView nameTextView;
        TextView messageTextView;
    }

    private void getUserUID(final OnUIDReceivedListener listener) {
        DatabaseReference UIDRef = FirebaseDatabase.getInstance().getReference("Users").child(getUID()).child("UID");
        UIDRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String UID = snapshot.getValue(String.class);
                if (UID != null) {
                    listener.onUIDReceived(UID);
                } else {
                    listener.onUIDReceived(" ");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                listener.onUIDReceived(" ");
            }
        });
    }

    interface OnUIDReceivedListener {
        void onUIDReceived(String UID);
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

    private String getUID() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        return sharedPreferences.getString("UID", "");
    }
}
