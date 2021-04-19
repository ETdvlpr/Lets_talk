package com.etdvlpr.letstalk.data;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.etdvlpr.letstalk.data.dao.MessageDao;
import com.etdvlpr.letstalk.data.dao.UserDao;
import com.etdvlpr.letstalk.data.dao.UserMessageDao;
import com.etdvlpr.letstalk.data.model.Message;
import com.etdvlpr.letstalk.data.model.User;
import com.etdvlpr.letstalk.data.model.UserMessage;
import com.etdvlpr.letstalk.util.SharedPref;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
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
        Executors.newSingleThreadExecutor().execute(()->messageDao.insert(newMessages));
    }

    public void insertUsers(User... newUsers) {
        Executors.newSingleThreadExecutor().execute(()->userDao.insert(newUsers));
    }

    public boolean checkUserName(String userName) {
        try {
            return Executors.newSingleThreadExecutor().submit(()->userDao.checkUserName(userName)).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Message> getMessagesAfter(Date last_up_sync) {
        try {
            return Executors.newSingleThreadExecutor().submit(()->messageDao.getMessagesAfter(last_up_sync)).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<Message>();
    }

    public void updateUnreadMessages(String user, Date date) {
        Executors.newSingleThreadExecutor().execute(()->messageDao.updateUnreadMessages(SharedPref.userName, user, date));
    }

    public void updateMessage(Message msg) {
        Executors.newSingleThreadExecutor().execute(()->messageDao.update(msg));
    }
}
