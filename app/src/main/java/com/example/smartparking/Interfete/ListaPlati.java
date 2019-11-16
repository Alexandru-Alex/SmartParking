package com.example.smartparking.Interfete;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.example.smartparking.BdComunica;
import com.example.smartparking.R;

public class ListaPlati extends AppCompatActivity {

    BdComunica comunica=new BdComunica();
    RecyclerView lista_plati;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_plati);

        lista_plati=findViewById(R.id.lista_plati);
        LinearLayoutManager mlayoutmanager = new LinearLayoutManager(getBaseContext());
        comunica.listaplati(mlayoutmanager,lista_plati);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
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
