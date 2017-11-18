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

public class TabUser extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_users, container, false);
        String[] listNames = {"User1", "User2", "User3", "User3"};
        ListView userListView = (ListView) rootView.findViewById(R.id.userListView);
        ListAdapter userListAdapter = new UserListAdapter(this.getContext(), listNames);
        userListView.setAdapter(userListAdapter);

        return rootView;


    }
}
