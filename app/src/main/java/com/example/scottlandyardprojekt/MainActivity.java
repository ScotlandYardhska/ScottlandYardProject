package com.example.scottlandyardprojekt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
//Buttons erstellen
    Button buttonCreate;
    Button buttonJoin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//Buttons den Buttons zuordnen
        buttonCreate = findViewById(R.id.buttonCreate);
        buttonJoin = findViewById(R.id.buttonJoin);

//Machen, dass Buttons was machen

        buttonCreate.setOnClickListener(this);
        buttonJoin.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.buttonCreate:
                Intent create = new Intent(this, createActivity.class);
                startActivity(create);
                break;

            case R.id.buttonJoin:
                Intent join = new Intent(this, joinActivity.class);
                startActivity(join);
                break;

                // Hier können wir neue Klicks einfügen
        }

    }
}
