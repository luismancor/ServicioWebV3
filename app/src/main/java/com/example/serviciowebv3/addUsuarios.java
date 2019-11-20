package com.example.serviciowebv3;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;

public class addUsuarios extends AppCompatActivity {

    EditText txtNombre, txtClave;
    Button btnGuardar, btnCancelar;
    Spinner cmbEstado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_usuarios);

        txtNombre =findViewById(R.id.txtNombre);
        txtClave =findViewById(R.id.txtClave);
        cmbEstado =findViewById(R.id.cmbEstado);
        btnGuardar =findViewById(R.id.btnGuardar);
        btnCancelar =findViewById(R.id.btnCancelar);

        String[] estados ={"A","X"};
        cmbEstado.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, estados));
    }



    public void mostrarAlerta(String titulo, String mensaje){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(titulo);
        builder.setMessage(mensaje);
        builder.setPositiveButton("Aceptar", null);
        AlertDialog alerta = builder.create();
        alerta.show();
    }
    public void volverUsuario(View v){
        Intent volverUsuaurio = new Intent(this, Usuarios.class);
        startActivity(volverUsuaurio);
        finish();
    }

    public void agregarUsuario(View v){
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {

                String nombre = txtNombre.getText().toString().trim();
                String clave = txtClave.getText().toString().trim();
                String estado = cmbEstado.getSelectedItem().toString();
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
                                        mostrarAlerta("Estado",
                                                "Se agrego registro correctamente");

                                    }else{
                                        mostrarAlerta("Estado",
                                                "Se produjo un error al intentar guardar la informacion");
                                    }
                                } catch (JSONException e) {
                                    mostrarAlerta("Error",
                                            "Error al realizar realizar la consulta");
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mostrarAlerta("Error de conexion",
                                "Compuebe que el servicio este activo");
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
