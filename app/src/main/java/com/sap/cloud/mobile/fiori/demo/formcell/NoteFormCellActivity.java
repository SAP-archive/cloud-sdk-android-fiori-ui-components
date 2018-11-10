package com.sap.cloud.mobile.fiori.demo.formcell;

import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;

import com.sap.cloud.mobile.fiori.demo.AbstractDemoActivity;
import com.sap.cloud.mobile.fiori.demo.R;
import com.sap.cloud.mobile.fiori.formcell.FormCell;
import com.sap.cloud.mobile.fiori.formcell.NoteFormCell;

public class NoteFormCellActivity extends AbstractDemoActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_form_cell);

        SwitchCompat switchCompat = findViewById(R.id.enableDisableSwitch);

        NoteFormCell note = findViewById(R.id.noteWithoutBorderCell);
        note.setCellValueChangeListener(new FormCell.CellValueChangeListener<CharSequence>() {
            @Override
            public void cellChangeHandler(CharSequence value) {
                Log.i("New text ", String.valueOf(value));
            }
        });
        NoteFormCell note1 = findViewById(R.id.noteWithBorderCell);
        note1.setCellValueChangeListener(new FormCell.CellValueChangeListener<CharSequence>() {
            @Override
            public void cellChangeHandler(CharSequence value) {
                if (value.length() > 6) {
                    note1.setErrorEnabled(true);
                    note1.setError("This is error");
                } else {
                    note1.setErrorEnabled(false);
                }

            }
        });

        NoteFormCell note3 = findViewById(R.id.notNoneEditableCell);
        NoteFormCell note4 = findViewById(R.id.notNoneEditableSelectableCell);
        note4.setCellValueChangeListener(new FormCell.CellValueChangeListener<CharSequence>() {
            @Override
            protected void cellChangeHandler(CharSequence value) {
                if (value.length() > 40) {
                    note4.setErrorEnabled(true);
                    note4.setError("This is long message");
                } else {
                    note4.setErrorEnabled(false);
                }
            }
        });
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                note3.getValueView().append("  Some long string to make it wrap.");
                note4.getValueView().append("  Some long string to make it wrap.");
            }
        });

        NoteFormCell editableNote = findViewById(R.id.startEditable);
        NoteFormCell readonlyNote = findViewById(R.id.startReadonly);
        Button switchButton = findViewById(R.id.switchButton);
        switchButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                editableNote.setEditable(!editableNote.isEditable());
                readonlyNote.setEditable(!readonlyNote.isEditable());
            }
        });

        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                note.setEnabled(!isChecked);
                note1.setEnabled(!isChecked);
                note3.setEnabled(!isChecked);
                note4.setEnabled(!isChecked);
                editableNote.setEnabled(!isChecked);
                readonlyNote.setEnabled(!isChecked);
                button.setEnabled(!isChecked);
                switchButton.setEnabled(!isChecked);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
