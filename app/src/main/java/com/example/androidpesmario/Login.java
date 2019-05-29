package com.example.androidpesmario;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
    }
    public void login(View view) {

        new Thread(new Runnable() {
            InputStream stream = null;
            String str = "";
            String result = null;
            Handler handler = new Handler();
            public void run() {

                try {
                     final EditText username = (EditText) findViewById(R.id.usernameInfo);
                     final EditText password = (EditText) findViewById(R.id.password);
                     final TextView passwordtext = (TextView) findViewById(R.id.passwordtext2);

                    String query = String.format("http://192.168.43.238:9000/ApplicationAndroid/login?username=" + username.getText().toString() + "&pass=" + password.getText().toString());
                    URL url = new URL(query);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(10000 );
                    conn.setConnectTimeout(15000 /* milliseconds */);
                    conn.setRequestMethod("GET");
                    conn.setDoInput(true);
                    conn.connect();
                    stream = conn.getInputStream();

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
                                Intent myIntent = new Intent(Login.this, InitialPage.class);
                                myIntent.putExtra("Username", username.getText().toString());
                                startActivity(myIntent);
                            }
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
