package com.example.smartparking.Interfete;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartparking.BdComunica;
import com.example.smartparking.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class Plata extends AppCompatActivity {


    TextView nume_parcare, totalplata;
    EditText nrinmatriculare;
    Button plateste;
    Spinner masina, metodaplata, oraplata;
    BdComunica comunica = new BdComunica();
    final int SEND_SMS_PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plata);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        nume_parcare = findViewById(R.id.nume_parcare_plata);
        nrinmatriculare = findViewById(R.id.nrinmatriculare);
        masina = findViewById(R.id.masina_plata);
        metodaplata = findViewById(R.id.metoda_plata);
        oraplata = findViewById(R.id.durata_plata);
        totalplata = findViewById(R.id.total_plata);
        plateste = findViewById(R.id.plata);
        Intent intent = getIntent();
        nume_parcare.setText(intent.getExtras().getString("nume_parcare"));
        ArrayList<String> metoda_plata = new ArrayList<>();
        metoda_plata.add("SMS");
        metoda_plata.add("Card Bancar");

        if (comunica.useruid() != null) {
            comunica.autovehicule(comunica.useruid(), masina, getApplicationContext());
            nrinmatriculare.setVisibility(View.GONE);
            masina.setVisibility(View.VISIBLE);
        } else {
            nrinmatriculare.setVisibility(View.VISIBLE);
            masina.setVisibility(View.GONE);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, metoda_plata) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 1) {
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 1) {
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        metodaplata.setAdapter(adapter);
        comunica.preturi(intent.getExtras().getInt("id_pret"), oraplata, getApplicationContext(), intent.getExtras().getString("nume_parcare"), totalplata);
        plateste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (intent.getExtras().getInt("ocupate") < intent.getExtras().getInt("capacitate")) {
                    if (comunica.useruid() != null) {
                        if (masina.getSelectedItem() == null) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(Plata.this, R.style.MyAlertDialogStyle);
                            builder.setTitle("NR DE INMATRICULARE");
                            builder.setMessage("Nu aveti niciun numar de inmatriculare selctat \ndoriti sa adaugati unul?");
                            builder.setPositiveButton("DA", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(Plata.this, ListaAutovehicule.class);
                                    startActivity(intent);
                                }
                            });
                            builder.setNegativeButton("NU", null);
                            builder.show();

                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(Plata.this, R.style.MyAlertDialogStyle);
                            builder.setTitle("FINALIZARE PLATA");
                            builder.setMessage("Sunteti sigur ca doriti sa finalizati plata pentru nr de inmatriculare: \n   " + masina.getSelectedItem().toString() + "?");
                            builder.setPositiveButton("DA", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (permisiune(Manifest.permission.SEND_SMS)) {
                                        trimiteSMS();
                                        Date c = Calendar.getInstance().getTime();
                                        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy ");
                                        SimpleDateFormat doa = new SimpleDateFormat("HH:mm:ss");
                                        String ziua = df.format(c);
                                        String ora = doa.format(c);
                                        comunica.plati(ziua, ora, masina.getSelectedItem().toString(), nume_parcare.getText().toString(), intent.getExtras().getInt("id_parcare"), oraplata.getSelectedItem().toString());

                                    } else {
                                        ActivityCompat.requestPermissions(Plata.this, new String[]{Manifest.permission.SEND_SMS}, SEND_SMS_PERMISSION_REQUEST_CODE);
                                    }
                                }
                            });
                            builder.setNegativeButton("NU", null);
                            builder.show();
                        }
                    } else {
                        if (nrinmatriculare.getText().toString().trim().length() == 0) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(Plata.this, R.style.MyAlertDialogStyle);
                            builder.setTitle("NR DE INMATRICULARE");
                            builder.setMessage("Varog completati nr-ul de inmatriculare \n   ");
                            builder.setPositiveButton("OK", null);
                            builder.show();
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(Plata.this, R.style.MyAlertDialogStyle);
                            builder.setTitle("FINALIZARE PLATA");
                            builder.setMessage("Sunteti sigur ca doriti sa finalizati plata pentru nr de inmatriculare: \n   " + nrinmatriculare.getText().toString() + "?");
                            builder.setPositiveButton("DA", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    if (permisiune(Manifest.permission.SEND_SMS)) {

                                        if (trimiteSMS() == true) {
                                            Date c = Calendar.getInstance().getTime();
                                            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy ");
                                            SimpleDateFormat doa = new SimpleDateFormat("HH:mm:ss");
                                            String ziua = df.format(c);
                                            String ora = doa.format(c);
                                            trimiteSMS();
                                    comunica.plati(ziua, ora, nrinmatriculare.getText().toString(), nume_parcare.getText().toString(), intent.getExtras().getInt("id_parcare"), oraplata.getSelectedItem().toString());
                                }
                            }else
                                {
                                ActivityCompat.requestPermissions(Plata.this, new String[]{Manifest.permission.SEND_SMS}, SEND_SMS_PERMISSION_REQUEST_CODE);
                                }
                        }
                    });
                    builder.setNegativeButton("NU", null);
                    builder.show();
                }
            }
        }
        else
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(Plata.this, R.style.MyAlertDialogStyle);
            builder.setTitle("LIMITA ATINSA");
            builder.setMessage("Sa atins limita de locuri,va rugam incercati mai tarziu");
            builder.setPositiveButton("Da",null);
            builder.show();
        }
    }
});
    }

public boolean trimiteSMS()
{
    if(permisiune(Manifest.permission.SEND_SMS))
    {
        SmsManager sms=SmsManager.getDefault();
        if(masina.getSelectedItem()!=null) {
            sms.sendTextMessage("+40752186688", null, "P" + getIntent().getExtras().get("id_parcare") + "-" + oraplata.getSelectedItem().toString() + "-" + masina.getSelectedItem().toString(), null, null);

        }
        else
        {
            sms.sendTextMessage("+40752186688", null, "P" + getIntent().getExtras().get("id_parcare") + "-" + oraplata.getSelectedItem().toString() + "-" +nrinmatriculare.getText().toString(), null, null);

        }
        Toast.makeText(Plata.this,"Mesaj trimis",Toast.LENGTH_LONG).show();
        return true;
    }
    else
    {
        Toast.makeText(Plata.this,"Mesaj netrimis",Toast.LENGTH_LONG).show();
        return false;
    }
}
public boolean permisiune(String permisiune)
{
    int check= ContextCompat.checkSelfPermission(this,permisiune);
    return (check== PackageManager.PERMISSION_GRANTED);
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
