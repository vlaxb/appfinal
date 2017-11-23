package vlaxapp.examples.com.vlaxapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by TYT2017 on 21/11/2017.
 */

public class PopupRes extends DialogFragment {

    String idPregunta;
    String correoResponde;
    EditText respuesta;
    Button preguntasPublic;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.cuadro_respuesta, null))
                .setTitle("Respuesta")
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                }).setPositiveButton("Responder", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(getContext(),"Prueba",null,1);
                SQLiteDatabase bd = admin.getWritableDatabase();

                respuesta = (EditText) getDialog().findViewById(R.id.EtxRespuesta);
                //preguntasPublic = (Button) getDialog().findViewById(R.id.BtnPreguntas);
                String res = respuesta.getText().toString();

                String id = getIdPregunta();
                String mail = getCorreoResponde();

                bd.execSQL("insert into Respuestas(idPregunta, contenido, autor) values('"+id+"','"+res+"','"+mail+"')");
                bd.execSQL("update Preguntas set estado = 'R' where id = '"+id+"'");

                Intent intent = new Intent(getContext(), ListaProducto.class);
                startActivity(intent);





            }
        });
        return builder.create();
    }

    public String getIdPregunta() {
        return idPregunta;
    }

    public void setIdPregunta(String idPregunta) {
        this.idPregunta = idPregunta;
    }

    public String getCorreoResponde() {
        return correoResponde;
    }

    public void setCorreoResponde(String correoResponde) {
        this.correoResponde = correoResponde;
    }
}
