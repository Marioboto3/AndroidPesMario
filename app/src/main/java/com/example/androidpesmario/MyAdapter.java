package com.example.androidpesmario;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.provider.Telephony;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<Partido> values;
    Activity activity;

    Singleton singleton = Singleton.getInstance();
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView txtHeader;
        public TextView txtFooter;
        public View layout;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            txtHeader = (TextView) v.findViewById(R.id.firstLine);
            txtFooter = (TextView) v.findViewById(R.id.secondLine);
        }
    }
    public void add(int position, Partido item) {
        values.add(position, item);
        notifyItemInserted(position);
    }
    public void remove(int position) {
        values.remove(position);
        notifyItemRemoved(position);
    }
    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(List<Partido> myDataset, Activity activity) {
        values = myDataset;
        this.activity=activity;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v =
                inflater.inflate(R.layout.row_layout, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }
    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final Partido partido = values.get(position);
        holder.txtHeader.setText(String.valueOf(partido.getLocal())+" vs " + String.valueOf(partido.getVisitante()));
        holder.txtFooter.setText("1: "+ String.valueOf(partido.getCuota_1())+" - X: "+ String.valueOf(partido.getCuota_X())+" - 2: "+ String.valueOf(partido.getCuota_2()));
        holder.txtHeader.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(activity.getApplicationContext(), InfoPartido.class);
                /*myIntent.putExtra("Local",partido.getLocal());
                myIntent.putExtra("Visitante",partido.getVisitante());
                String cuota1 = String.valueOf(partido.getCuota_1());
                myIntent.putExtra("Cuota1",cuota1);
                String cuotaX = String.valueOf(partido.getCuota_X());
                myIntent.putExtra("CuotaX",cuotaX);
                String cuota2 = String.valueOf(partido.getCuota_2());
                myIntent.putExtra("Cuota2",cuota2);*/
                singleton.setPartido(partido);
                activity.startActivity(myIntent);
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return values.size();
    }
}