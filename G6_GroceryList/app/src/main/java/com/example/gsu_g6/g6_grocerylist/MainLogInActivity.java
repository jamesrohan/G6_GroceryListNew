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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_log_in);
        getData = (TextView) findViewById(R.id.getData);
        new getDataFromDatabase().execute();


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


    private class getDataFromDatabase extends AsyncTask<Void, Void, Void> {
        //references: http://developer.android.com/reference/android/os/AsyncTask.html
        //            https://www.youtube.com/watch?v=N0FLT5NdSNU (about the 7 min mark)
        private String queryResult;
        protected Void doInBackground(Void... arg0)  {
            try {
                queryResult = "Database connection success\n";

                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(database_url, database_user, database_pass);
                String queryString = "select * from mobileappteam6.users";

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
        }//end database connection via doInBackground

        //after processing is completed, post to the screen
        protected void onPostExecute(Void result) {
            //put the results into the TextView on the app screen
            getData.setText(queryResult);

        }
    }//end getDataFromDatabase()
}
