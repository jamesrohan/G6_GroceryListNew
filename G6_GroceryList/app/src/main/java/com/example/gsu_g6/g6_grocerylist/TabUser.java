package com.example.gsu_g6.g6_grocerylist;

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

public class TabUser extends Fragment {

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_users, container, false);
        String[] listNames = {"User1", "User2", "User3", "User3"};
        ListView userListView = (ListView) rootView.findViewById(R.id.userListView);
        ListAdapter userListAdapter = new UserListAdapter(this.getContext(), listNames);
        userListView.setAdapter(userListAdapter);

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.userAddButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                View diaView = inflater.inflate(R.layout.add_users, null);
                builder.setView(diaView);
                final AlertDialog dialog = builder.create();
                final EditText etUser = (EditText) diaView.findViewById(R.id.etUser);
                final Button etButton = (Button) diaView.findViewById(R.id.etUserButton);
                etButton.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view){
                        if(etUser.getText().toString().isEmpty()){
                            Toast.makeText(getActivity(),
                                    "Please enter user to add",
                                    Toast.LENGTH_SHORT).show();
                        }else{
                            //code for add user here
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
