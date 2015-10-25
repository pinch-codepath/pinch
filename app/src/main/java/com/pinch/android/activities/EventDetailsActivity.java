package com.pinch.android.activities;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.pinch.android.R;
import com.pinch.android.adapters.EventsArrayAdapter;
import com.pinch.android.dialogs.FacebookLoginDialog;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;

public class EventDetailsActivity extends AppCompatActivity implements FacebookLoginDialog.FacebookLoginDialogListener {

    private ImageView mIvPic;
    private Button mBtnSignUp;
    private Button mBtnLearnMore;
    private TextView mTvEventName;
    private TextView mTvEventDescription;
    private TextView mTvAddressLine1;
    private TextView mTvAddressLine2;
    private TextView mTvRequirements;

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
    }

    private void onSignupButtonClicked() {
        Toast.makeText(getApplicationContext(), "Sign Up", Toast.LENGTH_SHORT).show();
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
}
