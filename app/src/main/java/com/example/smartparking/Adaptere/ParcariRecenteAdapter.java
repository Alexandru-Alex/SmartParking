package com.example.smartparking.Adaptere;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.smartparking.Obiecte.Plati;
import com.example.smartparking.R;

import java.util.ArrayList;

public class ParcariRecenteAdapter extends RecyclerView.Adapter<ParcariRecenteAdapter.ViewHolder> {


    ArrayList<Plati> plati;
    Context context;

    public ParcariRecenteAdapter(ArrayList<Plati> plati)
    {
        this.plati=plati;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.parcaricurente_layout,viewGroup,false);
        context=viewGroup.getContext();
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

            viewHolder.data.setText(plati.get(i).getData());
            viewHolder.ora.setText(plati.get(i).getOra());
            viewHolder.oraplata.setText(plati.get(i).getOraplata());
            viewHolder.nume_parcare.setText(plati.get(i).getNume_parcare());
        }

    @Override
    public int getItemCount() {
        return plati.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView data,ora,nume_parcare,oraplata;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            data=itemView.findViewById(R.id.data);
            ora=itemView.findViewById(R.id.ora);
            nume_parcare=itemView.findViewById(R.id.nume_parcare);
            oraplata=itemView.findViewById(R.id.oraplata);
        }
    }
}
