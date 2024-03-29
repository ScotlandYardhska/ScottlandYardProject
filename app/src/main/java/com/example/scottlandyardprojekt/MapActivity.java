
package com.example.scottlandyardprojekt;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import android.content.Intent;


import com.mapbox.geojson.Feature;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.LocationComponentOptions;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
// Klassen für Berechtigungen
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import java.util.List;

// Klassen für die LocationEngine
import com.mapbox.android.core.location.LocationEngine;
import com.mapbox.android.core.location.LocationEngineCallback;
import com.mapbox.android.core.location.LocationEngineProvider;
import com.mapbox.android.core.location.LocationEngineRequest;
import com.mapbox.android.core.location.LocationEngineResult;
import java.lang.ref.WeakReference;
import java.util.TimerTask;

// Klassen für das LocationComponent
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.style.layers.PropertyFactory;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;

import org.json.JSONObject;

import static com.mapbox.mapboxsdk.maps.Style.MAPBOX_STREETS;


public class MapActivity extends AppCompatActivity implements OnMapReadyCallback,PermissionsListener{

    // Variablen LocationEngine
    private LocationEngine locationEngine;
    private LocationComponent locationComponent;
    private long DEFAULT_INTERVAL_IN_MILLISECONDS = 1000L;
    private long DEFAULT_MAX_WAIT_TIME = DEFAULT_INTERVAL_IN_MILLISECONDS * 5;


    private MapView mapView;
    private PermissionsManager permissionsManager;


    // Variablen angeblich für LocationUpdates
    private MainActivityLocationCallback callback = new MapActivity.MainActivityLocationCallback(this);
    private MapboxMap mapboxMap;

    // Ortung
    public double lat = 0;
    public double lng = 0;
    Context context;
    LocationManager mLocationManager;
    IconFactory iconFactory;
    Icon xIcon;
    Icon dIcon;

   private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(final Location location) {
            lat = location.getLatitude();
            lng = location.getLongitude();
            System.out.println("function onLocationChanged works");

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onProviderDisabled(String provider) {
        }
    };

    public interface callback{
        public void setMarker(String coordinates, String playerRole);
    }

    callback cb = new callback() {
        @Override
        public void setMarker(String coordinates, String playerRole) {
            String[] coordinates2 = coordinates.split("T");
            double pLat = Double.parseDouble(coordinates2[0]);
            double pLng = Double.parseDouble(coordinates2[1]);

            System.out.println("LAT " + pLat);
            System.out.println("Long " + pLng);


            if (playerRole.equals("X")) {
                for (Marker marker : mapboxMap.getMarkers()) {
                    mapboxMap.removeMarker(marker);
                }
                mapboxMap.addMarker(new MarkerOptions()
                        .setPosition(new LatLng(pLat, pLng))
                        .setIcon(xIcon));
            } else {
                mapboxMap.addMarker(new MarkerOptions()
                        .setPosition(new LatLng(pLat, pLng))
                        .setIcon(dIcon));
            }

        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //try {

            super.onCreate(savedInstanceState);
        //    Mapbox.getInstance(this, getString(R.string.access_token));
////////////////////////////////////////////////////////////////////////////////////////////////////////
            Mapbox.getInstance(this, getString(R.string.access_token));
            setContentView(R.layout.activity_main);

        context = this;
            mapView = (MapView)findViewById(R.id.mapView);
            mapView.onCreate(savedInstanceState);
            mapView.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(@NonNull final MapboxMap mapboxMap) {

                    mapboxMap.setStyle(Style.LIGHT, new Style.OnStyleLoaded() {
                        @Override
                        public void onStyleLoaded(@NonNull Style style) {
// Add the marker image to map
                            style.addImage("marker-icon-id",
                                    BitmapFactory.decodeResource(
                                            MapActivity.this.getResources(), R.drawable.mapbox_marker_icon_default));

                            GeoJsonSource geoJsonSource = new GeoJsonSource("source-id", Feature.fromGeometry(
                                    Point.fromLngLat(-87.679, 41.885)));
                            style.addSource(geoJsonSource);

                            SymbolLayer symbolLayer = new SymbolLayer("layer-id", "source-id");
                            symbolLayer.withProperties(
                                    PropertyFactory.iconImage("marker-icon-id")
                            );
                            style.addLayer(symbolLayer);

                        }
                    });
                }
            });
    };

////////////////////////////////////////////////////////////////////////////////////////////////////////
/*
            context = this;
            setContentView(R.layout.activity_map);
            mapView = findViewById(R.id.mapView);
            mapView.onCreate(savedInstanceState);
            mapView.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(@NonNull MapboxMap mapboxMap) {
                    mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
                        @Override
                        public void onStyleLoaded(@NonNull Style style) {
                            enableLocationComponent(style);


                            // Map is set up and the style has loaded.


                        }
                    });
                }
            });
        } catch (Exception e) {
            System.out.println("map onCreate" + e.getMessage());
        }
        iconFactory = IconFactory.getInstance(context);

        xIcon = iconFactory.fromResource(R.drawable.icon);
        dIcon = iconFactory.fromResource(R.drawable.icon);

*/


    @Override
    public void onMapReady(@NonNull final MapboxMap mapboxMap) {
        try {
            this.mapboxMap = mapboxMap;

            mapboxMap.setStyle(Style.MAPBOX_STREETS,
                    new Style.OnStyleLoaded() {
                        @Override
                        public void onStyleLoaded(@NonNull Style style) {
                            enableLocationComponent(style);
                        }
                    });
        } catch (Exception e) {
            System.out.println("map onMapReady " + e.getMessage());
        }
    }



    /**
     * Initialize the Maps SDK's LocationComponent
     */
    @SuppressWarnings( {"MissingPermission"})
    private void enableLocationComponent(@NonNull Style loadedMapStyle) {
        // Check if permissions are enabled and if not request
        if (PermissionsManager.areLocationPermissionsGranted(this)) {

            //here
            // Get an instance of the component
            LocationComponent locationComponent = mapboxMap.getLocationComponent();

            // Set the LocationComponent activation options
            LocationComponentActivationOptions locationComponentActivationOptions =
                    LocationComponentActivationOptions.builder(this, loadedMapStyle)
                            .useDefaultLocationEngine(false)
                            .build();

            // Activate with the LocationComponentActivationOptions object
            locationComponent.activateLocationComponent(locationComponentActivationOptions);

            // Enable to make component visible
            locationComponent.setLocationComponentEnabled(true);

            // Set the component's camera mode
            locationComponent.setCameraMode(CameraMode.TRACKING);

            // Set the component's render mode
            locationComponent.setRenderMode(RenderMode.COMPASS);

            initLocationEngine();


        } else {
            permissionsManager = new PermissionsManager( this);
            permissionsManager.requestLocationPermissions(this);
        }

    }
    /**
     * Set up the LocationEngine and the parameters for querying the device's location
     */
    @SuppressLint("MissingPermission")
    private void initLocationEngine() {
        locationEngine = LocationEngineProvider.getBestLocationEngine(this);

        LocationEngineRequest request = new LocationEngineRequest.Builder(DEFAULT_INTERVAL_IN_MILLISECONDS)
                .setPriority(LocationEngineRequest.PRIORITY_HIGH_ACCURACY)
                .setMaxWaitTime(DEFAULT_MAX_WAIT_TIME).build();

        locationEngine.requestLocationUpdates(request, callback, getMainLooper());
        locationEngine.getLastLocation(callback);
    }




    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    private static class MainActivityLocationCallback
            implements LocationEngineCallback<LocationEngineResult> {

        private final WeakReference<MapActivity> activityWeakReference;

        MainActivityLocationCallback(MapActivity activity) {
            this.activityWeakReference = new WeakReference<>(activity);
        }

        /**
         * The LocationEngineCallback interface's method which fires when the device's location has changed.
         *
         * @param result the LocationEngineResult object which has the last known location within it.
         */
        @Override
        public void onSuccess(LocationEngineResult result) {
            MapActivity activity = activityWeakReference.get();

            if (activity != null) {
                Location location = result.getLastLocation();

                if (location == null) {
                    return;
                }


                // Pass the new location to the Maps SDK's LocationComponent
                if (activity.mapboxMap != null && result.getLastLocation() != null) {
                    activity.mapboxMap.getLocationComponent().forceLocationUpdate(result.getLastLocation());
                }
            }
        }

        /**
         * The LocationEngineCallback interface's method which fires when the device's location can not be captured
         *
         * @param exception the exception message
         */
        @Override
        public void onFailure(@NonNull Exception exception) {
            Log.d("LocationChangeActivity", exception.getLocalizedMessage());
            MapActivity activity = activityWeakReference.get();
            if (activity != null) {
                Toast.makeText(activity, exception.getLocalizedMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {
        Toast.makeText(this, R.string.user_location_permission_explanation, Toast.LENGTH_LONG).show();
    }
    @Override
    public void onPermissionResult(boolean granted) {
        if (granted) {
            if (mapboxMap.getStyle() != null) {
                enableLocationComponent(mapboxMap.getStyle());

            }
    } else {
        Toast.makeText(this, R.string.user_location_permission_not_granted, Toast.LENGTH_LONG).show();
        finish();
    }
}







    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (locationEngine != null) {
            locationEngine.removeLocationUpdates(callback);
        }
        mapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);

    }


}

