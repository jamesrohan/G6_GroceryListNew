package com.example.gsu_g6.g6_grocerylist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainLogInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_log_in);
    }

    public void toTabs(View view){
        Intent intent = new Intent(this, GroListActivity.class);
        startActivity(intent);
    }
}
