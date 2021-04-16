package com.etdvlpr.letstalk.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.etdvlpr.letstalk.data.model.User;
import com.etdvlpr.letstalk.ui.login.LoginActivity;

import java.util.List;

public interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(User user);

    @Query("SELECT * FROM user LEFT JOIN message WHERE sender = " + LoginActivity.userName + "+ OR receiver = " + LoginActivity.userName + " ORDER BY send_time DESC")
    LiveData<List<User>> getUserConversationList();
}
