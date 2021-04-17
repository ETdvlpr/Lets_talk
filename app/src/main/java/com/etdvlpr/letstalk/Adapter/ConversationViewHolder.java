package com.etdvlpr.letstalk.Adapter;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.etdvlpr.letstalk.R;
import com.etdvlpr.letstalk.activity.ConversationActivity;
import com.etdvlpr.letstalk.data.model.Message;
import com.etdvlpr.letstalk.data.model.UserMessage;

public class ConversationViewHolder extends RecyclerView.ViewHolder{
    TextView displayName;
    TextView date;
    TextView message;
    Message currentMessage;

    public ConversationViewHolder(@NonNull View itemView, int viewType) {
        super(itemView);
        if(viewType == ConversationAdapter.SENT_MESSAGE) {
            date = itemView.findViewById(R.id.sent_timestamp);
            message = itemView.findViewById(R.id.sent_message);
        } else {
            displayName = itemView.findViewById(R.id.received_contact_name);
            date = itemView.findViewById(R.id.received_timestamp);
            message = itemView.findViewById(R.id.received_message);
        }
    }

    public void bindTo(@NonNull Message msg) {
        currentMessage = msg;
        if(displayName != null) {
            displayName.setText(msg.getSender());
        }
        if(msg.getSendTime() != null) {
            date.setText(msg.getSendTime().toString());
            this.message.setText(msg.getContent());
        }
    }
}
