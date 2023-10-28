package com.example.myhisabadmin.Screen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myhisabadmin.Model.AccountHeadAdd.AccountHeadAdd;
import com.example.myhisabadmin.Network.ApiService;
import com.example.myhisabadmin.Network.retrofitclient;
import com.example.myhisabadmin.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EarnHeadActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    Button savebutton;
    EditText editText;
    RecyclerView recyclerView;
    ApiService api;
    ProgressDialog progressDialog;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earn_head);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        savebutton = findViewById(R.id.earnhead_save);
        editText = findViewById(R.id.earn_head);
        recyclerView = findViewById(R.id.earnhead_list_show);

        progressDialog = new ProgressDialog(EarnHeadActivity.this);
        progressDialog.setMessage("Please Wait......");
        progressDialog.setCancelable(false);


        BottomNav();
        Save();


    }

//    public void listshow() {
//        EarnListAdaptar adaptar = new EarnListAdaptar(list, getApplicationContext());
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
//        recyclerView.setAdapter(adaptar);
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                list.clear();
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                    EarnModel model = dataSnapshot.getValue(EarnModel.class);
//                    list.add(model);
//                }
//                adaptar.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }

    public void Save() {
        savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!TextUtils.isEmpty(editText.getText().toString())) {
                    progressDialog.show();

                    api = retrofitclient.get(getApplicationContext()).create(ApiService.class);
                    api.addhead(editText.getText().toString(), "0").enqueue(new Callback<AccountHeadAdd>() {
                        @Override
                        public void onResponse(Call<AccountHeadAdd> call, Response<AccountHeadAdd> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                progressDialog.dismiss();
                                String message = response.body().getMessage();
                                Toast.makeText(EarnHeadActivity.this, message, Toast.LENGTH_SHORT).show();
                                editText.setText("");
                            }
                        }

                        @Override
                        public void onFailure(Call<AccountHeadAdd> call, Throwable t) {
                            progressDialog.dismiss();
                            Toast.makeText(EarnHeadActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });

                } else {
                    editText.setError("please Input Field");
                    editText.requestFocus();
                }
            }
        });
    }

    public void BottomNav() {
        bottomNavigationView.setSelectedItemId(R.id.earn_head);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.user_list:
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.earn_head:
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}