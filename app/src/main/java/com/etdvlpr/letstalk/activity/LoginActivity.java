package com.etdvlpr.letstalk.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import android.os.Handler;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.etdvlpr.letstalk.R;
import com.etdvlpr.letstalk.data.DataRepository;
import com.etdvlpr.letstalk.util.SharedPref;
import com.etdvlpr.letstalk.util.SyncWorkerDown;
import com.etdvlpr.letstalk.util.SyncWorkerUp;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private ProgressBar loadingProgressBar;
    private Button loginButton;
    private DataRepository mRepository;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        SharedPref.init(getApplicationContext());

        usernameEditText = findViewById(R.id.username);
        loginButton = findViewById(R.id.login);
        loadingProgressBar = findViewById(R.id.loading);

        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();
        PeriodicWorkRequest downWorkRequest =
                new PeriodicWorkRequest.Builder(SyncWorkerDown.class, 1, TimeUnit.MINUTES)
                        .setConstraints(constraints)
                        .addTag("downWorkRequest")
                        .build();
        PeriodicWorkRequest upWorkRequest =
                new PeriodicWorkRequest.Builder(SyncWorkerUp.class, 1, TimeUnit.MINUTES)
                        .setConstraints(constraints)
                        .addTag("upWorkRequest")
                        .build();
        WorkManager
                .getInstance(getApplicationContext())
                .enqueue(Arrays.asList(downWorkRequest,upWorkRequest));

        if(SharedPref.userName != null && !SharedPref.userName.equals("")) {
            loadingProgressBar.setVisibility(View.VISIBLE);

            new Handler().postDelayed(() -> {
                startActivity(new Intent(LoginActivity.this, ConversationListActivity.class));
                finish();
            }, 10);
        }

        mRepository = DataRepository.getInstance(getApplicationContext());

        usernameEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_GO) {
                login(v);
                return true;
            }
            return false;
        });
        loginButton.setOnClickListener(v -> {
            login(v);
        });
    }

    private void login(View v){
        if(usernameEditText.getText() == null) return;
        if(mRepository.checkUserName(usernameEditText.getText().toString())){
            SharedPref.write("userName",usernameEditText.getText().toString());
            SharedPref.userName = usernameEditText.getText().toString();
            loadingProgressBar.setVisibility(View.VISIBLE);
            Intent intent = new Intent(v.getContext(), ConversationListActivity.class);
            v.getContext().startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent(v.getContext(), CreateUserActivity.class);
            intent.putExtra("user", usernameEditText.getText() == null ? "" : usernameEditText.getText().toString());
            v.getContext().startActivity(intent);
        }
    }
}