package com.etdvlpr.letstalk.data.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class User {

    @ColumnInfo(name = "user_name")
    @PrimaryKey
    @NonNull
    private String userName;

    @ColumnInfo(name = "display_name")
    private String displayName;

    @ColumnInfo(name = "created_at")
    private Date createdAt;

    public User(String userName, String displayName, Date createdAt) {
        this.userName = userName;
        this.displayName = displayName;
        this.createdAt = createdAt;
    }

    public void setUserName(@NonNull String userName) {
        this.userName = userName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @NonNull
    public String getUserName() {
        return userName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Date getCreatedAt() {
        return createdAt;
    }
}
