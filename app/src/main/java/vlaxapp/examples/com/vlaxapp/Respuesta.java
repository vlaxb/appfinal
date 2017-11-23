package vlaxapp.examples.com.vlaxapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Respuesta extends AppCompatActivity {

    Button volver;
    TextView titulo;
    TextView fecha;
    TextView autor;
    TextView pregunta;
    TextView respuesta;
    TextView autorRes;

    String idPregunta;
    String textCompartir = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.respuesta);

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"Prueba",null,1);
        SQLiteDatabase bd = admin.getWritableDatabase();

        Intent intent = getIntent();
        idPregunta = intent.getStringExtra("idPregunta");


        volver = (Button) findViewById(R.id.BtnVolver);
        titulo = (TextView) findViewById(R.id.TxvTitulo);
        fecha = (TextView) findViewById(R.id.TxvFecha);
        autor = (TextView) findViewById(R.id.TxvAutor);
        pregunta = (TextView) findViewById(R.id.TxvConPregunta);
        respuesta = (TextView) findViewById(R.id.TxvRespuesta);
        autorRes = (TextView) findViewById(R.id.TxvAutorRes);


        Cursor c1 = bd.rawQuery("select p.titulo, p.contenido, p.autor, p.fecha, r.contenido, r.autor from Preguntas p inner join Respuestas r on p.id = r.idPregunta and r.idPregunta = '"+idPregunta+"'", null);
        if(c1.moveToFirst()){
            titulo.setText(c1.getString(0));
            fecha.setText("Fecha: "+c1.getString(3));
            autor.setText("Autor: "+c1.getString(2));
            pregunta.setText(c1.getString(1));
            respuesta.setText("R// "+c1.getString(4));
            textCompartir = "La pregunta "+c1.getString(0)+", realizada por "+c1.getString(2)+" tiene como respuesta: "+c1.getString(4);
            autorRes.setText("Respondida por: "+c1.getString(5));
        }

        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Respuesta.this, ListaProducto.class);
                startActivity(intent);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, textCompartir);
                startActivity(Intent.createChooser(intent, "Compartir en..."));

            }
        });
    }
}
