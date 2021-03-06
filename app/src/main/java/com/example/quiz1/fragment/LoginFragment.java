package com.example.quiz1.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quiz1.HomeActivity;
import com.example.quiz1.R;
import com.example.quiz1.data.UserData;
import com.example.quiz1.helper.UserHelper;
import com.example.quiz1.models.User;

import java.util.ArrayList;
import java.util.Vector;

public class LoginFragment extends Fragment {

    EditText edtEmailAddress, edtPassword;
    Button btnLogin;
    UserData userData;

    private UserHelper userHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userHelper = new UserHelper(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        edtEmailAddress = view.findViewById(R.id.editEmailAddressLogin);
        edtPassword = view.findViewById(R.id.edtPasswordLogin);
        btnLogin = view.findViewById(R.id.buttonLogin);

        edtEmailAddress.setText("h.com");
        edtPassword.setText("H12");

        btnLogin.setOnClickListener( v -> {
            boolean flag = true;

            userHelper.open();
            userHelper.viewUsers();

            String email = edtEmailAddress.getText().toString();
            String password = edtPassword.getText().toString();

            if (email.isEmpty()) {
                Toast.makeText(getActivity(), "Email can't be empty!", Toast.LENGTH_LONG).show();
                flag = false;
            } else if (password.isEmpty()) {
                Toast.makeText(getActivity(), "Password can't be empty!", Toast.LENGTH_LONG).show();
                flag = false;
            } else {
                for (User check: UserData.getVectUser()) {
                    if (email.equals(check.getEmailAddress()) && password.equals(check.getPassword())) {
                        userData.setLoggedIn(check);
                        Intent intent = new Intent(getActivity(), HomeActivity.class);
                        Toast.makeText(getActivity(), "Login is successful!", Toast.LENGTH_LONG).show();
                        getActivity().startActivity(intent);
                        return;
                    }
                }
            }
            Toast.makeText(getActivity(), "User not found!", Toast.LENGTH_LONG).show();
        });
    }
}