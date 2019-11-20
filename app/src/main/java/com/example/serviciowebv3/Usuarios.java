package com.example.serviciowebv3;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class Usuarios extends AppCompatActivity {

    EditText txtBuscar;
    Button btnBuscar, btnCargar, btnSalir, btnAgregar;
    ListView lstLista;
    List<String> elementosLista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuarios);

        txtBuscar = findViewById(R.id.txtBuscar);
        btnBuscar = findViewById(R.id.btnBuscar);
        btnCargar = findViewById(R.id.btnCargar);
        btnSalir = findViewById(R.id.btnSalir);
        btnAgregar = findViewById(R.id.btnAgregar);
        lstLista = findViewById(R.id.lstLista);



    }

    public void cargarLista(View v){
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                elementosLista = new ArrayList<String>();
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String url ="http://172.22.136.17:8080/WebApp06_WebAppSistema/rest/usuarios/list";
                JsonArrayRequest stringRequest = new JsonArrayRequest(url,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                try {
                                    for (int i=0; i<response.length();i++){
                                        String codigo = response.getJSONObject(i).getString("codigo");
                                        String nombre = response.getJSONObject(i).getString("nombre");
                                        String clave = response.getJSONObject(i).getString("clave");
                                        String estado = response.getJSONObject(i).getString("estado");
                                        String data = "CODIGO: \u0009" + codigo + "\n" +
                                                "NOMBRE: \u0009" + nombre + "\n" +
                                                "CLAVE: \u0009" + clave + "\n" +
                                                "ESTADO: \u0009" + estado;
                                        elementosLista.add(data);
                                    }
                                    ArrayAdapter<String> adaptador =
                                            new ArrayAdapter<String>(getApplicationContext(),
                                                    android.R.layout.simple_list_item_1, elementosLista);
                                    lstLista.setAdapter(adaptador);
                                    mostrarAlerta("Estado de Carga",
                                            "Se cargaron " + elementosLista.size() + " elementos" );
                                } catch (JSONException e) {
                                    mostrarAlerta("Error de JSON",
                                            "Se produjo un error al intentar leer la data recibida");
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mostrarAlerta("Error de conexion",
                                "Compuebe que el servicio este activo");
                    }
                });
                queue.add(stringRequest);
            };
        });
    }


    public void buscarUsuario(View v){
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                elementosLista = new ArrayList<String>();
                String buscar = txtBuscar.getText().toString().trim();
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String url ="http://172.22.136.17:8080/WebApp06_WebAppSistema/rest/usuarios/consult?nombre=" + buscar;
                JsonArrayRequest stringRequest = new JsonArrayRequest(url,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                try {
                                    for (int i=0; i<response.length();i++){
                                        String codigo = response.getJSONObject(i).getString("codigo");
                                        String nombre = response.getJSONObject(i).getString("nombre");
                                        String clave = response.getJSONObject(i).getString("clave");
                                        String estado = response.getJSONObject(i).getString("estado");
                                        String data = "CODIGO: \u0009" + codigo + "\n" +
                                                "NOMBRE: \u0009" + nombre + "\n" +
                                                "CLAVE: \u0009" + clave + "\n" +
                                                "ESTADO: \u0009" + estado;
                                        elementosLista.add(data);
                                    }
                                    ArrayAdapter<String> adaptador =
                                            new ArrayAdapter<String>(getApplicationContext(),
                                                    android.R.layout.simple_list_item_1, elementosLista);
                                    lstLista.setAdapter(adaptador);
                                    mostrarAlerta("Estado de Carga",
                                            "Se cargaron " + elementosLista.size() + " elementos" );
                                } catch (JSONException e) {
                                    mostrarAlerta("Error de JSON",
                                            "Se produjo un error al intentar leer la data recibida");
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mostrarAlerta("Error de conexion",
                                "Compuebe que el servicio este activo");
                    }
                });
                queue.add(stringRequest);
            };
        });
    }

    public void Salir(View v){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Salir");
        builder.setMessage("¿Desea realmente salir de la aplicacion?");
        builder.setPositiveButton("Salir", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent mostrarLogin = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(mostrarLogin);
                finish();
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });
        AlertDialog alerta = builder.create();
        alerta.show();
    }



    public void mostrarAlerta(String titulo, String mensaje){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(titulo);
        builder.setMessage(mensaje);
        builder.setPositiveButton("Aceptar", null);
        AlertDialog alerta = builder.create();
        alerta.show();
    }

    public void addUsuario(View v){
        Intent addUsuaurio = new Intent(this, addUsuarios.class);
        startActivity(addUsuaurio);
        finish();
    }
}
