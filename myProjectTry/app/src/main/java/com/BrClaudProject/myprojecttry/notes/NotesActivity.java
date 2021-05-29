package com.BrClaudProject.myprojecttry.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.BrClaudProject.myprojecttry.Const;
import com.BrClaudProject.myprojecttry.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NotesActivity extends AppCompatActivity {

    ListView lvNotes;
    DatabaseReference db;
    FirebaseUser user;
    ArrayList<Pair<Note, String>> ListNote;
    ArrayList<Note> listNote;
    //ArrayList<String> ListNoteHeaders;
    //ArrayAdapter<String> arrayAdapter;

    DialogFragNote dfn;

    NoteAdapter noteAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        init();
        getNotesFromDB();
    }

    @Override
    protected void onStart() {
        super.onStart();
        setListeners();
    }

    private void init()
    {
        lvNotes = findViewById(R.id.lvNotes);
        db = FirebaseDatabase.getInstance().getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();
        ListNote = new ArrayList<>();
        listNote = new ArrayList<>();
        //ListNoteHeaders = new ArrayList<>();
        //arrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, ListNoteHeaders);
        //lvNotes.setAdapter(arrayAdapter);

        noteAdapter= new NoteAdapter(this, R.layout.list_note_item, listNote);
        lvNotes.setAdapter(noteAdapter);

    }

    private void getNotesFromDB()
    {
        DatabaseReference dbUser = db.child(Const.USERS).child(user.getUid()).child(Const.NOTES);
        ValueEventListener vListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!ListNote.isEmpty())
                {
                    ListNote.clear();
                    listNote.clear();
                    //ListNoteHeaders.clear();
                }
                for(DataSnapshot ds : snapshot.getChildren())
                {
                    Note note = ds.getValue(Note.class);

                    Pair<Note, String> pr = new Pair<>(note, ds.getKey());
                    ListNote.add(pr);
                    listNote.add(note);
                    //ListNoteHeaders.add(note.getHeaderNote());
                    noteAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        dbUser.addValueEventListener(vListener);

    }
    private void setListeners()
    {
        lvNotes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("onClickShowNote");
                Intent intent = new Intent(getApplicationContext(), ShowNote.class);
                intent.putExtra("text", ListNote.get(position).first.getNoteText());
                intent.putExtra("header", ListNote.get(position).first.getHeaderNote());
                intent.putExtra("uri", ListNote.get(position).first.getUploadUri());
                startActivity(intent);
            }
        });
        lvNotes.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                dfn = new DialogFragNote(ListNote.get(position).second, ListNote.get(position).first);
                dfn.show(getSupportFragmentManager(), "negative");
                System.out.println("long pressed button");
                return true;
            }
        });
    }



    public void btAddNote(View view) {
        Intent intent = new Intent(this, CreateNote.class);
        startActivity(intent);
    }
}