package com.example.scottlandyardprojekt;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static android.widget.RelativeLayout.TRUE;

public class lobbyActivity extends AppCompatActivity implements View.OnClickListener {

    ArrayList<String> spielerListe = new ArrayList<>();
    final static String DEVICE_UUID = UUID.randomUUID().toString();
    final static Long SYNC_TIME = 30000L;
    final static Long START_TIME = 30000L;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_lobby);
            TextView wait = findViewById(R.id.WaitText);
            ListView sList = findViewById(R.id.spielerListe);
            RadioGroup rad = findViewById(R.id.radig);
            rad.setVisibility(View.INVISIBLE);
            RadioButton r1 = findViewById(R.id.radioButton2);
            RadioButton r2 = findViewById(R.id.radioButton3);
            RadioButton r3 = findViewById(R.id.radioButton4);
            RadioButton r4 = findViewById(R.id.radioButton5);
            RadioButton r5 = findViewById(R.id.radioButton6);
            RadioButton r6 = findViewById(R.id.radioButton7);
            Button readyButton = findViewById(R.id.gameStart);


            //Daten aus dem Intent holen

            final Bundle pack = getIntent().getExtras();
            String spielName = pack.getString("sName");
            String passWort = pack.getString("pWort");
            String spitzName = pack.getString("pName");

            // Ist das hier korreckt? Wo kommt Rolle her?

            String rolle = pack.getString("rolle");
            String server_url = pack.getString("http://scotland-yard.iums.eu/");

            //Text ändern, um Warten auszudrücken


            if (rolle.equals("admin")) {
                wait.setText("Warten auf Mitspieler");


            }


            /// TODO: 01.06.2019 Mit server verbinden, einloggen, checken obs passt, warten bis alle da sind
            // Use this server:
            String URL_Create="https://scotland-yard.iums.eu/createGame";

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("deviceUUID", DEVICE_UUID);
            jsonObject.put("timestamp", System.currentTimeMillis());
            // Der Spieler, der das Spiel erstellt ist also Mr. X
            jsonObject.put("role", "X");
            jsonObject.put("syncTime", SYNC_TIME );
            jsonObject.put("startTime", START_TIME );

            //Set<String> keys = pack.keySet();
            //for (String key : keys) {
            //    try{
            //        jsonObject.put(key, JSONObject.wrap(pack.get(key)));
            //    } catch(JSONException e) {
            //        // Handle exception here
            //    }
            //}

             //Create Request Queue

            RequestQueue requestQueue= Volley.newRequestQueue(this);
            JsonObjectRequest objectRequest=new JsonObjectRequest(
                    Request.Method.POST,
                    URL_Create,
                    // TODO: ersetzte null durch json object
                    jsonObject,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            // Hier muss rein, was mit Antwort passieren soll
                            Log.e("Rest Response", response.toString());
                        }

                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e( "Rest Response", error.toString());
                        }
                    });
                    requestQueue.add(objectRequest);



            // write into join function!!!
            //RequestQueue requestQueue= Volley.newRequestQueue(this);
            //JsonObjectRequest objectRequest=new JsonObjectRequest(
            //        Request.Method.GET,
            //        URL,
            //        // TODO: ersetzte null durch json object
            //        null,
            //        new Response.Listener<JSONObject>() {
            //            @Override
            //            public void onResponse(JSONObject response) {
            //                Log.e("Rest Response", response.toString());
            //            }

             //       },
             //       new Response.ErrorListener() {
             //           @Override
             //           public void onErrorResponse(VolleyError error) {
             //               Log.e( "Rest Response", error.toString());
             //           }
             //       }


            //);

            requestQueue.add(objectRequest);

            //Testliste erstellen
            spielerListe.add(spitzName);


            //Hier dan die spitznamen der anderen spieler
            spielerListe.add("Günther");
            spielerListe.add("Olaf");
            spielerListe.add("Hauke");
            spielerListe.add("Cemal");
            spielerListe.add("Norman");

            ArrayAdapter arrayadapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, spielerListe);
            sList.setAdapter(arrayadapter);
            wait.setText("Warten auf Admin");

            // Wenn Admin, dann die Liste ausblenden und Stattdessen Radiobuttons einfügen, und den Text auf "Wähle Mr.X" ändern

            if (rolle.equals("admin")) {
                sList.setVisibility(View.INVISIBLE);
                rad.setVisibility(View.VISIBLE);
                wait.setText("Bitte wähle Mr.X");

                r1.setText(spielerListe.get(0));
                r2.setText(spielerListe.get(1));
                r3.setText(spielerListe.get(2));
                r4.setText(spielerListe.get(3));
                r5.setText(spielerListe.get(4));
                r6.setText(spielerListe.get(5));

            }
            readyButton.setOnClickListener(this);

        } catch(Exception exception){
            System.out.println("lobby onCreate" + exception.getMessage());
        }

        }

        @Override
        public void onClick (View v){
        try {

            RadioGroup rad = findViewById(R.id.radig);

            final Bundle pack = getIntent().getExtras();
            String rolle = pack.getString("rolle");

            switch (rolle) {

                // den Admin Mr x wählen lassen. Toast falls keine Auswahl getroffen
                case "admin":
                    Context context = getApplicationContext();
                    CharSequence text;
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast;

                    int checkedButton = rad.getCheckedRadioButtonId();
                    System.out.println("lobby button " + checkedButton);


                    if (rad.getCheckedRadioButtonId() == -1) {
                        text = "Wähle Mr.X!";
                        toast = Toast.makeText(context, text, duration);
                        toast.show();
                    } else {
                       // Integer mrx = rad.getCheckedRadioButtonId();
                        //das hier funktioniert nicht, weil es ist irgendwie nicht ButtonID 1 sondern 2131230856, TODO Das hier reparieren
                       // String mrxName = spielerListe.get(mrx);

                        // TODO Randomnumbergenerator, generiert zufällige startpositionen aus der Liste aller Stationen. Prüft, dass keine Position doppelt vergeben wurde

                        // randomnumbergenerator
                        //ArrayList<> ranKo = new ArrayList();
                        //for(int i=5;i<6;i++) {
                        //int r = "randomnumber"
                        //"String" newKoo = ListOfStations.get(r);
                        //if(ranko.contains(newKoo)){
                        //continue;
                        // }else{
                        // ranko.add(newKoo);
                        //
                        //}
                        Intent startMap = new Intent(this, MapActivity.class);
                        // startMap.putExtra("mrx",mrx);
                        //startMap.putExtra("mrxName",mrxName);
                        //startMap.putExtra("rolle", "admin");
                        //startMap.putExtra ("liste",spielerListe);
                        startActivity(startMap);
                    }


                    break;
                case "player":

                    //Startet Mapactivity
                    Intent startMap = new Intent(this, MapActivity.class);
                    //startMap.putExtra("rolle","player");
                    startActivity(startMap);

            }
        } catch (Exception e) {
            System.out.println("lobby onClick" + e.getMessage());
        }
    }
}