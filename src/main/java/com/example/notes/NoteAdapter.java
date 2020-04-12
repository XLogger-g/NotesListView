package com.example.notes;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class NoteAdapter extends ArrayAdapter<NoteClass> {

    private Activity context;
    List<NoteClass> notes;

    public NoteAdapter(Activity context, List<NoteClass> notes) {
        super(context, R.layout.custom_view, notes);
        this.context = context;
        this.notes = notes;
    }

    @Override
    public int getCount() {
        notes.size();
        return super.getCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = context.getLayoutInflater();
        View customView = layoutInflater.inflate(R.layout.custom_view, null, true);

        TextView tvCustomNoteTitle = (TextView) customView.findViewById(R.id.tvCustomNoteTitle);
        TextView tvCustomNote = (TextView) customView.findViewById(R.id.tvCustomNote);
        TextView tvCustomCreatedDate = (TextView) customView.findViewById(R.id.tvCustomCreatedDate);

        NoteClass note = notes.get(position);
        tvCustomNoteTitle.setText(note.getNoteTitle());
        tvCustomNote.setText(note.getNote());
        tvCustomCreatedDate.setText(note.getCreatedDate());


        return customView;
    }
}
