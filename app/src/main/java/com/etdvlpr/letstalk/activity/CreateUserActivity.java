package com.etdvlpr.letstalk.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.etdvlpr.letstalk.R;
import com.etdvlpr.letstalk.data.DataRepository;
import com.etdvlpr.letstalk.data.model.User;
import com.etdvlpr.letstalk.util.NetworkSingleton;
import com.etdvlpr.letstalk.util.SharedPref;

public class CreateUserActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);
        SharedPref.init(getApplicationContext());

        final EditText usernameEditText = findViewById(R.id.create_user_username);
        final EditText displaynameEditText = findViewById(R.id.create_user_display_name);
        final Button createButton = findViewById(R.id.create_user_btn);
        final ProgressBar loadingProgressBar = findViewById(R.id.create_user_loading);

        DataRepository mRepository = DataRepository.getInstance(getApplicationContext());

        createButton.setOnClickListener(v -> {
            if(usernameEditText.getText() == null) {
                usernameEditText.setError("user name can not be empty");
                return;
            } else if (displaynameEditText.getText() == null){
                displaynameEditText.setError("Display name cannot be empty");
               return;
            }
            String username = usernameEditText.getText().toString();
            String display_name = displaynameEditText.getText().toString();
            if(!mRepository.checkUserName(username)){
                //add user to online db
                NetworkSingleton.getInstance(getApplicationContext()).create_user(username, display_name,
                        new NetworkSingleton.VolleyCallback() {
                            @Override
                            public void onSuccess(String result) {
                                if(result.equals("success")) {
                                    User user = new User(username, display_name, null);
                                    mRepository.insertUsers(user);
                                    SharedPref.write("UserName", username);
                                    SharedPref.userName = username;
                                    loadingProgressBar.setVisibility(View.VISIBLE);
                                    Intent intent = new Intent(v.getContext(), ConversationListActivity.class);
                                    v.getContext().startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onError(VolleyError error) {
                                String errorMsg = error.getMessage();
                                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                                    errorMsg = "Internet connection not available.";
                                } else if (error instanceof AuthFailureError) {
                                    errorMsg = "Authentication Error.";
                                } else if (error instanceof ServerError) {
                                    errorMsg = "Server Error.";
                                } else if (error instanceof NetworkError) {
                                    errorMsg = "Network Error.";
                                } else if (error instanceof ParseError) {
                                    errorMsg = "Parse Error.";
                                }
                                Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_LONG).show();
                            }
                        });
            } else {
                usernameEditText.setError("user name already exists");
            }
        });
    }
}