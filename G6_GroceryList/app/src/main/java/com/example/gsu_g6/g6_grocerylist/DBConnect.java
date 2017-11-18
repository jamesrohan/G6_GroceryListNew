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

public class DBConnect   { // extends AsyncTask<Void,Void,Void> extends AsyncTask<Void,Void,Void>


    private static final String database_url = "jdbc:mysql://frankencluster.com:3306/team6rw";
    private static final String database_user = "csc4360dbviewer";
    private static final String database_pass = "Rohan2017!";
    private static boolean ConnectionSucessfull = false;
    private  Connection myConn;
    private Statement mySQL_Statement;

    protected void connect(){ //Void    doInBackground(Void... arg0)
        try {
            Class.forName("com.mysql.jdbc.Driver");
            myConn = DriverManager.getConnection(database_url, database_user, database_pass);
            mySQL_Statement = myConn.createStatement();
            ConnectionSucessfull = true;
            Log.d("ConnectionSucessfull", Boolean.toString(ConnectionSucessfull))
        }catch (Exception e){
            e.printStackTrace();
        }//End Catch

    }//End connect doInBackground


    protected void close(){
        myConn.close();
        Log.d("DBClosed", "Database Connection Closed");
    }


    public boolean isConnectionSucessfull(){
        return ConnectionSucessfull;
    }//End isConnectionSucessfull







}//End DBConnect



