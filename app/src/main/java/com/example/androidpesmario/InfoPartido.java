package com.example.androidpesmario;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class InfoPartido extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.infopartido);

        TextView local = (TextView) findViewById(R.id.textView);
        TextView visitante = (TextView) findViewById(R.id.textView2);

        String tit;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                tit= null;
            } else {
                tit=extras.getString("Local");
            }
        } else {
            tit =(String) savedInstanceState.getSerializable("Local");
        }
        local.setText(tit);

        String tot;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                tot= null;
            } else {
                tot=extras.getString("Visitante");
            }
        } else {
            tot =(String) savedInstanceState.getSerializable("Visitante");
        }
        visitante.setText(tot);
    }
}
