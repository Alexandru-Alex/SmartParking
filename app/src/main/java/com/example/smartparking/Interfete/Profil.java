package com.example.smartparking.Interfete;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.smartparking.BdComunica;
import com.example.smartparking.Main;
import com.example.smartparking.R;

public class Profil extends AppCompatActivity {



    BdComunica comunica;
    TextView nume,prenume,email,totalplati,totalauto;
    Button inchidere;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.profil);

        nume=(TextView)findViewById(R.id.numeeeeutilizator);
        prenume=findViewById(R.id.prenumeutilizator);
        email=findViewById(R.id.emailtilizator);
        totalplati=findViewById(R.id.totalplati);
        totalauto=findViewById(R.id.totalauto);
        inchidere=findViewById(R.id.inchiderecont);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

comunica=new BdComunica();


comunica.seteazauser(nume,prenume,email,totalplati,totalauto);

inchidere.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {


        AlertDialog.Builder builder=new AlertDialog.Builder(Profil.this,R.style.MyAlertDialogStyle);builder.setTitle("STERGERE CONT");
        builder.setMessage("Sunteti pe cale de a sterge contul de utilizator"+"\n Acest proces este ireversibil \n Sunteti sigur de acest lucru?");
        builder.setPositiveButton("DA", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                comunica.stergeutilizator();
                Intent intent=new Intent(Profil.this, Main.class);
                startActivity(intent);
                Profil.this.finish();
            }
        });
        builder.setNegativeButton("NU", null);
        builder.show();


    }
});
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
