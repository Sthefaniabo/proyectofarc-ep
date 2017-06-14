package com.example.equipo.farc_ep;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.IdRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import com.example.equipo.farc_ep.Registrar_zona;

import com.example.equipo.farc_ep.Data.Conexion;
import com.example.equipo.farc_ep.Data.LoginContract;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class Registrar_reincorporados extends AppCompatActivity {

    EditText editID;
    EditText editIDZona;
    EditText editNombre;
    EditText editEdad;
    EditText editEscolaridad;
    EditText editNacionalidad;
    Spinner lista;
    String[]datosLista = {"Preescolar", "Basica Primaria", "Basica Secundaria", "Educacion Media"};
    RadioGroup radiogroup;
    Conexion con;
    SQLiteDatabase db;
    String genero="";
    Spinner spzonas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_reincorporados);

        editID= (EditText) findViewById(R.id.editText12);
        //editIDZona = (EditText) findViewById(R.id.editText13);
        editNombre= (EditText) findViewById(R.id.editText14);
        editEdad = (EditText) findViewById(R.id.editText15);
        editEscolaridad = (EditText) findViewById(R.id.editText16);
        editNacionalidad = (EditText) findViewById(R.id.editText17);

        spzonas = (Spinner) findViewById(R.id.spinner3);
        getZonas();
        spzonas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getApplicationContext(),"ID  de la zona "+spzonas.getSelectedItemId(),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //visualizando su identificador único, nombre de usuario, genero, fecha de nacimiento, e-mail y su última fecha de inicio exitoso. g

        con= new Conexion(this,"proyecto_final",null,1);
        db = con.getWritableDatabase();

        lista =(Spinner)findViewById(R.id.spinner4);
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,datosLista);
        lista.setAdapter(adaptador);

        lista.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                //Toast to = Toast.makeText(getApplicationContext(), datosLista[position], Toast.LENGTH_LONG);
                String seleccion = lista.getSelectedItem().toString();
                //String selected = parent.getItemAtPosition(position).toString();
                editEscolaridad = (EditText) findViewById(R.id.editText16);
                editEscolaridad.setText( seleccion);
                //textView7 =  setText(selected);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        radiogroup = (RadioGroup) findViewById(R.id.radiogroup);
        //radiogroup = (RadioGroup) findViewById(R.id.radioButton5);
        radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if(radiogroup.getCheckedRadioButtonId()==(R.id.radioButton2)){
                    //Toast.makeText(getApplicationContext(),"Seleccionaste M",Toast.LENGTH_LONG).show();
                    genero="M";
                }else{
                    //Toast.makeText(getApplicationContext(),"Seleccionaste F",Toast.LENGTH_LONG).show();
                    genero="F";
                }
            }
        });


        verifyStoragePermissions(this);

        Button btnRegistrar=(Button)findViewById(R.id.button10);
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Registro_reincor();
            }
        });
    }

    public  void  Registro_reincor() {

        // INSERTAR DATOS EN BD
            String ID = editID.getText().toString();
            String ID_Zona = spzonas.getSelectedItemId()+"";
            String Nombrerein = editNombre.getText().toString();
            String Edad = editEdad.getText().toString();
            String Nivel = editEscolaridad.getText().toString();
            String Nacionalidad = editNacionalidad.getText().toString();

            ContentValues valores = new ContentValues();
            valores.put(com.example.equipo.farc_ep.Data.LoginContract.LoginEntry.NIVEL, Nivel);

            valores.put(com.example.equipo.farc_ep.Data.LoginContract.LoginEntry.ID, ID);
            //valores.put(LoginContract.LoginEntry.EMAIL, Email );
            valores.put(LoginContract.LoginEntry.IDZONA, ID_Zona);
            valores.put(LoginContract.LoginEntry.NOMBREREIN, Nombrerein);
            valores.put(LoginContract.LoginEntry.EDAD, Edad);
            valores.put(LoginContract.LoginEntry.GENERO2, genero);
            valores.put(LoginContract.LoginEntry.NACIONALIDAD, Nacionalidad);
            db.insert(com.example.equipo.farc_ep.Data.LoginContract.LoginEntry.TABLE_NAME2, null, valores);

            Intent guardar = new Intent(Registrar_reincorporados.this, Menu_Admin.class);
            guardar.addFlags(guardar.FLAG_ACTIVITY_CLEAR_TOP | guardar.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(guardar);

    }

    public void getZonas(){
        con= new Conexion(this,"proyecto_final",null,1);
        db = con.getWritableDatabase();


        //Creando Adaptador para GenreSpinner
        SimpleCursorAdapter spa = new SimpleCursorAdapter(this,
                android.R.layout.simple_spinner_item,
                db.rawQuery(" SELECT "+LoginContract.LoginEntry.NUMERO+" AS _id,"
                                + LoginContract.LoginEntry.NOMBREZONA+ " FROM "+LoginContract.LoginEntry.TABLE_NAME1, null),
                new String[]{LoginContract.LoginEntry.NOMBREZONA},
                new int[]{android.R.id.text1},
                SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        spzonas.setAdapter(spa);
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
