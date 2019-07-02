package com.example.scottlandyardprojekt;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class joinActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText spielname1;
    private EditText passwort1;
    private EditText spitzname1;
    Button join;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        spielname1 = findViewById(R.id.spielname);
        passwort1 = findViewById(R.id.passwort);
        spitzname1 = findViewById(R.id.spitzname);



        join = findViewById(R.id.beitreten);

        join.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        spielname1 = findViewById(R.id.spielname);
        passwort1 = findViewById(R.id.passwort);
        spitzname1 = findViewById(R.id.spitzname);
        int count = 1;
        /*
        Hier wird geprüft ob überall etwas eingegeben wurde. Wenn was fehlt wird per Toast darauf aufmerksam gemacht.
         */

        if (spielname1.getText().toString().equals("Spielname")){
            Context context = getApplicationContext();
            CharSequence text = "Bitte Spielname eingeben";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();



        }else{
            count ++;
        }
        if (passwort1.getText().toString().equals("Passwort")){
            Context context = getApplicationContext();
            CharSequence text = "Bitte Passwort eingeben";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();



        }else{
            count ++;}
        if (spitzname1.getText().toString().equals("Spitzname")){
            Context context = getApplicationContext();
            CharSequence text = "Bitte Spitzname eingeben";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();



        }else{
            count ++;}
// wenn überall etwas Steht wird der Buttton freigeschalten, ein Intent erstellt und die Lobbyactivity wird gestartet
        if(count == 4){


            Intent lobby = new Intent(this, lobbyActivity.class);

            String spielName = spielname1.getText().toString();
            String spitzName = spitzname1.getText().toString();
            String passWort = passwort1.getText().toString();

            lobby.putExtra("sName", spielName);
            lobby.putExtra("pName", spitzName);
            lobby.putExtra("pWort", passWort);
            lobby.putExtra("rolle","player");


            startActivity(lobby);


        }else{

            /* TODO: Seite Einrichten -> Erstelle Text: Bitte alle Felder vollständig ausfüllen
            *                           -> Erstelle Button "Weiter"
            *                           -> Bei drücken: Gehe auf vorherige Seite
            */
        }











    }
}
