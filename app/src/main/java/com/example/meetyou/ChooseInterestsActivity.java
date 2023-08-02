package com.example.meetyou;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.meetyou.Database.DatabaseHelper;
import com.example.meetyou.MYFiles.NotificationHelper;
import com.example.meetyou.MYFiles.Users;
import com.example.meetyou.databinding.ActivityChooseInterestsBinding;

public class ChooseInterestsActivity extends AppCompatActivity {

    private int Counter = 0;


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

    ActivityChooseInterestsBinding binding;

    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChooseInterestsBinding.inflate(getLayoutInflater());
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
                    Counter++;
                } else if (isClubsChanged) {
                    binding.clubsImage.setImageResource(R.drawable.clubs);
                    isClubsChanged = false;
                    Counter--;
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
                    Counter++;
                } else if (isCodingChanged) {
                    binding.codingImage.setImageResource(R.drawable.coding);
                    isCodingChanged = false;
                    Counter--;
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
                    Counter++;
                } else if (isSportChanged) {
                    binding.sportImage.setImageResource(R.drawable.sport);
                    isSportChanged = false;
                    Counter--;
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
                    Counter++;
                } else if (isVGamesChanged) {
                    binding.vgamesImage.setImageResource(R.drawable.vgames);
                    isVGamesChanged = false;
                    Counter--;
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
                    Counter++;
                } else if (isYogaChanged) {
                    binding.yogaImage.setImageResource(R.drawable.yoga);
                    isYogaChanged = false;
                    Counter--;
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
                    Counter++;
                } else if (isBooksChanged) {
                    binding.booksImage.setImageResource(R.drawable.books);
                    isBooksChanged = false;
                    Counter--;
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
                    Counter++;
                } else if (isArtChanged) {
                    binding.artImage.setImageResource(R.drawable.art);
                    isArtChanged = false;
                    Counter--;
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
                    Counter++;
                } else if (isMusicChanged) {
                    binding.musicImage.setImageResource(R.drawable.music);
                    isMusicChanged = false;
                    Counter--;
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
                    Counter++;
                } else if (isTTChanged) {
                    binding.ttImage.setImageResource(R.drawable.tt);
                    isTTChanged = false;
                    Counter--;
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
                    Counter++;
                } else if (isYTChanged) {
                    binding.ytImage.setImageResource(R.drawable.yt);
                    isYTChanged = false;
                    Counter--;
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
                    Counter++;
                } else if (isCarChanged) {
                    binding.carImage.setImageResource(R.drawable.car);
                    isCarChanged = false;
                    Counter--;
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
                    Counter++;
                } else if (isCGamesChanged) {
                    binding.cgamesImage.setImageResource(R.drawable.cgames_selected);
                    isCGamesChanged = false;
                    Counter--;
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
                    Counter++;
                } else if (isTGamesChanged) {
                    binding.tgamesImage.setImageResource(R.drawable.tgames);
                    isTGamesChanged = false;
                    Counter--;
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
                    Counter++;
                } else if (isChessChanged) {
                    binding.chessImage.setImageResource(R.drawable.chess);
                    isChessChanged = false;
                    Counter--;
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
                    Counter++;
                } else if (isCookChanged) {
                    binding.cookImage.setImageResource(R.drawable.cook);
                    isCookChanged = false;
                    Counter--;
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
                    Counter++;
                } else if (isFishChanged) {
                    binding.fishImage.setImageResource(R.drawable.fish);
                    isFishChanged = false;
                    Counter--;
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
                    Counter++;
                } else if (isTravChanged) {
                    binding.travImage.setImageResource(R.drawable.trav);
                    isTravChanged = false;
                    Counter--;
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
                    Counter++;
                } else if (isDanceChanged) {
                    binding.danceImage.setImageResource(R.drawable.dance);
                    isDanceChanged = false;
                    Counter--;
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
                    Counter++;
                } else if (isSexChanged) {
                    binding.sexImage.setImageResource(R.drawable.sex);
                    isSexChanged = false;
                    Counter--;
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
                    Counter++;
                } else if (isRockChanged) {
                    binding.rockImage.setImageResource(R.drawable.rock);
                    isRockChanged = false;
                    Counter--;
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
                    Counter++;
                } else if (isRRapChanged) {
                    binding.rrapImage.setImageResource(R.drawable.rrap);
                    isRRapChanged = false;
                    Counter--;
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
                    Counter++;
                } else if (isHuntChanged) {
                    binding.huntImage.setImageResource(R.drawable.hunt);
                    isHuntChanged = false;
                    Counter--;
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
                    Counter++;
                } else if (isFashChanged) {
                    binding.fashImage.setImageResource(R.drawable.fash);
                    isFashChanged = false;
                    Counter--;
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
                    Counter++;
                } else if (isGuitarChanged) {
                    binding.guitarImage.setImageResource(R.drawable.guitar);
                    isGuitarChanged = false;
                    Counter--;
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
                    Counter++;
                } else if (isPianoChanged) {
                    binding.pianoImage.setImageResource(R.drawable.piano);
                    isPianoChanged = false;
                    Counter--;
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
                    Counter++;
                } else if (isPhotoChanged) {
                    binding.photoImage.setImageResource(R.drawable.photo);
                    isPhotoChanged = false;
                    Counter--;
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
                    Counter++;
                } else if (isPoliticsChanged) {
                    binding.politicsImage.setImageResource(R.drawable.politics);
                    isPoliticsChanged = false;
                    Counter--;
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
                    Counter++;
                } else if (isPhilChanged) {
                    binding.philImage.setImageResource(R.drawable.phil);
                    isPhilChanged = false;
                    Counter--;
                }
                activateGoNextButton();
            }
        });

        binding.goNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringBuilder interestsStringBuilder = new StringBuilder();
                String interestsString = interestsStringBuilder.toString();
                binding.goNextButton.setText(interestsString);
                if(!isGoNextActive)
                {
                    NotificationHelper.showCustomNotification(ChooseInterestsActivity.this, null, getString(R.string.choose_a_few_interests_message), getString(R.string.close), 0, 0, 0,0);
                } else if(Counter <= 5 )
                {
                    Users.updateUserHobbies(getUID(), getSelectedInterestsString());
                    Intent intent = new Intent(ChooseInterestsActivity.this, ChooseFindGenderActivity.class);
                    startActivity(intent);
                    //NotificationHelper.showCustomNotification(StartActivity, getString(R.string.welcome), getString(R.string.you_ve_been_successfully_registered_please_sign_in), getString(R.string.okay), 0, 0, 0,0);
                } else if (Counter > 5 ){
                    NotificationHelper.showCustomNotification(ChooseInterestsActivity.this, null, getString(R.string.select_nm_5_interests_message), getString(R.string.close), 0, 0, 0,0);
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

    private String getUID(){
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        return sharedPreferences.getString("UID", "");
    }

    private String getSelectedInterestsString() {
        StringBuilder selectedInterests = new StringBuilder();
        int selectedCount = 0;

        if (isClubsChanged) {
            selectedInterests.append("Clubs");
            selectedCount++;
        }
        if (isCodingChanged) {
            if (selectedCount > 0) selectedInterests.append(", ");
            selectedInterests.append("Coding");
            selectedCount++;
        }
        if (isSportChanged) {
            if (selectedCount > 0) selectedInterests.append(", ");
            selectedInterests.append("Sport");
            selectedCount++;
        }
        if (isVGamesChanged) {
            if (selectedCount > 0) selectedInterests.append(", ");
            selectedInterests.append("Video Games");
            selectedCount++;
        }
        if (isYogaChanged) {
            if (selectedCount > 0) selectedInterests.append(", ");
            selectedInterests.append("Yoga");
            selectedCount++;
        }
        if (isBooksChanged) {
            if (selectedCount > 0) selectedInterests.append(", ");
            selectedInterests.append("Books");
            selectedCount++;
        }
        if (isArtChanged) {
            if (selectedCount > 0) selectedInterests.append(", ");
            selectedInterests.append("Art");
            selectedCount++;
        }
        if (isMusicChanged) {
            if (selectedCount > 0) selectedInterests.append(", ");
            selectedInterests.append("Music");
            selectedCount++;
        }
        if (isTTChanged) {
            if (selectedCount > 0) selectedInterests.append(", ");
            selectedInterests.append("Tik-Tok");
            selectedCount++;
        }
        if (isYTChanged) {
            if (selectedCount > 0) selectedInterests.append(", ");
            selectedInterests.append("YouTube");
            selectedCount++;
        }
        if (isCarChanged) {
            if (selectedCount > 0) selectedInterests.append(", ");
            selectedInterests.append("Cars");
            selectedCount++;
        }
        if (isCGamesChanged) {
            if (selectedCount > 0) selectedInterests.append(", ");
            selectedInterests.append("Console Games");
            selectedCount++;
        }
        if (isTGamesChanged) {
            if (selectedCount > 0) selectedInterests.append(", ");
            selectedInterests.append("Table Games");
            selectedCount++;
        }
        if (isChessChanged) {
            if (selectedCount > 0) selectedInterests.append(", ");
            selectedInterests.append("Chess");
            selectedCount++;
        }
        if (isCookChanged) {
            if (selectedCount > 0) selectedInterests.append(", ");
            selectedInterests.append("Cooking");
            selectedCount++;
        }
        if (isFishChanged) {
            if (selectedCount > 0) selectedInterests.append(", ");
            selectedInterests.append("Fishing");
            selectedCount++;
        }
        if (isTravChanged) {
            if (selectedCount > 0) selectedInterests.append(", ");
            selectedInterests.append("Traveling");
            selectedCount++;
        }
        if (isDanceChanged) {
            if (selectedCount > 0) selectedInterests.append(", ");
            selectedInterests.append("Dance");
            selectedCount++;
        }
        if (isSexChanged) {
            if (selectedCount > 0) selectedInterests.append(", ");
            selectedInterests.append("Sex");
            selectedCount++;
        }
        if (isRockChanged) {
            if (selectedCount > 0) selectedInterests.append(", ");
            selectedInterests.append("Rock");
            selectedCount++;
        }
        if (isRRapChanged) {
            if (selectedCount > 0) selectedInterests.append(", ");
            selectedInterests.append("Russian Rap");
            selectedCount++;
        }
        if (isHuntChanged) {
            if (selectedCount > 0) selectedInterests.append(", ");
            selectedInterests.append("Hunting");
            selectedCount++;
        }
        if (isFashChanged) {
            if (selectedCount > 0) selectedInterests.append(", ");
            selectedInterests.append("Fashion");
            selectedCount++;
        }
        if (isGuitarChanged) {
            if (selectedCount > 0) selectedInterests.append(", ");
            selectedInterests.append("Guitar");
            selectedCount++;
        }
        if (isPianoChanged) {
            if (selectedCount > 0) selectedInterests.append(", ");
            selectedInterests.append("Piano");
            selectedCount++;
        }
        if (isPhotoChanged) {
            if (selectedCount > 0) selectedInterests.append(", ");
            selectedInterests.append("Photography");
            selectedCount++;
        }
        if (isPoliticsChanged) {
            if (selectedCount > 0) selectedInterests.append(", ");
            selectedInterests.append("Politics");
            selectedCount++;
        }
        if (isPhilChanged) {
            if (selectedCount > 0) selectedInterests.append(", ");
            selectedInterests.append("Philosophy");
            selectedCount++;
        }

        return selectedInterests.toString();
    }

}