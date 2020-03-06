package com.example.a3_volodymyr;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class listDatos extends ArrayAdapter<DatosParaFirebase> {

    private Activity act;
    private List<DatosParaFirebase> dat;



    public listDatos(@NonNull Activity context, @NonNull List<DatosParaFirebase> objects) {
        super(context, R.layout.activity_read_fire_base, objects);
        this.act=context;
        this.dat=objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = act.getLayoutInflater();

        View listview = inflater.inflate(R.layout.activity_read_fire_base,null,true);

        TextView nombre = listview.findViewById(R.id.textView6);
        TextView fecha = listview.findViewById(R.id.textView7);
        TextView tiempo = listview.findViewById(R.id.textView9);

        DatosParaFirebase da = dat.get(position);
        nombre.setText(da.getMetodo());
        fecha.setText(da.getFechaYHora());

        return listview;
    }
}
