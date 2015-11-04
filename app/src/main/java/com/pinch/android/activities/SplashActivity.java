package com.pinch.android.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityOptionsCompat;

import com.pinch.android.R;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SplashActivity extends Activity {

    @Bind(R.id.tvShimmer)
    ShimmerTextView tvShimmer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        //setupWindowAnimations();

        Shimmer shimmer = new Shimmer();
        shimmer
                .setRepeatCount(1)
                .setDuration(3000)
                .setAnimatorListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        tvShimmer.setTextColor(getResources().getColor(android.R.color.white));
                        super.onAnimationEnd(animation);
                    }
                })
                .start(tvShimmer);

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent i = new Intent(SplashActivity.this, MainActivity.class);
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation(SplashActivity.this, tvShimmer, "toolbar");
                startActivity(i, options.toBundle());
                overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
                // close this activity
                finish();
            }
        }, 8000);

    }
}
