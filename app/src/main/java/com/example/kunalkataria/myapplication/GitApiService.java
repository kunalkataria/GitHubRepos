package com.example.kunalkataria.myapplication;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class GitApiService extends IntentService {

    public static final String BASE_URL = "https://api.github.com/";

    public GitApiService() {
        super("GitApiService");
        Log.i("details", "creating service");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        String username = intent.getStringExtra(MyActivity.EXTRA_MESSAGE);


        Log.i("info", "recieved intent, username is" + username);
        Gson gson = new GsonBuilder()
                .create();

        OkHttpClient.Builder client = new OkHttpClient.Builder();

//        client.addInterceptor(new Interceptor() {
//            @Override
//            public okhttp3.Response intercept(Chain chain) throws IOException {
//                Request original = chain.request();
//                Request.Builder requestBuilder = original.newBuilder()
//                        .header("Authorization", basic)
//                        .header("Accept", "application/json")
//                        .method(original.method(), original.body());
//
//                Request request = requestBuilder.build();
//                return chain.proceed(request);
//            }
//        });

        HttpLoggingInterceptor httpLogger = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.e("interceptor:", message);
            }
        });

        httpLogger.setLevel(HttpLoggingInterceptor.Level.BASIC);
        client.addInterceptor(httpLogger);
        OkHttpClient okClient = client.build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okClient)
                .build();

        GitHubService service = retrofit.create(GitHubService.class);

        Call<List<UserRepo>> repos = service.listRepos(username);

        Log.i("info", "api call created");
        try {
            Response<List<UserRepo>> userRepoResponse = repos.execute();
            if (userRepoResponse.isSuccessful()) {
                    List<UserRepo> repoList = userRepoResponse.body();
//                    for (UserRepo ur : repoList) {
//                        Log.i("REPO FOUND", ur.name);
//                    }
                    UserRepo[] repoArray = repoList.toArray(new UserRepo[repoList.size()]);
                    Bundle b = new Bundle();
                    b.putSerializable("listRepos", repoArray);
                    Intent i = new Intent("gitIntent");
                    i.putExtras(b);
                    LocalBroadcastManager.getInstance(GitApiService.this).sendBroadcast(i);
                    Log.i("details:", "message proadcasted");
                } else {
                    Log.e("error", "response unsuccessful: " + userRepoResponse.code() + " " + userRepoResponse.message());
                    Bundle b = new Bundle();
                    UserRepo[] repoArray = new UserRepo[3];
                    UserRepo r0 = new UserRepo();
                    r0.id = 0;
                    UserRepo r1 = new UserRepo();
                    r1.id = 1;
                    UserRepo r2 = new UserRepo();
                    r2.id = 2;
                    repoArray[0] = r0;
                    repoArray[1] = r1;
                    repoArray[2] = r2;
                    b.putSerializable("listRepos", repoArray);
                    Intent i = new Intent("gitIntent");
                    i.putExtras(b);
                    LocalBroadcastManager.getInstance(GitApiService.this).sendBroadcast(i);
                    Log.i("details:", "fake message broadcasted");
                }
        } catch (IOException e) {
            e.printStackTrace();
        }
//        repos.enqueue(new Callback<List<UserRepo>>() {
//            @Override
//            public void onResponse(Call<List<UserRepo>> call, Response<List<UserRepo>> response) {
//                if (response.isSuccessful()) {
//                    List<UserRepo> repoList = response.body();
//                    for (UserRepo ur : repoList) {
//                        Log.i("REPO FOUND", ur.name);
//                    }
//                    UserRepo[] repoArray = repoList.toArray(new UserRepo[repoList.size()]);
//                    Bundle b = new Bundle();
//                    b.putSerializable("listRepos", repoArray);
//                    Intent i = new Intent("gitIntent");
//                    i.putExtras(b);
//                    LocalBroadcastManager.getInstance(GitApiService.this).sendBroadcast(i);
//                    Log.i("DETAILS:", "MESSAGE BROADCASTED");
//                } else {
//                    Log.e("error", "repsonse unsuccessful" + response.message());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<UserRepo>> call, Throwable t) {
//                // do nothing
//                Log.e("error", "enqueue failure");
//            }
//        });

    }

        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            Toast.makeText(this, "service starting", Toast.LENGTH_SHORT).show();
            Log.i("details:", "git api service starting");
            return super.onStartCommand(intent,flags,startId);
    }

//    class LoggingInterceptor implements Interceptor {
//        @Override public okhttp3.Response intercept(Chain chain) throws IOException {
//            Request request = chain.request();
//            okhttp3.Response response = chain.proceed(request);
//
//            return response;
//        }
//    }

}
