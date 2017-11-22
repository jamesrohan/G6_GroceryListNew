package com.example.gsu_g6.g6_grocerylist;

import android.content.Context;
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
 * A placeholder fragment containing a simple view.
 */
public class GroListFragment extends Fragment {

    private static final String database_url = "jdbc:mysql://frankencluster.com:3306/mobileappteam6";
    private static final String database_user = "team6rw";
    private static final String database_pass = "Rohan2017!";
    private int userID;
    private ListView listListView;
    private Context cnText;
    private Application app;
    private String addName;
    private ArrayList<CustomList> cList = new ArrayList<>();
    private ArrayAdapter listListAdapter;

    public GroListFragment() {
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_grolist, container, false);
        app = new Application();
        userID = app.getId();
        cnText = this.getContext();

        ArrayList<String> g = new ArrayList<>();
        listListView = (ListView) rootView.findViewById(R.id.listListView);
        listListAdapter = new ListListAdapter(cnText, cList);
        listListView.setAdapter(listListAdapter);
        listListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        app.setChosenList(cList.get(position));
                        Intent intent = new Intent(getActivity(), MainTabedActivity.class);
                        startActivity(intent);
                    }
                }
        );
        new getListsFromDatabase().execute();



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
                            addName = etList.getText().toString();
                            new createGroList().execute();
                            dialog.dismiss();

                        }
                    }
                });


                dialog.show();

            }
        });
        return rootView;
    }//End OnCreateView


    private class getListsFromDatabase extends AsyncTask<Void, Void, Void> {
        //references: http://developer.android.com/reference/android/os/AsyncTask.html
        //            https://www.youtube.com/watch?v=N0FLT5NdSNU (about the 7 min mark)
        private String queryResult ="";

        protected Void doInBackground(Void... arg0)  {
            try {


                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(database_url, database_user, database_pass);
                String queryString = "select listID from mobileappteam6.users_lists where userID =" + userID;

                Statement st = con.createStatement();
                final ResultSet rs = st.executeQuery(queryString);
                //do some things with the data you've retrieved
                while (rs.next()) {
                    int id = rs.getInt("listID");
                    String nameQuery = "select lName from lists where listID="+id;
                    Statement sta = con.createStatement();
                    final  ResultSet rst = sta.executeQuery(nameQuery);
                    while (rst.next()){
                        String nam = rst.getString("lName");
                        cList.add(new CustomList(id, nam));
                    }
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
            listListAdapter.notifyDataSetChanged();

        }
    }//end getDataFromDatabase()

    private class createGroList extends AsyncTask<Void, Void, Void> {
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
                String queryString = "insert into mobileappteam6.lists (lName) values" +"(\""+ addName+"\")";
                Statement st = con.createStatement();
                st.executeUpdate(queryString);

                String addQueryString = "select listID from mobileappteam6.lists where lName =\"" + addName+"\"";

                Statement sta = con.createStatement();
                final ResultSet rst = sta.executeQuery(addQueryString);
                while(rst.next()){
                    nID = rst.getInt("listID");
                }

                String insertQueryString = "insert into mobileappteam6.users_lists (userID, listID) values"+"("+userID+", "+nID+")";
                Statement stat = con.createStatement();
                st.executeUpdate(insertQueryString);
                aList = new CustomList(nID, addName);

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
            cList.add(aList);
            listListAdapter.notifyDataSetChanged();
        }
    }//end getDataFromDatabase()

}//End GroListFragment
