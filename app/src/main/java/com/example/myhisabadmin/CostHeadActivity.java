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

public class CostHeadActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    Button costSave;
    EditText editText;
    String Costs;
    DatabaseReference reference;
    RecyclerView recyclerView;
    ArrayList<EarnModel> list;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cost_head);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        costSave = findViewById(R.id.costhead_save);
        editText = findViewById(R.id.cost_head);
        recyclerView = findViewById(R.id.costhead_list);

        list = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("Earn Cost");


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
        costSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Costs = editText.getText().toString();
                EarnModel model = new EarnModel(Costs);
                reference = FirebaseDatabase.getInstance().getReference().child("Earn Cost");
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

        bottomNavigationView.setSelectedItemId(R.id.cost_head);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.user_list:
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.cost_head:
                        return true;
                    case R.id.earn_head:
                        Intent intent1 = new Intent(getApplicationContext(), EarnHeadActivity.class);
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