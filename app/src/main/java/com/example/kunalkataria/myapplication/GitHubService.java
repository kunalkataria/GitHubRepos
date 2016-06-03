package com.example.kunalkataria.myapplication;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface GitHubService {
    @GET("/orgs/{user}/members")
    Call<List<Owner>> listRepos(@Path("user") String user);
}
