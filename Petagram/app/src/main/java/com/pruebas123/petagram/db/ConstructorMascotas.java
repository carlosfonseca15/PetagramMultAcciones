package com.pruebas123.petagram.db;

import android.content.ContentValues;
import android.content.Context;

import com.pruebas123.petagram.R;
import com.pruebas123.petagram.pojo.Mascota;
import com.pruebas123.petagram.restApiFirebase.model.UsuarioResponse;

import java.util.ArrayList;

public class ConstructorMascotas {

    private Context context;
    public ConstructorMascotas(Context context) {
        this.context = context;
    }

    public ArrayList<Mascota> obtenerDatos(){
        BaseDatos db = new BaseDatos(context);
        insertarMascotas(db);
        return  db.obtenerTodasLasMascotas("NO");
    }

    public void insertarMascotas(BaseDatos db){
        ContentValues contentValues = new ContentValues();
        contentValues.put(ConstantesBaseDatos.TABLE_PETS_IMAGEN, R.drawable.chispa);
        contentValues.put(ConstantesBaseDatos.TABLE_PETS_NOMBRE, "Chispa");
        db.insertarMascota(contentValues);

        contentValues = new ContentValues();
        contentValues.put(ConstantesBaseDatos.TABLE_PETS_IMAGEN, R.drawable.coco);
        contentValues.put(ConstantesBaseDatos.TABLE_PETS_NOMBRE, "Coco");
        db.insertarMascota(contentValues);

        contentValues = new ContentValues();
        contentValues.put(ConstantesBaseDatos.TABLE_PETS_IMAGEN, R.drawable.jack);
        contentValues.put(ConstantesBaseDatos.TABLE_PETS_NOMBRE, "Jack");
        db.insertarMascota(contentValues);

        contentValues = new ContentValues();
        contentValues.put(ConstantesBaseDatos.TABLE_PETS_IMAGEN, R.drawable.lucas);
        contentValues.put(ConstantesBaseDatos.TABLE_PETS_NOMBRE, "Lucas");
        db.insertarMascota(contentValues);

        contentValues = new ContentValues();
        contentValues.put(ConstantesBaseDatos.TABLE_PETS_IMAGEN, R.drawable.max);
        contentValues.put(ConstantesBaseDatos.TABLE_PETS_NOMBRE, "Max");
        db.insertarMascota(contentValues);

        contentValues = new ContentValues();
        contentValues.put(ConstantesBaseDatos.TABLE_PETS_IMAGEN, R.drawable.rex);
        contentValues.put(ConstantesBaseDatos.TABLE_PETS_NOMBRE, "Rex");
        db.insertarMascota(contentValues);

        contentValues = new ContentValues();
        contentValues.put(ConstantesBaseDatos.TABLE_PETS_IMAGEN, R.drawable.rocky);
        contentValues.put(ConstantesBaseDatos.TABLE_PETS_NOMBRE, "Rocky");
        db.insertarMascota(contentValues);

        contentValues = new ContentValues();
        contentValues.put(ConstantesBaseDatos.TABLE_PETS_IMAGEN, R.drawable.thor);
        contentValues.put(ConstantesBaseDatos.TABLE_PETS_NOMBRE, "Thor");
        db.insertarMascota(contentValues);

        contentValues = new ContentValues();
        contentValues.put(ConstantesBaseDatos.TABLE_PETS_IMAGEN, R.drawable.toby);
        contentValues.put(ConstantesBaseDatos.TABLE_PETS_NOMBRE, "Toby");
        db.insertarMascota(contentValues);

        contentValues = new ContentValues();
        contentValues.put(ConstantesBaseDatos.TABLE_PETS_IMAGEN, R.drawable.zeus);
        contentValues.put(ConstantesBaseDatos.TABLE_PETS_NOMBRE, "Zeus");
        db.insertarMascota(contentValues);

    }

    public void  actualizarCalificacion(Mascota mascota){
        BaseDatos db = new BaseDatos(context);
        db.updateMascota(mascota);
    }

    public UsuarioResponse ObtieneRegistro(){
        BaseDatos db = new BaseDatos(context);
        return db.obtenerRegistro();
    }

}
