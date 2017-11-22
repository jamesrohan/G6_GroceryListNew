package com.example.gsu_g6.g6_grocerylist;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by Ejiroghene on 11/14/2017.
 */

public class ItemListAdapter extends ArrayAdapter<String> {

    private static final String database_url = "jdbc:mysql://frankencluster.com:3306/mobileappteam6";
    private static final String database_user = "team6rw";
    private static final String database_pass = "Rohan2017!";
    private String itemName;


    ArrayList<String> g;
    ItemListAdapter(Context context, ArrayList<String> groups){
        super(context, R.layout.item_row, groups);

        g=groups;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent){
        LayoutInflater groupInflater = LayoutInflater.from(getContext());
        View groupRow = groupInflater.inflate(R.layout.item_row, parent, false);
        Button deleteItem = (Button) groupRow.findViewById(R.id.buttonDeleteItem);
        deleteItem.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                itemName = g.get(position);
                g.remove(position);
                changed();
                new deleteItemFromDatabase().execute();
            }

        });
        TextView tv = (TextView) groupRow.findViewById(R.id.itemName);
        tv.setText(g.get(position));
        return groupRow;
    }

    private void changed(){
        this.notifyDataSetChanged();
    }

    private class deleteItemFromDatabase extends AsyncTask<Void, Void, Void> {
        //references: http://developer.android.com/reference/android/os/AsyncTask.html
        //            https://www.youtube.com/watch?v=N0FLT5NdSNU (about the 7 min mark)
        private String queryResult;
        private int listID = new Application().getChosenList().getListID_PK();
        protected Void doInBackground(Void... arg0)  {
            try {
                queryResult = "Database connection success\n";

                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(database_url, database_user, database_pass);
                System.out.println(itemName);
                String queryString = "delete from mobileappteam6.lists_items where listID="+ listID+" and item= \""+itemName+"\"";

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

        }
    }//end getDataFromDatabase()
}
