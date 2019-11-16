package com.example.smartparking.Interfete;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.smartparking.BdComunica;
import com.example.smartparking.Obiecte.Autovehicule;
import com.example.smartparking.R;


public class ListaAutovehicule extends AppCompatActivity implements View.OnClickListener {

    RecyclerView lista;
    Button nrnou;
    EditText numarul;
    BdComunica comunica;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_autovehicule);
        lista=findViewById(R.id.listaautovehicule);
        nrnou=findViewById(R.id.btnnrnou);
        numarul=findViewById(R.id.insertnrinmatriculare);
        comunica=new BdComunica();
        LinearLayoutManager mlayoutmanager = new LinearLayoutManager(getBaseContext());
        comunica.autovehicule(comunica.useruid(),mlayoutmanager,lista);
        nrnou.setOnClickListener(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }
    @Override
    public void onClick(View v) {

        if(v==nrnou)
        {
            if(numarul.getText().toString().trim().length()==0)
            {
                Toast.makeText(getBaseContext(),"Introduceti un numar de inmatriculare", Toast.LENGTH_LONG).show();
                return;
            }
            Autovehicule autovehicule=new Autovehicule();
           AlertDialog.Builder builder=new AlertDialog.Builder(ListaAutovehicule.this,R.style.MyAlertDialogStyle);builder.setTitle("NR DE INMATRICULARE");
            builder.setMessage("Numarul de inmatriculare este: "+"\n"+numarul.getText().toString().trim()+"\n"+"Sunteti sigur?");
            builder.setPositiveButton("DA", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    autovehicule.setNrinmatriculare(numarul.getText().toString().trim());
                    comunica.adauganrnou(autovehicule.getNrinmatriculare().get(0));
                    numarul.getText().clear();
                }
            });
            builder.setNegativeButton("NU", null);
            builder.show();
        }
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
