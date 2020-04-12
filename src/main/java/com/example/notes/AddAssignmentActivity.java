package com.example.notes;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddAssignmentActivity extends AppCompatActivity {

    TextInputEditText etAddAssignment;
    TextInputEditText etAddAssignmentTitle;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;

    String noteId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assignment);

        etAddAssignment = findViewById(R.id.etAddAssignment);
        etAddAssignmentTitle = findViewById(R.id.ettAddAssignmentTitle);

        String id = getIntent().getStringExtra("id");

        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference("Notes").child(id).child("Assignments");

    }


    public void AddAssign(View view) {


        String assignment = etAddAssignment.getText().toString().trim();
        String asTitle = etAddAssignmentTitle.getText().toString().trim();

        if (!TextUtils.isEmpty(assignment) && !TextUtils.isEmpty(asTitle)) {

            String asId = myRef.push().getKey();

            AssignClass assignClass = new AssignClass(asId, asTitle, assignment);

            myRef.child(asId).setValue(assignClass);

            Toast.makeText(this, getString(R.string.assignment_added), Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(getApplicationContext(), ClassworkActivity.class);
            startActivity(intent);

        } else {
            Toast.makeText(this, getString(R.string.enter_assignment), Toast.LENGTH_LONG).show();
        }


    }

}
