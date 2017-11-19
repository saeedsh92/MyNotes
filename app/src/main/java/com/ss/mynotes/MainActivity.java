package com.ss.mynotes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class MainActivity extends AppCompatActivity implements NotesAdapter.NoteViewHolder.NoteViewCallback {
    private NotesAdapter notesAdapter;
    public static final int ADD_NOTE_REQUEST_ID = 1001;
    public static final int EDIT_NOTE_REQUEST_ID = 1002;
    public static final String EXTRA_KEY_TITLE = "title";
    public static final String EXTRA_KEY_DESCRIPTION = "description";
    private int pendingEditNotePosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupViews();
    }

    private void setupViews() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_main_notesList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        notesAdapter = new NotesAdapter(this);
        recyclerView.setAdapter(notesAdapter);

        View addNoteButton = findViewById(R.id.frameLayout_main_addNote);
        addNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EditNoteActivity.class);
                startActivityForResult(intent, ADD_NOTE_REQUEST_ID);
            }
        });
    }

    @Override
    public void onEditButtonClick(int position, Note note) {
        Intent intent = new Intent(this, EditNoteActivity.class);
        intent.putExtra(EXTRA_KEY_TITLE, note.getTitle());
        intent.putExtra(EXTRA_KEY_DESCRIPTION, note.getDescription());
        startActivityForResult(intent, EDIT_NOTE_REQUEST_ID);
        pendingEditNotePosition = position;
    }

    @Override
    public void onDeleteButtonClick(int position) {
        notesAdapter.removeItem(position);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_NOTE_REQUEST_ID &&
                resultCode == RESULT_OK) {
            String title = data.getStringExtra(EXTRA_KEY_TITLE);
            String description = data.getStringExtra(EXTRA_KEY_DESCRIPTION);
            Note note = new Note();
            note.setTitle(title);
            note.setDescription(description);
            notesAdapter.addNote(note);
        } else if (requestCode == EDIT_NOTE_REQUEST_ID &&
                resultCode == RESULT_OK) {
            String title = data.getStringExtra(EXTRA_KEY_TITLE);
            String description = data.getStringExtra(EXTRA_KEY_DESCRIPTION);
            notesAdapter.updateNote(pendingEditNotePosition, title, description);
        }
    }
}
