package com.example.smartparking.Adaptere;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.smartparking.R;
import com.example.smartparking.Obiecte.Recenzie;

public class RecenziiAdapter extends  RecyclerView.Adapter<RecenziiAdapter.ViewHolder> {


    private Context mcontext;
    private Recenzie[] recenzie;
    private TextView numerecenzie,recenziascrisa;
    private RatingBar notaparcare;

    public RecenziiAdapter(Context context, Recenzie[] recenzie)
    {
        mcontext=context;
        this.recenzie=recenzie;
    }


    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recenzielayout,viewGroup,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        numerecenzie.setText(recenzie[i].getNume());
        recenziascrisa.setText(recenzie[i].getRecenzia());
        notaparcare.setRating(recenzie[i].getNota());
    }

    @Override
    public int getItemCount() {
        return recenzie.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            numerecenzie=itemView.findViewById(R.id.numerecenzie);
            recenziascrisa=itemView.findViewById(R.id.recenziascrisa);
            notaparcare=itemView.findViewById(R.id.notaparcare);
        }
    }
}
