package com.etdvlpr.letstalk.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.etdvlpr.letstalk.data.dao.MessageDao;
import com.etdvlpr.letstalk.data.dao.UserDao;
import com.etdvlpr.letstalk.data.dao.UserMessageDao;
import com.etdvlpr.letstalk.data.model.Message;
import com.etdvlpr.letstalk.data.model.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Message.class, User.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class letsTalkDb extends RoomDatabase {
    private static letsTalkDb instance;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static synchronized letsTalkDb getInstance(Context context) {
        if(instance == null) {
            instance = Room.databaseBuilder(context, letsTalkDb.class, "lets_talk").fallbackToDestructiveMigration().build();
        }
        return instance;
    }

    public abstract UserDao userDao();
    public abstract MessageDao messageDao();
    public abstract UserMessageDao userMessageDao();
}
