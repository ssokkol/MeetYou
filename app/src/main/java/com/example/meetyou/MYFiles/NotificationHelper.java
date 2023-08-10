package com.example.meetyou.MYFiles;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
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
        titleTextView.setTextColor(textColor != 0 ? ContextCompat.getColor(context, textColor) : ContextCompat.getColor(context, defaultTextColor));

        TextView messageTextView = dialogView.findViewById(R.id.messageTextView);
        messageTextView.setText(message != null ? message : defaultText);
        messageTextView.setTextColor(textColor != 0 ? ContextCompat.getColor(context, textColor) : ContextCompat.getColor(context, defaultTextColor));

        AppCompatButton waitButton = dialogView.findViewById(R.id.waitButton);
        waitButton.setText(buttonLabel != null ? buttonLabel : defaultButtonLabel);
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

    public static void showCustomNotificationWithTwoButtons(Context context, String title, String message, String buttonLabel, String secondButtonLabel,
                                              int backgroundColor, int textColor, int buttonBackgroundColor, int buttonTextColor,
                                                            ButtonClickListener okayButtonClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.two_buttons_custom_notification_layout, null);
        builder.setView(dialogView);

        View overlayView = dialogView.findViewById(R.id.overlayView);
        overlayView.setAlpha(0);

        RelativeLayout mainLayout = dialogView.findViewById(R.id.main_layout);
        mainLayout.setBackgroundResource(backgroundColor != 0 ? backgroundColor : defaultBackgroundColor);

        TextView titleTextView = dialogView.findViewById(R.id.titleTextView);
        titleTextView.setText(title != null ? title : defaultTitle);
        titleTextView.setTextColor(textColor != 0 ? ContextCompat.getColor(context, textColor) : ContextCompat.getColor(context, defaultTextColor));

        TextView messageTextView = dialogView.findViewById(R.id.messageTextView);
        messageTextView.setText(message != null ? message : defaultText);
        messageTextView.setTextColor(textColor != 0 ? ContextCompat.getColor(context, textColor) : ContextCompat.getColor(context, defaultTextColor));

        AppCompatButton waitButton = dialogView.findViewById(R.id.waitButton);
        waitButton.setText(buttonLabel != null ? buttonLabel : defaultButtonLabel);
        waitButton.setTextColor(buttonTextColor != 0 ? ContextCompat.getColor(context, buttonTextColor) : ContextCompat.getColor(context, defaultButtonTextColor));
        waitButton.setBackgroundResource(buttonBackgroundColor != 0 ? buttonBackgroundColor : defaultButtonBackgroundColor);

        AppCompatButton okayButton = dialogView.findViewById(R.id.okay_Button);
        okayButton.setText(secondButtonLabel != null ? secondButtonLabel : defaultButtonLabel);
        okayButton.setTextColor(buttonTextColor != 0 ? ContextCompat.getColor(context, buttonTextColor) : ContextCompat.getColor(context, defaultButtonTextColor));
        okayButton.setBackgroundResource(buttonBackgroundColor != 0 ? buttonBackgroundColor : defaultButtonBackgroundColor);

        waitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeNotificationWithAnimation(dialogView);
            }
        });

        okayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeNotificationWithAnimation(dialogView);
                if (okayButtonClickListener != null) {
                    okayButtonClickListener.onButtonClick();
                }
            }
        });

        dialog = builder.create();
        dialog.show();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        animateScreenDim(overlayView);
    }
    public static void showMatchNotification(Context context, String title, String buttonLabel,
                                              int backgroundColor, int textColor, int buttonBackgroundColor, int buttonTextColor) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.match_notification, null);
        builder.setView(dialogView);

        View overlayView = dialogView.findViewById(R.id.overlayView);
        overlayView.setAlpha(0);

        RelativeLayout mainLayout = dialogView.findViewById(R.id.main_layout);
        mainLayout.setBackgroundResource(backgroundColor != 0 ? backgroundColor : defaultBackgroundColor);

        TextView titleTextView = dialogView.findViewById(R.id.titleTextView);
        titleTextView.setText(title != null ? title : "Match!");
        titleTextView.setTextColor(textColor != 0 ? ContextCompat.getColor(context, textColor) : ContextCompat.getColor(context, defaultTextColor));

        AppCompatButton waitButton = dialogView.findViewById(R.id.waitButton);
        waitButton.setText(buttonLabel != null ? buttonLabel : "Close");
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

    public static void showHeart(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.heart_layout, null);
        builder.setView(dialogView);

        ImageView heartImage = dialogView.findViewById(R.id.heart_image);

        dialog = builder.create();
        dialog.show();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        //RelativeLayout mainLayout = dialogView.findViewById(R.id.main_layout);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) heartImage.getLayoutParams();
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        heartImage.setLayoutParams(layoutParams);

        animateHeart(heartImage, dialog);
    }

    private static void animateHeart(View view, AlertDialog dialog) {
        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(view, "scaleX", 0.0f, 1.0f);
        scaleXAnimator.setDuration(400);
        scaleXAnimator.setInterpolator(new AccelerateDecelerateInterpolator());

        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(view, "scaleY", 0.0f, 1.0f);
        scaleYAnimator.setDuration(400);
        scaleYAnimator.setInterpolator(new AccelerateDecelerateInterpolator());

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(scaleXAnimator, scaleYAnimator);
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {}

            @Override
            public void onAnimationEnd(Animator animator) {
                view.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                    }
                }, 500); // Hide the dialog after 1 second (adjust this value for 1-2 pulses)
            }

            @Override
            public void onAnimationCancel(Animator animator) {}

            @Override
            public void onAnimationRepeat(Animator animator) {}
        });

        animatorSet.start();
    }

    public interface ButtonClickListener {
        void onButtonClick();
    }
}