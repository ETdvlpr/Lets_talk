package com.etdvlpr.letstalk.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;

import com.etdvlpr.letstalk.R;
import com.etdvlpr.letstalk.data.model.Message;
import com.etdvlpr.letstalk.data.model.UserMessage;
import com.etdvlpr.letstalk.util.SharedPref;

public class ConversationAdapter extends PagedListAdapter<Message, ConversationViewHolder> {
    final static int SENT_MESSAGE = 0, RECEIVED_MESSAGE = 1;

    public ConversationAdapter(){
        super(DIFF_CALLBACK);
    }
    @NonNull
    @Override
    public ConversationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        if(viewType == SENT_MESSAGE){
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_sent, parent, false);
        } else {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_received, parent, false);
        }
        return new ConversationViewHolder(v, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull ConversationViewHolder holder, int position) {
        Message message = getItem(position);
        if(message != null)
            holder.bindTo(message);
    }

    @Override
    public int getItemViewType(int position) {
        Message message = getItem(position);
        if(message != null) {
            if(message.getSender().equals(SharedPref.userName)){
                return SENT_MESSAGE;
            } else {
                return RECEIVED_MESSAGE;
            }
        }
        return super.getItemViewType(position);
    }

    private static DiffUtil.ItemCallback<Message> DIFF_CALLBACK = new DiffUtil.ItemCallback<Message>() {
        @Override
        public boolean areItemsTheSame(@NonNull Message oldItem, @NonNull Message newItem) {
            return oldItem.getID() == newItem.getID();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Message oldItem, @NonNull Message newItem) {
            return oldItem.equals(newItem);
        }
    };
}
