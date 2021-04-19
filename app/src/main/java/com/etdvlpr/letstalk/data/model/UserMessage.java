package com.etdvlpr.letstalk.data.model;

import java.io.Serializable;
import java.util.Date;

public class UserMessage implements Serializable {
    public String displayName;
    public String lastMessage;
    public Date lastMessageTime;
    public String lastMessageStatus;
    public String lastMessageSender;
    public String userName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserMessage conversation = (UserMessage) o;
        return userName.equals(conversation.userName) &&
                displayName.equals(conversation.displayName) &&
                lastMessage.equals(conversation.lastMessage) &&
                lastMessageTime.equals(conversation.lastMessageTime) &&
                lastMessageStatus.equals(conversation.lastMessageStatus) &&
                lastMessageSender.equals(conversation.lastMessageSender);
    }
}
