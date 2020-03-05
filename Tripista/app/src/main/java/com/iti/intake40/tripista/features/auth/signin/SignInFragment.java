package com.iti.intake40.tripista.features.auth.signin;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.iti.intake40.tripista.R;
import com.iti.intake40.tripista.core.FireBaseCore;
import com.iti.intake40.tripista.features.auth.Delegate;
import com.iti.intake40.tripista.features.auth.home.HomeActivity;
import com.iti.intake40.tripista.features.auth.signup.SignUp;


public class SignInFragment extends Fragment implements ViewInterface {

    private static final String TAG = "signinFragment";

    private FloatingActionButton nextBtn;
    private TextInputEditText etEmailPhone;
    private String inputData;
    private Delegate delegate;
    private TextView signupLink;

    private SigninPresenter signinPresenter;

    //facebook auth
    private LoginButton loginButton;
    private CallbackManager callbackManager;

    public SignInFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);
        etEmailPhone = view.findViewById(R.id.et_user_email_phone);
        nextBtn = view.findViewById(R.id.next_button);
        signupLink = view.findViewById(R.id.txt_sign_up_link);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoPasswordFragment();
            }
        });
        signupLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoSignUpActivity();
            }
        });

        signinPresenter = new SigninPresenter(getActivity(), this, FireBaseCore.getInstance());

        loginButton = view.findViewById(R.id.login_button);
        callbackManager = CallbackManager.Factory.create();

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "onSuccess: ");
                signinPresenter.handleFacebookSignin(loginResult.getAccessToken());
                sentMessage(R.string.logged_in_successfuly);
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "onCancel: ");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "onError: ");
            }
        });

        return view;
    }

    private void gotoSignUpActivity() {
        startActivity(new Intent(getActivity(), SignUp.class));
    }

    private boolean isEmailValid(String emailAddress) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return emailAddress.matches(regex);
    }

    public static boolean isPhoneValid(String phone) {
        if (phone.length() != 13)
            return false;
        String regex = "^\\+?[0-9. ()-]{9,25}$";
        return phone.matches(regex);
    }

    public void gotoPasswordFragment() {
        inputData = etEmailPhone.getText().toString();
        if (!TextUtils.isEmpty(inputData)) {
            if (isEmailValid(inputData)) {
                delegate = (Delegate) getActivity();
                Toast.makeText(getActivity(), "email", Toast.LENGTH_LONG).show();
                delegate.setData(inputData);

            } else if (isPhoneValid(inputData)) {
                Toast.makeText(getActivity(), "phone", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getActivity(), "this is not phone or email please enter correct data", Toast.LENGTH_LONG).show();

            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void sentMessage(int message) {
        Toast.makeText(getContext(), getActivity().getResources().getText(message), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void sentError(int message) {

    }

    @Override
    public void changeFragment(FirebaseUser user) {
        //update UI when login successful
        //go to home

        Intent gotoHomeIntent = new Intent(getContext(), HomeActivity.class);
        //gotoHomeIntent.putExtra("user", user);
        startActivity(gotoHomeIntent);
        getActivity().finish();
    }
}
