package com.example.gsu_g6.g6_grocerylist;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

/**
 * A placeholder fragment containing a simple view.
 */
public class GroListFragment extends Fragment {


    public GroListFragment() {
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
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

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.listAddButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                View diaView = inflater.inflate(R.layout.add_lists, null);
                builder.setView(diaView);
                final AlertDialog dialog = builder.create();
                final EditText etList = (EditText) diaView.findViewById(R.id.etList);
                final Button etListButton = (Button) diaView.findViewById(R.id.etListButton);
                etListButton.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view){
                        if(etList.getText().toString().isEmpty()){
                            Toast.makeText(getActivity(),
                                    "Please enter list to add",
                                    Toast.LENGTH_SHORT).show();
                        }else{
                            //code for add list here
                            dialog.dismiss();

                        }
                    }
                });


                dialog.show();

            }
        });
        return rootView;
    }
}
