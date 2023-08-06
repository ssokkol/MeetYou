package com.example.meetyou.Messager;

import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meetyou.R;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Message> messages;
    private String currentUserId;
    private static final int OWN_MESSAGE = 1;
    private static final int OTHER_MESSAGE = 2;

    public MessageAdapter(List<Message> messages, String currentUserId) {
        this.messages = messages;
        this.currentUserId = currentUserId;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == OWN_MESSAGE) {
            View ownMessageView = inflater.inflate(R.layout.own_message_style, parent, false);
            return new OwnMessageViewHolder(ownMessageView);
        } else {
            View otherMessageView = inflater.inflate(R.layout.message_style, parent, false);
            return new OtherMessageViewHolder(otherMessageView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Message message = messages.get(position);

        if (getItemViewType(position) == OWN_MESSAGE) {
            ((OwnMessageViewHolder) holder).user.setText(message.getUserName());
            ((OwnMessageViewHolder) holder).message.setText(message.getTextMessage());
            ((OwnMessageViewHolder) holder).messageTime.setText(DateFormat.format("HH:mm", message.getMessageTime()));
        } else {
            ((OtherMessageViewHolder) holder).user.setText(message.getUserName());
            ((OtherMessageViewHolder) holder).message.setText(message.getTextMessage());
            ((OtherMessageViewHolder) holder).messageTime.setText(DateFormat.format("HH:mm", message.getMessageTime()));
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    @Override
    public int getItemViewType(int position) {
        Message message = messages.get(position);
        if (message != null && message.getUserName() != null && message.getUserName().equals(currentUserId)) {
            return OWN_MESSAGE;
        } else {
            return OTHER_MESSAGE;
        }
    }

    public static class OwnMessageViewHolder extends RecyclerView.ViewHolder {
        TextView user;
        TextView message;
        TextView messageTime;

        public OwnMessageViewHolder(@NonNull View itemView) {
            super(itemView);
            user = itemView.findViewById(R.id.message_user);
            message = itemView.findViewById(R.id.message_text);
            messageTime = itemView.findViewById(R.id.message_time);
        }
    }

    public static class OtherMessageViewHolder extends RecyclerView.ViewHolder {
        TextView user;
        TextView message;
        TextView messageTime;

        public OtherMessageViewHolder(@NonNull View itemView) {
            super(itemView);
            user = itemView.findViewById(R.id.message_user);
            message = itemView.findViewById(R.id.message_text);
            messageTime = itemView.findViewById(R.id.message_time);
        }
    }
}

