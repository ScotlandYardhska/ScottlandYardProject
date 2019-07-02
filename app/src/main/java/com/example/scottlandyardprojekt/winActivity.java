package com.example.scottlandyardprojekt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class winActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win);
        TextView winner = findViewById(R.id.winView);
        final Bundle pack = getIntent().getExtras();
        String rolle = pack.getString("rolle");

        if(rolle.equals("admin")){
                String pax = pack.getString("mrxName");
                winner.setText(pax);


        }






        //

    }
}
