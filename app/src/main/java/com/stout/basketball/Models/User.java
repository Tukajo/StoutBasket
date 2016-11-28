package com.stout.basketball.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by TJ Frisch on 10/2/2016.
 */

public class User implements Parcelable {

    private String user_uuid;
    private String username;
    private String firstName;
    private String lastName;
    private ArrayList<Player> favoritePlayers = new ArrayList<Player>();
    //Extend this data model later.

    protected User(Parcel in) {
        user_uuid = in.readString();
        if(user_uuid==null){
            user_uuid = UUID.randomUUID().toString();
        }
        username = in.readString();
        firstName = in.readString();
        lastName = in.readString();
        in.readTypedList(favoritePlayers,Player.CREATOR);
    }

    public User(String username, String firstName, String lastName){
        user_uuid = UUID.randomUUID().toString();
        this.username = username;
        this.lastName = lastName;
        this.firstName = firstName;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return this.hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(user_uuid);
        dest.writeString(username);
        dest.writeString(lastName);
        dest.writeString(firstName);
        dest.writeTypedList(favoritePlayers);
    }
}
