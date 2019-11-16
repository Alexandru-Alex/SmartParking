package com.example.smartparking.Fragmente;


import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.smartparking.Adaptere.ParcariAdapter;
import com.example.smartparking.BdComunica;
import com.example.smartparking.Obiecte.Parcare;
import com.example.smartparking.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;


public class ParcariList extends Fragment {

    private View view;
    private RecyclerView listaparcari;
    private Harta harta;
    private LatLng locatiecurenta;
    private Bundle bundle;
    private RecyclerView.LayoutManager mlayoutmanager;
    private RecyclerView.Adapter madapter;
    private BdComunica comunica;
    private Parcare[] parcare;



    ViewGroup container;
    int index = 0;

    public ParcariList() { }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        this.container = container;
        view = inflater.inflate(R.layout.parcari, container, false);
        listaparcari = view.findViewById(R.id.listaparcari);
        comunica = new BdComunica();

        distanta();
        comunica.parcare();
        comunica.getReferinta().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                parcare = new Parcare[(int) dataSnapshot.getChildrenCount()];
                harta=new Harta();

                if(bundle==null)
                {
                    bundle=new Bundle();
                }
                double distante []=new double[parcare.length];
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        parcare[index] = ds.getValue(Parcare.class);
                        bundle.putFloat("lat", parcare[index].getLat());
                        bundle.putFloat("lon", parcare[index].getLon());
                        bundle.putString("nume", parcare[index].getNume());
                        harta.setArguments(bundle);
                        harta.setLatLong();

                      if(locatiecurenta!=null)
                      {
                          distante[index] = CalculationByDistance(parcare[index].getLat(), parcare[index].getLon());
                      }
                        index++;
                    }
                    index = 0;
                    listaparcari.setHasFixedSize(true);


                    madapter = new ParcariAdapter(parcare,distante);
                    listaparcari.setLayoutManager(new LinearLayoutManager(getActivity()));
                    listaparcari.setAdapter(madapter);

                    if(getActivity()!=null)
                    {
                        FragmentTransaction trans = getActivity().getSupportFragmentManager().beginTransaction();
                        trans.replace(R.id.harta1, harta).commitAllowingStateLoss();
                    }
                }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });

        return view;
    }

    public void distanta()
    {
        FusedLocationProviderClient fusedLocationClient;
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getContext());
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {
            fusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {

                        locatiecurenta = new LatLng(location.getLatitude(), location.getLongitude());
                    }
                }
            });
        }
    }

    public double CalculationByDistance(double latitude,double longitude) {
        int Radius = 6371;// radius of earth in Km
        double lat1 = locatiecurenta.latitude;
        double lat2 = latitude;
        double lon1 = locatiecurenta.longitude;
        double lon2 = longitude;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
                * Math.sin(dLon / 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        double valueResult = Radius * c;
        double km = valueResult / 1;
        DecimalFormat newFormat = new DecimalFormat("####");
        int kmInDec = Integer.valueOf(newFormat.format(km));
        double meter = valueResult % 1000;
        int meterInDec = Integer.valueOf(newFormat.format(meter));

        return Radius * c;
    }
    }