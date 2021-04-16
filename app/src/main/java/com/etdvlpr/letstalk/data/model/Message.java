package com.etdvlpr.letstalk.data.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.apache.commons.net.ntp.NTPUDPClient;
import org.apache.commons.net.ntp.TimeInfo;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

@Entity
public class Message {
    public Message(String content, String sender, String receiver){
        this.content = content;
        this.sender = sender;
        this.receiver = receiver;

        try {
            NTPUDPClient timeClient = new NTPUDPClient();
            InetAddress inetAddress = InetAddress.getByName("time-a.nist.gov");
            TimeInfo timeInfo = timeClient.getTime(inetAddress);
            sendTime = new Date(timeInfo.getMessage().getTransmitTimeStamp().getTime());
        } catch (IOException e) {
            e.printStackTrace();
        }
        ID = sender + sendTime;
    }

    @PrimaryKey
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
}
