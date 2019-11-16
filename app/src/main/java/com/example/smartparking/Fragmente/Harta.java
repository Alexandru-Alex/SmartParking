package com.example.smartparking.Fragmente;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.smartparking.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;

import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;

public class Harta extends Fragment implements OnMapReadyCallback
{
    View mview;
    MapView mView;
    GoogleMap mMap;
    FusedLocationProviderClient fusedLocationClient;
    LatLng locatiecurenta;
    ArrayList<LatLng> parcari=new ArrayList<>();
    ArrayList<String> nume = new ArrayList<>();

    public Harta() { }

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mview = inflater.inflate(R.layout.harta, container, false);

        return mview;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        mView = (MapView) mview.findViewById(R.id.harta1);
        if (mView != null) {
            mView.onCreate(null);
            mView.onResume();
            mView.getMapAsync(this);
        }
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

       if(getContext()!=null) {
           MapsInitializer.initialize(getContext());
           mMap = googleMap;

           if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
               ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
           {
               ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
               ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
           }
           else
           {
               mMap.setMyLocationEnabled(true);
               fusedLocationClient = LocationServices.getFusedLocationProviderClient(getContext());
               fusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                   @Override
                   public void onSuccess(Location location) {
                       if(location!=null)
                       {
                           locatiecurenta=new LatLng(location.getLatitude(),location.getLongitude());
                       }
                   }
               });
           }
           adaugamarker(mMap);
       }
    }
    public  void adaugamarker(GoogleMap mMap)
    {
        for (int i = 0; i < parcari.size(); i++) {
            LatLng Constanta =parcari.get(i);
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(Constanta);
            mMap.addMarker(markerOptions).setTitle(nume.get(i));
            CameraPosition pozitie = CameraPosition.builder().target(Constanta).zoom(14).bearing(0).tilt(45).build();
            mMap.moveCamera(CameraUpdateFactory.newCameraPosition(pozitie));
            CameraUpdate cu = CameraUpdateFactory.newCameraPosition(pozitie);
            mMap.animateCamera(cu);
        }
    }
    public void setLatLong()
    {
    Bundle bundle= getArguments();

    if(bundle!=null)
    {
        float a= (float) bundle.get("lat");
        float b= (float) bundle.get("lon");
        String nume=bundle.getString("nume");
        LatLng auxiliar=new LatLng(a,b);
        parcari.add(auxiliar);
        this.nume.add(nume);
    }
    bundle.clear();
    }
}
