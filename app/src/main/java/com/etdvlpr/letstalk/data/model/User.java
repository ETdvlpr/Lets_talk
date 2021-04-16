package com.etdvlpr.letstalk.data.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {

    @ColumnInfo(name = "user_name")
    @PrimaryKey
    private String userName;

    @ColumnInfo(name = "display_name")
    private String displayName;

    public User(String userName, String displayName) {
        this.userName = userName;
        this.displayName = displayName;
    }

    public String getUserName() {
        return userName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
