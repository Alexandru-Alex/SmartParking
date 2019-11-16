package com.example.smartparking.Adaptere;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.smartparking.Obiecte.Dotari;
import com.example.smartparking.R;

public class DotariAdapter extends BaseAdapter {


    private Context mcontext;
    private Dotari dotari;

    public DotariAdapter(Context context, Dotari dotari)
    {
        mcontext=context;
        this.dotari=dotari;
    }

    @Override
    public int getCount() {
        return dotari.getDotare().size();
    }

    @Override
    public Object getItem(int position) {
        return  dotari.getDotare().get(position);
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
            convertView=inflater.inflate(R.layout.dotarilayout,null);
        }
        TextView dotare=(TextView) convertView.findViewById(R.id.dotare );
        dotare.setText(String.valueOf(dotari.getDotare().get(position)));
        return convertView;

    }

}
