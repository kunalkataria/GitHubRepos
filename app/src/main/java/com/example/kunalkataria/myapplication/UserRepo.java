package com.example.kunalkataria.myapplication;

/**
 * Created by kunalkataria on 6/1/16.
 */

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserRepo {

    @SerializedName("id")
    @Expose
    public Integer id;

    @SerializedName("name")
    @Expose
    public String name;

    @SerializedName("full_name")
    @Expose
    public String fullName;

    @SerializedName("login")
    @Expose
    public String login;

    @SerializedName("avatar_url")
    @Expose
    public String avatarUrl;

    @SerializedName("gravatar_id")
    @Expose
    public String gravatarId;

}
