package com.example.gsu_g6.g6_grocerylist;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainLogInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_log_in);

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
}
