package com.etdvlpr.letstalk.data;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.etdvlpr.letstalk.data.dao.MessageDao;
import com.etdvlpr.letstalk.data.dao.UserDao;
import com.etdvlpr.letstalk.data.dao.UserMessageDao;
import com.etdvlpr.letstalk.data.model.Message;
import com.etdvlpr.letstalk.data.model.User;
import com.etdvlpr.letstalk.data.model.UserMessage;

import java.util.List;
import java.util.concurrent.Executors;

public class DataRepository {
    private UserMessageDao userMessageDao;
    private MessageDao messageDao;
    private UserDao userDao;
    private letsTalkDb DB;
    private static DataRepository instance;
    private LiveData<List<UserMessage>> conversationList;

    public static DataRepository getInstance(Context context) {
        if(instance == null) {
            instance = new DataRepository(context);
        }
        return instance;
    }

    private DataRepository(Context context){
        DB = letsTalkDb.getInstance(context);
        messageDao = DB.messageDao();
        userDao = DB.userDao();
        userMessageDao = DB.userMessageDao();
        conversationList = userMessageDao.getConversationList();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public LiveData<List<UserMessage>> getConversationList() {
        return conversationList;
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    public void insertMessage(Message msg) {
        DB.databaseWriteExecutor.execute(() -> {
            messageDao.insert(msg);
        });
    }

//    public List<User> getUserConversationList() {
//        try {
//            return Executors.newSingleThreadExecutor().submit(userDao::getUserConversationList).get();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
}
