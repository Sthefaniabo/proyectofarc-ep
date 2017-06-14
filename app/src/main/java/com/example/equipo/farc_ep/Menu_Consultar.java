package com.example.equipo.farc_ep;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.NoSuchAlgorithmException;

public class Menu_Consultar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu__consultar);

    }

    public void consultarUsuarios (View v)throws NoSuchAlgorithmException {


        Intent consultar = new Intent(Menu_Consultar.this, Consultar_Usuarios.class);
        consultar.addFlags(consultar.FLAG_ACTIVITY_CLEAR_TOP | consultar.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(consultar);


    }

    public void consultarZonas (View v)throws NoSuchAlgorithmException {

        Intent consultar = new Intent(Menu_Consultar.this, Consultar_Zonas.class);
        consultar.addFlags(consultar.FLAG_ACTIVITY_CLEAR_TOP | consultar.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(consultar);


    }

    public void consultarReincor (View v)throws NoSuchAlgorithmException {

        Intent consultar = new Intent(Menu_Consultar.this, Consultar_Reincorporados.class);
        consultar.addFlags(consultar.FLAG_ACTIVITY_CLEAR_TOP | consultar.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(consultar);


    }

    public void consultarCapacitaci (View v)throws NoSuchAlgorithmException {

        Intent consultar = new Intent(Menu_Consultar.this, Consultar_Capacitaciones.class);
        consultar.addFlags(consultar.FLAG_ACTIVITY_CLEAR_TOP | consultar.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(consultar);


    }

    public void consultarpenas (View v)throws NoSuchAlgorithmException {

        Intent consultar = new Intent(Menu_Consultar.this, Consultar_Penas.class);
        consultar.addFlags(consultar.FLAG_ACTIVITY_CLEAR_TOP | consultar.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(consultar);


    }

    public void volvercon (View v)throws NoSuchAlgorithmException {

        Intent registrar = new Intent(Menu_Consultar.this, Menu_Admin.class);
        registrar.addFlags(registrar.FLAG_ACTIVITY_CLEAR_TOP | registrar.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(registrar);


    }

}
