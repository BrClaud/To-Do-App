package com.BrClaudProject.myprojecttry.notes;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.BrClaudProject.myprojecttry.Const;
import com.BrClaudProject.myprojecttry.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DialogFragNote extends DialogFragment implements DialogInterface.OnClickListener {

    private String key;
    private Note note;
    private DatabaseReference db;
    public DialogFragNote(String key, Note note) {
        this.key = key;
        this.note = note;
        db = FirebaseDatabase.getInstance().getReference().child(Const.USERS).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(Const.NOTES).child(key);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder adb = new AlertDialog.Builder(getActivity()).
                setNegativeButton(R.string.note_delete, this).
                setTitle("что делать с заметкой");
        if(note.isCheck())
            adb.setPositiveButton(R.string.note_not_complete, this);
        else
            adb.setPositiveButton(R.string.note_complete, this);
        return adb.create();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        int i = 0;
        switch (which)
        {
            case Dialog.BUTTON_NEGATIVE:
                i = R.string.note_delete;
                //db = FirebaseDatabase.getInstance().getReference().child(Const.USERS).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(Const.NOTES).child(key);
                db.removeValue();
                break;
            case Dialog.BUTTON_POSITIVE:
                i = R.string.note_complete;
                DatabaseReference dbAdd = FirebaseDatabase.getInstance().getReference().child(Const.USERS).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(Const.NOTES);
                db.removeValue();
                if(note.isCheck()) {
                    note.setCheck(false);
                }
                else
                    note.setCheck(true);
                dbAdd.push().setValue(note);
                break;
        }
    }
}
