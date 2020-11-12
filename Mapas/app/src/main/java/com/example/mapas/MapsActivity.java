package com.sdkmapasgoogle;

import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setMapType( GoogleMap.MAP_TYPE_NORMAL );

        final LatLng ibirapuera = new LatLng(-23.587097, -46.657635);

        //Adicionando evento de clique no mapa
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

                Double latitude = latLng.latitude;
                Double longitude = latLng.longitude;

                PolylineOptions polylineOptions = new PolylineOptions();
                polylineOptions.add(ibirapuera);
                polylineOptions.add( latLng );
                polylineOptions.color(Color.BLUE);
                polylineOptions.width(20);

                mMap.addPolyline( polylineOptions );

                mMap.addMarker(
                        new MarkerOptions()
                                .position( latLng )
                                .title("Local")
                                .snippet("Descricao")
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.icone_loja))
                );
            }
        });

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                Double latitude = latLng.latitude;
                Double longitude = latLng.longitude;

                Toast.makeText(MapsActivity.this,
                        "onLong Lat: " + latitude + " long:" + longitude ,
                        Toast.LENGTH_SHORT).show();

                mMap.addMarker(
                        new MarkerOptions()
                                .position( latLng )
                                .title("Local")
                                .snippet("Descricao")
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.carro))
                );
            }
        });

        mMap.addMarker(
                new MarkerOptions()
                        .position( ibirapuera )
                        .title("Ibirapuera")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.icone_carro_roxo_48px))
        );

        mMap.moveCamera(//2.0 até 21.0
              CameraUpdateFactory.newLatLngZoom(ibirapuera,18)
        );
    }
}
