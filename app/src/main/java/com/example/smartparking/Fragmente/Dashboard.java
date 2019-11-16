package com.example.smartparking.Fragmente;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.smartparking.BdComunica;
import com.example.smartparking.Interfete.ListaAutovehicule;
import com.example.smartparking.Interfete.ListaPlati;
import com.example.smartparking.Main;
import com.example.smartparking.Interfete.ParcariCurente;
import com.example.smartparking.Interfete.Profil;
import com.example.smartparking.R;


public class Dashboard extends Fragment implements View.OnClickListener
{


    View view;
    CardView delogare,autovehicule,plati,parcaricurente,profil;
    BdComunica comunica;
    Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.dashboard, container, false);
        delogare=view.findViewById(R.id.delogare);
        autovehicule=view.findViewById(R.id.autovehicul);
        plati=view.findViewById(R.id.plati);
        parcaricurente=view.findViewById(R.id.parcaricurente);
        profil=view.findViewById(R.id.profil);
        comunica=new BdComunica();
        delogare.setOnClickListener(this);
        autovehicule.setOnClickListener(this);
        plati.setOnClickListener(this);
        parcaricurente.setOnClickListener(this);
        profil.setOnClickListener(this);
        context=getContext();
        return view;
    }


    @Override
    public void onClick(View v) {

        if(v==delogare)
        {
            if(comunica.useruid()!=null) {
                comunica.signout();
            }
            Intent intent=new Intent(getActivity(), Main.class);
            startActivity(intent);
            getActivity().finish();
        }
        if(v==autovehicule)
        {
            Intent intent=new Intent(getContext(), ListaAutovehicule.class);
            context.startActivity(intent);
        }
        if(v==plati)
        {
            Intent intent=new Intent(getContext(), ListaPlati.class);
            context.startActivity(intent);
        }
        if(v==parcaricurente)
        {
            Intent intent=new Intent(getContext(), ParcariCurente.class);
            context.startActivity(intent);
        }
        if(v==profil) {
            Intent intent = new Intent((getContext()), Profil.class);
            context.startActivity(intent);
        }
    }
}

