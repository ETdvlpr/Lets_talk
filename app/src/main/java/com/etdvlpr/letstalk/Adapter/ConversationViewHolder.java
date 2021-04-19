package com.etdvlpr.letstalk.Adapter;

import android.text.format.DateUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.etdvlpr.letstalk.R;
import com.etdvlpr.letstalk.data.model.Message;

public class ConversationViewHolder extends RecyclerView.ViewHolder{
    ImageView status;
    TextView date;
    TextView message;
    Message currentMessage;

    public ConversationViewHolder(@NonNull View itemView, int viewType) {
        super(itemView);
        if(viewType == ConversationAdapter.SENT_MESSAGE) {
            date = itemView.findViewById(R.id.sent_timestamp);
            message = itemView.findViewById(R.id.sent_message);
            status = itemView.findViewById(R.id.message_sent_status);
        } else {
            date = itemView.findViewById(R.id.received_timestamp);
            message = itemView.findViewById(R.id.received_message);
        }
    }

    public void bindTo(@NonNull Message msg) {
        currentMessage = msg;
        date.setText(DateUtils.getRelativeTimeSpanString(msg.getSendTime().getTime()));
        message.setText(msg.getContent());

        if(status != null) {
            if(msg.getStatus().equals("Read")) {
                status.setImageResource(R.drawable.ic_read_24);
            } else if (msg.getStatus().equals("Sent")) {
                status.setImageResource(R.drawable.ic_sent_24);
            } else if (msg.getStatus().equals("Sending")) {
                status.setImageResource(R.drawable.ic_sending_24);
            }
        }
    }
}
