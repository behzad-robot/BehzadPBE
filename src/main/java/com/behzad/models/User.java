package com.behzad.models;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

/**
 * Created by behzad on 20/02/18.
 */

public class User
{
    public long id;
    @SerializedName("login") public String username;
    public static User create(Gson gson, String json){
        return gson.fromJson(json , User.class);
    }
}
