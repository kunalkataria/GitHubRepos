package com.example.kunalkataria.myapplication;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.net.URI;

public class CustomArrayAdapter extends BaseAdapter{
//    private String[] listNames;
//    private String[] urls;
//    private String avatarImageURL;
    private Owner[] userRepos;
    private Context context;
    private static LayoutInflater inflater = null;

    public CustomArrayAdapter(DisplayMessageActivity mainActivity, Owner[] userRepos) {
        this.userRepos = userRepos;
        this.context = mainActivity;
//        this.listNames = listNames;
//        this.urls = urls;

//        this.avatarImageURL = avatarImageURL;
//        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return  userRepos.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ItemShell item;
        View rowView = convertView;

        if (rowView == null) {
            LayoutInflater inflater=((Activity)context).getLayoutInflater();
            rowView = inflater.inflate(R.layout.imagelist, parent, false);

            item = new ItemShell();
            item.textView = (TextView) rowView.findViewById(R.id.Itemname);
            item.imgView = (ImageView) rowView.findViewById(R.id.icon);

            rowView.setTag(item);
        } else {
            item = (ItemShell) rowView.getTag();
        }

//        rowView = inflater.inflate(R.layout.imagelist, null);

        item.textView.setText(userRepos[position].login);

        String url = userRepos[position].avatarUrl;
        Picasso.with(context).load(url).into(item.imgView);


        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("details", "item on list selected");
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(userRepos[position].htmlUrl));
                context.startActivity(browserIntent);
//                Snackbar.make(v, urls[position], Snackbar.LENGTH_SHORT)
//                        .setAction("cool", new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                // do nothing
//                            }
//                        })
//                        .show();
            }
        });
        return rowView;
    }

    public class ItemShell {
        TextView textView;
        ImageView imgView;

    }



}
