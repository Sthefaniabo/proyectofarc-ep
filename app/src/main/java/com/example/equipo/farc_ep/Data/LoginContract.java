package com.example.equipo.farc_ep.Data;

/**
 * Created by Equipo on 4/06/2017.
 */

public class LoginContract {

    public static abstract class LoginEntry {

        public static final String TABLE_NAME = "usuarios";
        public static final String NOMBRE = "nombre";
        public static final String PASSWD = "passwd";
        public static final String USERID = "user_id";
        public static final String EMAIL = "email";
        public static final String GENERO = "genero";
        public static final String PERMISOS = "permisos";
        public static final String FECHA_NACI = "fecha_naci";
        public static final String FECHA_INI = "fecha_ini";

        public static final String TABLE_NAME1 = "zonas";
        public static final String NUMERO = "numerozona";
        public static final String ADMINISTRADOR = "administrador";
        public static final String DEPARTAMENTO = "departamento";
        public static final String NOMBREZONA = "nombrezona";
        public static final String CAMPAMENTOS = "campamentos";

        public static final String TABLE_NAME2 = "reincorporados";
        public static final String ID = "identificacion";
        public static final String IDZONA = "idzona";
        public static final String NOMBREREIN = "nombrerein";
        public static final String EDAD = "edad";
        public static final String GENERO2 = "genero2";
        public static final String NIVEL = "nivel";
        public static final String NACIONALIDAD = "nacionalidad";

        public static final String TABLE_NAME3 = "capacitaciones";
        public static final String IDCAPA = "idcapacitacion";
        public static final String IDREINCOR = "idreincorporado";
        public static final String TIPO = "tipocapacitacion";

        public static final String TABLE_NAME4 = "penas";
        public static final String IDPENA = "idpena";
        public static final String NOMBREPENA = "nombrepena";
        public static final String ID_REINCORPORADO = "idreincorporado";
        public static final String TIPOPENA = "tipopena";
        public static final String ENCARGADO = "encargado";

    }
}
