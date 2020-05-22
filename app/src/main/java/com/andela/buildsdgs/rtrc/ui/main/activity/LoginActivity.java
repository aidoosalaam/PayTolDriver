package com.andela.buildsdgs.rtrc.ui.main.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.andela.buildsdgs.rtrc.MainActivity;
import com.andela.buildsdgs.rtrc.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {

    private View parent_view;
    TextInputEditText usernameEditText;
    TextInputEditText passwordEditText;
    TextView btnForgotPassword;
    TextView btnloginSignUp;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        parent_view = findViewById(android.R.id.content);
        usernameEditText = findViewById(R.id.edt_login_username);
        passwordEditText = findViewById(R.id.edt_login_password);
        btnForgotPassword = findViewById(R.id.btn_forgot_password);
        btnloginSignUp = findViewById(R.id.btn_login_sign_up);
        btnLogin = findViewById(R.id.btn_login);
        performActions(R.id.btn_login);

    }

    private void performActions(int viewId){
        switch (viewId){
            case R.id.btn_login:
                btnLogin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);

                    }
                });

            case R.id.btn_login_sign_up:
                break;
            case R.id.btn_forgot_password:

            default:
                break;
        }
        btnForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(parent_view, "Forgot Password", Snackbar.LENGTH_SHORT).show();
            }
        });
        ((View) findViewById(R.id.btn_login_sign_up)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(parent_view, "Sign Up", Snackbar.LENGTH_SHORT).show();
            }
        });
    }


}
