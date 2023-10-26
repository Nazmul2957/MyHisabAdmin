package com.example.myhisabadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.myhisabadmin.Adaptar.EarnListAdaptar;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EarnHeadActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    DatabaseReference reference;
    Button savebutton;
    String Heads;
    EditText editText;
    RecyclerView recyclerView;

    ArrayList<EarnModel> list;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earn_head);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        savebutton = findViewById(R.id.earnhead_save);
        editText = findViewById(R.id.earn_head);
        recyclerView = findViewById(R.id.earnhead_list_show);

        list = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("Earn Head");


        BottomNav();
        Save();
        listshow();

    }

    public void listshow() {
        EarnListAdaptar adaptar = new EarnListAdaptar(list, getApplicationContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adaptar);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    EarnModel model = dataSnapshot.getValue(EarnModel.class);
                    list.add(model);
                }
                adaptar.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void Save() {
        savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Heads = editText.getText().toString();
                EarnModel model = new EarnModel(Heads);
                reference = FirebaseDatabase.getInstance().getReference().child("Earn Head");
                String key = reference.push().getKey();
                reference.child(key).setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        editText.setText("");
                    }
                });
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