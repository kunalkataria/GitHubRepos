package com.example.kunalkataria.myapplication;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kunalkataria on 6/2/16.
 */
public class Owner {

    @SerializedName("login")
    @Expose
    public String login;

    @SerializedName("avatar_url")
    @Expose
    public String avatarUrl;

    @SerializedName("html_url")
    @Expose
    public String htmlUrl;
}
