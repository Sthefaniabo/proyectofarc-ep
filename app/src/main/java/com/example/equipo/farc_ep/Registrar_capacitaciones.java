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
import android.widget.Toast;

import com.example.equipo.farc_ep.Data.Conexion;
import com.example.equipo.farc_ep.Data.LoginContract;

import java.io.IOException;

public class Registrar_capacitaciones extends AppCompatActivity {

    EditText editIDcapa;
    EditText editIDreincor;
    EditText editTipo;
    Spinner lista;
    String[] datosLista = {"Preescolar", "Basica Primaria", "Basica Secundaria", "Educacion Media", "Acompañamiento psicosocial"};
    Conexion con;
    SQLiteDatabase db;
    Spinner spidreincor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_capacitaciones);

        editIDcapa = (EditText) findViewById(R.id.editText);
        editIDreincor = (EditText) findViewById(R.id.editText2);

        spidreincor = (Spinner) findViewById(R.id.spinner5);
        getReincor();
        spidreincor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getApplicationContext(), "ID  de la zona " + spidreincor.getSelectedItemId(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        con = new Conexion(this, "proyecto_final", null, 1);
        db = con.getWritableDatabase();

        lista = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, datosLista);
        lista.setAdapter(adaptador);

        lista.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                //Toast to = Toast.makeText(getApplicationContext(), datosLista[position], Toast.LENGTH_LONG);
                String seleccion = lista.getSelectedItem().toString();
                //String selected = parent.getItemAtPosition(position).toString();
                editTipo = (EditText) findViewById(R.id.editText3);
                editTipo.setText(seleccion);
                //textView7 =  setText(selected);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        verifyStoragePermissions(this);

        Button btnRegistrar = (Button) findViewById(R.id.button2);
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Registro_capacitaciones();
            }
        });
    }

    public void Registro_capacitaciones() {

        // INSERTAR DATOS EN BD
        String IDcapa = editIDcapa.getText().toString();
        String ID_reincorpo = spidreincor.getSelectedItemId() + "";
        String Tipo = editTipo.getText().toString();

        ContentValues valores = new ContentValues();
        valores.put(com.example.equipo.farc_ep.Data.LoginContract.LoginEntry.TIPO, Tipo);

        valores.put(com.example.equipo.farc_ep.Data.LoginContract.LoginEntry.IDCAPA, IDcapa);
        //valores.put(LoginContract.LoginEntry.EMAIL, Email );
        valores.put(LoginContract.LoginEntry.IDREINCOR, ID_reincorpo);

        db.insert(com.example.equipo.farc_ep.Data.LoginContract.LoginEntry.TABLE_NAME3, null, valores);

        Intent guardar = new Intent(Registrar_capacitaciones.this, Menu_Admin.class);
        guardar.addFlags(guardar.FLAG_ACTIVITY_CLEAR_TOP | guardar.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(guardar);


    }

    public void getReincor() {
        con = new Conexion(this, "proyecto_final", null, 1);
        db = con.getWritableDatabase();


        //Creando Adaptador para GenreSpinner
        SimpleCursorAdapter spa = new SimpleCursorAdapter(this,
                android.R.layout.simple_spinner_item,
                db.rawQuery(" SELECT " + LoginContract.LoginEntry.ID + " AS _id,"
                        + LoginContract.LoginEntry.NOMBREREIN + " FROM " + LoginContract.LoginEntry.TABLE_NAME2, null),
                new String[]{LoginContract.LoginEntry.NOMBREREIN},
                new int[]{android.R.id.text1},
                SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        spidreincor.setAdapter(spa);
    }

    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    /**
     * Checks if the app has permission to write to device storage
     * <p>
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