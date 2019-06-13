package com.example.androidpesmario;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

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

        local.setText(singleton.getPartido().getLocal());

        visitante.setText(singleton.getPartido().getVisitante());

        cuota1.setText(Double.toString(singleton.getPartido().getCuota_1()));

        cuotaX.setText(Double.toString(singleton.getPartido().getCuota_X()));

        cuota2.setText(Double.toString(singleton.getPartido().getCuota_2()));

        cuota1.setEnabled(true);
        cuotaX.setEnabled(true);
        cuota2.setEnabled(true);

        cuota1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cuota2.setEnabled(false);
                cuotaX.setEnabled(false);
                cuotaApostada = Double.parseDouble(cuota1.getText().toString());
            }
        });
        cuotaX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cuota2.setEnabled(false);
                cuota1.setEnabled(false);
                cuotaApostada = Double.parseDouble(cuotaX.getText().toString());
            }
        });
        cuota2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cuota1.setEnabled(false);
                cuotaX.setEnabled(false);
                cuotaApostada = Double.parseDouble(cuota2.getText().toString());
            }
        });
        ganancias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    calculo(cuotaApostada, Double.parseDouble(importe.getText().toString()));
                } catch (Exception e) {
                    guanys.setText("Clica una cuota o pon un importe.");
                }
            }
        });
        apostar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    InputStream stream = null;
                    String str = "";
                    String result = null;
                    Handler handler = new Handler();
                    public void run() {

                        try {
                            String query = String.format("http://192.168.43.238:9000/ApplicationAndroid/createBet");
                            URL url = new URL(query);
                            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                            conn.setReadTimeout(10000 );
                            conn.setConnectTimeout(15000 /* milliseconds */);
                            conn.setRequestMethod("POST");
                            conn.setDoInput(true);
                            conn.connect();
                            stream = conn.getInputStream();

                            JSONObject postDataParams = new JSONObject();
                            postDataParams.put("username", singleton.getUsername());
                            postDataParams.put("importe",importe.toString() );
                            postDataParams.put("idPartido", singleton.getPartido().getIdPartido());
                            postDataParams.put("pronostico", cuotaApostada.toString());

                            Log.e("params", postDataParams.toString());

                            BufferedReader reader = null;

                            StringBuilder sb = new StringBuilder();

                            reader = new BufferedReader(new InputStreamReader(stream));

                            String line = null;
                            while ((line = reader.readLine()) != null) {
                                sb.append(line);
                            }
                            result = sb.toString();

                            // Mostrar resultat en el quadre de text.
                            // Codi incorrecte
                            Log.i("message","Codigo incorrecto");

                            //Codi correcte
                            handler.post(new Runnable() {
                                public void run() {
                                    if (result.equals("200")) {
                                        Intent myIntent = new Intent(InfoPartido.this, InitialPage.class);
                                        startActivity(myIntent);
                                    }
                                    else
                                    {                    guanys.setText("Clica una cuota o pon un importe.");
                                    }
                                }
                            });

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
    }

    public void calculo(double cuota, double importe) {
        Double resultado = cuota * importe;
        guanys.setText(resultado.toString());
    }
}

