package com.bestbeforeapp.couchnotes.list;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bestbeforeapp.couchnotes.R;
import com.bestbeforeapp.couchnotes.model.Note;
import com.firebase.client.Query;
import com.firebase.ui.FirebaseRecyclerAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

public class NoteListAdapter extends FirebaseRecyclerAdapter<Note, NoteListAdapter.NoteViewHolder> {

    private OnItemClickListener onItemClickListener;
    private OnActivatedListener onActivatedListener;
    private int positionActivated;

    public NoteListAdapter(Class<Note> modelClass, int modelLayout,
                           Query ref) {
        super(modelClass, modelLayout, NoteViewHolder.class, ref);
    }

    @Override
    protected void populateViewHolder(NoteViewHolder viewHolder, Note note, final int position) {
        viewHolder.textView.setText(note.getTitle());
        viewHolder.date.setText(String.valueOf((long) note.get_created().get("timestamp")));

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(v, position);
                onActivatedListener.onActivated(position);
            }
        });

        if (position == positionActivated) {
            viewHolder.itemView.setActivated(true);
            viewHolder.itemView.requestFocus();
        } else {
            viewHolder.itemView.setActivated(false);
        }
    }

    public static class NoteViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.title) TextView textView;
        @Bind(R.id.date) TextView date;

        public NoteViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public interface OnActivatedListener {
        void onActivated(int position);
    }

    public void setActivated(int positionActivated) {
        int oldPositionActivated = this.positionActivated;
        this.positionActivated = positionActivated;

        notifyItemChanged(oldPositionActivated);
        notifyItemChanged(positionActivated);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setPositionActivatedListener(OnActivatedListener positionActivatedListener) {
        this.onActivatedListener = positionActivatedListener;
    }
}
