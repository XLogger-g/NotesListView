package com.example.notes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;
    List<NoteClass> noteList;
    ListView listView;

    TextView tvNoteCount;


    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);

        noteList = new ArrayList<>();

        tvNoteCount = findViewById(R.id.tvNoteCount);


        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference("Notes");


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                NoteClass noteClass = noteList.get(position);
                Intent intent = new Intent(getApplicationContext(), ClassworkActivity.class);

                intent.putExtra("noteId", noteClass.getNoteId());
                intent.putExtra("noteTitle", noteClass.getNoteTitle());
                intent.putExtra("note", noteClass.getNote());

                startActivity(intent);

            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

        getDataForFirebase();
    }


    public void addNote(View view) {
        Intent intent = new Intent(getApplicationContext(), AddNoteActivity.class);
        startActivity(intent);
    }


    public void getDataForFirebase() {
        noteList.clear();


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                for (DataSnapshot ds : dataSnapshot.getChildren()) {


                    NoteClass notes = ds.getValue(NoteClass.class);

                    noteList.add(notes);


                }

                Collections.reverse(noteList);
                NoteAdapter noteAdapter = new NoteAdapter(MainActivity.this, noteList);
                tvNoteCount.setText(noteAdapter.getCount() + " " + getString(R.string.note));

                listView.setAdapter(noteAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


}
