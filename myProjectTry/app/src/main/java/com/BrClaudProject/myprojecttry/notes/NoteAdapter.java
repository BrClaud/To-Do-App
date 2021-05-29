package com.BrClaudProject.myprojecttry.notes;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.BrClaudProject.myprojecttry.R;

import org.w3c.dom.Text;

import java.util.List;

public class NoteAdapter extends ArrayAdapter<Note> {

    private LayoutInflater inflater;
    private int layout;
    private List<Note> notes;


    public NoteAdapter(@NonNull Context context, int resource, @NonNull List<Note> notes) {
        super(context, resource, notes);
        this.notes = notes;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        View view = inflater.inflate(layout, parent, false);

        TextView tvNoteHeadItem = (TextView) view.findViewById(R.id.tvNoteHeadItem);
        TextView tvNoteTextItem = (TextView) view.findViewById(R.id.tvNoteTextItem);

        Note note = notes.get(position);

        tvNoteHeadItem.setText(note.getHeaderNote());
        tvNoteTextItem.setText(note.getNoteText());

        if(note.isCheck())
        {
            tvNoteHeadItem.setPaintFlags(tvNoteHeadItem.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            tvNoteTextItem.setPaintFlags(tvNoteTextItem.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }else
        {
            tvNoteHeadItem.setPaintFlags(tvNoteHeadItem.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            tvNoteTextItem.setPaintFlags(tvNoteTextItem.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }

        return view;
    }
}
