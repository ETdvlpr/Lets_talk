package com.etdvlpr.letstalk.data.dao;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Query;

import com.etdvlpr.letstalk.data.model.UserMessage;

import java.util.Date;
import java.util.List;

@Dao
public interface UserMessageDao {

    @Query("SELECT user_name AS userName, display_name AS displayName, content AS lastMessage, send_time AS lastMessageTime, userMessages.status AS lastMessageStatus " +
            "FROM\n" +
            "    USER\n" +
            "LEFT JOIN (SELECT * FROM message WHERE sender = :userName OR receiver = :userName) AS userMessages ON user.user_name = userMessages.receiver OR user.user_name = userMessages.sender\n" +
            "WHERE user_name != :userName\n" +
            "GROUP BY user_name\n" +
            "ORDER BY\n" +
            "    userMessages.send_time\n" +
            "DESC")
     DataSource.Factory<Integer, UserMessage> getConversationList(String userName);
}
