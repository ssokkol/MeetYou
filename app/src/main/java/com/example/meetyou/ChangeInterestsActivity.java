package com.example.meetyou;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import com.example.meetyou.Database.DatabaseHelper;
import com.example.meetyou.MYFiles.NotificationHelper;
import com.example.meetyou.databinding.ActivityChangeInterestsBinding;

public class ChangeInterestsActivity extends AppCompatActivity {

    private static boolean isClubsChanged = false;
    private static boolean isCodingChanged = false;
    private static boolean isSportChanged = false;
    private static boolean isVGamesChanged = false;
    private static boolean isYogaChanged = false;
    private static boolean isBooksChanged = false;
    private static boolean isArtChanged = false;
    private static boolean isMusicChanged = false;
    private static boolean isTTChanged = false;
    private static boolean isYTChanged = false;
    private static boolean isCarChanged = false;
    private static boolean isCGamesChanged = false;
    private static boolean isTGamesChanged = false;
    private static boolean isChessChanged = false;
    private static boolean isCookChanged = false;
    private static boolean isFishChanged = false;
    private static boolean isTravChanged = false;
    private static boolean isDanceChanged = false;
    private static boolean isSexChanged = false;
    private static boolean isRockChanged = false;
    private static boolean isRRapChanged = false;
    private static boolean isHuntChanged = false;
    private static boolean isFashChanged = false;
    private static boolean isGuitarChanged = false;
    private static boolean isPianoChanged = false;
    private static boolean isPhotoChanged = false;
    private static boolean isPoliticsChanged = false;
    private static boolean isPhilChanged = false;

    public static boolean isGoNextActive = false;

    ActivityChangeInterestsBinding binding;

    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChangeInterestsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);

        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.main));

        databaseHelper = new DatabaseHelper(this);

        binding.clubsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isClubsChanged){
                    binding.clubsImage.setImageResource(R.drawable.clubs_selected);
                    isClubsChanged = true;
                } else if (isClubsChanged) {
                    binding.clubsImage.setImageResource(R.drawable.clubs);
                    isClubsChanged = false;
                }
                activateGoNextButton();
            }
        });

        binding.codingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isCodingChanged){
                    binding.codingImage.setImageResource(R.drawable.coding_selected);
                    isCodingChanged = true;
                } else if (isCodingChanged) {
                    binding.codingImage.setImageResource(R.drawable.coding);
                    isCodingChanged = false;
                }
                activateGoNextButton();
            }
        });

        binding.sportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isSportChanged){
                    binding.sportImage.setImageResource(R.drawable.sport_selected);
                    isSportChanged = true;
                } else if (isSportChanged) {
                    binding.sportImage.setImageResource(R.drawable.sport);
                    isSportChanged = false;
                }
                activateGoNextButton();
            }
        });

        binding.vgamesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isVGamesChanged){
                    binding.vgamesImage.setImageResource(R.drawable.vgames_selected);
                    isVGamesChanged = true;
                } else if (isVGamesChanged) {
                    binding.vgamesImage.setImageResource(R.drawable.vgames);
                    isVGamesChanged = true;
                }
                activateGoNextButton();
            }
        });

        binding.yogaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isYogaChanged){
                    binding.yogaImage.setImageResource(R.drawable.yoga_selected);
                    isYogaChanged = true;
                } else if (isYogaChanged) {
                    binding.yogaImage.setImageResource(R.drawable.yoga);
                    isYogaChanged = false;
                }
                activateGoNextButton();
            }
        });

        binding.booksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isBooksChanged){
                    binding.booksImage.setImageResource(R.drawable.books_selected);
                    isBooksChanged = true;
                } else if (isBooksChanged) {
                    binding.booksImage.setImageResource(R.drawable.books);
                    isBooksChanged = false;
                }
                activateGoNextButton();
            }
        });

        binding.artButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isArtChanged){
                    binding.artImage.setImageResource(R.drawable.art_selected);
                    isArtChanged = true;
                } else if (isArtChanged) {
                    binding.artImage.setImageResource(R.drawable.art);
                    isArtChanged = false;
                }
                activateGoNextButton();
            }
        });

        binding.musicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isMusicChanged){
                    binding.musicImage.setImageResource(R.drawable.music_selected);
                    isMusicChanged = true;
                } else if (isMusicChanged) {
                    binding.musicImage.setImageResource(R.drawable.music);
                    isMusicChanged = false;
                }
                activateGoNextButton();
            }
        });

        binding.ttButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isTTChanged){
                    binding.ttImage.setImageResource(R.drawable.tt_selected);
                    isTTChanged = true;
                } else if (isTTChanged) {
                    binding.ttImage.setImageResource(R.drawable.tt);
                    isTTChanged = false;
                }
                activateGoNextButton();
            }
        });

        binding.ytButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isYTChanged){
                    binding.ytImage.setImageResource(R.drawable.yt_selected);
                    isYTChanged = true;
                } else if (isYTChanged) {
                    binding.ytImage.setImageResource(R.drawable.yt);
                    isYTChanged = false;
                }
                activateGoNextButton();
            }
        });

        binding.carButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isCarChanged){
                    binding.carImage.setImageResource(R.drawable.car_selected);
                    isCarChanged = true;
                } else if (isCarChanged) {
                    binding.carImage.setImageResource(R.drawable.car);
                    isCarChanged = false;
                }
                activateGoNextButton();
            }
        });

        binding.cgamesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isCGamesChanged){
                    binding.cgamesImage.setImageResource(R.drawable.cgames);
                    isCGamesChanged = true;
                } else if (isCGamesChanged) {
                    binding.cgamesImage.setImageResource(R.drawable.cgames_selected);
                    isCGamesChanged = false;
                }
                activateGoNextButton();
            }
        });

        binding.tgamesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isTGamesChanged){
                    binding.tgamesImage.setImageResource(R.drawable.tgames_selected);
                    isTGamesChanged = true;
                } else if (isTGamesChanged) {
                    binding.tgamesImage.setImageResource(R.drawable.tgames);
                    isTGamesChanged = false;
                }
                activateGoNextButton();
            }
        });

        binding.chessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isChessChanged){
                    binding.chessImage.setImageResource(R.drawable.chess_selected);
                    isChessChanged = true;
                } else if (isChessChanged) {
                    binding.chessImage.setImageResource(R.drawable.chess);
                    isChessChanged = false;
                }
                activateGoNextButton();
            }
        });

        binding.cookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isCookChanged){
                    binding.cookImage.setImageResource(R.drawable.cook_selected);
                    isCookChanged = true;
                } else if (isCookChanged) {
                    binding.cookImage.setImageResource(R.drawable.cook);
                    isCookChanged = false;
                }
                activateGoNextButton();
            }
        });

        binding.fishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isFishChanged){
                    binding.fishImage.setImageResource(R.drawable.fish_selected);
                    isFishChanged = true;
                } else if (isFishChanged) {
                    binding.fishImage.setImageResource(R.drawable.fish);
                    isFishChanged = false;
                }
                activateGoNextButton();
            }
        });

        binding.travButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isTravChanged){
                    binding.travImage.setImageResource(R.drawable.trav_selected);
                    isTravChanged = true;
                } else if (isTravChanged) {
                    binding.travImage.setImageResource(R.drawable.trav);
                    isTravChanged = false;
                }
                activateGoNextButton();
            }
        });

        binding.danceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isDanceChanged){
                    binding.danceImage.setImageResource(R.drawable.dance_selected);
                    isDanceChanged = true;
                } else if (isDanceChanged) {
                    binding.danceImage.setImageResource(R.drawable.dance);
                    isDanceChanged = false;
                }
                activateGoNextButton();
            }
        });

        binding.sexButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isSexChanged){
                    binding.sexImage.setImageResource(R.drawable.sex_selected);
                    isSexChanged = true;
                } else if (isSexChanged) {
                    binding.sexImage.setImageResource(R.drawable.sex);
                    isSexChanged = false;
                }
                activateGoNextButton();
            }
        });

        binding.rockButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isRockChanged){
                    binding.rockImage.setImageResource(R.drawable.rock_selected);
                    isRockChanged = true;
                } else if (isRockChanged) {
                    binding.rockImage.setImageResource(R.drawable.rock);
                    isRockChanged = false;
                }
                activateGoNextButton();
            }
        });

        binding.rrapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isRRapChanged){
                    binding.rrapImage.setImageResource(R.drawable.rrap_selected);
                    isRRapChanged = true;
                } else if (isRRapChanged) {
                    binding.rrapImage.setImageResource(R.drawable.rrap);
                    isRRapChanged = false;
                }
                activateGoNextButton();
            }
        });

        binding.huntButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isHuntChanged){
                    binding.huntImage.setImageResource(R.drawable.hunt_selected_);
                    isHuntChanged = true;
                } else if (isHuntChanged) {
                    binding.huntImage.setImageResource(R.drawable.hunt);
                    isHuntChanged = false;
                }
                activateGoNextButton();
            }
        });

        binding.fashButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isFashChanged){
                    binding.fashImage.setImageResource(R.drawable.fash_selected);
                    isFashChanged = true;
                } else if (isFashChanged) {
                    binding.fashImage.setImageResource(R.drawable.fash);
                    isFashChanged = false;
                }
                activateGoNextButton();
            }
        });

        binding.guitarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isGuitarChanged){
                    binding.guitarImage.setImageResource(R.drawable.guitar_selected);
                    isGuitarChanged = true;
                } else if (isGuitarChanged) {
                    binding.guitarImage.setImageResource(R.drawable.guitar);
                    isGuitarChanged = false;
                }
                activateGoNextButton();
            }
        });

        binding.pianoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isPianoChanged){
                    binding.pianoImage.setImageResource(R.drawable.piano_selected);
                    isPianoChanged = true;
                } else if (isPianoChanged) {
                    binding.pianoImage.setImageResource(R.drawable.piano);
                    isPianoChanged = false;
                }
                activateGoNextButton();
            }
        });

        binding.photoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isPhotoChanged){
                    binding.photoImage.setImageResource(R.drawable.photo_selected);
                    isPhotoChanged = true;
                } else if (isPhotoChanged) {
                    binding.photoImage.setImageResource(R.drawable.photo);
                    isPhotoChanged = false;
                }
                activateGoNextButton();
            }
        });

        binding.politicsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isPoliticsChanged){
                    binding.politicsImage.setImageResource(R.drawable.politics_selected);
                    isPoliticsChanged = true;
                } else if (isPoliticsChanged) {
                    binding.politicsImage.setImageResource(R.drawable.politics);
                    isPoliticsChanged = false;
                }
                activateGoNextButton();
            }
        });

        binding.philButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isPhilChanged){
                    binding.philImage.setImageResource(R.drawable.phil_selected);
                    isPhilChanged = true;
                } else if (isPhilChanged) {
                    binding.philImage.setImageResource(R.drawable.phil);
                    isPhilChanged = false;
                }
                activateGoNextButton();
            }
        });

        binding.goNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isGoNextActive)
                {
                    NotificationHelper.showCustomNotification(ChangeInterestsActivity.this, null, getString(R.string.choose_a_few_interests_message), getString(R.string.close), 0, 0, 0,0);
                } else
                {
                    Intent intent = new Intent(ChangeInterestsActivity.this, StartActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    //NotificationHelper.showCustomNotification(StartActivity, getString(R.string.welcome), getString(R.string.you_ve_been_successfully_registered_please_sign_in), getString(R.string.okay), 0, 0, 0,0);
                }
            }
        });

        binding.goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
    public void activateGoNextButton(){
        if (isClubsChanged || isCodingChanged || isSportChanged || isVGamesChanged || isYogaChanged || isBooksChanged || isArtChanged || isMusicChanged || isTTChanged
                || isYTChanged || isCarChanged || isCGamesChanged || isTGamesChanged || isChessChanged || isCookChanged || isFishChanged || isTravChanged || isDanceChanged ||
                isSexChanged || isRockChanged || isRRapChanged || isHuntChanged || isFashChanged || isGuitarChanged || isPianoChanged || isPhotoChanged || isPoliticsChanged || isPhilChanged)
        {
            binding.goNextButton.setBackgroundResource(R.drawable.button_background_blue);
            binding.goNextButton.setTextColor(getColor(R.color.white));
            isGoNextActive = true;
        }
        else
        {
            binding.goNextButton.setTextColor(getColor(R.color.neutral_dark_gray));
            binding.goNextButton.setBackgroundResource(R.drawable.button_background_gray);
            isGoNextActive = false;
        }
    }
}