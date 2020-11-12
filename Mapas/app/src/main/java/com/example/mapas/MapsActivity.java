package com.mapas.sdkmapasgoogle;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

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

        //Mudar exibição do mapa
        mMap.setMapType( GoogleMap.MAP_TYPE_NORMAL );

        LatLng ibirapuera = new LatLng(-23.587097, -46.657635);

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
