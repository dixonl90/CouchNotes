package com.bestbeforeapp.couchnotes.list;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bestbeforeapp.couchnotes.R;
import com.couchbase.lite.Document;
import com.couchbase.lite.LiveQuery;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ListAdapter extends LiveQueryRecyclerAdapter<ListAdapter.ViewHolder> {

    private OnItemClickListener onItemClickListener;
    private OnActivatedListener onActivatedListener;
    private int positionActivated;

    public ListAdapter(Context context, LiveQuery liveQuery) {
        super(context, liveQuery);
    }

    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        final View view = LayoutInflater.from(this.context).inflate(R.layout.note_list_content,
                viewGroup, false);

        ViewHolder holder = new ViewHolder(view, i);

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        ListAdapter.ViewHolder listViewHolder = (ListAdapter.ViewHolder) viewHolder;

        final Document task = (Document) getItem(position);

        listViewHolder.textView.setText((String) task.getProperty("text"));
        listViewHolder.date.setText(task.getId());

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

        listViewHolder.textView.setTag(position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setPositionActivatedListener(OnActivatedListener positionActivatedListener) {
        this.onActivatedListener = positionActivatedListener;
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

    /*
    The ViewHolder class extends the RecyclerView.ViewHolder and
    is responsible for storing the inflated views in order to
    recycle them. It's a parameter type on the ListAdapter class.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.title) TextView textView;
        @Bind(R.id.date) TextView date;

        public ViewHolder(View itemView, int ViewType) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

    }
}