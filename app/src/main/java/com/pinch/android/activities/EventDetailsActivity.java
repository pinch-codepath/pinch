package com.pinch.android.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.pinch.android.R;
import com.pinch.android.dialogs.FacebookLoginDialog;
import com.pinch.android.remote.EventSignUpTask;
import com.pinch.android.remote.HasSignedUpForEventTask;
import com.pinch.android.remote.RemoveEventSignUpTask;
import com.pinch.backend.signUpEndpoint.model.SignUp;
import com.squareup.picasso.Picasso;

import static com.pinch.android.util.SharedPreferenceUtil.getSharedPreferenceLongFromKey;

public class EventDetailsActivity extends AppCompatActivity
        implements FacebookLoginDialog.FacebookLoginDialogListener,
        EventSignUpTask.EventSignUpTaskResultListener,
        RemoveEventSignUpTask.RemoveEventSignUpTaskResultListener,
        HasSignedUpForEventTask.HasSignedUpForEventResultListener{

    private ImageView mIvPic;
    private Button mBtnSignUp;
    private Button mBtnLearnMore;
    private TextView mTvEventName;
    private TextView mTvEventDescription;
    private TextView mTvAddressLine1;
    private TextView mTvAddressLine2;
    private TextView mTvRequirements;
    private boolean signedUp;
    private SignUp signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
        setupViewObjects();
    }

    private void setupViewObjects() {
        mIvPic = (ImageView) findViewById(R.id.ivPic);
        mBtnSignUp = (Button) findViewById(R.id.btnSignUp);
        mBtnLearnMore = (Button) findViewById(R.id.btnLearnMore);
        mTvEventName = (TextView) findViewById(R.id.tvEventName);
        mTvAddressLine1 = (TextView) findViewById(R.id.tvAddressLine1);
        mTvAddressLine2 = (TextView) findViewById(R.id.tvAddressLine2);
        mTvRequirements = (TextView) findViewById(R.id.tvRequirements);
        mTvEventDescription = (TextView) findViewById(R.id.tvEventDescription);

        mBtnSignUp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(AccessToken.getCurrentAccessToken() != null) {
                    onSignupButtonClicked();
                }
                else {
                    openFacebookLoginDialog();
                }
            }
        });

        mBtnLearnMore.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Learn More", Toast.LENGTH_SHORT).show();
            }
        });

        String imageUrl = "https://style.codeforamerica.org/media/images/big-data.jpg";
//        mIvPic.setImageResource(0);
        Picasso.with(this).load(imageUrl).fit().centerCrop().into(mIvPic);

        signUp = new SignUp();
        Long userId = getSharedPreferenceLongFromKey(this, getString(R.string.user_id));
        signUp.setUserId(userId);
        signUp.setEventId(getEventId());

        new HasSignedUpForEventTask(this).execute(signUp);
    }

    private void onSignupButtonClicked() {
        if(signedUp) {
            new RemoveEventSignUpTask(this).execute(signUp);
        } else {
            new EventSignUpTask(this).execute(signUp);
        }
    }

    private long getEventId() {
        return 4505676780929024L;
    }

    private void openFacebookLoginDialog() {
        FragmentManager fm = getSupportFragmentManager();
        FacebookLoginDialog facebookLoginDialog = FacebookLoginDialog.newInstance();
        facebookLoginDialog.show(fm, "dialog_login_facebook");
    }

    @Override
    public void onLoginSuccess() {
        if(AccessToken.getCurrentAccessToken() != null) {
            onSignupButtonClicked();
        }
    }

    @Override
    public void onEventSignUp(SignUp signUp) {
        mBtnSignUp.setText("Signed Up!!");
        signedUp = true;
        Toast.makeText(getApplicationContext(), "Signed Up!!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRemoveEventSignUp() {
        mBtnSignUp.setText("Sign Up");
        signedUp = false;
        Toast.makeText(getApplicationContext(), "Remove sign up!!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onHasSignUpResult(boolean signedUp) {
        this.signedUp = signedUp;
        if(signedUp) {
            mBtnSignUp.setText("Signed Up!!");
        }
    }
}
