package com.etdvlpr.letstalk.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;

import com.etdvlpr.letstalk.R;
import com.etdvlpr.letstalk.data.model.UserMessage;

public class ConversationListAdapter  extends PagedListAdapter<UserMessage, ConversationListViewHolder> {
    public ConversationListAdapter(){
        super(DIFF_CALLBACK);
    }
    @NonNull
    @Override
    public ConversationListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.conversation_list_item, parent, false);
        return new ConversationListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ConversationListViewHolder holder, int position) {
        UserMessage conversation = getItem(position);
        if(conversation != null)
            holder.bindTo(conversation);
    }

    private static DiffUtil.ItemCallback<UserMessage> DIFF_CALLBACK = new DiffUtil.ItemCallback<UserMessage>() {
        @Override
        public boolean areItemsTheSame(@NonNull UserMessage oldItem, @NonNull UserMessage newItem) {
            return oldItem.displayName == newItem.displayName;
        }

        @Override
        public boolean areContentsTheSame(@NonNull UserMessage oldItem, @NonNull UserMessage newItem) {
            return oldItem.equals(newItem);
        }
    };
}
