package com.example.kunalkataria.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.io.IOException;

public class MyActivity extends AppCompatActivity {

    public final static String EXTRA_MESSAGE = "com.mycompany.myfirstapp.MESSAGE";
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("details", "original activity being started");
        setContentView(R.layout.activity_my);
        editText = null;
    }

    public void sendMessage(View view) {
        Log.e("CAUTION", "BUTTON WAS PRESSED");
        // do something in response to button
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        editText = (EditText) findViewById(R.id.edit_message);
        String username = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, username);
        startActivity(intent);
    }
}
