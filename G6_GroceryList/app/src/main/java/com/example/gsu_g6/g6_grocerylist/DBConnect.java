package com.example.gsu_g6.g6_grocerylist;

import android.os.AsyncTask;
import android.util.Log;
//import android.os.Bundle;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.Button;
//import android.widget.TextView;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

/**
 * Created by Nikki on 11/18/2017.
 */

public class DBConnect   extends AsyncTask<Void,Void,Void>{ // extends AsyncTask<Void,Void,Void> extends AsyncTask<Void,Void,Void>


    private static final String database_url = "jdbc:mysql://frankencluster.com:3306/csc4360_class";
    private static final String database_user = "csc4360dbviewer";
    private static final String database_pass = ";fw3X2K!a]b,";
    private static boolean ConnectionSucessfull = false;
    private  Connection myConn;
    private Statement mySQL_Statement;
    private String queryResult;

    protected Void doInBackground(Void... arg0){ //Void    doInBackground(Void... arg0)
        try {
            queryResult = "Database connection success\n";

            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(database_url, database_user, database_pass);
            String queryString = "select * from csc4360_class.film limit 5";

            Statement st = con.createStatement();
            final ResultSet rs = st.executeQuery(queryString);
            ResultSetMetaData rsmd = rs.getMetaData();

            //do some things with the data you've retrieved
            while (rs.next()) {
                queryResult += rsmd.getColumnName(1) + ": " + rs.getString(1) + "\n";
                queryResult += rsmd.getColumnName(2) + ": " + rs.getString(2) + "\n";
            }

            con.close(); //close database connection
        } catch (Exception e) {
            e.printStackTrace();
            //put the error into the TextView on the app screen
            queryResult = "Database connection failure\n" +  e.toString();
        }
        return null;

    }//End connect doInBackground


    public  String getData(){
        return queryResult;
    }



}//End DBConnect



