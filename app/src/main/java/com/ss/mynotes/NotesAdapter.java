package com.ss.mynotes;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Saeed shahini on 11/19/2017.
 */

public class NotesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int ITEM_NOTE = 1;
    private static final int ITEM_DELETE_ALL_NOTES = 2;

    private List<Note> notes = new ArrayList<>();
    private NoteViewHolder.NoteViewCallback noteViewCallback;

    public NotesAdapter(NoteViewHolder.NoteViewCallback noteViewCallback) {
        this.noteViewCallback = noteViewCallback;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        if (viewType == ITEM_NOTE) {
            View view = layoutInflater.inflate(R.layout.item_note, parent, false);
            return new NoteViewHolder(view, noteViewCallback);
        } else {
            View view = layoutInflater.inflate(R.layout.item_delete_notes, parent, false);
            return new DeleteAllNotesViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof NoteViewHolder) {
            ((NoteViewHolder) holder).bindNote(notes.get(position));
        } else if (holder instanceof DeleteAllNotesViewHolder) {
            ((DeleteAllNotesViewHolder) holder).deleteAllNotesButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    notifyItemRangeRemoved(0, notes.size());
                    notes.clear();
                    checkDeleteAllNotesButtonState();

                }
            });
            ((DeleteAllNotesViewHolder) holder).setButtonVisibility(notes.size() > 1 ? View.VISIBLE : View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return notes.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        return position == notes.size() ? ITEM_DELETE_ALL_NOTES : ITEM_NOTE;
    }

    public void removeItem(int position) {
        notifyItemRemoved(position);
        this.notes.remove(position);
        checkDeleteAllNotesButtonState();
    }

    public void addNote(Note note) {
        notifyItemInserted(this.notes.size() - 1);
        this.notes.add(note);
        checkDeleteAllNotesButtonState();
    }

    private void checkDeleteAllNotesButtonState() {
        notifyItemChanged(notes.size());
    }

    public static class NoteViewHolder extends RecyclerView.ViewHolder {
        private ImageView deleteImageView;
        private ImageView editImageView;
        private TextView titleTextView;
        private TextView descriptionTextView;

        public NoteViewHolder(View itemView, final NoteViewCallback noteViewCallback) {
            super(itemView);
            deleteImageView = itemView.findViewById(R.id.iv_note_delete);
            editImageView = itemView.findViewById(R.id.iv_note_edit);
            titleTextView = itemView.findViewById(R.id.tv_note_title);
            descriptionTextView = itemView.findViewById(R.id.tv_note_description);

            deleteImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    noteViewCallback.onDeleteButtonClick(getAdapterPosition());
                }
            });

            editImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    noteViewCallback.onEditButtonClick(getAdapterPosition());
                }
            });
        }

        public void bindNote(Note note) {
            titleTextView.setText(note.getTitle());
            descriptionTextView.setText(note.getDescription());
        }

        public interface NoteViewCallback {
            void onEditButtonClick(int position);

            void onDeleteButtonClick(int position);
        }
    }

    private static class DeleteAllNotesViewHolder extends RecyclerView.ViewHolder {
        private View deleteAllNotesButton;

        public DeleteAllNotesViewHolder(View itemView) {
            super(itemView);
            deleteAllNotesButton = itemView.findViewById(R.id.ll_deleteNotes_buttonDeleteAllNotes);
        }

        public void setButtonVisibility(int visibility) {
            deleteAllNotesButton.setVisibility(visibility);
        }
    }
}
