package com.example.myhisabadmin.Screen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.example.myhisabadmin.R;
import com.example.myhisabadmin.Utils.MySharedPreference;
import com.example.myhisabadmin.Utils.constants;

public class SplashScreenActivity extends AppCompatActivity {
    String sToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        int secondsDelay = 1;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                sToken = MySharedPreference.getInstance(getApplicationContext()).getString(constants.TOKEN, "not found");
                //Uid = MySharedPreference.getInstance(getApplicationContext()).getString(constants.MY_TOKEN_ID, "not found");
                Log.d("splash", sToken);

                if (sToken.equals(new String("not found"))) {
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                } else {
                    Intent intento = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intento);

                }

            }
        }, secondsDelay * 2000);


    }
}