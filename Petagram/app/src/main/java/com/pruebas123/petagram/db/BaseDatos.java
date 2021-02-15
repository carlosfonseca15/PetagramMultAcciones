package com.pruebas123.petagram.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.pruebas123.petagram.pojo.Mascota;
import com.pruebas123.petagram.restApiFirebase.model.UsuarioResponse;

import java.util.ArrayList;

public class BaseDatos extends SQLiteOpenHelper {

    private  Context context;
    public BaseDatos(@Nullable Context context) {
        super(context, ConstantesBaseDatos.DATABASE_NAME, null, ConstantesBaseDatos.DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String queryCrearTablaMascota = "CREATE TABLE " + ConstantesBaseDatos.TABLE_PETS + "(" +
                                         ConstantesBaseDatos.TABLE_PETS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                         ConstantesBaseDatos.TABLE_PETS_IMAGEN + " INTEGER, " +
                                         ConstantesBaseDatos.TABLE_PETS_NOMBRE + " TEXT, " +
                                         ConstantesBaseDatos.TABLE_PETS_CALIFICACION + " INTEGER, " +
                                         ConstantesBaseDatos.TABLE_PETS_IMAGEN_ID + " TEXT  " +
                                         ")";
        sqLiteDatabase.execSQL(queryCrearTablaMascota);

        String queryCrearTablaRegistro = "CREATE TABLE " + ConstantesBaseDatos.TABLE_USER_REGISTER + "(" +
                ConstantesBaseDatos.TABLE_USER_REGISTER_ID + " TEXT, " +
                ConstantesBaseDatos.TABLE_USER_REGISTER_TOKEN + " TEXT, " +
                ConstantesBaseDatos.TABLE_USER_REGISTER_USERID + " TEXT " +
                ")";
        sqLiteDatabase.execSQL(queryCrearTablaRegistro);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ConstantesBaseDatos.TABLE_PETS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ConstantesBaseDatos.TABLE_USER_REGISTER);
        onCreate(sqLiteDatabase);
    }

    //si top = NO consulta todas las mascotas
    //si top = SI consulta las 5 mascotas mas calificadas
    public ArrayList<Mascota> obtenerTodasLasMascotas(String top){
        ArrayList<Mascota> mascotas = new ArrayList<>();
        String query = "SELECT * FROM " + ConstantesBaseDatos.TABLE_PETS;
        if (top.equals("SI")) {
            query = query + " ORDER BY " + ConstantesBaseDatos.TABLE_PETS_CALIFICACION + " DESC LIMIT 5 ";
            //query = query + " ORDER BY 4 DESC LIMIT 5 ";
        }
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor registros = db.rawQuery(query, null);   //2 arg para filtros
        while (registros.moveToNext()){
            Mascota mascotaActual = new Mascota();
            mascotaActual.setId(registros.getInt(0));
            mascotaActual.setImagen(registros.getInt(1));
            mascotaActual.setNombre(registros.getString(2));
            mascotaActual.setCalificacion(registros.getInt(3));
            mascotas.add(mascotaActual);
        }

        db.close();
        return mascotas;
    }

    public void insertarMascota(ContentValues contentValues){
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(ConstantesBaseDatos.TABLE_PETS, null, contentValues);
        db.close();

    }

    //modifica la calificacion de cada mascota
    public void updateMascota(Mascota mascota) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ConstantesBaseDatos.TABLE_PETS_ID, mascota.getId());
        contentValues.put(ConstantesBaseDatos.TABLE_PETS_CALIFICACION, mascota.getCalificacion());
        contentValues.put(ConstantesBaseDatos.TABLE_PETS_IMAGEN_ID, mascota.getImagenid());
        String whereClause = "id=?";
        String[] whereArgs = {String.valueOf(mascota.getId())};
        db.update(ConstantesBaseDatos.TABLE_PETS, contentValues, whereClause, whereArgs);
        db.close();

    }

    public void insertarRegistro(ContentValues contentValues){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + ConstantesBaseDatos.TABLE_USER_REGISTER);
        db.insert(ConstantesBaseDatos.TABLE_USER_REGISTER, null, contentValues);
        db.close();
    }

    public UsuarioResponse obtenerRegistro(){
        String query = "SELECT * FROM " + ConstantesBaseDatos.TABLE_USER_REGISTER;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor registros = db.rawQuery(query, null);
        UsuarioResponse usuarioResponse = new UsuarioResponse();
        while (registros.moveToNext()){
            usuarioResponse.setId(registros.getString(0));
            usuarioResponse.setToken(registros.getString(1));
            usuarioResponse.setUserid(registros.getString(2));
        }
        return usuarioResponse;
    }
}
