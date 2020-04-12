package com.example.notes;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class AddNoteActivity extends AppCompatActivity {

    EditText etAddNote;
    EditText etAddNoteTitle;
    TextView tvAddNoteCreatedDate;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        etAddNote = findViewById(R.id.etAddNote);
        etAddNoteTitle = findViewById(R.id.ettAddNoteTitle);
        tvAddNoteCreatedDate = findViewById(R.id.tvAddNoteCreatedDate);

        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference("Notes");


    }

    public void add(View view) {


        Date createdDateNote = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        tvAddNoteCreatedDate.setText(simpleDateFormat.format(createdDateNote));

        String note = etAddNote.getText().toString().trim();
        String noteTitle = etAddNoteTitle.getText().toString().trim();
        String createdDate = tvAddNoteCreatedDate.getText().toString();


        if (!TextUtils.isEmpty(note) && !TextUtils.isEmpty(noteTitle)) {

            String noteId = myRef.push().getKey();

            NoteClass noteClass = new NoteClass(noteId, noteTitle, note, createdDate);

            myRef.child(noteId).setValue(noteClass);

            Toast.makeText(this, getString(R.string.note_added), Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);

        } else {
            Toast.makeText(this, getString(R.string.enter_note), Toast.LENGTH_LONG).show();
        }


    }

    /*private void writeNewNote(String noteId, String note, String createdDate) {

        NoteClass notes = new NoteClass(nonote,createdDate);


        notes.setNote(note);
        notes.setNote(createdDate);

        myRef.child("Notes").child(noteId).child("createdDate").setValue(createdDate);
        myRef.child("Notes").child(noteId).child("noteContent").setValue(note);

    }*/
}
