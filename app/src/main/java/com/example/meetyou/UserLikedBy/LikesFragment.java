package com.example.meetyou.UserLikedBy;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;

import com.example.meetyou.R;
import com.example.meetyou.databinding.FragmentLikesBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LikesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LikesFragment extends Fragment {
    private Context context;

    FragmentLikesBinding binding;
    private ArrayList<NonMegaLikeItem> nonMegaLikeItems;
    public static LikesFragment newInstance(String param1, String param2) {
        LikesFragment fragment = new LikesFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLikesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadUsersWhoLikedMe();
        binding = FragmentLikesBinding.bind(view);
    }

    private void loadUsersWhoLikedMe() {
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("Users");
        DatabaseReference userStatus = FirebaseDatabase.getInstance().getReference("Users").child(getUID());

        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                getStatus(new LikesFragment.OnStatusReceivedListener() {
                    @Override
                    public void OnStatusReceived(String userSub) {
                        if (!userSub.equals("basic") && !userSub.equals("vip")) {
                            List<NonMegaLikeItem> likedByUsersList = new ArrayList<>();
                            List<MegaLikeItem> megaLikedUsersList = new ArrayList<>();
                            List<LuxeMegaLikeItem> luxeLikedUsersList = new ArrayList<>();
                            for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                                String userID = userSnapshot.getKey();
                                DataSnapshot likedUsersSnapshot = userSnapshot.child("likedUsers");

                                if (likedUsersSnapshot.hasChild(getUID()) && likedUsersSnapshot.child(getUID()).getValue(Boolean.class)) {
                                    likedByUsersList.add(new NonMegaLikeItem(userID, "photo1"));
                                }
                            }
                            for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                                String userID = userSnapshot.getKey();

                                DataSnapshot likedUsersSnapshot = userSnapshot.child("sympedUsers");
                                String bdMessage = likedUsersSnapshot.child(getUID()).child("message").getValue(String.class);
                                if (likedUsersSnapshot.hasChild(getUID()) && bdMessage.equals("J9pR7cE2qL5zT1hY6gN0vW4xM8bF3a")){
                                    megaLikedUsersList.add(new MegaLikeItem(userID,"photo1"));
                                }else if (likedUsersSnapshot.hasChild(getUID()) && !bdMessage.equals("J9pR7cE2qL5zT1hY6gN0vW4xM8bF3a")){
                                    luxeLikedUsersList.add(new LuxeMegaLikeItem(userID,"photo1",bdMessage));
                                }
                            }

                            GridLayout likedByTable = binding.likedByTable;
                            LuxeMegaLikeAdapter luxeAdapter = new LuxeMegaLikeAdapter(context,R.layout.luxe_mega_like,luxeLikedUsersList);
                            MegaLikeAdapter megaAdapter = new MegaLikeAdapter(context,R.layout.megalike,megaLikedUsersList);
                            NonMegaLikeAdapter adapter = new NonMegaLikeAdapter(context, R.layout.nonmegalike, likedByUsersList);
                            for (int i = 0; i < luxeAdapter.getCount(); i++) {
                                View itemRow = luxeAdapter.getView(i, null, likedByTable);
                                likedByTable.addView(itemRow);
                            }
                            for (int i = 0; i < megaAdapter.getCount(); i++) {

                                View itemRow = megaAdapter.getView(i, null, likedByTable);
                                likedByTable.addView(itemRow);
                            }
                            for (int i = 0; i < adapter.getCount(); i++) {
                                View itemRow = adapter.getView(i, null, likedByTable);
                                likedByTable.addView(itemRow);
                            }
                        }else {
                            List<NonPrimeLikeItem> likedByUsersList = new ArrayList<>();
                            for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                                String userID = userSnapshot.getKey();
                                DataSnapshot likedUsersSnapshot = userSnapshot.child("likedUsers");

                                if (likedUsersSnapshot.hasChild(getUID()) && likedUsersSnapshot.child(getUID()).getValue(Boolean.class)) {
                                    likedByUsersList.add(new NonPrimeLikeItem(null, null));
                                }
                            }

                            GridLayout likedByTable = binding.likedByTable;
                            NonPrimeLikeAdapter adapter = new NonPrimeLikeAdapter(context, R.layout.non_prime_like, likedByUsersList);

                            for (int i = 0; i < adapter.getCount(); i++) {
                                View itemRow = adapter.getView(i, null, likedByTable);
                                likedByTable.addView(itemRow);
                            }
                        }

                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void getStatus(final LikesFragment.OnStatusReceivedListener listener){
        DatabaseReference statusRef = FirebaseDatabase.getInstance().getReference("Users").child(getUID()).child("status");
        statusRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String userSub = snapshot.getValue(String.class);
                if (snapshot.exists()) {
                    listener.OnStatusReceived(userSub);
                } else {
                    listener.OnStatusReceived("basic");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                listener.OnStatusReceived("basic");
            }
        });
    }


    public interface OnStatusReceivedListener{
        void OnStatusReceived(String userSub);
    }

    private String getUID() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        return sharedPreferences.getString("UID", "");
    }
}