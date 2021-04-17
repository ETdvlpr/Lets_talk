package com.etdvlpr.letstalk.data.dao;

import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.etdvlpr.letstalk.data.model.Message;
import com.etdvlpr.letstalk.data.model.User;
import com.etdvlpr.letstalk.data.model.UserMessage;

import java.util.Date;
import java.util.List;

@Dao
public interface MessageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Message... msg);

    @Query("SELECT * FROM Message WHERE send_time > :targetDate")
    List<Message> findMessagesSentAfter(Date targetDate);

    @Query("SELECT * FROM Message WHERE (sender = :user1 AND receiver = :user2) OR (sender = :user2 AND receiver = :user1) ORDER BY send_time")
    DataSource.Factory<Integer, Message> getMessageList(String user1, String user2);
}
