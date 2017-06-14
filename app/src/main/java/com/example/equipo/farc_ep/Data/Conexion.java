package com.example.equipo.farc_ep.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Equipo on 4/06/2017.
 */

public class Conexion extends SQLiteOpenHelper {

    static  String DATABASE_NAME= "proyecto_final";

    interface  tablas {

        String query="";
        String query1="";
        String query2="";
        String query3="";
        String query4="";


    }

       String query =" CREATE TABLE "+LoginContract.LoginEntry.TABLE_NAME +"( "+LoginContract.LoginEntry.USERID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                ""+LoginContract.LoginEntry.EMAIL+" TEXT, "+
                ""+LoginContract.LoginEntry.PASSWD+" TEXT, "+
                ""+LoginContract.LoginEntry.NOMBRE+" TEXT, "+
                ""+LoginContract.LoginEntry.GENERO+" TEXT, "+
                ""+LoginContract.LoginEntry.FECHA_NACI+"  TEXT, "+
                ""+LoginContract.LoginEntry.FECHA_INI+" TEXT, "+
                ""+LoginContract.LoginEntry.PERMISOS+" TEXT );";

       String query1=" CREATE TABLE "+LoginContract.LoginEntry.TABLE_NAME1 +"( "+
                ""+LoginContract.LoginEntry.NUMERO+" INTEGER PRIMARY KEY AUTOINCREMENT, "+//PK
                ""+LoginContract.LoginEntry.ADMINISTRADOR+" TEXT, "+
                ""+LoginContract.LoginEntry.DEPARTAMENTO+" TEXT, "+
                ""+LoginContract.LoginEntry.NOMBREZONA+" TEXT, "+
                ""+LoginContract.LoginEntry.CAMPAMENTOS+"  TEXT );";

       String query2=" CREATE TABLE "+LoginContract.LoginEntry.TABLE_NAME2 +"( "+
            ""+LoginContract.LoginEntry.ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            ""+LoginContract.LoginEntry.IDZONA+" TEXT, "+//foranea numero
            ""+LoginContract.LoginEntry.NOMBREREIN+" TEXT, "+
            ""+LoginContract.LoginEntry.EDAD+" TEXT, "+
               ""+LoginContract.LoginEntry.GENERO2+" TEXT, "+
               ""+LoginContract.LoginEntry.NIVEL+" TEXT, "+
            ""+LoginContract.LoginEntry.NACIONALIDAD+"  TEXT," +
               "FOREIGN KEY("+LoginContract.LoginEntry.IDZONA+") REFERENCES "+LoginContract.LoginEntry.TABLE_NAME1 +
               "("+LoginContract.LoginEntry.NUMERO+") );";

    String query3=" CREATE TABLE "+LoginContract.LoginEntry.TABLE_NAME3 +"( "+
            ""+LoginContract.LoginEntry.IDCAPA+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            ""+LoginContract.LoginEntry.IDREINCOR+" TEXT, "+
            ""+LoginContract.LoginEntry.TIPO+" TEXT, " +
            "FOREIGN KEY ("+LoginContract.LoginEntry.IDREINCOR+") REFERENCES "+LoginContract.LoginEntry.TABLE_NAME2+
            "("+LoginContract.LoginEntry.ID+") );";

            //FOREIGN KEY (table2_id) REFERENCES table2(id),

    String query4=" CREATE TABLE "+LoginContract.LoginEntry.TABLE_NAME4 +"( "+
            ""+LoginContract.LoginEntry.IDPENA+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            ""+LoginContract.LoginEntry.NOMBREPENA+" TEXT, "+
            ""+LoginContract.LoginEntry.ID_REINCORPORADO+" TEXT, "+
            ""+LoginContract.LoginEntry.TIPOPENA+" TEXT, "+
            ""+LoginContract.LoginEntry.ENCARGADO+" TEXT, "+
            "FOREIGN KEY ("+LoginContract.LoginEntry.ID_REINCORPORADO+") REFERENCES "+LoginContract.LoginEntry.TABLE_NAME2+
            "("+LoginContract.LoginEntry.ID+") );";



    public Conexion(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase){
        try{

        sqLiteDatabase.execSQL(query);
        sqLiteDatabase.execSQL(query1);
            sqLiteDatabase.execSQL(query2);
            sqLiteDatabase.execSQL(query3);
            sqLiteDatabase.execSQL(query4);
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("TABLE_NAME"+query);
        sqLiteDatabase.execSQL("TABLE_NAME1"+query1);
        sqLiteDatabase.execSQL("TABLE_NAME2"+query2);
        sqLiteDatabase.execSQL("TABLE_NAME3"+query3);
        sqLiteDatabase.execSQL("TABLE_NAME4"+query4);

        onCreate(sqLiteDatabase);


    }

   public static void BD_backup() throws IOException {
        String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmmss").format(new Date());
        final String inFileName = "/data/data/com.example.equipo.farc_ep/databases/"+DATABASE_NAME;
        File dbFile = new File(inFileName);
        FileInputStream fis = null;
        fis = new FileInputStream(dbFile);
        //String directorio = obtenerDirectorioCopias();
        String directorio = "/storage/emulated/0/Download/";
        File d = new File(directorio);
        if (!d.exists()) {
            d.mkdir();
        }
        String outFileName = directorio + "/"+DATABASE_NAME + "_"+timeStamp;
        OutputStream output = new FileOutputStream(outFileName);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = fis.read(buffer)) > 0) {
            output.write(buffer, 0, length);
        }
        output.flush();
        output.close();
        fis.close();


    }


}
