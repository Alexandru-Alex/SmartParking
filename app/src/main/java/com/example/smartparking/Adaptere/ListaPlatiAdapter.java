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

public class ListaPlatiAdapter extends RecyclerView.Adapter<ListaPlatiAdapter.ViewHolder> {

    ArrayList<Plati> plati;
    Context context;

  public ListaPlatiAdapter(ArrayList<Plati> plati)
  {
      this.plati=plati;

  }
   @NonNull
    @Override
    public ListaPlatiAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

       View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.listaplati_layout,viewGroup,false);
       context=viewGroup.getContext();

       return new ViewHolder(view);
    }
    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView data,ora,pret;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            data=itemView.findViewById(R.id.data);
            ora=itemView.findViewById(R.id.ora);
            pret=itemView.findViewById(R.id.pret);
        }
    }
    @Override
    public void onBindViewHolder(@NonNull ListaPlatiAdapter.ViewHolder viewHolder, int i) {

        viewHolder.data.setText(plati.get(i).getData());
        viewHolder.ora.setText(plati.get(i).getOra());
        viewHolder.pret.setText(plati.get(i).getPret());
    }
    @Override
    public int getItemCount() {
        return plati.size();
    }
}
