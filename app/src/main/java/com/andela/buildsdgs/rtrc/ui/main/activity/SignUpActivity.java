package com.andela.buildsdgs.rtrc.ui.main.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.andela.buildsdgs.rtrc.R;
import com.andela.buildsdgs.rtrc.models.User;
import com.andela.buildsdgs.rtrc.models.UserDetail;
import com.andela.buildsdgs.rtrc.services.RTRCService;
import com.andela.buildsdgs.rtrc.services.ServiceUtil;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    private EditText edtEmail;
    private EditText edtUserName;
    private EditText edtPassword;
    private EditText edtConfirmPassword;
    private EditText edtPhoneNumber;
    private EditText edtFullname;
    private TextView btnSignupLogin;
    private View parent_view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        parent_view = findViewById(android.R.id.content);

        edtEmail = findViewById(R.id.edt_signup_email);
        edtUserName = findViewById(R.id.edt_registration_number);
        edtPassword = findViewById(R.id.edt_vehicle_model);
        edtConfirmPassword = findViewById(R.id.edt_chassis_number);
        edtFullname = findViewById(R.id.edt_signup_fullname);
        edtPhoneNumber = findViewById(R.id.edt_signup_phone);
        btnSignupLogin = findViewById(R.id.btn_signup_login);
        TextView btnCreateAccount = findViewById(R.id.btn_signup_create_acc);

        btnSignupLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = edtEmail.getText().toString().trim();
                final String userName = edtUserName.getText().toString().trim();
                final String password = edtPassword.getText().toString().trim();
                final String confirmPassword = edtConfirmPassword.getText().toString().trim();
                final String fullname = edtFullname.getText().toString().trim();
                final String phoneNumber = edtPhoneNumber.getText().toString().trim();
                if ("".equals(email) || "".equals(userName) || "".equals(password) || "".equals(confirmPassword) || "".equals(fullname) || "".equals(phoneNumber)) {
                    Snackbar.make(parent_view, "All fields must have values", Snackbar.LENGTH_SHORT).show();
                } else {
                    User registerUser = new User(phoneNumber, fullname, email, userName, password, confirmPassword);
                    RTRCService rtrcService = ServiceUtil.buildService(RTRCService.class);
                    System.out.println("SignUp Request : " + registerUser.toString());
                    Call<UserDetail> userSignUpCall = rtrcService.signUpUser(registerUser);
                    userSignUpCall.enqueue(new Callback<UserDetail>() {
                        @Override
                        public void onResponse(Call<UserDetail> call, Response<UserDetail> response) {
                            System.out.println("Request " + call.request().body().toString());
                            System.out.println("Request url : "+ call.request().url().toString());
                            if (response.code() == 200 || response.code() == 201) {
                                System.out.println("Debugging starts 2....");
                                System.out.println("Login Details ::: " + response.body());
                                Toast.makeText(SignUpActivity.this, "Account Created, Kindly login", Toast.LENGTH_LONG).show();

                                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                                startActivity(intent);
                            } else {

                                try {
                                    JSONObject jObjError = new JSONObject(response.errorBody().string());
                                    Snackbar.make(parent_view, "Sign Up Failed, Reason : " + jObjError.toString(), Snackbar.LENGTH_SHORT).show();

                                } catch (Exception e) {
                                    Snackbar.make(parent_view, "Failed; Reason : " + e.toString(), Snackbar.LENGTH_SHORT).show();
                                }
                            }
                        }
                        @Override
                        public void onFailure(Call<UserDetail> call, Throwable t) {
                            System.out.println("Debugging starts 3....");
                            System.out.println("Error Occurred ::: " + t.toString());
                            Snackbar.make(parent_view, "Failed; Reason : " + t.toString(), Snackbar.LENGTH_SHORT).show();

                        }
                    });
                }
            }
        });

    }

    private void checkFields() {

    }
}
