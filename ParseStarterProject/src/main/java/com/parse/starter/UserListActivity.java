package com.parse.starter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class UserListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        // creating listview
        final ListView listView = findViewById(R.id.listView);
        // creating arraylist usernames
        final ArrayList<String> usernames = new ArrayList<String>();
        // connecting arrayadapter with listview
        final ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, usernames);

        // creating parsequery query
        ParseQuery<ParseUser> query = ParseUser.getQuery();

        // query all username except current user
        query.whereNotEqualTo("username",ParseUser.getCurrentUser().getUsername());
        // query username in ascending order
        query.addAscendingOrder("username");

        // after querying is done, add usernames into arraylist username and update listview
        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                if (e==null){
                    if(objects.size()>0){
                        for(ParseUser user : objects){
                            usernames.add(user.getUsername());
                        }
                        listView.setAdapter(arrayAdapter);
                    }
                }else{
                    e.printStackTrace();
                }
            }
        });
    }
}