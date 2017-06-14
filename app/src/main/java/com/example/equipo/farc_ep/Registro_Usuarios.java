package com.example.equipo.farc_ep;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.annotation.IdRes;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.equipo.farc_ep.Data.Conexion;
import com.example.equipo.farc_ep.Data.LoginContract;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;

public class Registro_Usuarios extends AppCompatActivity {

    EditText editNombre;
    EditText editContra;
    EditText editContra2;
    EditText editEmail;
    Spinner lista;
    String[]datosLista = {"@hotmail.com", "@gmail.com", "@outlook.es"};
    RadioGroup radiogroup;
    TextView textFecha;
    ImageButton imageButton3;
    int year;
    int month;
    int day;
    static final int DATE_DIALOG_ID = 999;
    CheckBox checkBox2;
    Conexion con;
    SQLiteDatabase db;
    String genero="";
    Boolean permisos=false;
    String FechaHoy;
    ListView listaUsuarios;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro__usuarios);

        editNombre= (EditText) findViewById(R.id.editNombre);
        editContra = (EditText) findViewById(R.id.editContra);
        editContra2= (EditText) findViewById(R.id.editContra2);
        //visualizando su identificador único, nombre de usuario, genero, fecha de nacimiento, e-mail y su última fecha de inicio exitoso. g

        con= new Conexion(this,"proyecto_final",null,1);
        db = con.getWritableDatabase();
        //db.delete( LoginContract.LoginEntry.TABLE_NAME,LoginContract.LoginEntry.USERID +"=3",null);
        final Calendar c1 = Calendar.getInstance();
        year = c1.get(Calendar.YEAR);
        month = c1.get(Calendar.MONTH);
        day = c1.get(Calendar.DAY_OF_MONTH);

        //Bundle data = getIntent().getExtras().getBundle("datos");

        // set current date into textview

        ContentValues valores = new ContentValues();
        valores.put(LoginContract.LoginEntry.FECHA_INI,(month + 1)+("-")+(day-1)+("-")+(year));
        //db.update(LoginContract.LoginEntry.TABLE_NAME, valores, LoginContract.LoginEntry.USERID+"="+data.getString("user"),null);

        Cursor cur = db.rawQuery(" SELECT "+LoginContract.LoginEntry.USERID+","+ LoginContract.LoginEntry.NOMBRE+","+ LoginContract.LoginEntry.GENERO+","+
                LoginContract.LoginEntry.FECHA_NACI+","+ LoginContract.LoginEntry.EMAIL+","+LoginContract.LoginEntry.FECHA_INI+ " FROM "+
                LoginContract.LoginEntry.TABLE_NAME, null);


        String [] datos=new String[cur.getCount()];
        int i=0;
        while (cur.moveToNext()){
            datos[i]=cur.getString(0) + "-" + cur.getString(1)+"-"+cur.getString(3)+"-"+cur.getString(4)+"-"+cur.getString(5);

            i++;
        }


        ArrayAdapter<String> adaptadorUsuarios;

        listaUsuarios = (ListView)findViewById(R.id.listView);

        adaptadorUsuarios = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,datos);

        listaUsuarios.setAdapter(adaptadorUsuarios);


        lista =(Spinner)findViewById(R.id.spinner2);
        ArrayAdapter<String>adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,datosLista);
        lista.setAdapter(adaptador);

        lista.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                //Toast to = Toast.makeText(getApplicationContext(), datosLista[position], Toast.LENGTH_LONG);
                String seleccion = lista.getSelectedItem().toString();
                //String selected = parent.getItemAtPosition(position).toString();
                editEmail = (EditText) findViewById(R.id.editEmail);
                editEmail.setText( seleccion);
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
                if(radiogroup.getCheckedRadioButtonId()==(R.id.radioButton6)){
                    //Toast.makeText(getApplicationContext(),"Seleccionaste M",Toast.LENGTH_LONG).show();
                    genero="M";
                }else{
                    //Toast.makeText(getApplicationContext(),"Seleccionaste F",Toast.LENGTH_LONG).show();
                    genero="F";
                }
            }
        });


        //radiogroup.setOnClickListener(this);

        textFecha = (TextView) findViewById(R.id.textFecha);
        setCurrentDateOnView();
        addListenerOnButton();

        checkBox2 = (CheckBox) findViewById(R.id.checkBox2);
        checkBox2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkBox2.isChecked()==true){
                    permisos=true;
                    //Toast.makeText(getApplicationContext(),"Tiene permisos de administrador",Toast.LENGTH_LONG).show();
                }
            }
        });

        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        // set current date into textview
        textFecha.setText(new StringBuilder()
                // Month is 0 based, just add 1
                .append(month + 1).append("-").append(day).append("-")
                .append(year).append(" "));
        month=month+1;
        FechaHoy = Integer.toString(day) + "-"+ Integer.toString(month)+ "-"+Integer.toString(year);
        Toast.makeText(this, "fecha hoy:  " + FechaHoy, Toast.LENGTH_SHORT).show();


        verifyStoragePermissions(this);



        Button btnRegistrar=(Button)findViewById(R.id.button6);
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Registro();
            }
        });

        Button btnSalir =(Button)findViewById(R.id.button5);
        btnSalir.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Salir();
            }
        }));

    }

    public void Registro() {

        if (editContra.getText().toString().equals(editContra2.getText().toString())) {
            //Toast is the pop up message
            Toast.makeText(getApplicationContext(), "Las contraseñas coinciden!",
                    Toast.LENGTH_LONG).show();

            // INSERTAR DATOS EN BD
            final String Nombre = editNombre.getText().toString();
            final String Contraseña = editContra.getText().toString();
            final String Email = editEmail.getText().toString();

            ContentValues valores = new ContentValues();
            valores.put(com.example.equipo.farc_ep.Data.LoginContract.LoginEntry.EMAIL, Email);
            try {
                valores.put(com.example.equipo.farc_ep.Data.LoginContract.LoginEntry.PASSWD, this.toMd5(Contraseña));
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            valores.put(com.example.equipo.farc_ep.Data.LoginContract.LoginEntry.NOMBRE, Nombre);
            //valores.put(LoginContract.LoginEntry.EMAIL, Email );
            valores.put(LoginContract.LoginEntry.GENERO, genero);
            valores.put(LoginContract.LoginEntry.FECHA_NACI, textFecha.getText().toString());
            valores.put(LoginContract.LoginEntry.FECHA_INI, FechaHoy);
            valores.put(LoginContract.LoginEntry.PERMISOS, permisos);
            db.insert(com.example.equipo.farc_ep.Data.LoginContract.LoginEntry.TABLE_NAME, null, valores);

            /*Thread trConection = new Thread(){
                int error=0;
                public void run() {
                    URL url = null;
                    String linea = "";
                    int respuesta = 0;
                    StringBuilder resul = null;
                    try {

                        //URL url1="http://192.168.0.11/WebServicesFarc-Ep"; //DIRECCION BASE, EN ESTE CASO LOCAMENTE, PODRÃŒA SER UN SERVER
                        url = new URL("http://192.168.0.11/WebServicesFarc-Ep/InsertarUsuarios.php?"+
                                "email="+Email+
                                "&passwd="+toMd5(Contraseña)+
                                "&nombre="+Nombre+
                                "&genero="+genero+
                                "&fecha_naci="+textFecha.getText().toString()+
                                "&fecha_ini="+FechaHoy+
                                "&permisos="+permisos);
                        String url2 = url.toString();
                        HttpURLConnection conection = (HttpURLConnection) url.openConnection();
                        respuesta = conection.getResponseCode();

                        resul = new StringBuilder();

                        if (respuesta == HttpURLConnection.HTTP_OK) {
                            InputStream in = new BufferedInputStream(conection.getInputStream());
                            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                            while ((linea = reader.readLine()) != null) {
                                resul.append(linea);
                            }
                        }
                    } catch (Exception e) {
                        error=1;
                    }
                    if(resul!=null) {
                        System.out.println(resul.toString());

                        if (resul.equals("\"00000\"")) {
                            error=2;
                        } else {
                            error=3;
                        }
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(error==2){
                                Toast.makeText(getApplicationContext(), "guardado correctamente",
                                        Toast.LENGTH_LONG).show();
                            }else{

                                    Toast.makeText(getApplicationContext(), "error al guardar",
                                            Toast.LENGTH_LONG).show();

                            }

                        }
                        });
                }


            };
            trConection.start();*/

            /*try{JSONArray json=new JSONArray(resul.toString());
                for(int i=0; i<json.length();i++){
                    editNombre.setText(json.getJSONObject(i).getString("fechanac"));
                    editContra.setText(json.getJSONObject(i).getString("nombre"));
                    editEmail.setText(json.getJSONObject(i).getString("apellido"));
                    textFecha.setText(json.getJSONObject(i).getString("apellido"));
                    FechaHoy.toString(json.getJSONObject(i));
                }
            }catch(Exception e){}*/


        } else {
            //Toast is the pop up message
            Toast.makeText(getApplicationContext(), "Contraseñas no coinciden!",
                    Toast.LENGTH_LONG).show();

        }

    }

    public void Salir (){

        Intent salir = new Intent(Registro_Usuarios.this, Sing.class);
        salir.addFlags(salir.FLAG_ACTIVITY_CLEAR_TOP | salir.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(salir);


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

    public String toMd5(String texto) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(texto.getBytes());
        byte[] digest = md.digest();
        StringBuffer sb = new StringBuffer();
        for (byte b : digest) {
            sb.append(String.format("%02x", b & 0xff));
        }
        //ahora sb.toString(); es la contraseña cifrada
        return  sb.toString();
    }


    // display current date
    public void setCurrentDateOnView() {

        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        // set current date into textview
        textFecha.setText(new StringBuilder()
                // Month is 0 based, just add 1
                .append(month + 1).append("-").append(day).append("-")
                .append(year).append(" "));
    }

    public void addListenerOnButton() {

        imageButton3 = (ImageButton) findViewById(R.id.imageButton3);
        imageButton3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View w) {
                showDialog(DATE_DIALOG_ID);
            }
        });
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                // set date picker as current date
                return new DatePickerDialog(this, datePickerListener,
                        year, month, day);
        }
        return null;
    }

    public DatePickerDialog.OnDateSetListener datePickerListener
            = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            year = selectedYear;
            month = selectedMonth;
            day = selectedDay;

            // set selected date into textview
            textFecha.setText(new StringBuilder().append(month + 1)
                    .append("-").append(day).append("-").append(year)
                    .append(" "));

        }
    };

}
