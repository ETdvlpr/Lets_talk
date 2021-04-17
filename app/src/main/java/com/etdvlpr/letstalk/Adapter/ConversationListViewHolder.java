package com.etdvlpr.letstalk.Adapter;

import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.etdvlpr.letstalk.R;
import com.etdvlpr.letstalk.activity.ConversationActivity;
import com.etdvlpr.letstalk.data.model.UserMessage;
import com.google.android.material.textview.MaterialTextView;

public class ConversationListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    TextView displayName;
    TextView date;
    TextView lastMessage;
    UserMessage currentConversation;

    public ConversationListViewHolder(@NonNull View itemView) {
        super(itemView);
        displayName = itemView.findViewById(R.id.conversation_list_item_name);
        date = itemView.findViewById(R.id.conversation_list_item_date);
        lastMessage = itemView.findViewById(R.id.conversation_list_item_last_msg);
        itemView.setOnClickListener(this);
    }

    public void bindTo(@NonNull UserMessage conversation) {
        currentConversation = conversation;
        displayName.setText(conversation.displayName);
        if(conversation.lastMessageTime != null) {
            date.setText(conversation.lastMessageTime.toString());
            lastMessage.setText(conversation.lastMessage);
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(v.getContext(), ConversationActivity.class);
        intent.putExtra("user", currentConversation.userName);
        v.getContext().startActivity(intent);
    }
}
