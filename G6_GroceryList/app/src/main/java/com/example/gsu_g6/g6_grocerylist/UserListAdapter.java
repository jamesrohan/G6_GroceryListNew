package com.example.gsu_g6.g6_grocerylist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by Ejiroghene on 11/14/2017.
 */

public class UserListAdapter extends ArrayAdapter<String>{

    String[] g;
    UserListAdapter(Context context, String[] groups){
        super(context, R.layout.item_row, groups);

        g=groups;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater groupInflater = LayoutInflater.from(getContext());
        View groupRow = groupInflater.inflate(R.layout.user_row, parent, false);

        TextView nameList = (TextView) groupRow.findViewById(R.id.nameList);
        nameList.setText(g[position]);
        return groupRow;
    }
}
