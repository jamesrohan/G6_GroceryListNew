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

public class ItemListAdapter extends ArrayAdapter<String> {

    String[] g;
    ItemListAdapter(Context context, String[] groups){
        super(context, R.layout.item_row, groups);

        g=groups;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater groupInflater = LayoutInflater.from(getContext());
        View groupRow = groupInflater.inflate(R.layout.item_row, parent, false);

        TextView tv = (TextView) groupRow.findViewById(R.id.groupName);
        tv.setText(g[position]);
        return groupRow;
    }
}
