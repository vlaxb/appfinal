package com.example.desarrollotyt.appfinal;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by DESARROLLOTYT on 17/11/2017.
 */

public class Login extends AppCompatActivity {

    EditText EditCorreo;
    EditText EditContra;
    Button BtnIngresar;
    String correoElectronico;
    String contrasena;


    AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"Prueba",null,1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        EditCorreo = (EditText) findViewById(R.id.usuario);
        EditContra = (EditText) findViewById(R.id.clave);
        BtnIngresar = (Button) findViewById(R.id.button2);


        BtnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                correoElectronico = EditCorreo.getText().toString();
                contrasena = EditContra.getText().toString();

                SQLiteDatabase bd = admin.getWritableDatabase();
                Cursor c1 = bd.rawQuery("select count(*) from Registros where correo = '"+correoElectronico+"'", null);

                if (c1.moveToFirst()) {
                    // capturo la consulta
                    int cantidad = Integer.parseInt(c1.getString(0));


                    if(cantidad == 0){
                        Intent intent = new Intent(Login.this, Registro.class);
                        startActivity(intent);

                        Toast.makeText(getApplicationContext(),"Usuario no registrado. LLene el formulario para crear un usuario", Toast.LENGTH_SHORT).show();
                    }else{
                        c1 = bd.rawQuery("select count(*) from Registros where correo = '"+correoElectronico+"' and contrasena='"+contrasena+"'", null);

                        if (c1.moveToFirst()) {
                            //
                            int cantidad2 = Integer.parseInt(c1.getString(0));

                            if(cantidad2 == 0){
                                Toast.makeText(getApplicationContext(),"Contrasena incorrecta", Toast.LENGTH_SHORT).show();
                            }else{
                                Intent intent = new Intent(Login.this, MainActivity.class);
                                startActivity(intent);
                            }
                        }

                    }

                }
            }
        });

    }
}