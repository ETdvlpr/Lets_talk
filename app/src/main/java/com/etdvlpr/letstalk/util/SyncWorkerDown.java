package com.etdvlpr.letstalk.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.etdvlpr.letstalk.data.DataRepository;
import com.etdvlpr.letstalk.data.model.Message;
import com.etdvlpr.letstalk.data.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.concurrent.ExecutionException;

public class SyncWorkerDown extends Worker {
    public SyncWorkerDown(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        //download new data
        downloadData();
        //if upload flag then upload new data
        return Result.success();
    }

    private void downloadData() {
        String url = String.format("%sdata.php?since=%susername=%s",NetworkSingleton.url,SharedPref.last_down_sync,SharedPref.userName);
        RequestQueue queue = NetworkSingleton.getInstance(getApplicationContext()).getRequestQueue();
        RequestFuture<JSONObject> future = RequestFuture.newFuture();
        JsonObjectRequest request = new JsonObjectRequest(url, null, future, future);
        queue.add(request);

        try {
            JSONObject response = future.get();
            final JSONArray users = response.getJSONArray("users");
            final JSONArray messages = response.getJSONArray("messages");
            DataRepository repo = DataRepository.getInstance(getApplicationContext());
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if(users.length() > 0) {
                User[] newUsers = new User[users.length()];
                for (int i=0; i<users.length(); i++) {
                    JSONObject user = users.getJSONObject(i);
                    try{
                        newUsers[i] = new User(user.getString("user_name"), user.getString("display_name"), df.parse(user.getString("created_at")));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                repo.insertUsers(newUsers);
            }
            if(messages.length() > 0) {
                Message[] newMessages = new Message[messages.length()];
                for (int i=0; i<messages.length(); i++) {
                    JSONObject message = messages.getJSONObject(i);
                    try{
                        newMessages[i] = new Message(message.getString("content"), message.getString("sender"), message.getString("receiver"));
                        newMessages[i].setID(message.getString("ID"));
                        if(!message.getString("read_time").equals("null"))
                            newMessages[i].setReadTime(df.parse(message.getString("read_time")));
                        if(!message.getString("sync_time").equals("null"))
                            newMessages[i].setSyncTime(df.parse(message.getString("sync_time")));
                        newMessages[i].setSendTime(df.parse(message.getString("send_time")));
                        newMessages[i].setStatus(message.getString("status"));

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                repo.insertMessages(newMessages);
            }
            SharedPref.write("last_down_sync", response.getString("time"));
            SharedPref.last_down_sync = response.getString("time");
        } catch (JSONException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
