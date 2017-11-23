package vlaxapp.examples.com.vlaxapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.nfc.Tag;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ListaProducto extends AppCompatActivity {

    List<Product> productList = new ArrayList<>();
    String correoInicioSesion;

    Button btnPreguntaspublic;
    Button btnPreguntaspivate;

    AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"Prueba",null,1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_producto);

        TabLayout tabs = (TabLayout) findViewById(R.id.tabs);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        tabs.addTab(tabs.newTab().setText("Preguntas"));
        tabs.addTab(tabs.newTab().setText("Mis Preguntas"));

        tucho();

        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition() == 0){
                    setInitialData1();
                    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list);

                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ListaProducto.this);
                    recyclerView.setLayoutManager(linearLayoutManager);

                    MyAdapter myAdapter = new MyAdapter(ListaProducto.this,productList);
                    recyclerView.setAdapter(myAdapter);
                }if(tab.getPosition() == 1){
                    setInitialData2();
                    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list);

                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ListaProducto.this);
                    recyclerView.setLayoutManager(linearLayoutManager);

                    MyAdapter myAdapter = new MyAdapter(ListaProducto.this,productList);
                    recyclerView.setAdapter(myAdapter);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        SQLiteDatabase bd = admin.getWritableDatabase();
        Intent intent = getIntent();
        Log.d("Tag","intent: "+intent.hasExtra("correoSesion"));
        if(intent.hasExtra("correoSesion")){
            correoInicioSesion = intent.getStringExtra("correoSesion");
            bd.execSQL("update Sesion set correo = '"+correoInicioSesion+"'");
        }else{
            Cursor c1 = bd.rawQuery("select correo from Sesion where id = 'sesionActual'", null);

            if(c1.moveToFirst()){
                correoInicioSesion = c1.getString(0);
            }
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListaProducto.this, CrearPreguntas.class);
                startActivity(intent);
            }
        });

    }

    private void setInitialData1(){

        productList.clear();

        String id = "";
        String titulo = "";
        String contenido = "";
        String autor = "";
        String fecha = "";
        String estado = "";

        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor c1 = bd.rawQuery("select id, titulo, contenido, autor, fecha, estado from Preguntas where autor != '"+correoInicioSesion+"'", null);

        if (c1.moveToFirst()) {
            do {
                id = c1.getString(0);
                titulo = c1.getString(1);
                contenido = c1.getString(2);
                autor = c1.getString(3);
                fecha = c1.getString(4);
                estado = c1.getString(5);

                productList.add(new Product(id, titulo,contenido,autor,fecha, estado));
            } while(c1.moveToNext());

        }
    }

    private void setInitialData2(){

        productList.clear();

        String id = "";
        String titulo = "";
        String contenido = "";
        String autor = "";
        String fecha = "";
        String estado = "";

        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor c1 = bd.rawQuery("select id, titulo, contenido, autor, fecha, estado from Preguntas where autor = '"+correoInicioSesion+"'", null);

        if (c1.moveToFirst()) {
            do {
                id = c1.getString(0);
                titulo = c1.getString(1);
                contenido = c1.getString(2);
                autor = c1.getString(3);
                fecha = c1.getString(4);
                estado = c1.getString(5);

                productList.add(new Product(id,titulo,contenido,autor,fecha,estado));
            } while(c1.moveToNext());

        }
    }

    public void tucho(){
        setInitialData1();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ListaProducto.this);
        recyclerView.setLayoutManager(linearLayoutManager);

        MyAdapter myAdapter = new MyAdapter(ListaProducto.this,productList);
        recyclerView.setAdapter(myAdapter);
    }
}
