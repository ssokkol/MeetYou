package com.example.meetyou.MYFiles;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;

import com.example.meetyou.R;

public class NotificationHelper {
    private static AlertDialog dialog;
    private static String defaultTitle = "Oops!";
    private static String defaultButtonLabel = "Wait";
    private static String defaultText = "Notify text is equal \"null\"";

    private static int defaultBackgroundColor = R.drawable.button_background_blue;
    private static int defaultButtonBackgroundColor = R.drawable.button_background;
    private static int defaultTextColor = R.color.white;
    private static int defaultButtonTextColor = R.color.black;

    public static void showCustomNotification(Context context, String title, String message, String buttonLabel,
                                              int backgroundColor, int textColor, int buttonBackgroundColor, int buttonTextColor) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.custom_notification_layout, null);
        builder.setView(dialogView);

        View overlayView = dialogView.findViewById(R.id.overlayView);
        overlayView.setAlpha(0);

        RelativeLayout mainLayout = dialogView.findViewById(R.id.main_layout);
        mainLayout.setBackgroundResource(backgroundColor != 0 ? backgroundColor : defaultBackgroundColor);

        TextView titleTextView = dialogView.findViewById(R.id.titleTextView);
        titleTextView.setText(title != null ? title : defaultTitle);
        //titleTextView.setAllCaps(false);
        titleTextView.setTextColor(textColor != 0 ? ContextCompat.getColor(context, textColor) : ContextCompat.getColor(context, defaultTextColor));

        TextView messageTextView = dialogView.findViewById(R.id.messageTextView);
        messageTextView.setText(message != null ? message : defaultText);
        //messageTextView.setAllCaps(false);
        messageTextView.setTextColor(textColor != 0 ? ContextCompat.getColor(context, textColor) : ContextCompat.getColor(context, defaultTextColor));

        AppCompatButton waitButton = dialogView.findViewById(R.id.waitButton);
        waitButton.setText(buttonLabel != null ? buttonLabel : defaultButtonLabel);
        //waitButton.setAllCaps(false);
        waitButton.setTextColor(buttonTextColor != 0 ? ContextCompat.getColor(context, buttonTextColor) : ContextCompat.getColor(context, defaultButtonTextColor));
        waitButton.setBackgroundResource(buttonBackgroundColor != 0 ? buttonBackgroundColor : defaultButtonBackgroundColor);

        waitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeNotificationWithAnimation(dialogView);
            }
        });

        dialog = builder.create();
        dialog.show();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        animateScreenDim(overlayView);
    }

    private static void closeNotificationWithAnimation(View dialogView) {
        if (dialogView == null) {
            return;
        }

        float initialTranslationY = 0f;
        float finalTranslationY = dialogView.getHeight() * 0.5f; // Move the dialog down during the animation
        float initialScale = 1f;
        float finalScale = 0f;

        ObjectAnimator translateAnimation = ObjectAnimator.ofFloat(
                dialogView.findViewById(R.id.matter_layout),
                "translationY",
                initialTranslationY,
                finalTranslationY
        );
        translateAnimation.setDuration(300);

        ObjectAnimator scaleAnimationX = ObjectAnimator.ofFloat(
                dialogView.findViewById(R.id.matter_layout),
                "scaleX",
                initialScale,
                finalScale
        );
        scaleAnimationX.setDuration(300);

        ObjectAnimator scaleAnimationY = ObjectAnimator.ofFloat(
                dialogView.findViewById(R.id.matter_layout),
                "scaleY",
                initialScale,
                finalScale
        );
        scaleAnimationY.setDuration(300);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(translateAnimation, scaleAnimationX, scaleAnimationY);

        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                dialog.dismiss();
            }
        });

        animatorSet.start();
    }


    private static void animateScreenDim(View overlayView) {
        ObjectAnimator alphaAnimation = ObjectAnimator.ofFloat(
                overlayView,
                "alpha",
                0f,
                0.8f
        );
        alphaAnimation.setDuration(350);
        alphaAnimation.start();
    }
}
