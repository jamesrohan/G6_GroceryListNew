package com.example.gsu_g6.g6_grocerylist;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by Ejiroghene on 11/16/2017.
 */

public class ListListAdapter extends ArrayAdapter<CustomList>{
    private static final String database_url = "jdbc:mysql://frankencluster.com:3306/mobileappteam6";
    private static final String database_user = "team6rw";
    private static final String database_pass = "Rohan2017!";
    private int listID;
    ArrayList<CustomList> g;
    ListListAdapter(Context context, ArrayList<CustomList> groups){
        super(context, R.layout.list_row, groups);

        g=groups;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent){
        LayoutInflater groupInflater = LayoutInflater.from(getContext());
        View groupRow = groupInflater.inflate(R.layout.list_row, parent, false);
        TextView nameList = (TextView) groupRow.findViewById(R.id.listName);
        Button deleteList = (Button) groupRow.findViewById(R.id.buttonDeleteList);
        deleteList.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                listID = g.get(position).getListID_PK();
                g.remove(position);
                changed();
                new deleteListFromDatabase().execute();
            }
        });
        nameList.setText(g.get(position).getListName());
        return groupRow;
    }

    private void changed(){
        this.notifyDataSetChanged();
    }

    private class deleteListFromDatabase extends AsyncTask<Void, Void, Void> {
        //references: http://developer.android.com/reference/android/os/AsyncTask.html
        //            https://www.youtube.com/watch?v=N0FLT5NdSNU (about the 7 min mark)
        private String queryResult;
        protected Void doInBackground(Void... arg0)  {
            try {
                queryResult = "Database connection success\n";

                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(database_url, database_user, database_pass);
                String queryString = "delete from mobileappteam6.lists where listID="+ listID;

                Statement st = con.createStatement();
                st.executeUpdate(queryString);

                String deleteQueryString = "delete from mobileappteam6.users_lists where listID="+ listID;

                Statement sta = con.createStatement();
                sta.executeUpdate(deleteQueryString);

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
