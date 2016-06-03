package com.example.kunalkataria.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.net.URI;

public class DisplayMessageActivity extends AppCompatActivity {

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i("details", "message recieved");
            Bundle bundle = intent.getExtras();
//            Owner[] repos = (Owner[]) bundle.getSerializable("listRepos");
            Owner[] repos = (Owner[]) bundle.getParcelableArray("listRepos");
            fillList(repos);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("details", "second activity created");
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, new IntentFilter("gitIntent"));
        setContentView(R.layout.activity_display_message);
        Intent intent = getIntent();
        String message = intent.getStringExtra(MyActivity.EXTRA_MESSAGE);
        GitApiService myService = new GitApiService();
        Intent myIntent = new Intent(this, GitApiService.class);
        myIntent.putExtra(MyActivity.EXTRA_MESSAGE, message);
        startService(myIntent);
        Log.i("details", "past service start");
    }

    public void fillList(Owner[] repos) {
//        String[] repoNames = new String[repos.length];
//        String[] repoURLS = new String[repos.length];
//        for (int i = 0; i < repos.length; i++) {
//            repoNames[i] = repos[i].name;
//            repoURLS[i] = repos[i].htmlUrl;
//            Log.i("repo", repoNames[i]);
//        }
//        ImageView avatar = new ImageView(this);
//        Log.i("details", "avatar url " + repos[0].owner.avatarUrl);
//        String avatar = repos[0].owner.avatarUrl;
        ListView listView = new ListView(this);
        listView.setAdapter(new CustomArrayAdapter(this, repos));
//        listView.setAdapter(new ArrayAdapter<String> (
//                this, R.layout.imagelist,
//                R.id.Itemname,items
//        ));

        RelativeLayout layout = (RelativeLayout) findViewById(R.id.content);
        layout.addView(listView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }
}