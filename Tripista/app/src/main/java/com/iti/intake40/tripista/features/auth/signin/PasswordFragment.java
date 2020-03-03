package com.iti.intake40.tripista.features.auth.signin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.iti.intake40.tripista.R;
import com.iti.intake40.tripista.core.FireBaseCore;

import static com.iti.intake40.tripista.features.auth.signin.SigninActivity.EMAIL_ARG;

public class PasswordFragment extends Fragment implements ViewInterface {
    private String email;
    private String password;
    private FireBaseCore core;
    private PresenterInterface presenterInterface;
    private TextInputEditText et_password;
    private FloatingActionButton signIn;
    public PasswordFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        core =FireBaseCore.getInstance();
        presenterInterface = new SigninPresenter(this,core);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_password, container, false);
        et_password=view.findViewById(R.id.et_password_login);
        signIn =view.findViewById(R.id.sign_in);
        // Inflate the layout for this fragment
        email= getArguments().getString(EMAIL_ARG);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                password = et_password.getText().toString();
                if(!TextUtils.isEmpty(email)&&!TextUtils.isEmpty(password))
                {
                    presenterInterface.signIn(email,password);
                }
            }
        });

        return view;
    }

    @Override
    public void sentMessage(int message) {
        Toast.makeText(getActivity(),getResources().getString(message),Toast.LENGTH_LONG).show();

    }

    @Override
    public void sentError(int message) {
        Toast.makeText(getActivity(),getResources().getString(message),Toast.LENGTH_LONG).show();

    }

    @Override
    public void changeFragment() {

    }
}
