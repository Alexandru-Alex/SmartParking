package com.example.smartparking.Adaptere;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.smartparking.Obiecte.Autovehicule;
import com.example.smartparking.BdComunica;
import com.example.smartparking.R;

public class ListaAutovehiculeAdapter extends RecyclerView.Adapter<ListaAutovehiculeAdapter.ViewHolder>  {

    Autovehicule autovehicule;
    Context context;

    public ListaAutovehiculeAdapter( Autovehicule autovehicule)
    {
        this.autovehicule=autovehicule;
    }


    @NonNull
    @Override
    public ListaAutovehiculeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.listautovehicule_layout,viewGroup,false);

        context=viewGroup.getContext();
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return autovehicule.getNrinmatriculare().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView auto;
        LinearLayout linear;
        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            auto=itemView.findViewById(R.id.autotext);
            linear=itemView.findViewById(R.id.linearauto);
        }
    }
    @Override
    public void onBindViewHolder(@NonNull ListaAutovehiculeAdapter.ViewHolder viewHolder, int i) {

        viewHolder.auto.setText(autovehicule.getNrinmatriculare().get(i));
        viewHolder.linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(context,R.style.MyAlertDialogStyle);builder.setTitle("STERGERE NR DE INMATRICULARE");
                builder.setMessage("Sigur doriti sa stergeti numarul: \n"+autovehicule.getNrinmatriculare().get(i));
                builder.setPositiveButton("DA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        BdComunica comunica=new BdComunica();
                         comunica.stergenr(  viewHolder.auto.getText().toString());
                    }
                });
                builder.setNegativeButton("NU", null);
                builder.show();
            }
        });
    }
}
