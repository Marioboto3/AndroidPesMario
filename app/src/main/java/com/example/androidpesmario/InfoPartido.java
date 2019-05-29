package com.example.androidpesmario;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InfoPartido extends AppCompatActivity {

    Apuesta apuesta;
    String pronostico;
     Double cuotaApostada;
     TextView guanys;

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://192.168.43.238:9000/ApplicationAndroid/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    API api = retrofit.create(API.class);

    Singleton singleton = Singleton.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.infopartido);
        final TextView local = (TextView) findViewById(R.id.local);
        final TextView visitante = (TextView) findViewById(R.id.visitante);
        final Button cuota1 = (Button) findViewById(R.id.cuota_1);
        final Button cuotaX = (Button) findViewById(R.id.cuota_X);
        final Button cuota2 = (Button) findViewById(R.id.cuota_2);
        final EditText importe = (EditText) findViewById(R.id.importe);
        final Button ganancias = (Button) findViewById(R.id.ganancias);
        final Button apostar = (Button) findViewById(R.id.apostar);
        guanys = (TextView) findViewById(R.id.guanysview);

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
        String pep;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                pep= null;
            } else {
                pep=extras.getString("Cuota1");
            }
        } else {
            pep = (String) savedInstanceState.getSerializable("Cuota1");
        }
        cuota1.setText(pep);

        String com;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                com= null;
            } else {
                com=extras.getString("CuotaX");
            }
        } else {
            com =(String) savedInstanceState.getSerializable("CuotaX");
        }
        cuotaX.setText(com);
        String plas;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                plas= null;
            } else {
                plas=extras.getString("Cuota2");
            }
        } else {
            plas =(String) savedInstanceState.getSerializable("Cuota2");
        }
        cuota2.setText(plas);

        cuota1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cuota2.setEnabled(false);
                cuotaX.setEnabled(false);
                cuotaApostada= Double.parseDouble(cuota1.getText().toString());
            }
        });
        cuotaX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cuota2.setEnabled(false);
                cuota1.setEnabled(false);
                cuotaApostada= Double.parseDouble(cuotaX.getText().toString());
            }
        });
        cuota2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cuota1.setEnabled(false);
                cuotaX.setEnabled(false);
                cuotaApostada= Double.parseDouble(cuota2.getText().toString());
            }
        });
        ganancias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    calculo(cuotaApostada, Double.parseDouble(importe.getText().toString()));
                }
                catch (Exception e)
                {
                    guanys.setText("Clica una cuota o pon un importe.");
                }
            }
            });
        apostar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    apuesta = new Apuesta(1, pronostico, importe.getText().toString(), "mario");
                    Call<String> call = api.createBet(apuesta);
                    call.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            if (!response.isSuccessful()) {
                            }
                            guanys.setText(response.body());
                        }
                        @Override
                        public void onFailure(Call<String> call, Throwable t){
                        }
                    });
                }
                catch (Exception e)
                {}
            }
            });
    }
    public void calculo(double cuota, double importe)
    {
        Double resultado=cuota*importe;
        guanys.setText(resultado.toString());
    }

}
