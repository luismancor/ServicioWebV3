package com.example.serviciowebv3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

public class MainActivity extends AppCompatActivity {

    EditText txtUsuario,txtClave;
    Button btnLogear, btnRegistrar,btnSalir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        txtUsuario = findViewById(R.id.txtUsuario);
        txtClave=findViewById(R.id.txtClave);
        btnLogear=findViewById(R.id.btnLogear);
        btnRegistrar=findViewById(R.id.btnRegistrar);
        btnSalir=findViewById(R.id.btnSalir);
    }

    public void Logear(View v){
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                String nombre = txtUsuario.getText().toString().trim();
                String clave = txtClave.getText().toString().trim();
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String url ="http://192.168.0.27:8080/WebApp06_WebAppSistema/rest/usuarios/login?";
                url = url + "nombre=" + nombre + "&clave=" + clave;

                JsonArrayRequest stringRequest = new JsonArrayRequest(url,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                try {
                                    String valor = response.getJSONObject(0).getString("login");
                                    if(valor.equals("true")){
                                        Intent llamar = new Intent(getApplicationContext(),Usuarios.class);
                                        startActivity(llamar);
                                        finish();
                                    }else{
                                        Toast.makeText(getApplicationContext(),"Credenciales invalidas",
                                                Toast.LENGTH_LONG).show();
                                    }
                                } catch (JSONException e) {
                                    Toast.makeText(getApplicationContext(),
                                            "Error en la data recibida",
                                            Toast.LENGTH_LONG).show();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),
                                "Compruebe que tiene acceso a internet",
                                Toast.LENGTH_LONG).show();
                    }
                });
                queue.add(stringRequest);
            };
        });
    }
}
