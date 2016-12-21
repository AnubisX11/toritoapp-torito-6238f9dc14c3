package com.torito;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.*;

import java.util.ArrayList;
import java.util.HashMap;

import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;

/**
 * Created by Wall on 08/12/2015.
 */
public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Torito.db";
    public static final String CLIENT_TABLE_NAME = "cliente";
    public static final String CLIENT_COLUMN_ID = "id";
    public static final String CLIENT_COLUMN_IDUS = "idus";
    public static final String CLIENT_COLUMN_TOKEN = "token";
    public static final String CLIENT_COLUMN_ORDEN = "orden";
    public static final String CLIENT_COLUMN_NAME = "name";
    public static final String CLIENT_COLUMN_PATERNO = "paterno";
    public static final String CLIENT_COLUMN_MATERNO = "materno";
    public static final String CLIENT_COLUMN_CORREO = "correo";
    public static final String CLIENT_COLUMN_TELEFONO = "telefono";
    private HashMap hp;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS cliente");
        db.execSQL(
                "CREATE TABLE cliente " +
                        "(id integer primary key, idus integer, token text,orden text, name text, paterno text, materno text, correo text, telefono text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS cliente");
        onCreate(db);
    }

    /*Inserta cliente nuevo en la tabla cliente
    * Recibe datos [id del websservice, token de tarjeta, nombre, apellido paterno,
    * apellido materno, correo, telefono] del cliente*/
    public boolean insertarCliente(int idus, String token, String orden, String name, String paterno, String materno, String correo, String telefono) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("idus", idus);
        contentValues.put("token", token);
        contentValues.put("orden", orden);
        contentValues.put("name", name);
        contentValues.put("paterno", paterno);
        contentValues.put("materno", materno);
        contentValues.put("correo", correo);
        contentValues.put("telefono", telefono);
        db.insert("cliente", null, contentValues);
        return true;
    }

    //Modifica atributos del cliente en donde el idus coincida
    public boolean updateCliente(Integer idus, String name, String paterno, String materno, String correo, String telefono) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("paterno", paterno);
        contentValues.put("materno", materno);
        contentValues.put("correo", correo);
        contentValues.put("telefono", telefono);
        db.update("cliente", contentValues, "idus = ? ", new String[]{Integer.toString(idus)});
        return true;
    }

    //Inserta o actualiza el token en donde el idus coincida
    public boolean actualizarToken(int idus, String token) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("token", token);
        db.update("cliente", contentValues, "idus = ? ", new String[]{Integer.toString(idus)});
        return true;
    }

    //Cuenta el numero de registros existentes en la tabla cliente
    public Cursor sinCliente() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor count = db.rawQuery("SELECT count(*) from cliente", null);
        return count;//Devuelve un cursor con el número de registros
    }

    //Obtiene todos los registros de la tabla cliente
    public Cursor getData() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from cliente", null);
        return res;//Devuelve un cursor con el contenido del query
    }

}
