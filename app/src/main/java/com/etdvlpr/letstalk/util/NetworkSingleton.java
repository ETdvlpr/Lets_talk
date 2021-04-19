package com.etdvlpr.letstalk.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.util.LruCache;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.etdvlpr.letstalk.data.DataRepository;
import com.etdvlpr.letstalk.data.model.Message;
import com.etdvlpr.letstalk.data.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class NetworkSingleton {
    public static String url = "https://etdvlpr.com/";
    private static NetworkSingleton instance;
    private RequestQueue requestQueue;
    private static Context ctx;

    private NetworkSingleton(Context context) {
        ctx = context;
        requestQueue = getRequestQueue();
    }

    public static synchronized NetworkSingleton getInstance(Context context) {
        if (instance == null) {
            instance = new NetworkSingleton(context);
        }
        return instance;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            requestQueue = Volley.newRequestQueue(ctx.getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    public interface VolleyCallback{
        void onSuccess(String result);
        void onError(VolleyError error);
    }

    public void create_user(String username, String display_name, final VolleyCallback callback) {
        String url = NetworkSingleton.url +"data.php";
        final StringRequest dataRequest = new StringRequest
                (Request.Method.POST, url, response -> callback.onSuccess(response), error -> callback.onError(error)){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("user_name", username);
                MyData.put("display_name", display_name);
                return MyData;
            }
        };
        dataRequest.setShouldCache(false);
        instance.getRequestQueue().add(dataRequest);
    }

    public void uploadMsg(Map<String, String> msg, VolleyCallback callback) {
        String url = NetworkSingleton.url +"data.php";
        final StringRequest dataRequest = new StringRequest
                (Request.Method.POST, url, response -> callback.onSuccess(response), error -> callback.onError(error)){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return msg;
            }
        };
        dataRequest.setShouldCache(false);
        instance.getRequestQueue().add(dataRequest);
    }
}