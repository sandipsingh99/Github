package com.college.github;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

public class Signin extends AppCompatActivity implements Responsedata{

    LinearLayout top,bottom;
    EditText et_search,searchbottom;
    Button search,bt_search;
    ListView mylist;
    Context context=this;
    String url;
    ArrayList<String> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        top=(LinearLayout)findViewById(R.id.top);
        bottom=(LinearLayout)findViewById(R.id.bottom);
        et_search=(EditText)findViewById(R.id.et_search);
        searchbottom=(EditText)findViewById(R.id.searchbottom);
        mylist=(ListView)findViewById(R.id.list);
        search=(Button)findViewById(R.id.bsearch);
        bt_search=(Button)findViewById(R.id.bt_search);
        bt_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                url="https://api.github.com/search/repositories?q="+et_search.getText().toString();
                top.setVisibility(View.GONE);
                bottom.setVisibility(View.VISIBLE);
                GetJson.getdata(context,url,Signin.this);
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                url="https://api.github.com/search/repositories?q="+searchbottom.getText().toString();
                top.setVisibility(View.GONE);
                bottom.setVisibility(View.VISIBLE);
                GetJson.getdata(context,url,Signin.this);
            }
        });
        list=new ArrayList();
        et_search.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
                searchbottom.setText(et_search.getText().toString());
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
                // TODO Auto-generated method stub
            }
        });
        searchbottom.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
                if(searchbottom.getText().toString().length()==0){
                    top.setVisibility(View.VISIBLE);
                    bottom.setVisibility(View.GONE);
                }

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
                // TODO Auto-generated method stub
            }
        });
        mylist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
        ContactAdapter   contactAdapter=new ContactAdapter(context,R.layout.list);
        try {

            JSONObject data=new JSONObject(output);
            JSONArray jsonArray = data.getJSONArray("items");
            int count=0;
          if(jsonArray.length()==0){
              Intent intent = new Intent(getApplicationContext(), Home.class);
              intent.putExtra("username", searchbottom.getText().toString());
              startActivity(intent);
          }
            while (count<jsonArray.length()) {
                JSONObject jo1 = jsonArray.getJSONObject(count);
                JSONObject jo2 = jo1.getJSONObject("owner");
                String name=jo2.getString("login");
                list.add(name);
                String img=jo2.getString("avatar_url");
                Contacts contacts = new Contacts(img,name);
                contactAdapter.add(contacts);
                count++;
            }
        } catch (JSONException e) {
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
        mylist.setAdapter(contactAdapter);
        mylist.setVisibility(View.VISIBLE);
    }
}
