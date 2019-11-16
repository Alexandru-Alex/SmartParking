package com.example.smartparking.Fragmente;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;


import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.smartparking.BdComunica;
import com.example.smartparking.Interfete.Inregistrare;
import com.example.smartparking.Main;
import com.example.smartparking.R;
import com.example.smartparking.Interfete.RecuperareParola;
import com.example.smartparking.Obiecte.Utilizator;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;


public class Logare extends Fragment implements View.OnClickListener   {
    View view;

    Button login,inregistrare,recuperare_parola;

    EditText email,parola;
    String emailk,parolak;
    ProgressBar progressBar;
    BdComunica comunica;

    public Logare() { }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.logare, container, false);
        email=(EditText)view.findViewById(R.id.emaillogare);
        parola=(EditText)view.findViewById(R.id.logareparola);
        recuperare_parola=(Button)view.findViewById(R.id.recuperare_parola);
        inregistrare=(Button)view.findViewById(R.id.inregistarebutton);
        login=(Button)view.findViewById(R.id.logarebutton);
        inregistrare.setOnClickListener(this);
        login.setOnClickListener(this);
        recuperare_parola.setOnClickListener(this);
        progressBar=view.findViewById(R.id.progresslogin);
        progressBar.setVisibility(View.GONE);
        comunica=new BdComunica();
        comunica.inregistrare();

        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
    }
    @Override
    public void onStop() {
        super.onStop();
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
    }
    @Override
    public void onClick(View v) {

        if(v==inregistrare)
        {
            Intent intent=new Intent(getActivity(), Inregistrare.class);
            startActivity(intent);
            getActivity().getFragmentManager().popBackStack();
        }
        if(v==login)
        {
            progressBar.setVisibility(View.VISIBLE);
            emailk=email.getText().toString().trim();
            parolak=parola.getText().toString().trim();

            if(!emailk.isEmpty()&&!parolak.isEmpty()) {
               comunica.getAutentifica().signInWithEmailAndPassword(emailk, parolak).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getActivity(), "Login cu succes", Toast.LENGTH_LONG).show();
                            Intent intent=new Intent(getContext(), Main.class);
                           comunica.getReferinta().addValueEventListener(new ValueEventListener() {
                               @Override
                               public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                              Utilizator informatie=dataSnapshot.getValue(Utilizator.class);
                                   email.getText().clear();
                                   parola.getText().clear();
                                   progressBar.setVisibility(View.GONE);

                                   if(getFragmentManager()!=null) {
                                       getActivity().getFragmentManager().popBackStack();
                                       getActivity().finish();
                                       startActivity(intent);
                                   }
                               }

                               @Override
                               public void onCancelled(@NonNull DatabaseError databaseError) {
                               }
                           });

                        } else {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(getActivity(), "Datele sunt gresite", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }else
            {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Completati toate campurile", Toast.LENGTH_LONG).show();
            }
        }

        if(v==recuperare_parola)
        {
            Intent intent=new Intent(getActivity(), RecuperareParola.class);
            startActivity(intent);
            getActivity().getFragmentManager().popBackStack();
            comunica.signout();
        }
    }
}