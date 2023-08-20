package com.example.meetyou.UserLikedBy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.meetyou.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.Nullable;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MegaLikeAdapter extends ArrayAdapter<MegaLikeItem> {

    private Context context;
    private int resource;

    public MegaLikeAdapter(Context context, int resource, List<MegaLikeItem> items) {
        super(context, resource);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(resource, parent, false);
        }

        MegaLikeItem item = getItem(position);
        if (item != null) {
            TextView nameText = view.findViewById(R.id.name_text_view);
            getName(item.getName(), new OnNameReceivedListener() {
                @Override
                public void onNameReceived(String name) {
                    getAge(item.getName(), new OnAgeReceivedListener() {
                        @Override
                        public void onAgeReceived(int age) {
                            nameText.setText(name + ", " + String.valueOf(age));
                        }
                    });
                }
            });
            ImageView imageView = view.findViewById(R.id.profile_image);
            customPhotoLoadingToClient(item.getName(), item.getPhotoName(), imageView);
        }

        return view;
    }

    private void customPhotoLoadingToClient(String name, String photoName, ImageView imageView) {
        DatabaseReference imagesRef = FirebaseDatabase.getInstance().getReference("Users").child(name).child(photoName);
        imagesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String imageUrl = snapshot.getValue(String.class);
                if (imageUrl != null) {
                    Picasso.get().load(imageUrl).into(imageView);
                } else {
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void getName(String UID, final OnNameReceivedListener listener) {
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
    public interface OnNameReceivedListener {
        void onNameReceived(String name);
    }

    private void getAge(String UID, final OnAgeReceivedListener listener) {
        DatabaseReference ageRef = FirebaseDatabase.getInstance().getReference("Users").child(UID).child("age");
        ageRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int age = snapshot.getValue(Integer.class);
                if (snapshot.exists()) {
                    listener.onAgeReceived(age);
                } else {
                    listener.onAgeReceived(0);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                listener.onAgeReceived(0);
            }
        });
    }
    public interface OnAgeReceivedListener {
        void onAgeReceived(int age);
    }
}
