package com.example.gsu_g6.g6_grocerylist;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * Created by Ejiroghene on 11/14/2017.
 */

public class TabItems extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_ltems, container, false);
        String[] groupNames = getArguments().getStringArray("items");
        ListView itemsListView = (ListView) rootView.findViewById(R.id.itemListView);
        ListAdapter itemListAdapter = new ItemListAdapter(this.getContext(), groupNames);
        itemsListView.setAdapter(itemListAdapter);
        return rootView;
    }
}
