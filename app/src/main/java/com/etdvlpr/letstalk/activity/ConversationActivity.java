package com.etdvlpr.letstalk.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.etdvlpr.letstalk.Adapter.ConversationAdapter;
import com.etdvlpr.letstalk.Adapter.ConversationListAdapter;
import com.etdvlpr.letstalk.R;
import com.etdvlpr.letstalk.data.DataRepository;
import com.etdvlpr.letstalk.data.letsTalkDb;
import com.etdvlpr.letstalk.data.model.Message;
import com.etdvlpr.letstalk.util.SharedPref;
import com.etdvlpr.letstalk.viewModel.ConversationListViewModel;
import com.etdvlpr.letstalk.viewModel.ConversationListViewModelFactory;
import com.etdvlpr.letstalk.viewModel.ConversationViewModel;
import com.etdvlpr.letstalk.viewModel.ConversationViewModelFactory;

public class ConversationActivity extends AppCompatActivity {
    private ConversationViewModel viewModel;
    private ConversationAdapter adapter = new ConversationAdapter();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);

        final EditText messageEditText = findViewById(R.id.activity_conversation_message);
        final ImageButton sendButton = findViewById(R.id.activity_conversation_message_button);

        final RecyclerView recyclerView = findViewById(R.id.activity_conversation_recycler);
        LinearLayoutManager homeLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(homeLayoutManager);

        String user = getIntent().getStringExtra("user");
        viewModel = new ViewModelProvider(this,
                new ConversationViewModelFactory(letsTalkDb.getInstance(getApplicationContext()).messageDao(), user)
        ).get(ConversationViewModel.class);
        viewModel.messageList.observe(this, adapter::submitList);
        recyclerView.setAdapter(adapter);

        DataRepository mRepository = DataRepository.getInstance(getApplicationContext());

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(messageEditText.getText() == null) return;
                Message msg = new Message(messageEditText.getText().toString(), SharedPref.userName, user);
                mRepository.insertMessages(msg);
                //trigger update
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewModel.messageList.removeObservers(this);
    }
}
