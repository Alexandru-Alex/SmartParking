package com.example.smartparking.Adaptere;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.smartparking.Obiecte.Program;
import com.example.smartparking.R;


public class ProgramAdapter extends BaseAdapter {

    private Context mcontext;
    private Program program;

  public  ProgramAdapter(Context context,Program program)
    {
        mcontext=context;
        this.program=program;
    }

    @Override
    public int getCount() {
        return program.getZi().size();
    }

    @Override
    public Object getItem(int position) {
        return program.getZi().get(position);
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
        TextView timp=(TextView) convertView.findViewById(R.id.pret);
        TextView zi=(TextView) convertView.findViewById(R.id.timp);
        timp.setText(program.getOre().get(position));
        zi.setText(String.valueOf(program.getZi().get(position)));

        return convertView;
    }

}
