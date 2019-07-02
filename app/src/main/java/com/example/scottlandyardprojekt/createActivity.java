package com.example.scottlandyardprojekt;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class createActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText spielname1;
    private EditText passwort1;
    private EditText spitzname1;
    Button create;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        spielname1 = findViewById(R.id.spielname2);
        passwort1 = findViewById(R.id.passwort2);
        spitzname1 = findViewById(R.id.spitzname2);



        create = findViewById(R.id.createButt);

        create.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        spielname1 = findViewById(R.id.spielname2);
        passwort1 = findViewById(R.id.passwort2);
        spitzname1 = findViewById(R.id.spitzname2);
        int count = 1;
        /*
        Hier wird geprüft ob überall etwas eingegeben wurde. Das Programm läuft durch alles durch und sendet toasts fslls was fehlt
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

                /// TODO: 01.06.2019 Testing
                /*  Hier werden die eingegebenen Daten an die Lobbyactivity abgegeben

                 */






                Intent lobby = new Intent(this, lobbyActivity.class);

                String spielName = spielname1.getText().toString();
                String spitzName = spitzname1.getText().toString();
                String passwort = passwort1.getText().toString();

                lobby.putExtra("sName", spielName);
                lobby.putExtra("pName", spitzName);
                lobby.putExtra("pWort", passwort);
                lobby.putExtra("rolle","admin");


                startActivity(lobby);


            }else{

            }


        }
}
