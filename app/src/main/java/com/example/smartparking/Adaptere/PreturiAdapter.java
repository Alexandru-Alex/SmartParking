package com.example.smartparking.Adaptere;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.smartparking.Obiecte.Preturi;
import com.example.smartparking.R;

public class PreturiAdapter extends BaseAdapter {

    private Context mcontext;
    private Preturi preturi;

    public PreturiAdapter(Context context,Preturi preturi)
    {
    mcontext=context;
    this.preturi=preturi;
    }


    @Override
    public int getCount() {
        return preturi.getPret().size();
    }

    @Override
    public Object getItem(int position) {
        return preturi.getPret().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

    if(convertView==null)
    {
        LayoutInflater inflater=(LayoutInflater)mcontext.getSystemService(mcontext.LAYOUT_INFLATER_SERVICE);
        convertView=inflater.inflate(R.layout.preturilayout,null);
    }
    TextView pret=(TextView) convertView.findViewById(R.id.pret);
    TextView timp=(TextView) convertView.findViewById(R.id.timp);
    timp.setText(preturi.getTimp().get(position));
    pret.setText(String.valueOf(preturi.getPret().get(position))+" RON");

        return convertView;
    }

}
