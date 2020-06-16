package com.example.website;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.example.website.Api.RetrofitClient;
import com.example.website.model.DefaultResponse;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Sign_Up extends AppCompatActivity implements View.OnClickListener {
    TextInputEditText etuser_name,etemail,etpass,etconfirmpass;
    String nonce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign__up);
        etuser_name = findViewById(R.id.username_signup);
        etemail = findViewById(R.id.email_signup);
        etpass = findViewById(R.id.password_signup);
        etconfirmpass = findViewById(R.id.confirm_password_signup);
        findViewById(R.id.signup_signup).setOnClickListener(this);
        findViewById(R.id.login_signup).setOnClickListener(this);
    }

        public void usersignup() {

            String username, email, pass, confirmpass;

            username = etuser_name.getText().toString().trim();
            email = etemail.getText().toString().trim();
            pass = etpass.getText().toString().trim();
            confirmpass = etconfirmpass.getText().toString().trim();

            if (username.isEmpty()) {
                etuser_name.setError("user_name required");
                etuser_name.requestFocus();
                return;
            }
            if (email.isEmpty()) {
                etemail.setError("Email required");
                etemail.requestFocus();
                return;
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                etemail.setError("Error");
                etemail.requestFocus();
                return;

            }
            if (pass.isEmpty()) {
                etpass.setError("Password required");
                etpass.requestFocus();
                return;
            }
            if (pass.length() < 6) {
                etpass.setError("Weak password");
                etpass.requestFocus();
                return;
            }

            if (confirmpass.isEmpty()) {
                etconfirmpass.setError("Confirm Password required");
                etconfirmpass.requestFocus();
                return;
            }
            if (!confirmpass.equals(pass)) {
                etconfirmpass.setError("Password doesn't match");
                etconfirmpass.requestFocus();
                return;
            }
            Call<DefaultResponse> call = RetrofitClient
                    .getInstance()
                    .getApi()
                    .nonce();
            call.enqueue(new Callback<DefaultResponse>() {
                @Override
                public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                    DefaultResponse dr = response.body();
                    nonce = dr.getNonce();
                    Toast.makeText(Sign_Up.this,nonce, Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onFailure(Call<DefaultResponse> call, Throwable t) {
                    Toast.makeText(Sign_Up.this, "failed to generate nonce", Toast.LENGTH_SHORT).show();

                }
            });
            Call<DefaultResponse> register = RetrofitClient
                    .getInstance()
                    .getApi()
                    .create_user(username, email,nonce,username,"both",pass);
            register.enqueue(new Callback<DefaultResponse>() {
                @Override
                public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {

                    try {
                        DefaultResponse dr = response.body();
                        Toast.makeText(Sign_Up.this, dr.getMsg() + " try ", Toast.LENGTH_SHORT).show();

                    } catch (Exception e) {
                        Toast.makeText(Sign_Up.this,"catch block", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<DefaultResponse> call, Throwable t) {
                    Toast.makeText(Sign_Up.this,"Error!", Toast.LENGTH_SHORT).show();
                }
            });
        }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.signup_signup:
                usersignup();
                break;
            case R.id.login_signup:
                startActivity(new Intent(Sign_Up.this,Login.class));
                break;
        }

    }
}
