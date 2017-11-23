package vlaxapp.examples.com.vlaxapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dd on 01.05.2017.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {


    Context context;
    List<Product> productList = new ArrayList<>();

    public MyAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.items, parent, false);


        return new ViewHolder(view);
    }


    String correoSesion = "";

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        //**************************************************
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(context,"Prueba",null,1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor c1 = bd.rawQuery("select correo from Sesion where id = 'sesionActual'", null);


        if(c1.moveToFirst()){
            correoSesion = c1.getString(0);
        }
        //**************************************************


        final Product product  = productList.get(position);

        holder.mTextView1.setText(product.getTitulo());
        holder.mTextView2.setText(product.getContenido());
        holder.mTextView3.setText(product.getAutor());
        holder.mTextView4.setText(product.getFecha());

        if(correoSesion.equals(product.getAutor())){
            if("R".equals(product.getEstado())){
                holder.mButton1.setText("Ver respuesta");
                holder.mButton1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, Respuesta.class);
                        intent.putExtra("idPregunta",product.getId());
                        context.startActivity(intent);
                    }
                });
            }else{
                holder.mButton1.setVisibility(View.INVISIBLE);
            }
        }else{
            if("R".equals(product.getEstado())){
                holder.mButton1.setText("Ver respuesta");
                holder.mButton1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, Respuesta.class);
                        intent.putExtra("idPregunta",product.getId());
                        context.startActivity(intent);
                    }
                });
            }else{
                holder.mButton1.setText("Responder");
                holder.mButton1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FragmentManager fragmentManager = ((FragmentActivity)context).getSupportFragmentManager();
                        PopupRes popupRes = new PopupRes();
                        popupRes.setIdPregunta(product.getId());
                        popupRes.setCorreoResponde(correoSesion);
                        popupRes.show(fragmentManager,"tagPersonalizado");
                    }
                });
            }
        }



    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView1;
        TextView mTextView2;
        TextView mTextView3;
        TextView mTextView4;
        Button mButton1;

        public ViewHolder(View itemView) {
            super(itemView);

            this.mTextView1 = (TextView) itemView.findViewById(R.id.TxvTitulo);
            this.mTextView2 = (TextView) itemView.findViewById(R.id.TxvContenido);
            this.mTextView3 = (TextView) itemView.findViewById(R.id.TxvAutor);
            this.mTextView4 = (TextView) itemView.findViewById(R.id.TxvFecha);
            this.mButton1 = (Button) itemView.findViewById(R.id.BtnResponder);


        }


    }
}
