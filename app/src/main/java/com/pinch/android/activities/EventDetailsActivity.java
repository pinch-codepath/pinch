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

public class EventDetailsActivity extends AppCompatActivity
        implements FacebookLoginDialog.FacebookLoginDialogListener,
        EventSignUpTask.EventSignUpTaskResultListener,
        RemoveEventSignUpTask.RemoveEventSignUpTaskResultListener,
        HasSignedUpForEventTask.HasSignedUpForEventResultListener,
        UnfavoriteOrgTask.UnfavoriteOrgResultListener,
        FavoriteOrgTask.FavoriteOrgResultListener,
        HasFavoriteForOrgTask.HasFavoriteForOrgResultListener {

    @Bind(R.id.ivPic)
    ImageView mIvPic;
    @Bind(R.id.ivMap)
    ImageView mIvMap;
    @Bind(R.id.btnSignUp)
    Button mBtnSignUp;
    @Bind(R.id.btnFavoriteOrg)
    Button mBtnFavoriteOrg;
    @Bind(R.id.btnLearnMore)
    Button mBtnLearnMore;
    @Bind(R.id.tvEventName)
    TextView mTvEventName;
    @Bind(R.id.tvEventDate)
    TextView mTvEventDate;
    @Bind(R.id.tvEventTime)
    TextView mTvEventTime;
    @Bind(R.id.tvEventDescription)
    TextView mTvEventDescription;
    @Bind(R.id.tvAddressLine1)
    TextView mTvAddressLine1;
    @Bind(R.id.tvAddressLine2)
    TextView mTvAddressLine2;
    @Bind(R.id.tvRequirements1)
    TextView mTvRequirements1;
    @Bind(R.id.tvRequirements2)
    TextView mTvRequirements2;
    @Bind(R.id.tvRequirements3)
    TextView mTvRequirements3;
    private SignUp signUp;
    private Favorite favorite;

    Event e;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
        ButterKnife.bind(this);
        eventId = getIntent().getLongExtra("eventId", 0);
        eventTitle = getIntent().getStringExtra("eventTitle");
        eventDescription = getIntent().getStringExtra("eventDescription");
        eventAddressStreet = getIntent().getStringExtra("eventAddressStreet");
        eventAddressCity = getIntent().getStringExtra("eventAddressCity");
        eventAddressState = getIntent().getStringExtra("eventAddressState");
        eventAddressNeighborhood = getIntent().getStringExtra("eventAddressNeighborHood");
        eventAddressZip = getIntent().getLongExtra("eventAddressZip", 0);
        eventSkill1 = getIntent().getStringExtra("eventSkill1");
        eventSkill2 = getIntent().getStringExtra("eventSkill2");
        eventSkill3 = getIntent().getStringExtra("eventSkill3");
        eventUrl = getIntent().getStringExtra("eventUrl");
        eventDate = getIntent().getStringExtra("eventDate");
        eventTime = getIntent().getStringExtra("eventTime");
        eventOrgName = getIntent().getStringExtra("eventOrgName");
        eventOrgId = getIntent().getLongExtra("eventOrgId", 0);
        setupViewObjects();
    }

    private void setupViewObjects() {
        mTvEventName.setText(this.eventTitle);
        mTvEventDate.setText(this.eventDate);
        mTvEventTime.setText(this.eventTime);
        mTvAddressLine1.setText(this.eventAddressStreet);
        mTvAddressLine2.setText(this.eventAddressCity + ", " + this.eventAddressState);
        mTvEventDescription.setText(this.eventDescription);
        mTvRequirements1.setText("- " + this.eventSkill1);
        mTvRequirements2.setText("- " + this.eventSkill2);
        mTvRequirements3.setText("- " + this.eventSkill3);

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

        mBtnLearnMore.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Learn More", Toast.LENGTH_SHORT).show();
            }
        });

        String imageUrl = "https://style.codeforamerica.org/media/images/big-data.jpg";
//        mIvPic.setImageResource(0);
        Picasso.with(this).load(eventUrl).fit().centerCrop().into(mIvPic);

        String mapUrl = "http://pcad.lib.washington.edu/media/geo-images/gmap/19053.png";
        Picasso.with(this).load(mapUrl).fit().centerCrop().into(mIvMap);

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
        mBtnFavoriteOrg.setText("Favorite Org");
        this.favorite = null;
        Toast.makeText(getApplicationContext(), "Not following org!!", Toast.LENGTH_SHORT).show();
        PinchApplication application = (PinchApplication)getApplication();
        application.bus.post(new RefreshUserFavoritesEvent());
    }

    @Override
    public void onFavoriteOrg(Favorite v) {
        mBtnFavoriteOrg.setText("Following Org!!");
        this.favorite = v;
        Toast.makeText(getApplicationContext(), "Following Org!!", Toast.LENGTH_SHORT).show();
        PinchApplication application = (PinchApplication)getApplication();
        application.bus.post(new RefreshUserFavoritesEvent());
    }

    @Override
    public void onHasFavoriteResult(Favorite favorite) {
        this.favorite = favorite;
        if(favorite != null) {
            mBtnFavoriteOrg.setText("Following Org!!");
        }
    }
}
