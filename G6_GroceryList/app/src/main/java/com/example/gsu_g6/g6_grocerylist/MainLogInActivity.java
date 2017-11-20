package com.example.gsu_g6.g6_grocerylist;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

public class MainLogInActivity extends AppCompatActivity {

    private static final String database_url = "jdbc:mysql://frankencluster.com:3306/mobileappteam6";
    private static final String database_user = "team6rw";
    private static final String database_pass = "Rohan2017!";
    private TextView getData;

    //Set these variables before the isPasswordCorrectDB.execute  is called
    String userEmail;
    String userPassword;
    //This boolean value is set after you call isPasswordCorrectDB.execute  is called
    boolean isPassCorrect = false;

    //This boolean value is set when user hits SignUp and his email is sucessfully inserted in
    // the DB
    boolean isInsertSucessfull = false;
    // Make sure these variables are set before calling signUpUserDB.execute
    String userEmail_SignUp;
    String userPassword_SignUp;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_log_in);
        getData = (TextView) findViewById(R.id.getData);
        //new getDataFromDatabase().execute();


        Button signup_Button = (Button) findViewById(R.id.button_SignUp);
        signup_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainLogInActivity.this);
                View diaView = getLayoutInflater().inflate(R.layout.signup_dialog, null);
                builder.setView(diaView);
                final AlertDialog dialog = builder.create();
                final EditText etSignupEmail = (EditText) diaView.findViewById(R.id.etSignEmail);
                final EditText etSignupPass = (EditText) diaView.findViewById(R.id.etSignPass);
                final EditText etSignupConPass = (EditText) diaView.findViewById(R.id.etSignConfirmPass);
                final Button createAccountButton = (Button) diaView.findViewById(R.id.createAccountButton);
                createAccountButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Create account code goes here
                        if(etSignupEmail.getText().toString().isEmpty()
                                || etSignupPass.getText().toString().isEmpty()
                                || etSignupConPass.getText().toString().isEmpty()){
                            Toast.makeText(MainLogInActivity.this,
                                    "All fields are necessary to create account",
                                    Toast.LENGTH_SHORT).show();
                        }else{
                            String confirmPassword = etSignupConPass.getText().toString();
                            if(etSignupPass.getText().toString().equals(confirmPassword)){
                                // code for create account here
                                dialog.dismiss();
                            }else{
                                Toast.makeText(MainLogInActivity.this,
                                        "Passwords must match",
                                        Toast.LENGTH_SHORT).show();
                            }


                        }
                    }
                });

                dialog.show();
            }
        });

    }

    public void toTabs(View view){
        Intent intent = new Intent(this, GroListActivity.class);
        startActivity(intent);
    }


    //Must Set userEmail and userPassword before calling this method
    private class isPasswordCorrectDB extends AsyncTask<Void, Void, Void> {
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


                String queryString = "select password from mobileappteam6.users where email = "+userEmail;
                Statement myStatement = dbConnection.createStatement();
                final ResultSet myResultSet = myStatement.executeQuery(queryString);

                //First check if userEmail and userPassword is set
                if(userEmail.length() > 0 && userPassword.length()>0){
                        while (myResultSet.next()) {
                            if (myResultSet.getString(0).equals(userPassword)) {
                                isPassCorrect = true;
                                //return isPassCorrect;
                            }
                        }
                }//End if


            } catch (Exception e) {
                e.printStackTrace();
                //put the error into the TextView on the app screen
                isPassCorrect =false;
                queryResult = "Database connection failure\n" +  e.toString();
            }

            return null;
        }//end database connection via doInBackground




        //after processing is completed, post to the screen
        protected void onPostExecute(Void result) {
            //put the results into the TextView on the app screen
            //getData.setText(queryResult);
            try {
                dbConnection.close();
            }catch (Exception e){
                return;
            }

        }//End on Post Execute



        /*
        //Checks Password and Email address if they correspond. Returns true.
        protected boolean checkPassword(String userEmail, String userPassword){
            //EditText userEmailInput = (EditText) findViewById(R.id.)
            //String userEmail =
            boolean isPassCorrect = false;
            try{
                String queryString = "select password from mobileappteam6.users where email = "+userEmail;
                Statement myStatement = dbConnection.createStatement();
                final ResultSet myResultSet = myStatement.executeQuery(queryString);
                while (myResultSet.next()){
                    if(myResultSet.getString(0).equals(userPassword)){
                        isPassCorrect = true;
                        return isPassCorrect;
                    }
                }
            }catch (Exception e){
                isPassCorrect = false;
            }
            return isPassCorrect;

        }//checkPassword
        */


        /*
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
        */

        /*
        //Returns true if the Insert is sucessfull
        protected boolean signUpUser(String userEmail, String userPassword){
            boolean isInsertSucessfull = false;
            try{
                String queryString = "insert into mobileappteam6.users (email, password) " +
                        "values ("+userEmail+","+userPassword +")";
                Statement myStatement = dbConnection.createStatement();
                isInsertSucessfull = myStatement.executeUpdate(queryString) > 0;

            }catch (Exception e){
                //isPassCorrect = false;
                isInsertSucessfull =false;
            }
            return isInsertSucessfull;
        }
        */

    }//end getDataFromDatabase()



    //Used singUpUserDB.execute to singup a  user. userEmail_SignUp and userPassword_SignUp
    // must be set before calling/executing
    private class signUpUserDB extends AsyncTask<Void, Void, Void> {
        private Connection dbConnection2;
        protected Void doInBackground(Void... arg0)  {
            try{
                Class.forName("com.mysql.jdbc.Driver");
                dbConnection2 = DriverManager.getConnection(database_url, database_user, database_pass);
                //First check if userEmail and userPassword is set
                if(userEmail_SignUp.length() > 0 && userPassword_SignUp.length()>0) {
                    String queryString = "insert into mobileappteam6.users (email, password) " +
                            "values (" + userEmail_SignUp + "," + userPassword_SignUp + ")";
                    Statement myStatement = dbConnection2.createStatement();
                    isInsertSucessfull = myStatement.executeUpdate(queryString) > 0;
                }
            }catch (Exception e){
                //isPassCorrect = false;
                isInsertSucessfull =false;
            }
            return null;
        }//End doInBackground



        protected void onPostExecute(Void result) {
            //put the results into the TextView on the app screen
            //getData.setText(queryResult);
            try {
                dbConnection2.close();
            }catch (Exception e){
                return;
            }

        }//End on Post Execute

    }//End signUpUserDB






}//End MainLogInActivity
