package com.etdvlpr.letstalk.data.model;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.apache.commons.net.ntp.NTPUDPClient;
import org.apache.commons.net.ntp.TimeInfo;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.Objects;

@Entity
public class Message {

    @PrimaryKey
    @NonNull
    private String ID;

    private String content;

    private String sender;

    private String receiver;

    @ColumnInfo(name = "send_time")
    private Date sendTime;

    @ColumnInfo(name = "sync_time")
    private Date syncTime;

    @ColumnInfo(name = "read_time")
    private Date readTime;

    private String status;

    public Message(String content, String sender, String receiver){
        this.content = content;
        this.sender = sender;
        this.receiver = receiver;
        this.status = "Sending";

        try {
            NTPUDPClient timeClient = new NTPUDPClient();
            InetAddress inetAddress = InetAddress.getByName("time-a.nist.gov");
            TimeInfo timeInfo = timeClient.getTime(inetAddress);
            sendTime = new Date(timeInfo.getMessage().getTransmitTimeStamp().getTime());
        } catch (IOException e) {
            e.printStackTrace();
        }
        ID = sender + sendTime.getTime();
    }

    public String getID() {
        return ID;
    }

    public String getContent() {
        return content;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public Date getSyncTime() {
        return syncTime;
    }

    public Date getReadTime() {
        return readTime;
    }

    public String getStatus() {
        return status;
    }

    public void setID(@NonNull String ID) {
        this.ID = ID;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public void setSyncTime(Date syncTime) {
        this.syncTime = syncTime;
    }

    public void setReadTime(Date readTime) {
        this.readTime = readTime;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Message)) return false;
        Message message = (Message) o;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return ID.equals(message.ID) &&
                    Objects.equals(content, message.content) &&
                    Objects.equals(sender, message.sender) &&
                    Objects.equals(receiver, message.receiver) &&
                    Objects.equals(sendTime, message.sendTime) &&
                    Objects.equals(syncTime, message.syncTime) &&
                    Objects.equals(readTime, message.readTime) &&
                    Objects.equals(status, message.status);
        } else {
            return ID.equals(message.getID());
        }
    }

    public JSONObject toJSON(){
        JSONObject msg = new JSONObject();

        try {
            msg.put("ID", getID());
            msg.put("content", getContent());
            msg.put("sender", getSender());
            msg.put("receiver", getReceiver());
            msg.put("send_time", getSendTime());
            msg.put("sync_time", getSyncTime());
            msg.put("read_time", getReadTime());
            msg.put("status", getStatus());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return msg;
    }
}
