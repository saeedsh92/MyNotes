package com.ss.mynotes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class MainActivity extends AppCompatActivity implements NotesAdapter.NoteViewHolder.NoteViewCallback {
    private NotesAdapter notesAdapter;

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

        Note note1=new Note();
        note1.setTitle("لورم ایپسوم متن ساختگی");
        note1.setDescription("لورم ایپسوم متن ساختگی با تولید سادگی نامفهوم از صنعت چاپ و با استفاده از طراحان گرافیک است. چاپگرها و متون بلکه روزنامه و مجله در ستون و سطرآنچنان که لازم است و برای شرایط فعلی تکنولوژی مورد نیاز و کاربردهای متنوع با هدف بهبود ابزارهای کاربردی می باشد.");
        notesAdapter.addNote(note1);

        Note note2=new Note();
        note2.setTitle("لورم ایپسوم متن ساختگی");
        note2.setDescription("لورم ایپسوم متن ساختگی با تولید سادگی نامفهوم از صنعت چاپ و با استفاده از طراحان گرافیک است. چاپگرها و متون بلکه روزنامه و مجله در ستون و سطرآنچنان که لازم است و برای شرایط فعلی تکنولوژی مورد نیاز و کاربردهای متنوع با هدف بهبود ابزارهای کاربردی می باشد.");
        notesAdapter.addNote(note2);

    }

    @Override
    public void onEditButtonClick(int position) {

    }

    @Override
    public void onDeleteButtonClick(int position) {
        notesAdapter.removeItem(position);
    }
}
