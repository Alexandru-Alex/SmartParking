package com.example.smartparking.Interfete;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.smartparking.BdComunica;
import com.example.smartparking.Main;
import com.example.smartparking.Obiecte.Utilizator;
import com.example.smartparking.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public class Inregistrare extends AppCompatActivity implements View.OnClickListener  {

    private EditText numeI,prenumeI,usernameI,parolaI,parolaverificareI,emailI;
    private Button btn;
    private BdComunica comunica;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.inregistrare);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        numeI=(EditText) findViewById(R.id.numeinregistrare);
        prenumeI=(EditText)findViewById(R.id.prenumeinregistrare);
        usernameI=(EditText)findViewById(R.id.usernameinregistare);
        parolaI=(EditText)findViewById(R.id.parolainregistare);
        parolaverificareI=(EditText)findViewById(R.id.verificareparola);
        emailI=(EditText)findViewById(R.id.emailinregistare);
        btn=(Button)findViewById(R.id.inregistarebutton);
        comunica=new BdComunica();
        progressBar=findViewById(R.id.progressBara);
        btn.setOnClickListener(this);
progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {

        if(v==btn) {
            String nume = numeI.getText().toString().trim();
            String prenume = prenumeI.getText().toString().trim();
            String username = usernameI.getText().toString().trim();
            String email = emailI.getText().toString().trim();
            String parola = parolaI.getText().toString().trim();
            String verificaparola=parolaverificareI.getText().toString().trim();

            if(verificaempty(nume,prenume,username,parola,verificaparola,email)==false)
            {
                Toast.makeText(this,"Toate campurile sunt obligatorii",Toast.LENGTH_LONG).show();
                return;
            }
            if(verificaremail(email)==false)
            {
                Toast.makeText(this,"Trebuie introdus un email valid",Toast.LENGTH_LONG).show();
                return;
            }
            if(parola.length()<6)
            {
                Toast.makeText(this,"Parola trebuie sa contina cel putin 6 caractere",Toast.LENGTH_LONG).show();
                return;
            }
            if(!parola.equals(verificaparola))
            {
                Toast.makeText(this,"Cele 2 parole nu se potrivesc",Toast.LENGTH_LONG).show();
                return;
            }

            comunica.inregistrare();
            progressBar.setVisibility(View.VISIBLE);
            comunica.getAutentifica().createUserWithEmailAndPassword(email,parola).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if(task.isSuccessful())
                    {
                        progressBar.setVisibility(View.GONE);
                        Utilizator informatie = new Utilizator(comunica.getAutentifica().getUid(), nume, prenume, username, parola, email);
                        comunica.getReferinta().child(comunica.getAutentifica().getUid()).setValue(informatie);
                        Toast.makeText(Inregistrare.this,"Utilizator inregistrat cu succes",Toast.LENGTH_LONG).show();
                        Intent intent=new Intent(Inregistrare.this, Main.class);
                        startActivity(intent);
                        finish();
                    }
                    else
                    {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(Inregistrare.this,"Email-ul este deja folosit la un alt cont",Toast.LENGTH_LONG).show();
                        return;
                    }

                }
            });

        }


    }
    boolean verificaremail(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    boolean verificaempty(String nume,String prenume,String username,String parola,String verificaparola,String email)
    {

        if(!nume.isEmpty()&!prenume.isEmpty()&!username.isEmpty()&!parola.isEmpty()&!verificaparola.isEmpty()&!email.isEmpty())
        {
            return true;
        }
        return false;
    }
}




