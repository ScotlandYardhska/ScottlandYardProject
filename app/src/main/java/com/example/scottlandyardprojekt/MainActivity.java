package com.example.scottlandyardprojekt;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.mapbox.geojson.Feature;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.style.layers.PropertyFactory;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
//Buttons erstellen
    Button buttonCreate;
    Button buttonJoin;

    private MapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


////////////////////////////////////////////////////

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
