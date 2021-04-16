package com.etdvlpr.letstalk.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.etdvlpr.letstalk.data.model.UserMessage;

import java.util.Date;
import java.util.List;

@Dao
public interface UserMessageDao {

    @Query("SELECT display_name AS displayName, content AS lastMessage, send_time AS lastMessageTime, message.status AS lastMessageStatus " +
            "FROM user LEFT JOIN message ON user.user_name = message.sender " +
            "WHERE sender = 'd' OR receiver = 'd' GROUP BY user.user_name ORDER BY message.send_time DESC")
    public LiveData<List<UserMessage>> getConversationList();
}
