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

public class NonPrimeLikeAdapter extends ArrayAdapter<NonPrimeLikeItem> {

    private Context context;
    private int resource;

    public NonPrimeLikeAdapter(Context context, int resource, List<NonPrimeLikeItem> items) {
        super(context, resource, items);
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

        NonPrimeLikeItem item = getItem(position);
        if (item != null) {
            TextView nameText = view.findViewById(R.id.name_text_view);
            ImageView imageView = view.findViewById(R.id.profile_image);
        }

        return view;
    }

}