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
    }

    public void insertMessages(Message... newMessages) {
        DB.databaseWriteExecutor.execute(() -> {
            messageDao.insert(newMessages);
        });
    }

    public void insertUsers(User... newUsers) {
        DB.databaseWriteExecutor.execute(() -> {
            userDao.insert(newUsers);
        });
    }

    public boolean checkUserName(String userName) {
        try {
            return Executors.newSingleThreadExecutor().submit(()->userDao.checkUserName(userName)).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

//    public LiveData<List<UserMessage>> getConversationList(String userName) {
//        try {
//            return Executors.newSingleThreadExecutor().submit(()->userMessageDao.getConversationList(userName)).get();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
}
