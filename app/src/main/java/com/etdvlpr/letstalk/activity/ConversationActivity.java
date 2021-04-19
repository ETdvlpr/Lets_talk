package com.etdvlpr.letstalk.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;
import com.etdvlpr.letstalk.Adapter.ConversationAdapter;
import com.etdvlpr.letstalk.Adapter.ConversationListAdapter;
import com.etdvlpr.letstalk.R;
import com.etdvlpr.letstalk.data.DataRepository;
import com.etdvlpr.letstalk.data.letsTalkDb;
import com.etdvlpr.letstalk.data.model.Message;
import com.etdvlpr.letstalk.util.NetworkSingleton;
import com.etdvlpr.letstalk.util.SharedPref;
import com.etdvlpr.letstalk.viewModel.ConversationListViewModel;
import com.etdvlpr.letstalk.viewModel.ConversationListViewModelFactory;
import com.etdvlpr.letstalk.viewModel.ConversationViewModel;
import com.etdvlpr.letstalk.viewModel.ConversationViewModelFactory;

import java.util.Date;

public class ConversationActivity extends AppCompatActivity {
    private ConversationViewModel viewModel;
    private ConversationAdapter adapter = new ConversationAdapter();
    private DataRepository mRepository;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);

        final TextView title = findViewById(R.id.activity_conversation_title);
        final EditText messageEditText = findViewById(R.id.activity_conversation_message);
        final ImageButton sendButton = findViewById(R.id.activity_conversation_message_button);

        String user = getIntent().getStringExtra("user");
        title.setText(user);

        Toolbar toolbar = findViewById(R.id.activity_conversation_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        final RecyclerView recyclerView = findViewById(R.id.activity_conversation_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);

        viewModel = new ViewModelProvider(this,
                new ConversationViewModelFactory(letsTalkDb.getInstance(getApplicationContext()).messageDao(), user)
        ).get(ConversationViewModel.class);
        viewModel.messageList.observe(this, adapter::submitList);
        recyclerView.setAdapter(adapter);

        mRepository = DataRepository.getInstance(getApplicationContext());

        //update any unread messages from this user
        mRepository.updateUnreadMessages(user, new Date());

        sendButton.setOnClickListener(v -> {
            if(messageEditText.getText() == null) return;
            Message msg = new Message(messageEditText.getText().toString(), SharedPref.userName, user);
            messageEditText.setText("");
            mRepository.insertMessages(msg);

            //upload message
            NetworkSingleton.getInstance(getApplicationContext()).uploadMsg(msg.toMap(), new NetworkSingleton.VolleyCallback() {
                @Override
                public void onSuccess(String result) {
                    msg.setStatus("Sent");
                    mRepository.updateMessage(msg);
                }

                @Override
                public void onError(VolleyError error) {
                    Log.d("uploadMsg", error.getMessage());
                }
            });
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewModel.messageList.removeObservers(this);
    }
}
