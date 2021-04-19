package com.etdvlpr.letstalk.data.dao;

import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.etdvlpr.letstalk.data.model.Message;
import com.etdvlpr.letstalk.data.model.User;
import com.etdvlpr.letstalk.data.model.UserMessage;

import java.util.Date;
import java.util.List;

@Dao
public interface MessageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Message... msg);

    @Update
    void update(Message... msg);

    @Query("SELECT * FROM Message WHERE (sender = :user1 AND receiver = :user2) OR (sender = :user2 AND receiver = :user1) ORDER BY send_time")
    DataSource.Factory<Integer, Message> getMessageList(String user1, String user2);

    @Query("SELECT * FROM Message WHERE send_time > :targetDate OR read_time > :targetDate")
    List<Message> getMessagesAfter(Date targetDate);

    @Query("UPDATE Message SET read_time = :date, status = 'Read' WHERE status = 'Sent' AND sender = :user AND receiver = :userName")
    void updateUnreadMessages(String userName, String user, Date date);
}
