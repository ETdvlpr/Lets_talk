package com.etdvlpr.letstalk.Adapter;

import android.content.Intent;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.etdvlpr.letstalk.R;
import com.etdvlpr.letstalk.activity.ConversationActivity;
import com.etdvlpr.letstalk.data.model.UserMessage;
import com.etdvlpr.letstalk.util.SharedPref;

import de.hdodenhof.circleimageview.CircleImageView;

public class ConversationListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    TextView displayName;
    TextView date;
    TextView lastMessage;
    CircleImageView unread_indicator;
    ImageView status;
    UserMessage currentConversation;

    public ConversationListViewHolder(@NonNull View itemView) {
        super(itemView);
        displayName = itemView.findViewById(R.id.conversation_list_item_name);
        date = itemView.findViewById(R.id.conversation_list_item_date);
        lastMessage = itemView.findViewById(R.id.conversation_list_item_last_msg);
        unread_indicator = itemView.findViewById(R.id.unread_indicator);
        status = itemView.findViewById(R.id.conversation_list_item_status);
        itemView.setOnClickListener(this);
    }

    public void bindTo(@NonNull UserMessage conversation) {
        currentConversation = conversation;
        displayName.setText(conversation.displayName);
        Log.d("lastMsg",conversation.lastMessageStatus);
        if(conversation.lastMessageTime != null) {
            date.setText(DateUtils.getRelativeTimeSpanString(conversation.lastMessageTime.getTime()));
            lastMessage.setText(conversation.lastMessage);
            if(conversation.lastMessageSender.equals(SharedPref.userName)) {
                if(conversation.lastMessageStatus.equals("Read")) {
                    status.setImageResource(R.drawable.ic_read_24_black);
                } else if (conversation.lastMessageStatus.equals("Sent")) {
                    status.setImageResource(R.drawable.ic_sent_24_black);
                } else if (conversation.lastMessageStatus.equals("Sending")) {
                    status.setImageResource(R.drawable.ic_sending_24_black);
                }
            } else if(!conversation.lastMessageStatus.equals("Read")){
                Log.d("sender",conversation.lastMessageSender);
                unread_indicator.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(v.getContext(), ConversationActivity.class);
        intent.putExtra("user", currentConversation.userName);
        v.getContext().startActivity(intent);
    }
}
