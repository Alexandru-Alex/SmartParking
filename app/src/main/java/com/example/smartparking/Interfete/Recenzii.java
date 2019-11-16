package com.example.smartparking.Interfete;

import android.content.Intent;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartparking.BdComunica;
import com.example.smartparking.Obiecte.Recenzie;
import com.example.smartparking.R;

public class Recenzii extends AppCompatActivity {

    private RecyclerView listarecenzie;
    private BdComunica comunica;
    private Button submit;
    private TextView nupoti;
    private RatingBar nota;
    private EditText recenziescrisa;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recenzii);

setTitle("Recenzii-"+getIntent().getExtras().getString("titlu"));
        listarecenzie=findViewById(R.id.recenziidate);
        nupoti=findViewById(R.id.nupoti);
        nota=findViewById(R.id.notarecenzie);
        recenziescrisa=findViewById(R.id.recenziescrisa);
        nupoti.setVisibility(View.GONE);
        submit=findViewById(R.id.submit);
        DisplayMetrics dm=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int w=dm.widthPixels;
        int h=dm.heightPixels;
        getWindow().setLayout((int) (w*.9),(int) (h*.8));
        comunica = new BdComunica();
        Intent intent=getIntent();
        int id=intent.getExtras().getInt("id");
        float notaparcare=intent.getExtras().getFloat("nota");
        if(comunica.useruid()!=null)
        {
            uid=comunica.useruid();
            comunica.cautauser(uid);
        }
        else
        {
            submit.setEnabled(false);
            nupoti.setVisibility(View.VISIBLE);
        }

        comunica.recenzii(String.valueOf(id),getApplicationContext(),listarecenzie);
        submit.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        Recenzie recenzie=new Recenzie();

        if(nota.getRating()<1)
        {
            Toast.makeText(Recenzii.this,"Da-ti o nota de la 1 la 5 ",Toast.LENGTH_LONG).show();
            return;
        }
        recenzie.setNota(nota.getRating());
        recenzie.setNume(comunica.getUtilizator().getUsername());
        recenzie.setRecenzia(recenziescrisa.getText().toString());
        comunica.getReferinta().child(uid).setValue(recenzie);
        comunica.medierecenzi(String.valueOf(id));
        finish();
    }
});
    }
}
