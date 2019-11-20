package com.example.serviciowebv3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

public class registrarse extends AppCompatActivity {

    EditText txtRegistrarNombre, txtRegistrarClave,txtRegistrarEstado;
    Button btnRegistrar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);


        txtRegistrarNombre =findViewById(R.id.txtRegistrarNombre);
        txtRegistrarClave =findViewById(R.id.txtRegistrarClave);
        txtRegistrarEstado =findViewById(R.id.txtRegistrarEstado);
        btnRegistrar =findViewById(R.id.btnRegistrar);
    }


    public void registrarUsuario(View v){
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {

                String nombre = txtRegistrarNombre.getText().toString().trim();
                String clave = txtRegistrarClave.getText().toString().trim();
                String estado = txtRegistrarEstado.getText().toString().trim();
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String url ="http://172.22.136.17:8080/WebApp06_WebAppSistema/rest/usuarios/add?"
                        + "nombre=" + nombre
                        + "&clave=" + clave
                        + "&estado=" + estado;
                JsonArrayRequest stringRequest = new JsonArrayRequest(Request.Method.PUT,url,null,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                try {
                                    String valor = response.getJSONObject(0).getString("status");

                                    if(valor.equals("true")){
                                        Toast.makeText(getApplicationContext(),"Registro creado con exito",Toast.LENGTH_SHORT).show();
                                        Intent addUsuaurio = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(addUsuaurio);
                                        finish();

                                    }else{
                                        Toast.makeText(getApplicationContext(),"Ocurrio un error",Toast.LENGTH_SHORT).show();
                                    }


                                } catch (JSONException e) {
                                    Toast.makeText(getApplicationContext(),"Ocurrio un error",Toast.LENGTH_SHORT).show();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),"Ocurrio un error",Toast.LENGTH_SHORT).show();
                    }
                })
                {    /**
                 * Passing some request headers
                 */
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", "application/json; charset=utf-8");
                    return headers;
                }
                };
                queue.add(stringRequest);
            };
        });
    }

}
