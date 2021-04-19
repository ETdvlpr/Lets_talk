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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

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

        sendTime = new Date();
//        try {
//            sendTime = Executors.newSingleThreadExecutor().submit(()-> {
//                NTPUDPClient timeClient = new NTPUDPClient();
//                InetAddress inetAddress = InetAddress.getByName("time-a.nist.gov");
//                TimeInfo timeInfo = timeClient.getTime(inetAddress);
//                return new Date(timeInfo.getMessage().getTransmitTimeStamp().getTime());
//            }).get();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
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
        return ID.equals(message.ID) &&
                content.equals(message.content) &&
                sender.equals(message.sender) &&
                receiver.equals(message.receiver) &&
                sendTime.equals(message.sendTime) &&
                syncTime.equals(message.syncTime) &&
                readTime.equals(message.readTime) &&
                status.equals(message.status);
    }

    public Map<String,String> toMap(){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Map<String, String> msg = new HashMap<String, String>();
        msg.put("ID", getID());
        msg.put("content", getContent());
        msg.put("sender", getSender());
        msg.put("receiver", getReceiver());
        msg.put("send_time", df.format(getSendTime()));
        msg.put("sync_time", getSyncTime() == null ? "" : df.format(getSyncTime()));
        msg.put("read_time", getReadTime() == null ? "" : df.format(getReadTime()));
        msg.put("status", getStatus());

        return msg;
    }
}
