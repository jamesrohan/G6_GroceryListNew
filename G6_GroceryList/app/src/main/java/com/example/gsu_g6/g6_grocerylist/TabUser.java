package com.example.gsu_g6.g6_grocerylist;

import android.os.AsyncTask;
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

public class TabUser extends Fragment {

    private static final String database_url = "jdbc:mysql://frankencluster.com:3306/mobileappteam6";
    private static final String database_user = "team6rw";
    private static final String database_pass = "Rohan2017!";
    Application app;
    private ArrayList<Users> userNames = new ArrayList<>();
    private ArrayAdapter userListAdapter;
    private int listID;
    private String addUser;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_users, container, false);
        app = new Application();
        listID = app.getChosenList().getListID_PK();
        ListView userListView = (ListView) rootView.findViewById(R.id.userListView);
        userListAdapter = new UserListAdapter(this.getContext(), userNames);
        userListView.setAdapter(userListAdapter);
        new getUsersFromDatabase().execute();

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
                            addUser = etUser.getText().toString();
                            new addUsertoList().execute();
                            dialog.dismiss();

                        }
                    }
                });


                dialog.show();

            }
        });

        return rootView;


    }

    private class getUsersFromDatabase extends AsyncTask<Void, Void, Void> {
        //references: http://developer.android.com/reference/android/os/AsyncTask.html
        //            https://www.youtube.com/watch?v=N0FLT5NdSNU (about the 7 min mark)
        private String queryResult ="";

        protected Void doInBackground(Void... arg0)  {
            try {


                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(database_url, database_user, database_pass);
                String queryString = "select userID, email from mobileappteam6.users where userID in (select userID from users_lists where listID="+listID+")";

                Statement st = con.createStatement();
                final ResultSet rs = st.executeQuery(queryString);
                //do some things with the data you've retrieved
                while (rs.next()) {
                    userNames.add(new Users(rs.getInt("userID"), rs.getString("email")));
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
            userListAdapter.notifyDataSetChanged();

        }
    }//end getDataFromDatabase()

    private class addUsertoList extends AsyncTask<Void, Void, Void> {
        //references: http://developer.android.com/reference/android/os/AsyncTask.html
        //            https://www.youtube.com/watch?v=N0FLT5NdSNU (about the 7 min mark)
        private String queryResult;
        private int nID;
        private CustomList aList;
        private Users nUser;
        protected Void doInBackground(Void... arg0)  {
            try {
                queryResult = "Database connection success\n";

                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(database_url, database_user, database_pass);

                String addQueryString = "select userID from mobileappteam6.users where email=\""+addUser+"\"";
                Statement sta = con.createStatement();
                final ResultSet rs = sta.executeQuery(addQueryString);
                //do some things with the data you've retrieved
                while (rs.next()) {
                    int id = rs.getInt("userID");
                    nUser = new Users(id, addUser);
                }

                if(nUser!=null){
                    String queryString = "insert into mobileappteam6.users_lists (listID, userID) values" +"("+listID+", "+ nUser.getId()+")";
                    Statement st = con.createStatement();
                    st.executeUpdate(queryString);
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
            if(nUser!=null){
                userNames.add(nUser);
                userListAdapter.notifyDataSetChanged();
            }

        }
    }//end getDataFromDatabase()
}
