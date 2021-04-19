package com.etdvlpr.letstalk.util;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutionException;

public class SyncWorkerUp extends Worker {
    DataRepository mRepository;
    NetworkSingleton mNetwork;
    NetworkSingleton.VolleyCallback mVolleyCallback;
    public SyncWorkerUp(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        mRepository = DataRepository.getInstance(context);
        mNetwork = NetworkSingleton.getInstance(context);
        mVolleyCallback = new NetworkSingleton.VolleyCallback() {
            @Override
            public void onSuccess(String result)  {
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SharedPref.last_up_sync = new Date();
                SharedPref.write("lastUpSync", df.format(SharedPref.last_up_sync));
                Log.d("uploadMsg", result);
            }

            @Override
            public void onError(VolleyError error) {
                Log.d("uploadMsg", error.getMessage());
            }
        };
    }

    @NonNull
    @Override
    public Result doWork() {
        uploadData();
        return Result.success();
    }

    private void uploadData() {
        ArrayList<Message> pendingMessages = (ArrayList<Message>) mRepository.getMessagesAfter(SharedPref.last_up_sync);
        if(!pendingMessages.isEmpty()){
            for (Message msg:pendingMessages) {
                mNetwork.uploadMsg(msg.toMap(), mVolleyCallback);
            }
        }
    }
}
