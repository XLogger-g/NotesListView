package com.example.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ClassworkActivity extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;
    List<AssignClass> assignmentList;
    ListView listView;

    AlertDialog dialog;
    String noteId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classwork);

        //toolbar

        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        // bottom nav

        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setSelectedItemId(R.id.classwork);


        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.classwork:
                        startActivity(new Intent(getApplicationContext(), ClassworkActivity.class));
                        overridePendingTransition(0,0);
                        break;
                    case R.id.people:
                        startActivity(new Intent(getApplicationContext(), PeopleActivity.class));
                        overridePendingTransition(0,0);
                        break;
                }
                return true;
            }
        });
        // end bottom nav

        // fab

        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(), AddAssignmentActivity.class));
            }
        });


        // Classwork

        listView = findViewById(R.id.listViewAssignment);

        assignmentList = new ArrayList<>();

        String id = getIntent().getStringExtra("id");

        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference("Notes").child(id).child("Assignments");


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                AssignClass assignClass = assignmentList.get(position);
                Intent intent = new Intent(getApplicationContext(), AssignmentScreenActivity.class);

                intent.putExtra("asId", assignClass.getAsId());
                intent.putExtra("asTitle", assignClass.getAsTitle());
                intent.putExtra("assignment", assignClass.getAssignment());

                startActivity(intent);

            }
        });

        // end Classwork

    }

    @Override
    protected void onStart() {
        super.onStart();

        getDataForFirebase();
    }




    public void getDataForFirebase() {
        assignmentList.clear();


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                for (DataSnapshot ds : dataSnapshot.getChildren()) {


                    AssignClass assignments = ds.getValue(AssignClass.class);

                    assignmentList.add(assignments);


                }

                Collections.reverse(assignmentList);
                AssignAdapter assignAdapter = new AssignAdapter(ClassworkActivity.this, assignmentList);

                listView.setAdapter(assignAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }



    // item toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }
}
