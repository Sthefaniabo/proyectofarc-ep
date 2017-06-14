package com.example.equipo.farc_ep;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.equipo.farc_ep.Data.Conexion;
import com.example.equipo.farc_ep.Data.LoginContract;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class Registrar_zona extends AppCompatActivity {

    EditText editNumero;
    EditText editAdministrador;
    EditText editDepartamento;
    EditText editNombre;
    EditText editCampamentos;
    Spinner lista;
    String[]datosLista = {"Antioquia", "Arauca", "Caqueta", "Cauca", "Cesar", "Guaviare", "Meta", "Nariño", "Norte de santander", "Putumayo", "Tolima", "Vichada"};
    Conexion con;
    SQLiteDatabase db;
    Spinner spadmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_zona);
        editNumero= (EditText) findViewById(R.id.editText);
        editAdministrador = (EditText) findViewById(R.id.editText2);
        editDepartamento = (EditText) findViewById(R.id.editText3);
        editNombre= (EditText) findViewById(R.id.editText5);
        editCampamentos= (EditText) findViewById(R.id.editText7);

        spadmin = (Spinner) findViewById(R.id.spinner7);
        getZonas();
        spadmin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getApplicationContext(),"ID  de la zona "+spzonas.getSelectedItemId(),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        lista =(Spinner)findViewById(R.id.spinner);
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,datosLista);
        lista.setAdapter(adaptador);

        lista.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                //Toast to = Toast.makeText(getApplicationContext(), datosLista[position], Toast.LENGTH_LONG);
                String seleccion = lista.getSelectedItem().toString();
                //String selected = parent.getItemAtPosition(position).toString();
                editDepartamento = (EditText) findViewById(R.id.editText3);
                editDepartamento.setText( seleccion);
                //textView7 =  setText(selected);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        con= new Conexion(this,"proyecto_final",null,1);
        db = con.getWritableDatabase();

        //verifyStoragePermissions(this);


        Button btnRegistrar=(Button)findViewById(R.id.button2);
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registro_zona();
            }
        });

    }

    public void registro_zona(){

        // INSERTAR DATOS EN BD
        String Numero = editNumero.getText().toString();
        String Administrador = spadmin.getSelectedItemId()+"";
        String Departamento = editDepartamento.getText().toString();
        String Nombre = editNombre.getText().toString();
        String Campamentos = editCampamentos.getText().toString();

        ContentValues valores = new ContentValues();

        valores.put(com.example.equipo.farc_ep.Data.LoginContract.LoginEntry.NUMERO, Numero);
        valores.put(LoginContract.LoginEntry.ADMINISTRADOR, Administrador);
        valores.put(LoginContract.LoginEntry.DEPARTAMENTO, Departamento);
        valores.put(LoginContract.LoginEntry.NOMBREZONA, Nombre);
        valores.put(LoginContract.LoginEntry.CAMPAMENTOS, Campamentos);
        db.insert(com.example.equipo.farc_ep.Data.LoginContract.LoginEntry.TABLE_NAME1, null, valores);


        Intent guardar = new Intent(Registrar_zona.this, Menu_Admin.class);
        guardar.addFlags(guardar.FLAG_ACTIVITY_CLEAR_TOP | guardar.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(guardar);

    }

    public void getZonas(){
        con= new Conexion(this,"proyecto_final",null,1);
        db = con.getWritableDatabase();


        //Creando Adaptador para GenreSpinner
        SimpleCursorAdapter spa = new SimpleCursorAdapter(this,
                android.R.layout.simple_spinner_item,
                db.rawQuery(" SELECT "+LoginContract.LoginEntry.USERID+" AS _id,"
                        + LoginContract.LoginEntry.NOMBRE+ " FROM "+LoginContract.LoginEntry.TABLE_NAME, null),
                new String[]{LoginContract.LoginEntry.NOMBRE},
                new int[]{android.R.id.text1},
                SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        spadmin.setAdapter(spa);
    }

    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    /**
     * Checks if the app has permission to write to device storage
     *
     * If the app does not has permission then the user will be prompted to grant permissions
     *
     * @param activity
     */
   public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }
    protected void onDestroy() {
        super.onDestroy();
        try {
            con.BD_backup();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
