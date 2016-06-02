package com.example.kunalkataria.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class DisplayMessageActivity extends AppCompatActivity {

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i("details", "message recieved");
            Bundle bundle = intent.getExtras();
            UserRepo[] repos = (UserRepo[]) bundle.getSerializable("listRepos");
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

    public void fillList(UserRepo[] repos) {
        String[] items = new String[repos.length];
        for (int i = 0; i < repos.length; i++) {
            items[i] = repos[i].name;
            Log.i("repo", items[i]);
        }
        ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(DisplayMessageActivity.this, android.R.layout.simple_list_item_1, items);
        ListView listView = new ListView(this);
        listView.setAdapter(new ArrayAdapter<String> (
                this, R.layout.imagelist,
                R.id.Itemname,items
        ));

        RelativeLayout layout = (RelativeLayout) findViewById(R.id.content);
        layout.addView(listView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }
}