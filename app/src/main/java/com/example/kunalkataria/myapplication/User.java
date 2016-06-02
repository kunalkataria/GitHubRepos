package com.example.kunalkataria.myapplication;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("login")
    @Expose
    public String login;

    @SerializedName("id")
    @Expose
    public Integer id;

    @SerializedName("avatar_url")
    @Expose
    public String avatarUrl;


}
