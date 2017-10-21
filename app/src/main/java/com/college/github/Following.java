package com.college.github;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Following extends AppCompatActivity implements Responsedata {
    String url="https://api.github.com/users/";
    ListView listview;
    Context ctx=this;
    TextView msg;
    ArrayList<String> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_following);
        listview=(ListView)findViewById(R.id.listview);
        String username=getIntent().getExtras().getString("username");
        url=url+username+"/following";
        GetJson.getdata(this,url,this);
        list=new ArrayList();
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), Home.class);
                intent.putExtra("username", list.get(i)+"");
                startActivity(intent);
            }
        });
    }

    @Override
    public void processFinish(String output) {
        ContactAdapter   contactAdapter=new ContactAdapter(ctx,R.layout.list);
        try {
            JSONArray jsonArray = new JSONArray(output);
            int count=0;
                while (count<jsonArray.length()) {
                JSONObject jo = jsonArray.getJSONObject(count);
                String name=jo.getString("login");
                list.add(name);
                String img=jo.getString("avatar_url");
                Contacts contacts = new Contacts(img,name);
                contactAdapter.add(contacts);


                count++;
            }
        } catch (JSONException e) {
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
        listview.setAdapter(contactAdapter);
    }

}
