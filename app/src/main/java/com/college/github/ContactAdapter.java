package com.college.github;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rahul Bhardwaj on 5/3/2016.
 */
public class ContactAdapter extends ArrayAdapter {
    List list=new ArrayList();
    public ContactAdapter(Context context, int resource) {
        super(context, resource);
    }


    public void add(Contacts object) {
        super.add(object);
        list.add(object);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row;
        row=convertView;
        final ContactHolder contactHolder;
        if(row==null)
        {
            LayoutInflater layoutInflater=(LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row=layoutInflater.inflate(R.layout.list,parent,false);
            contactHolder =new ContactHolder();
            contactHolder.tx_name=(TextView)row.findViewById(R.id.name);

            contactHolder.imageview=(ImageView) row.findViewById(R.id.imageview);
            contactHolder.progresbar=(ProgressBar) row.findViewById(R.id.progresbar);
            row.setTag(contactHolder);
        }else {
            contactHolder =(ContactHolder)row.getTag();
        }
         Contacts contacts=(Contacts)this.getItem(position);
        contactHolder.tx_name.setText(contacts.getName());
        Picasso.with(getContext()).load(contacts.getimg())
                .fit()
                .into(contactHolder.imageview, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        if (contactHolder.progresbar != null) {
                            contactHolder.progresbar.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onError() {

                    }
                });
        return row;
    }

    static class ContactHolder
    {
        TextView tx_name;
        ImageView imageview;
        ProgressBar progresbar;
    }







}
