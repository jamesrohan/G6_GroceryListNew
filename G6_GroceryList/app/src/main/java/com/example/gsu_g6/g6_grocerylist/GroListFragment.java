package com.example.gsu_g6.g6_grocerylist;

import android.content.Intent;
import android.os.AsyncTask;
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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class GroListFragment extends Fragment {

    private static final String database_url = "jdbc:mysql://frankencluster.com:3306/mobileappteam6";
    private static final String database_user = "team6rw";
    private static final String database_pass = "Rohan2017!";


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
    }//End OnCreateView





    private class getDataFromDatabase extends AsyncTask<Void, Void, Void> {
        //references: http://developer.android.com/reference/android/os/AsyncTask.html
        //            https://www.youtube.com/watch?v=N0FLT5NdSNU (about the 7 min mark)
        private String queryResult;
        private Connection dbConnection;
        protected Void doInBackground(Void... arg0)  {


            try {
                queryResult = "Database connection success\n";

                Class.forName("com.mysql.jdbc.Driver");
                dbConnection = DriverManager.getConnection(database_url, database_user, database_pass); //con
                //String queryString = "select password from mobileappteam6.users where email"+userInputPassword;

                //Statement st = con.createStatement();
                //final ResultSet rs = st.executeQuery(queryString);
                //ResultSetMetaData rsmd = rs.getMetaData();

                //do some things with the data you've retrieved
                /*
                while (rs.next()) {
                    queryResult += rsmd.getColumnName(1) + ": " + rs.getString(1) + "\n";
                    queryResult += rsmd.getColumnName(2) + ": " + rs.getString(2) + "\n";
                } */

                //con.close(); //close database connection
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
            //getData.setText(queryResult);

        }//End on Post Execute


        protected ArrayList<CustomList> getUserLists(int UserID){
            //boolean isPassCorrect = false;
            ArrayList<CustomList> retLists = new ArrayList<CustomList>();
            try{
                String queryString = "select password from mobileappteam6.users where email = ";
                Statement myStatement = dbConnection.createStatement();
                final ResultSet myResultSet = myStatement.executeQuery(queryString);
                while (myResultSet.next()){

                }
            }catch (Exception e){

            }
            return retLists;

        }


        //Closes Connection
        protected void closeConnection(){
            try {
                dbConnection.close();
                return;
            }catch (Exception e){
                e.printStackTrace();
                return;
            }
        }//End close connection



    }//end getDataFromDatabase()




}//End GroListFragment
