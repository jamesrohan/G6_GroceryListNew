package com.example.gsu_g6.g6_grocerylist;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * A placeholder fragment containing a simple view.
 */
public class GroListFragment extends Fragment {


    public GroListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_grolist, container, false);
        String[] listNames = {"List1", "List2", "List3", "List3"};
        ListView listListView = (ListView) rootView.findViewById(R.id.listListView);
        ListAdapter listListAdapter = new ListListAdapter(this.getContext(), listNames);
        listListView.setAdapter(listListAdapter);
        listListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(getActivity(), MainTabedActivity.class);
                        startActivity(intent);
                    }
                }
        );
        return rootView;
    }
}
