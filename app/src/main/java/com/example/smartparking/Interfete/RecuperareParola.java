package com.example.smartparking.Interfete;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartparking.BdComunica;
import com.example.smartparking.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class RecuperareParola extends AppCompatActivity  implements View.OnClickListener {

    private Button recuperare;
    private ProgressBar progressBar;
    private BdComunica comunica;
    private TextView email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recuperare_parola);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);;
        recuperare=(Button) findViewById(R.id.recuperare);
        progressBar=(ProgressBar) findViewById(R.id.progres);
        email=(TextView)findViewById(R.id.emailrecuperare);
        recuperare.setOnClickListener(this);
        progressBar.setVisibility(View.GONE);
        comunica=new BdComunica();
        comunica.inregistrare();
    }

    @Override
    public void onClick(View v) {

        String emailu = email.getText().toString().trim();
        progressBar.setVisibility(View.VISIBLE);

        if (!emailu.isEmpty()) {
            if (verificaremail(emailu) == true)
            {
                comunica.getAutentifica().sendPasswordResetEmail(emailu).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isComplete()) {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(RecuperareParola.this, "Email-ul a fost trimis,daca se afla in baza de date", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                });
            }
            else
            {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(RecuperareParola.this, "Furnizati un mail valid", Toast.LENGTH_SHORT).show();
            }
        }
    }
    boolean verificaremail(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
