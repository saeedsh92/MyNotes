package com.ss.mynotes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class EditNoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);
        setupViews();
    }

    private void setupViews() {
        final EditText titleEditText = (EditText) findViewById(R.id.et_editNote_title);
        final EditText descriptionEditText = (EditText) findViewById(R.id.et_editNote_description);

        String title = getIntent().getStringExtra(MainActivity.EXTRA_KEY_TITLE);
        String description = getIntent().getStringExtra(MainActivity.EXTRA_KEY_DESCRIPTION);
        if (title != null &&
                description != null) {
            titleEditText.setText(title);
            descriptionEditText.setText(description);
        }

        View saveNoteButton = findViewById(R.id.frameLayout_editNote_saveNote);
        saveNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (titleEditText.length() > 0) {
                    if (descriptionEditText.length() > 0) {
                        Intent intent = new Intent();
                        intent.putExtra(MainActivity.EXTRA_KEY_TITLE, titleEditText.getText().toString());
                        intent.putExtra(MainActivity.EXTRA_KEY_DESCRIPTION, descriptionEditText.getText().toString());
                        setResult(RESULT_OK, intent);
                        finish();
                    } else {
                        descriptionEditText.setError("متن یادداشت نمی تواند خالی باشد");
                    }
                } else {
                    titleEditText.setError("عنوان یادداشت نمی تواند خالی باشد");
                }
            }
        });

        View backButton = findViewById(R.id.iv_editNote_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
