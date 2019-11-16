package com.example.smartparking.Interfete;


import android.content.Intent;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.smartparking.BdComunica;
import com.example.smartparking.Interfete.Plata;
import com.example.smartparking.Interfete.Recenzii;
import com.example.smartparking.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import com.squareup.picasso.Picasso;

public class Detaliiparcare extends AppCompatActivity  implements View.OnClickListener {

    String poza,nume;
    int id,id_pret_parcare,id_program,index=0,id_dotari,ocupate,capacitate;
    float lat,lon,notaparcare;
    ImageView imagine;
    TextView titlu,nota;
    BdComunica comunica=new BdComunica();
    ListView listapreturi,listaprogram,listadotari;
    Button afiseazapret,afiseazaprogram,afiseazadotari,plateste,recenzii;
    MapView mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detaliiparcare);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        imagine=findViewById(R.id.imagineparcare);
        titlu=findViewById(R.id.titlu);
        listapreturi=findViewById(R.id.listapreturi);
        listaprogram=findViewById(R.id.listaprogram);
        listadotari=findViewById(R.id.listadotari);
        afiseazapret=findViewById(R.id.afiseazapret);
        afiseazaprogram=findViewById(R.id.afiseazaprogram);
        afiseazadotari=findViewById(R.id.afiseazadotari);
        recenzii=findViewById(R.id.recenzii);
        nota=findViewById(R.id.nota);
        plateste=findViewById(R.id.plateste);
        mMap= findViewById(R.id.harta1);
        listapreturi.setVisibility(View.GONE);
        listaprogram.setVisibility(View.GONE);
        listadotari.setVisibility(View.GONE);
        imagine.setAdjustViewBounds(true);
        Intent intent=getIntent();
        poza=intent.getExtras().getString("poza");
        id=intent.getExtras().getInt("id");
        id_pret_parcare=intent.getExtras().getInt("id_pret_parcare");
        id_dotari=intent.getExtras().getInt("id_dotari");
        nume=intent.getExtras().getString("nume");
        id_program=intent.getExtras().getInt("id_program");
        lat=intent.getExtras().getFloat("lat");
        lon=intent.getExtras().getFloat("lon");
        notaparcare=intent.getExtras().getFloat("nota");
        ocupate=intent.getExtras().getInt("ocupate");
        capacitate=intent.getExtras().getInt("capacitate");
        Picasso.get().load(poza).into(imagine);
        titlu.setText(nume);
        nota.setText("Nota:"+intent.getExtras().getFloat("nota")+"/5.0");
        afiseazapret.setOnClickListener(this);
        afiseazaprogram.setOnClickListener(this);
        afiseazadotari.setOnClickListener(this) ;
        recenzii.setOnClickListener(this);
        plateste.setOnClickListener(this);
        comunica.dotari(getApplicationContext(),id_dotari,listadotari);
        comunica.program(getApplicationContext(),id_program,listaprogram);
        comunica.preturi(getApplicationContext(),id_pret_parcare,listapreturi);
        initializareHarta();
    }
    public void initializareHarta()
    {
        mMap.onCreate(null);
        mMap.onResume();
        mMap.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {

                GoogleMap google;
                MapsInitializer.initialize(getBaseContext());
                LatLng marker=new LatLng(lat,lon);
                google = googleMap;

                MarkerOptions markerOptions=new MarkerOptions();
                markerOptions.position(marker);
                google.addMarker(markerOptions).setTitle(nume);
                CameraPosition pozitie= CameraPosition.builder().target(marker).zoom(14).bearing(0).tilt(45).build();
                googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(pozitie));
                CameraUpdate cu = CameraUpdateFactory.newCameraPosition(pozitie);
                google.animateCamera(cu);
            }
        });
    }

    @Override
    public void onClick(View v) {

        if(v==afiseazadotari)
        {
            if(listadotari.getVisibility()==v.VISIBLE) {
                afiseazadotari.setBackgroundResource(R.drawable.plus);
                listadotari.setVisibility(v.GONE);
            }
            else {
                listadotari.setVisibility(v.VISIBLE);
                afiseazadotari.setBackgroundResource(R.drawable.minus);
            }
        }
        if(v==afiseazaprogram)
        {
            if(listaprogram.getVisibility()==v.VISIBLE) {
                afiseazaprogram.setBackgroundResource(R.drawable.plus);
                listaprogram.setVisibility(v.GONE);
            }
            else {
                listaprogram.setVisibility(v.VISIBLE);
                afiseazaprogram.setBackgroundResource(R.drawable.minus);
            }
        }
        if(v==afiseazapret)
        {
            if(listapreturi.getVisibility()==v.VISIBLE) {
                afiseazapret.setBackgroundResource(R.drawable.plus);
                listapreturi.setVisibility(v.GONE);
            }
            else {
                listapreturi.setVisibility(v.VISIBLE);
                afiseazapret.setBackgroundResource(R.drawable.minus);
            }
        }
        if(v==recenzii)
        {
            Intent intent=new Intent(this, Recenzii.class);
            intent.putExtra("id",id);
            intent.putExtra("nota",notaparcare);
            intent.putExtra("titlu",nume);
            startActivity(intent);
            this.getFragmentManager().popBackStack();
        }
        if(v==plateste)
        {
            Intent intent=new Intent(this, Plata.class);
            intent.putExtra("nume_parcare",nume);
            intent.putExtra("id_pret",id_pret_parcare);
            intent.putExtra("id_parcare",id);
            intent.putExtra("capacitate",capacitate);
            intent.putExtra("ocupate",ocupate);
            startActivity(intent);
        }
        }
    }

