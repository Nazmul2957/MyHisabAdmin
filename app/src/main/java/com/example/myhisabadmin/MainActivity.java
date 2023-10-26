package com.example.myhisabadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.icu.text.CaseMap;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    RecyclerView UserList;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        UserList = findViewById(R.id.userList);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        BottomNav();
    }

    public void BottomNav() {
        bottomNavigationView.setSelectedItemId(R.id.user_list);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.user_list:
                        return true;
                    case R.id.earn_head:
                        Intent intent = new Intent(getApplicationContext(), EarnHeadActivity.class);
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.cost_head:
                        Intent intent1 = new Intent(getApplicationContext(), CostHeadActivity.class);
                        startActivity(intent1);
                        overridePendingTransition(0, 0);
                        return true;

                }

                return false;
            }
        });
    }
}