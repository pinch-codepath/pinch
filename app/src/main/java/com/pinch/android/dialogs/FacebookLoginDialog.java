package com.pinch.android.dialogs;


import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.pinch.android.PinchApplication;
import com.pinch.android.R;
import com.pinch.android.remote.InsertIfMissingUserTask;
import com.pinch.android.util.UserUtil;
import com.pinch.backend.userEndpoint.model.User;

import org.json.JSONObject;

import static com.pinch.android.util.SharedPreferenceUtil.writeToSharedPreferences;

public class FacebookLoginDialog extends DialogFragment implements InsertIfMissingUserTask.InsertIfMissingUserResultListener {

    CallbackManager callbackManager;

    public FacebookLoginDialog() {
    }

    @Override
    public void onUserUpdate(User user) {
        FacebookLoginDialogListener listener = (FacebookLoginDialogListener) getActivity();
        if(user != null) {
            PinchApplication pinchApplication = (PinchApplication) this.getActivity().getApplication();
            pinchApplication.setUser(user);
            writeToSharedPreferences(getActivity(), getString(R.string.user_id), user.getId());
            writeToSharedPreferences(getActivity(), getString(R.string.auth_source), user.getAuthSource());
            writeToSharedPreferences(getActivity(), getString(R.string.auth_source_id), user.getId());
            writeToSharedPreferences(getActivity(), getString(R.string.user_name), user.getName());
            listener.onLoginSuccess();
        }
        dismiss();
    }

    public interface FacebookLoginDialogListener {
        void onLoginSuccess();
    }

    public static FacebookLoginDialog newInstance() {
        return new FacebookLoginDialog();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);

        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));

        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_login_facebook, container);

        callbackManager = CallbackManager.Factory.create();

        // Other app specific specialization
        // loginButton.setReadPermissions("user_friends");

        // Callback registration
        LoginButton loginButton = (LoginButton) view.findViewById(R.id.btLogin);
        loginButton.setFragment(this);
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject jsonObject, GraphResponse graphResponse) {
                        UserUtil.getUserFromGraphResponse(jsonObject, FacebookLoginDialog.this);
                    }
                }).executeAsync();
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });

        ImageView cancelButton = (ImageView) view.findViewById(R.id.btCancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
