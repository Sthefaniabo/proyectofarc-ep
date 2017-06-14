package com.example.equipo.farc_ep;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.equipo.farc_ep.Data.Conexion;
import com.example.equipo.farc_ep.Data.LoginContract;

public class Consultar_Zonas extends AppCompatActivity {

    ListView listaUsuarios;
    Conexion con;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar__zonas);

        //visualizando su identificador único, nombre de usuario, genero, fecha de nacimiento, e-mail y su última fecha de inicio exitoso. g

        con= new Conexion(this,"proyecto_final",null,1);
        db = con.getWritableDatabase();
        //db.delete( LoginContract.LoginEntry.TABLE_NAME,LoginContract.LoginEntry.USERID +"=3",null);

        //Bundle data = getIntent().getExtras().getBundle("datos");

// set current date into textview

        Cursor cur = db.rawQuery(" SELECT "+ LoginContract.LoginEntry.NUMERO+","+ LoginContract.LoginEntry.ADMINISTRADOR+","+ LoginContract.LoginEntry.DEPARTAMENTO+","+
                LoginContract.LoginEntry.NOMBREZONA+","+ LoginContract.LoginEntry.CAMPAMENTOS+ " FROM "+
                LoginContract.LoginEntry.TABLE_NAME1, null);


        String [] datos=new String[cur.getCount()];
        int i=0;
        while (cur.moveToNext()){
            datos[i]=cur.getString(0) + "-" + cur.getString(1)+"-"+cur.getString(2)+"-"+cur.getString(3)+"-"+cur.getString(4);

            i++;
        }


        ArrayAdapter<String> adaptadorUsuarios;

        listaUsuarios = (ListView)findViewById(R.id.listView);

        adaptadorUsuarios = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,datos);

        listaUsuarios.setAdapter(adaptadorUsuarios);

        Button btnSalir =(Button)findViewById(R.id.button2);
        btnSalir.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Volver();
            }
        }));
    }

    public void Volver (){

        Intent volver = new Intent(Consultar_Zonas.this, Menu_Consultar.class);
        volver.addFlags(volver.FLAG_ACTIVITY_CLEAR_TOP | volver.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(volver);


    }

}
