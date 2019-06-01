package com.appquiz.bakke.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.appquiz.bakke.Model.Pedido;
import com.appquiz.bakke.R;

import java.util.ArrayList;

/**
 * Clase para almacenar el adaptador con los datos
 * de los acontecimientos que va a mostrar
 * el RecyclerView
 */
public class PedidosAdapter extends RecyclerView.Adapter<PedidosAdapter.PedidoViewHolder> {

    private Context myContext;
    private ArrayList<Pedido> items;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    /**
     * Contruye el objeto adaptador recibiendo la lista de datos
     *
     * @param myContext
     * @param items
     */
    public PedidosAdapter(Context myContext, @NonNull ArrayList<Pedido> items) {
        this.myContext = myContext;
        this.items = items;
    }

    /**
     * Clase interna:
     *  Se implementa el ViewHolder que se encargará
     *  de almacenar la vista del elemento y sus datos
     */
    public class PedidoViewHolder extends RecyclerView.ViewHolder {
        private TextView nombre;
        private TextView fecha;
        private TextView direccion;

        public PedidoViewHolder(View itemView) {
            super(itemView);
            nombre = (TextView) itemView.findViewById(R.id.TextView_nombre);
            fecha = (TextView) itemView.findViewById(R.id.TextView_fecha);
            direccion = (TextView) itemView.findViewById(R.id.TextView_direccion);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            mListener.onItemClick(position);
                        }
                    }
                }
            });
        }

        public void PedidoBind(Pedido item) {
            nombre.setText(item.getNombre());
            fecha.setText(item.getFecha());
            direccion.setText(item.getDireccionCliente());
        }
    }

    @NonNull
    @Override
    /**
     * Se encarga de crear los nuevos objetos ViewHolder necesarios
     * para los elementos de la colección.
     * Infla la vista del layout, crea y devuelve el objeto ViewHolder
     */
    public PedidoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View row = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row, viewGroup, false);

        PedidoViewHolder pvh = new PedidoViewHolder(row);
        return pvh;
    }

    @Override
    /**
     * Se encarga de actualizar los datos de un ViewHolder ya existente.
     */
    public void onBindViewHolder(@NonNull PedidoViewHolder pedidoViewHolder, int i) {
        Pedido item = items.get(i);
        pedidoViewHolder.PedidoBind(item);
    }

    @Override
    /**
     * Indica el número de elementos de la colección de datos.
     */
    public int getItemCount() {
        return items.size();
    }

    /**
     * Limpia el recyclerview
     */
    public void borrarDatos() {
        items.clear();
    }
}
