package com.example.equipo.farc_ep;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class Menu_Admin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu__admin);
    }

    public void registrarzona (View v)throws NoSuchAlgorithmException {

        Intent registrar = new Intent(Menu_Admin.this, Registrar_zona.class);
        registrar.addFlags(registrar.FLAG_ACTIVITY_CLEAR_TOP | registrar.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(registrar);


    }

    public void registrarreincor (View v)throws NoSuchAlgorithmException {

        Intent registrar = new Intent(Menu_Admin.this, Registrar_reincorporados.class);
        registrar.addFlags(registrar.FLAG_ACTIVITY_CLEAR_TOP | registrar.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(registrar);


    }

    public void registrarcapacita (View v)throws NoSuchAlgorithmException {

        Intent registrar = new Intent(Menu_Admin.this, Registrar_capacitaciones.class);
        registrar.addFlags(registrar.FLAG_ACTIVITY_CLEAR_TOP | registrar.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(registrar);


    }

    public void registrarpenas (View v)throws NoSuchAlgorithmException {

        Intent registrar = new Intent(Menu_Admin.this, Registrar_penas.class);
        registrar.addFlags(registrar.FLAG_ACTIVITY_CLEAR_TOP | registrar.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(registrar);


    }

    public void consultar (View v)throws NoSuchAlgorithmException {

        Intent consultar = new Intent(Menu_Admin.this, Menu_Consultar.class);
        consultar.addFlags(consultar.FLAG_ACTIVITY_CLEAR_TOP | consultar.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(consultar);


    }

    public void volver (View v)throws NoSuchAlgorithmException {

        Intent registrar = new Intent(Menu_Admin.this, Sing.class);
        registrar.addFlags(registrar.FLAG_ACTIVITY_CLEAR_TOP | registrar.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(registrar);


    }

   /* @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            con.BD_backup();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
}
