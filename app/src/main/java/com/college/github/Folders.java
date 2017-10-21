package com.college.github;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Folders extends AppCompatActivity implements Responsedata {

    ListView repo_list;
    String url="https://api.github.com/users/";
    Context ctx=this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_folders);
        repo_list=(ListView)findViewById(R.id.listview);
        String username=getIntent().getExtras().getString("username");
        url=url+username+"/repos";
        GetJson.getdata(this,url,this);
    }

    @Override
    public void processFinish(String output) {


        ContactAdapter   contactAdapter=new ContactAdapter(ctx,R.layout.list);
        try {
            JSONArray jsonArray = new JSONArray(output);
            int count=0;
            while (count<jsonArray.length()) {
                JSONObject jo = jsonArray.getJSONObject(count);
                String name=jo.getString("name");
                Contacts contacts = new Contacts("http://pngimg.com/uploads/folder/folder_PNG8773.png",name);
                contactAdapter.add(contacts);
                count++;
            }
        } catch (JSONException e) {
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
        repo_list.setAdapter(contactAdapter);
    }
}
