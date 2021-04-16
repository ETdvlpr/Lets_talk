package com.etdvlpr.letstalk.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.etdvlpr.letstalk.R;
import com.etdvlpr.letstalk.ui.viewModel.ConversationListViewModel;

public class ConversationListActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation_list);

        final RecyclerView recyclerView = findViewById(R.id.activity_conversation_list_recycler);
        ConversationListViewModel model = new ViewModelProvider(this).get(ConversationListViewModel.class);
        model.getUsers().observe(this, users -> {
            // update UI
        });
    }
}
