package vlaxapp.examples.com.vlaxapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Calendar;

public class CrearPreguntas extends AppCompatActivity {

    EditText titulo;
    EditText fecha;
    EditText autor;
    EditText contenido;
    Button guardar;

    String fechaActual;
    String correoSesion = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crear_preguntas);

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"Prueba",null,1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor c1 = bd.rawQuery("select correo from Sesion where id = 'sesionActual'", null);
        if(c1.moveToFirst()){
            correoSesion = c1.getString(0);
        }

        Calendar calendar = Calendar.getInstance();

        titulo = (EditText) findViewById(R.id.EtxTitulo);
        fecha = (EditText) findViewById(R.id.EtxFecha);
        autor = (EditText) findViewById(R.id.EtxAutor);
        contenido = (EditText) findViewById(R.id.EtxContenido);
        guardar = (Button) findViewById(R.id.BtnGuardar);

        fechaActual = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH))+"/"+
                      String.valueOf(calendar.get(Calendar.MONTH) + 1)+"/"+
                      String.valueOf(calendar.get(Calendar.YEAR));

        fecha.setText(fechaActual);
        autor.setText(correoSesion);

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(CrearPreguntas.this,"Prueba",null,1);
                SQLiteDatabase bd = admin.getWritableDatabase();
                bd.execSQL("insert into Preguntas(titulo, contenido, autor, fecha, estado) values ('"+titulo.getText().toString()+"','"
                                                                                                     +contenido.getText().toString()+"','"
                                                                                                     +autor.getText().toString()+"','"
                                                                                                     +fecha.getText().toString()+"','SR')");

                Intent intent = new Intent(CrearPreguntas.this, ListaProducto.class);
                startActivity(intent);
            }
        });




    }
}

