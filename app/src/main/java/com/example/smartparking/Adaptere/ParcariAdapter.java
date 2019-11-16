package com.example.smartparking.Adaptere;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartparking.BdComunica;
import com.example.smartparking.Interfete.Detaliiparcare;
import com.example.smartparking.Obiecte.Parcare;
import com.example.smartparking.R;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ParcariAdapter extends RecyclerView.Adapter<ParcariAdapter.ViewHolder>  {

 CircleImageView imagine;
 Parcare[] parcare;
 double [] distante;
 Context context;

    public ParcariAdapter(Parcare [] parcarea,double [] distante)
    {
        this.parcare=parcarea;
        this.distante=distante;
    }
    @NonNull
    @Override
    public ParcariAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.listalayout,viewGroup,false);
        imagine=view.findViewById(R.id.imaginecerc);
        context=view.getContext();

        return  new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder  {

        TextView name,informatie,distanta;
        LinearLayout linear;
        CircleImageView imagine;
        RatingBar rating;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

           name=itemView.findViewById(R.id.nume_parcare);
            linear=itemView.findViewById(R.id.linearparcare);
            imagine=itemView.findViewById(R.id.imaginecerc);
            distanta=itemView.findViewById(R.id.distanta);
            informatie=itemView.findViewById(R.id.informatie);
            rating=itemView.findViewById(R.id.notaparcare);
        }
    }
    @Override
    public void onBindViewHolder(@NonNull ParcariAdapter.ViewHolder viewHolder, int i) {

        viewHolder.name.setText(parcare[i].getNume());
        Picasso.get().load(parcare[i].getPoza()).into(imagine);
        viewHolder.informatie.setText(Integer.toString(parcare[i].getOcupate())+"|"+Integer.toString(parcare[i].getCapacitate()));
        if(distante[i]!=0) {
            viewHolder.distanta.setText(String.format("%.1f", distante[i]) + "Km");
        }
        BdComunica comunica=new BdComunica();
        comunica.medierecenzi(String.valueOf(i+1));
        viewHolder.rating.setRating(parcare[i].getNota());
        viewHolder.linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context=v.getContext();
                Intent intent=new Intent(context, Detaliiparcare.class);
                intent.putExtra("poza",parcare[i].getPoza());
                intent.putExtra("id",parcare[i].getid());
                intent.putExtra("id_pret_parcare",parcare[i].getId_pret());
                intent.putExtra("nume",parcare[i].getNume());
                intent.putExtra("id_program",parcare[i].getId_program());
                intent.putExtra("lat",parcare[i].getLat());
                intent.putExtra("lon",parcare[i].getLon());
                intent.putExtra("id_dotari",parcare[i].getId_dotari());
                intent.putExtra("nota",parcare[i].getNota());
                intent.putExtra("ocupate",parcare[i].getOcupate());
                intent.putExtra("capacitate",parcare[i].getCapacitate());
                context.startActivity(intent);
                Toast.makeText(v.getContext(),"AI APASAT: "+parcare[i].getNume(),Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public int getItemCount() {
        return parcare.length;
    }
}
