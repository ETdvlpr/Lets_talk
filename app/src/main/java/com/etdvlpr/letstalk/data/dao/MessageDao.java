package com.etdvlpr.letstalk.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.etdvlpr.letstalk.data.model.Message;
import com.etdvlpr.letstalk.data.model.User;

import java.util.Date;
import java.util.List;

@Dao
public interface MessageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Message msg);

    @Query("SELECT * FROM Message WHERE send_time > :targetDate")
    List<Message> findMessagesSentAfter(Date targetDate);
}
