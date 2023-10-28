package com.example.myhisabadmin.Screen;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myhisabadmin.Network.ApiService;
import com.example.myhisabadmin.Network.retrofitclient;
import com.example.myhisabadmin.R;
import com.example.myhisabadmin.Utils.MySharedPreference;
import com.example.myhisabadmin.Utils.constants;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    TextView login;
    EditText mobilenum, password;

    ProgressDialog progressDialog;

    ApiService api;
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = findViewById(R.id.loginBtn);
        mobilenum = findViewById(R.id.mobileET);
        password = findViewById(R.id.passETs);

        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage("Please Wait......");
        progressDialog.setCancelable(false);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(mobilenum.getText().toString())) {
                    if (!TextUtils.isEmpty(password.getText().toString())) {
                        progressDialog.show();
                        api = retrofitclient.noInterceptor().create(ApiService.class);
                        api.login(mobilenum.getText().toString(), password.getText().toString()).enqueue(new Callback<JsonObject>() {
                            @Override
                            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                progressDialog.dismiss();
                                if (response.isSuccessful() && response.body() != null) {
                                    token = response.body().get("remember_token").getAsString();
                                    MySharedPreference.getInstance(getApplicationContext()).edit()
                                            .putString(constants.TOKEN, token).apply();
                                    Log.d("tokensave", token);
                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                    finish();
                                }
                            }

                            @Override
                            public void onFailure(Call<JsonObject> call, Throwable t) {
                                progressDialog.dismiss();
                                Toast.makeText(LoginActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
                            }
                        });


                    } else {
                        password.setError("Input Your Password");
                        password.requestFocus();
                    }

                } else {
                    mobilenum.setError("Input Mobile Number");
                    mobilenum.requestFocus();

                }
            }
        });
    }
}