package vlaxapp.examples.com.vlaxapp;

/**
 * Created by DESARROLLOTYT on 17/11/2017.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {
    public AdminSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table Registros(correo text primary key, contrasena text)");
        db.execSQL("create table Sesion(id text, correo text)");
        db.execSQL("create table Preguntas(id integer primary key autoincrement, titulo text, contenido text, autor text, fecha text, estado text)");
        db.execSQL("create table Respuestas(id integer primary key autoincrement, idPregunta text, contenido text, autor text)");
        //Datos
        db.execSQL("insert into Registros(correo, contrasena) values('tucho@gmail.com','tucho')");
        db.execSQL("insert into Registros(correo, contrasena) values('vladimir@gmail.com','tucho')");

        db.execSQL("insert into Sesion(id, correo) values('sesionActual','')");

        db.execSQL("insert into Preguntas(titulo, contenido, autor, fecha, estado) values('Pregunta 1?','Contenido 1','vladimir@gmail.com','01/11/2017','R')");
        db.execSQL("insert into Preguntas(titulo, contenido, autor, fecha, estado) values('Pregunta 2?','Contenido 2','vladimir@gmail.com','05/11/2017','SR')");
        db.execSQL("insert into Preguntas(titulo, contenido, autor, fecha, estado) values('Pregunta 3?','Contenido 3','tucho@gmail.com','16/11/2017','SR')");
        db.execSQL("insert into Preguntas(titulo, contenido, autor, fecha, estado) values('Pregunta 4?','Contenido 4','tucho@gmail.com','19/11/2017','SR')");

        db.execSQL("insert into Respuestas(idPregunta, contenido, autor) values('1','Respuesta 1','tucho@gmail.com')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
