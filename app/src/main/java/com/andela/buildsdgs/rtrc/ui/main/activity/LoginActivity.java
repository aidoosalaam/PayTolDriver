package com.andela.buildsdgs.rtrc.ui.main.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.andela.buildsdgs.rtrc.MainActivity;
import com.andela.buildsdgs.rtrc.R;
import com.andela.buildsdgs.rtrc.controller.UserController;
import com.andela.buildsdgs.rtrc.models.User;
import com.andela.buildsdgs.rtrc.models.UserDetail;
import com.andela.buildsdgs.rtrc.services.RTRCService;
import com.andela.buildsdgs.rtrc.services.ServiceBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private View parent_view;
    TextInputEditText usernameEditText;
    TextInputEditText passwordEditText;
    TextView btnForgotPassword;
    TextView btnloginSignUp;
    Button btnLogin;
    UserController userController;

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
        userController = new UserController();


        btnloginSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (usernameEditText.getText().toString().trim().isEmpty()) {
                    Snackbar.make(parent_view, "Email field must not be empty", Snackbar.LENGTH_SHORT).show();
                } else if (passwordEditText.getText().toString().trim().isEmpty()) {
                    Snackbar.make(parent_view, "Password field must not be empty", Snackbar.LENGTH_SHORT).show();
                } else {

                    String userEmail = usernameEditText.getText().toString().trim();
                    String password =  passwordEditText.getText().toString().trim();
                    RTRCService rtrcService = ServiceBuilder.buildService(RTRCService.class);
                    System.out.println("Login Details : Email : " + userEmail + "\n Password : " + password);

                    Call<UserDetail> userLoginCall = rtrcService.loginUser(new User(userEmail, password));
                    userLoginCall.enqueue(new Callback<UserDetail>() {

                        @Override
                        public void onResponse(Call<UserDetail> call, Response<UserDetail> response) {

                            if (response.code() == 200) {
                                System.out.println(" debuggin starts 2....");
                                System.out.println("Login Details ::: " + response.body().toString());
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                            } else {
                                System.out.println("Error Occured ...." + response.errorBody().toString());
                                Snackbar.make(parent_view, "Error occured : Status Code  " + response.code(), Snackbar.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<UserDetail> call, Throwable t) {
                            System.out.println(" debuggin starts 3....");
                            System.out.println("Error Occurred ::: " + t.toString());
                        }
                    });
                }
            }
        });
    }
    private void performActions(int viewId) {
        switch (viewId) {
            case R.id.btn_login:
                btnLogin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Snackbar.make(parent_view, "Sign Up", Snackbar.LENGTH_SHORT).show();

//                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                        startActivity(intent);

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
