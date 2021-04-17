package com.etdvlpr.letstalk.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.etdvlpr.letstalk.data.model.User;
import java.util.List;

@Dao
public interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User... user);

    @Query("SELECT * FROM user")
    LiveData<List<User>> getUserList();

    @Query("SELECT EXISTS(SELECT * FROM user WHERE user_name = :userName)")
    boolean checkUserName(String userName);
}
