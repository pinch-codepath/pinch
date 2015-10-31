package com.pinch.android.activities;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.pinch.android.PinchApplication;
import com.pinch.android.R;
import com.pinch.android.dialogs.FacebookLoginDialog;
import com.pinch.android.events.RefreshUserFavoritesEvent;
import com.pinch.android.events.RefreshUserSignupsEvent;
import com.pinch.android.remote.EventSignUpTask;
import com.pinch.android.remote.FavoriteOrgTask;
import com.pinch.android.remote.HasFavoriteForOrgTask;
import com.pinch.android.remote.HasSignedUpForEventTask;
import com.pinch.android.remote.RemoveEventSignUpTask;
import com.pinch.android.remote.UnfavoriteOrgTask;
import com.pinch.backend.eventEndpoint.model.Event;
import com.pinch.backend.favoriteEndpoint.model.Favorite;
import com.pinch.backend.signUpEndpoint.model.SignUp;
import com.pinch.backend.userEndpoint.model.User;
import com.squareup.picasso.Picasso;
import butterknife.Bind;
import butterknife.ButterKnife;
import static com.pinch.android.util.SharedPreferenceUtil.getSharedPreferenceLongFromKey;

public class EventDetailsActivity extends AppCompatActivity
        implements FacebookLoginDialog.FacebookLoginDialogListener,
        EventSignUpTask.EventSignUpTaskResultListener,
        RemoveEventSignUpTask.RemoveEventSignUpTaskResultListener,
        HasSignedUpForEventTask.HasSignedUpForEventResultListener,
        UnfavoriteOrgTask.UnfavoriteOrgResultListener,
        FavoriteOrgTask.FavoriteOrgResultListener,
        HasFavoriteForOrgTask.HasFavoriteForOrgResultListener {

    private ImageView mIvPic;
    private Button mBtnSignUp;
    private Button mBtnFavoriteOrg;
    private TextView mTvEventDate;
    private TextView mTvEventTime;
    private TextView mTvEventDescription;
    private TextView mTvAddressLine1;
    private TextView mTvAddressLine2;
    private TextView mTvRequirements;
    private TextView mTvOrgName;
    private TextView mTvOrgAddress;
    private TextView mTvOrgPhone;
    private TextView mTvOrgUrl;

    private boolean signedUp;
    private SignUp signUp;
    private Favorite favorite;

    private long eventId;
    private String eventTitle;
    private String eventDescription;
    private String eventAddressStreet;
    private String eventAddressCity;
    private String eventAddressState;
    private String eventAddressNeighborhood;
    private Long eventAddressZip;
    private String eventSkill1;
    private String eventSkill2;
    private String eventSkill3;
    private String eventUrl;
    private String eventDate;
    private String eventTime;
    private String eventOrgName;
    private long eventOrgId;
    private String lastClick;
    private String eventOrgAddress;
    private String eventOrgPhone;
    private String eventOrgUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_event_details);

        eventId = (Long) getIntent().getLongExtra("eventId", 0);
        eventTitle = (String)getIntent().getStringExtra("eventTitle");
        eventDescription = (String)getIntent().getStringExtra("eventDescription");
        eventAddressStreet = (String)getIntent().getStringExtra("eventAddressStreet");
        eventAddressCity = (String)getIntent().getStringExtra("eventAddressCity");
        eventAddressState = (String)getIntent().getStringExtra("eventAddressState");
        eventAddressNeighborhood = (String)getIntent().getStringExtra("eventAddressNeighborHood");
        eventAddressZip = (Long)getIntent().getLongExtra("eventAddressZip", 0);
        eventSkill1 = (String)getIntent().getStringExtra("eventSkill1");
        eventSkill2 = (String)getIntent().getStringExtra("eventSkill2");
        eventSkill3 = (String)getIntent().getStringExtra("eventSkill3");
        eventUrl = (String)getIntent().getStringExtra("eventUrl");
        eventDate = (String)getIntent().getStringExtra("eventDate");
        eventTime = (String)getIntent().getStringExtra("eventTime");
        eventOrgName = (String)getIntent().getStringExtra("eventOrgName");
        eventOrgAddress = (String)getIntent().getStringExtra("eventOrgAddress");
        eventOrgPhone = (String)getIntent().getStringExtra("eventOrgPhone");
        eventOrgId = getIntent().getLongExtra("eventOrgId", 0);
        eventOrgUrl = (String)getIntent().getStringExtra("eventOrgUrl");

        setupToolbar();
        setupViewObjects();
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        if(ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setTitle(eventTitle);
            Drawable upArrow = ContextCompat.getDrawable(getApplicationContext(), R.drawable.abc_ic_ab_back_mtrl_am_alpha);
            upArrow.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
            ab.setHomeAsUpIndicator(upArrow);
        }

    }

    private void setupViewObjects() {
        mIvPic = (ImageView) findViewById(R.id.ivPic);
        mBtnSignUp = (Button) findViewById(R.id.btnSignUp);
        mTvEventDate = (TextView) findViewById(R.id.tvEventDate);
        mTvEventTime = (TextView) findViewById(R.id.tvEventTime);
        mTvAddressLine1 = (TextView) findViewById(R.id.tvAddressLine1);
        mTvAddressLine2 = (TextView) findViewById(R.id.tvAddressLine2);
        mTvRequirements = (TextView) findViewById(R.id.tvRequirements);
        mTvEventDescription = (TextView) findViewById(R.id.tvEventDescription);
        mTvOrgName = (TextView) findViewById(R.id.tvOrgName);
        mTvOrgAddress = (TextView) findViewById(R.id.tvOrgAddress);
        mTvOrgPhone = (TextView) findViewById(R.id.tvOrgPhoneNumber);
        mTvOrgUrl = (TextView) findViewById(R.id.tvOrgUrl);
        mBtnFavoriteOrg = (Button) findViewById(R.id.btFollow);

        mTvEventDate.setText(this.eventDate);
        mTvEventTime.setText(this.eventTime);
        mTvAddressLine1.setText(this.eventAddressStreet);
        mTvAddressLine2.setText(this.eventAddressCity + ", " + this.eventAddressState);
        mTvEventDescription.setText(this.eventDescription);
        mTvRequirements.setText(this.eventSkill1 + ", " + this.eventSkill2 + ", " + this.eventSkill3);
        mTvOrgName.setText(this.eventOrgName);
        mTvOrgAddress.setText("Address:\n" + this.eventOrgAddress);
        mTvOrgPhone.setText("Ph:(" + this.eventOrgPhone.substring(0, 3) + ")" + this.eventOrgPhone.substring(3,6) + "-" + this.eventOrgPhone.substring(6,10));
        mTvOrgUrl.setText("Website: " + this.eventOrgUrl);

        mBtnSignUp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (AccessToken.getCurrentAccessToken() != null) {
                    onSignupButtonClicked();
                } else {
                    lastClick = "sign up";
                    openFacebookLoginDialog();
                }
            }
        });

        if(mBtnFavoriteOrg != null) {
            mBtnFavoriteOrg.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if(AccessToken.getCurrentAccessToken() != null) {
                        onFavoriteButtonClicked();
                    }
                    else {
                        lastClick = "favorite";
                        openFacebookLoginDialog();
                    }
                }
            });
        }

//        mIvPic.setImageResource(0);
        Picasso.with(this).load(eventUrl).fit().centerCrop().into(mIvPic);

        if(AccessToken.getCurrentAccessToken() != null) {
            User user = getUser();
            if(user != null) {
                SignUp signUp = new SignUp();
                signUp.setUserId(user.getId());
                signUp.setEventId(eventId);
                new HasSignedUpForEventTask(this).execute(signUp);

                Favorite favorite = new Favorite();
                favorite.setUserId(user.getId());
                favorite.setOrganizationId(eventOrgId);
                new HasFavoriteForOrgTask(this).execute(favorite);
            } else {
                Toast.makeText(getApplicationContext(), "Sign Up failed!! Try again in sometime.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void onFavoriteButtonClicked() {
        if(favorite != null) {
            new UnfavoriteOrgTask(this).execute(favorite.getId());
        } else {
            User user = getUser();
            if(user != null) {
                Favorite favorite = new Favorite();
                favorite.setUserId(user.getId());
                favorite.setOrganizationId(eventOrgId);
                new FavoriteOrgTask(this).execute(favorite);
            }
        }
    }

    private User getUser() {
        PinchApplication pinchApplication = (PinchApplication) getApplication();
        return pinchApplication.getUser();
    }

    private void onSignupButtonClicked() {
        if(signUp != null) {
            new RemoveEventSignUpTask(this).execute(signUp.getId());
        } else {
            User user = getUser();
            if(user != null) {
                   SignUp signUp = new SignUp();
                    signUp.setUserId(user.getId());
                    signUp.setEventId(eventId);
                new EventSignUpTask(this).execute(signUp);
            }
        }
    }

    private void openFacebookLoginDialog() {
        FragmentManager fm = getSupportFragmentManager();
        FacebookLoginDialog facebookLoginDialog = FacebookLoginDialog.newInstance();
        facebookLoginDialog.show(fm, "dialog_login_facebook");
    }

    @Override
    public void onLoginSuccess() {
        if(AccessToken.getCurrentAccessToken() != null) {
            if(lastClick.equals("sign up")) {
                onSignupButtonClicked();
            } else {
                onFavoriteButtonClicked();
            }
        }
    }

    @Override
    public void onEventSignUp(SignUp signUp) {
        mBtnSignUp.setText("Signed Up!!");
        this.signUp = signUp;
        Toast.makeText(getApplicationContext(), "Signed Up!!", Toast.LENGTH_SHORT).show();
        PinchApplication application = (PinchApplication)getApplication();
        application.bus.post(new RefreshUserSignupsEvent());
    }

    @Override
    public void onRemoveEventSignUp() {
        mBtnSignUp.setText("Sign Up");
        this.signUp = null;
        Toast.makeText(getApplicationContext(), "Remove sign up!!", Toast.LENGTH_SHORT).show();
        PinchApplication application = (PinchApplication)getApplication();
        application.bus.post(new RefreshUserSignupsEvent());
    }

    @Override
    public void onHasSignUpResult(SignUp signedUp) {
        this.signUp = signedUp;
        if(signUp != null) {
            mBtnSignUp.setText("Signed Up!!");
        }
    }

    @Override
    public void onUnfavoriteOrg() {
        mBtnFavoriteOrg.setText("Follow");
        this.favorite = null;
        Toast.makeText(getApplicationContext(), "Not following org!!", Toast.LENGTH_SHORT).show();
        PinchApplication application = (PinchApplication)getApplication();
        application.bus.post(new RefreshUserFavoritesEvent());
    }

    @Override
    public void onFavoriteOrg(Favorite v) {
        mBtnFavoriteOrg.setText("Following!");
        this.favorite = v;
        Toast.makeText(getApplicationContext(), "Following Org!!", Toast.LENGTH_SHORT).show();
        PinchApplication application = (PinchApplication)getApplication();
        application.bus.post(new RefreshUserFavoritesEvent());
    }

    @Override
    public void onHasFavoriteResult(Favorite favorite) {
        this.favorite = favorite;
        if(favorite != null && mBtnFavoriteOrg != null) {
            mBtnFavoriteOrg.setText("Following!");
        }
    }
}
