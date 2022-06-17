package com.example.quiz1.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.quiz1.R;
import com.example.quiz1.data.UserData;
import com.example.quiz1.helper.UserHelper;
import com.example.quiz1.models.User;

public class RegisterFragment extends Fragment {

    EditText edtEmailAddress, edtUsername, edtPhoneNum, edtPassword;
    Button btnRegister;
    UserData userData;

    private UserHelper userHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        edtEmailAddress = view.findViewById(R.id.edtEmailRegister);
        edtUsername = view.findViewById(R.id.edtUsernameRegister);
        edtPhoneNum = view.findViewById(R.id.edtPhoneRegister);
        edtPassword = view.findViewById(R.id.edtPasswordRegister);
        btnRegister = view.findViewById(R.id.buttonRegister);
        userHelper = new UserHelper(getActivity());


        edtEmailAddress.setText("h.com");
        edtUsername.setText("hhh");
        edtPhoneNum.setText("123");
        edtPassword.setText("H12");

        btnRegister.setOnClickListener(v -> {
            boolean flag = true;

            userHelper.open();
            userHelper.viewUsers();

            String email = edtEmailAddress.getText().toString();
            String username = edtUsername.getText().toString();
            String phoneNum = edtPhoneNum.getText().toString();
            String password = edtPassword.getText().toString();

            if (email.isEmpty()) {
                Toast.makeText(getActivity(), "Email can't be empty!", Toast.LENGTH_LONG).show();
                flag = false;
            } else if (!email.endsWith(".com")){
                Toast.makeText(getActivity(), "Email is invalid!", Toast.LENGTH_LONG).show();
                flag = false;
            } else if (userData.getVectUser().contains(email)) {
                Toast.makeText(getActivity(), "Email must be unique!", Toast.LENGTH_LONG).show();
                flag = false;
            } else if (username.isEmpty()) {
                Toast.makeText(getActivity(), "Username can't be empty!", Toast.LENGTH_LONG).show();
                flag = false;
            }
            else if (username.length() < 3) {
                Toast.makeText(getActivity(), "Min 3 characters", Toast.LENGTH_LONG).show();
                flag = false;
            } else if(username.length() > 20) {
                Toast.makeText(getActivity(), "Max 20 characters", Toast.LENGTH_LONG).show();
                flag = false;
            }
            else if (userData.getVectUser().contains(username)) {
                Toast.makeText(getActivity(), "Username must be unique!", Toast.LENGTH_LONG).show();
                flag = false;
            } else if (phoneNum.isEmpty()){
                Toast.makeText(getActivity(), "Phone number can't be empty!", Toast.LENGTH_LONG).show();
                flag = false;
            } else if (password.isEmpty()) {
                Toast.makeText(getActivity(), "password can't be empty!", Toast.LENGTH_LONG).show();
                flag = false;
            } else if (password.matches("^[a-zA-Z]+$") || password.matches("^[0-9]+$")) {
                Toast.makeText(getActivity(), "Password is invalid!", Toast.LENGTH_LONG).show();
                flag = false;
            } else {
                for (User check: UserData.getVectUser()) {
                    if (username.equals(check.getUsername())) {
                        Toast.makeText(getActivity(), "Username already exist!", Toast.LENGTH_LONG).show();
                        flag = false;
                    } else if (email.equals(check.getEmailAddress())) {
                        Toast.makeText(getActivity(), "Email already exist!", Toast.LENGTH_LONG).show();
                        flag = false;
                    }
                }
            }

            if (flag == true) {
                userHelper.insertNew(email, username, phoneNum, password);
                Toast.makeText(getActivity(), "Succesfully Registered!", Toast.LENGTH_LONG).show();
            }
            userHelper.close();
        });
    }
}