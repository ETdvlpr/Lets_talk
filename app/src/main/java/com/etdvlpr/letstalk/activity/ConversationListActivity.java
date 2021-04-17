package com.etdvlpr.letstalk.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.etdvlpr.letstalk.Adapter.ConversationListAdapter;
import com.etdvlpr.letstalk.R;
import com.etdvlpr.letstalk.data.letsTalkDb;
import com.etdvlpr.letstalk.viewModel.ConversationListViewModel;
import com.etdvlpr.letstalk.viewModel.ConversationListViewModelFactory;

public class ConversationListActivity extends AppCompatActivity {
    private ConversationListViewModel viewModel;
    private ConversationListAdapter adapter = new ConversationListAdapter();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation_list);

        final RecyclerView recyclerView = findViewById(R.id.activity_conversation_list_recycler);
        LinearLayoutManager homeLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(homeLayoutManager);

        viewModel = new ViewModelProvider(this,
                new ConversationListViewModelFactory(letsTalkDb.getInstance(getApplicationContext()).userMessageDao())
        ).get(ConversationListViewModel.class);
        viewModel.conversationList.observe(this, adapter::submitList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewModel.conversationList.removeObservers(this);
    }
}
