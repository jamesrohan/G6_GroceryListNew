package com.example.gsu_g6.g6_grocerylist;

import android.content.Context;
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

import static java.lang.Integer.parseInt;

public class MainLogInActivity extends AppCompatActivity {

    private static final String database_url = "jdbc:mysql://frankencluster.com:3306/mobileappteam6";
    private static final String database_user = "team6rw";
    private static final String database_pass = "Rohan2017!";
    private EditText logEmail;
    private EditText logPass;
    private String signupEmail;
    private String signupPass;
    private String loginEmail;
    private String loginPass;

    private TextView getData;
    private boolean success = false;
    Application app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_log_in);
        logEmail = (EditText) findViewById(R.id.Email_ID);
        logPass = (EditText) findViewById(R.id.Password);

        getData = (TextView) findViewById(R.id.getData);
        app = new Application();

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
                                signupEmail = etSignupEmail.getText().toString();
                                signupPass = etSignupPass.getText().toString();
                                new addUserToDatabase().execute();
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
        loginEmail = logEmail.getText().toString();
        loginPass = logPass.getText().toString();
//        new checkLogInDatabase().execute();
        Intent intent = new Intent(this, GroListActivity.class);
        startActivity(intent);

    }

    private class checkLogInDatabase extends AsyncTask<Void, Void, Void> {
        //references: http://developer.android.com/reference/android/os/AsyncTask.html
        //            https://www.youtube.com/watch?v=N0FLT5NdSNU (about the 7 min mark)
        private String queryResult ="";
        protected Void doInBackground(Void... arg0)  {
            try {


                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(database_url, database_user, database_pass);
                String queryString = "select userID from mobileappteam6.users where email =" + "\""+loginEmail+"\" and password=\""+loginPass +"\";";

                Statement st = con.createStatement();
                final ResultSet rs = st.executeQuery(queryString);
                //do some things with the data you've retrieved
                while (rs.next()) {
                    queryResult += rs.getInt("userID");
                }

                if(queryResult.length()>0){
                    success=true;
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
            if(queryResult.length()>0){
                getData.setText(queryResult);
                int ad = parseInt(queryResult.trim());
                app.setId(ad);
                success=true;
                Intent intent = new Intent(getBaseContext(), GroListActivity.class);
                startActivity(intent);
            }


        }
    }//end getDataFromDatabase()




    private class addUserToDatabase extends AsyncTask<Void, Void, Void> {
        //references: http://developer.android.com/reference/android/os/AsyncTask.html
        //            https://www.youtube.com/watch?v=N0FLT5NdSNU (about the 7 min mark)
        private String queryResult;
        protected Void doInBackground(Void... arg0)  {
            try {
                queryResult = "Database connection success\n";

                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(database_url, database_user, database_pass);
                String queryString = "insert into mobileappteam6.users (email, password) values" + " (\""+signupEmail+"\",\""+signupPass +"\");";

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

}//End MainLogInActivity