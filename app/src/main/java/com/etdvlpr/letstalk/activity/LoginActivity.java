package com.etdvlpr.letstalk.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.etdvlpr.letstalk.R;
import com.etdvlpr.letstalk.data.DataRepository;
import com.etdvlpr.letstalk.util.SharedPref;
import com.etdvlpr.letstalk.util.SyncWorker;

import java.util.concurrent.TimeUnit;

public class LoginActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        SharedPref.init(getApplicationContext());

        final EditText usernameEditText = findViewById(R.id.username);
        final Button loginButton = findViewById(R.id.login);
        final ProgressBar loadingProgressBar = findViewById(R.id.loading);

        DataRepository mRepository = DataRepository.getInstance(getApplicationContext());

        loginButton.setOnClickListener(v -> {
            if(usernameEditText.getText() == null) return;
            if(mRepository.checkUserName(usernameEditText.getText().toString())){
                SharedPref.write("UserName",usernameEditText.getText().toString());
                SharedPref.userName = usernameEditText.getText().toString();
                loadingProgressBar.setVisibility(View.VISIBLE);
                Intent intent = new Intent(v.getContext(), ConversationListActivity.class);
                v.getContext().startActivity(intent);
                finish();
            } else {
                Intent intent = new Intent(v.getContext(), CreateUserActivity.class);
                v.getContext().startActivity(intent);
            }
        });

        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();
        PeriodicWorkRequest syncWorkRequest =
                new PeriodicWorkRequest.Builder(SyncWorker.class, 1, TimeUnit.MINUTES)
                        .setConstraints(constraints)
                        .build();
        WorkManager
                .getInstance(getApplicationContext())
                .enqueue(syncWorkRequest);
    }
}