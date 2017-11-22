package com.example.gsu_g6.g6_grocerylist;

import android.os.AsyncTask;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by Ejiroghene on 11/14/2017.
 */

public class TabItems extends Fragment {

    private static final String database_url = "jdbc:mysql://frankencluster.com:3306/mobileappteam6";
    private static final String database_user = "team6rw";
    private static final String database_pass = "Rohan2017!";
    Application app;
    private ArrayList<String> itemNames = new ArrayList<>();
    private ArrayAdapter itemListAdapter;
    private int listID;
    private String addItem;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_ltems, container, false);
        app = new Application();
        listID = app.getChosenList().getListID_PK();
        ListView itemsListView = (ListView) rootView.findViewById(R.id.itemListView);
        itemListAdapter = new ItemListAdapter(this.getContext(), itemNames);
        itemsListView.setAdapter(itemListAdapter);
        new getitemssFromDatabase().execute();

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
                            addItem = etItem.getText().toString();
                            new addItemtoList().execute();
                            dialog.dismiss();

                        }
                    }
                });


                dialog.show();

            }
        });

        return rootView;
    }

    private class getitemssFromDatabase extends AsyncTask<Void, Void, Void> {
        //references: http://developer.android.com/reference/android/os/AsyncTask.html
        //            https://www.youtube.com/watch?v=N0FLT5NdSNU (about the 7 min mark)
        private String queryResult ="";

        protected Void doInBackground(Void... arg0)  {
            try {


                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(database_url, database_user, database_pass);
                String queryString = "select item from mobileappteam6.lists_items where listID=" + listID;

                Statement st = con.createStatement();
                final ResultSet rs = st.executeQuery(queryString);
                //do some things with the data you've retrieved
                while (rs.next()) {
                    itemNames.add(rs.getString("item"));
                }

                con.close(); //close database connection
            } catch (Exception e) {
                e.printStackTrace();
                //put the error into the TextView on the app screen
                queryResult = "Database connection failure\n" +  e.toString();
            }

            return null;
        }//end database connection via doInBackground

        //after processing is completed, post to the screen
        protected void onPostExecute(Void result) {
            //put the results into the TextView on the app screen
            itemListAdapter.notifyDataSetChanged();

        }
    }//end getDataFromDatabase()

    private class addItemtoList extends AsyncTask<Void, Void, Void> {
        //references: http://developer.android.com/reference/android/os/AsyncTask.html
        //            https://www.youtube.com/watch?v=N0FLT5NdSNU (about the 7 min mark)
        private String queryResult;
        private int nID;
        private CustomList aList;
        protected Void doInBackground(Void... arg0)  {
            try {
                queryResult = "Database connection success\n";

                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(database_url, database_user, database_pass);
                String queryString = "insert into mobileappteam6.lists_items (listID, item) values" +"("+listID+", \""+ addItem+"\")";
                Statement st = con.createStatement();
                st.executeUpdate(queryString);



                con.close(); //close database connection
            } catch (Exception e) {
                e.printStackTrace();
                //put the error into the TextView on the app screen
                queryResult = "Database connection failure\n" +  e.toString();
            }

            return null;
        }//end database connection via doInBackground

        //after processing is completed, post to the screen
        protected void onPostExecute(Void result) {
            //put the results into the TextView on the app screen
            itemNames.add(addItem);
            itemListAdapter.notifyDataSetChanged();
        }
    }//end getDataFromDatabase()
}
