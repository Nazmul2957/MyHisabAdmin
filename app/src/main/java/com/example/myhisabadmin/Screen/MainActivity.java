package com.example.myhisabadmin.Screen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.myhisabadmin.Adaptar.UserListAdaptar;
import com.example.myhisabadmin.Model.AdminUserList.AdminUserList;
import com.example.myhisabadmin.Model.AdminUserList.Datas;
import com.example.myhisabadmin.Model.AdminUserList.Datum;
import com.example.myhisabadmin.Network.ApiService;
import com.example.myhisabadmin.Network.retrofitclient;
import com.example.myhisabadmin.R;
import com.example.myhisabadmin.Utils.MySharedPreference;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    RecyclerView UserList;
    BottomNavigationView bottomNavigationView;

    ApiService api;
    ProgressDialog progressDialog;
    NestedScrollView nestedScrollView;

    int page = 1, limit = 15;
    UserListAdaptar adaptar;
    LinearLayoutManager layoutManager;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        UserList = findViewById(R.id.userList);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        nestedScrollView = findViewById(R.id.nested_scrool);

        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Please Wait......");
        progressDialog.setCancelable(false);


        BottomNav();
        userlists();

        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(@NonNull NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY==v.getChildAt(0).getMeasuredHeight()-v.getMeasuredHeight()){
                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
                    if (!adaptar.isLoading() && adaptar.hasMoreData() && (visibleItemCount + firstVisibleItemPosition) >= totalItemCount) {
                        int nextPage = adaptar.getCurrentPage() + 1;
                        loadMoreData(nextPage);
                    }
                }

            }
        });
        UserList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

            }
        });


    }

    public void userlists() {
        progressDialog.show();
        api = retrofitclient.get(getApplicationContext()).create(ApiService.class);
        api.userlist().enqueue(new Callback<AdminUserList>() {
            @Override
            public void onResponse(Call<AdminUserList> call, Response<AdminUserList> response) {
                if (response.isSuccessful() && response.body() != null) {
                    progressDialog.dismiss();
                    adaptar = new UserListAdaptar(response.body().getData().getData(), getApplicationContext());
                    UserList.setHasFixedSize(true);
                    layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                    UserList.setLayoutManager(layoutManager);
                    UserList.setAdapter(adaptar);


                }
            }

            @Override
            public void onFailure(Call<AdminUserList> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(MainActivity.this, t.toString(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void loadMoreData(int nextPage) {
        api = retrofitclient.get(getApplicationContext()).create(ApiService.class);

        adaptar.setLoading(true);
        api.userlistpage(nextPage).enqueue(new Callback<AdminUserList>() {
            @Override
            public void onResponse(Call<AdminUserList> call, Response<AdminUserList> response) {
                if (response.isSuccessful() && response.body() != null) {
                    AdminUserList user = response.body();
                    Datas datas = user.getData();
                    List<Datum> newdata = datas.getData();
                    adaptar.addData(newdata);
                    adaptar.setCurrentPage(datas.getCurrentPage());
                    adaptar.setLastPage(datas.getLastPage());
                }
                adaptar.setLoading(false);
            }

            @Override
            public void onFailure(Call<AdminUserList> call, Throwable t) {
                adaptar.setLoading(false);
            }
        });

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
                    case R.id.logout:
                        MySharedPreference.editor(getApplicationContext()).clear().commit();
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        finish();
                        return true;
                }

                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}