package com.andela.buildsdgs.rtrc.ui.main.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.andela.buildsdgs.rtrc.R;
import com.andela.buildsdgs.rtrc.models.User;
import com.andela.buildsdgs.rtrc.models.UserDetail;
import com.andela.buildsdgs.rtrc.services.RTRCService;
import com.andela.buildsdgs.rtrc.services.ServiceUtil;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.facebook.appevents.AppEventsLogger;

import java.util.Arrays;


public class SignUpActivity extends AppCompatActivity {

    private EditText edtEmail;
    private EditText edtUserName;
    private EditText edtPassword;
    private EditText edtConfirmPassword;
    private EditText edtPhoneNumber;
    private EditText edtFullname;
    private View parent_view;
    private CallbackManager callbackManager;


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
        TextView  btnSignupLogin = findViewById(R.id.btn_signup_login);
        LoginButton btnFacebooklogin = findViewById(R.id.btn_facebook_signup);
        TextView btnCreateAccount = findViewById(R.id.btn_signup_create_acc);

        AppEventsLogger.activateApp(getApplication());
        callbackManager = CallbackManager.Factory.create();
        AccessTokenTracker  accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {

            }
        };
        ProfileTracker profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {

            }
        };
        accessTokenTracker.startTracking();
        profileTracker.startTracking();
        FacebookCallback<LoginResult> loginCallback = new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                Log.v("LoginActivity", response.toString());
                                //Application code
                                try {
                                    String email = object.getString("email");
                                    String username = email.split("@")[0];
                                    String fullName = object.getString("name");
                                    edtEmail.setText(email);
                                    edtUserName.setText(username);
                                    edtFullname.setText(fullName);
                                    System.out.println("Email : ..." + email);
                                    Snackbar.make(parent_view, "Almost done, complete sign up with password and mobile number", Snackbar.LENGTH_SHORT).show();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender,birthday");
                request.setParameters(parameters);
                request.executeAsync();

            }

            @Override
            public void onCancel() {
                Snackbar.make(parent_view, "Facebook Sign up canceled", Snackbar.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Snackbar.make(parent_view, "Error during Facebook Sign up", Snackbar.LENGTH_SHORT).show();
            }
        };
        btnFacebooklogin.setReadPermissions(Arrays.asList("public_profile", "email"));
        // Callback registration
        btnFacebooklogin.registerCallback(callbackManager, loginCallback);

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
                            System.out.println("Request url : " + call.request().url().toString());
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
}
