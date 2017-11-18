package com.example.gsu_g6.g6_grocerylist;

import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by Ejiroghene on 11/14/2017.
 */

public class TabItems extends Fragment {

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_ltems, container, false);
        String[] groupNames = getArguments().getStringArray("items");
        ListView itemsListView = (ListView) rootView.findViewById(R.id.itemListView);
        ListAdapter itemListAdapter = new ItemListAdapter(this.getContext(), groupNames);
        itemsListView.setAdapter(itemListAdapter);


        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.itemAddButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                View diaView = inflater.inflate(R.layout.add_items, null);
                builder.setView(diaView);
                final AlertDialog dialog = builder.create();
                final EditText etItem = (EditText) diaView.findViewById(R.id.etItem);
                final Button etButton = (Button) diaView.findViewById(R.id.etButton);
                etButton.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view){
                        if(etItem.getText().toString().isEmpty()){
                            Toast.makeText(getActivity(),
                                    "Please enter item to add",
                                    Toast.LENGTH_SHORT).show();
                        }else{
                            //code for add item here
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
