package com.example.honeycumb.activities.Utils.models.AdapterApi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;


import com.example.honeycumb.R;
import com.example.honeycumb.activities.Utils.models.Pokemon;

import java.util.ArrayList;

public class ListaPokemonAdapter extends  RecyclerView.Adapter<ListaPokemonAdapter.ViewHolder>{


    private ArrayList<Pokemon> dataset;//trae la lista de atributos del api y los designa
    private Context context;

    public ListaPokemonAdapter(Context context) {
        this.context=context;
        dataset=new ArrayList<>();//recorre la lista
    }


    //metodo encargado de la vista
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {//muestra los objetos
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_api,parent,false);
        return new ViewHolder(view);
    }


    //metodo encargado de poscionar los datos e insertarlos
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Pokemon p=dataset.get(position);
        holder.nombreTextView.setText(p.getName());
        Glide.with(context)
                .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/"+p.getNumber()+ ".png")
                .centerCrop()// que la imagen quede centrada
                .diskCacheStrategy(DiskCacheStrategy.ALL)//limpia del cache para que traiga otras imagenes
                .into(holder.fotoImageView);//que lo acomopode en holder fotoimageview  que viene del adaptador


    }

    //metodo encargado del tamaño
    @Override
    public int getItemCount() {
        return  dataset.size();
    }

    public void adicionarListaPokemon(ArrayList<Pokemon> listapokemon) {
        dataset.addAll(listapokemon);//trae la url y los organiza en los grid para que no se sobrescriba
        notifyDataSetChanged();//Actualiza el recycle view en la pantalla

    }

    //metodo encargado de declarar los datos
    public class ViewHolder extends RecyclerView.ViewHolder{//insertar todas vistas
        private ImageView fotoImageView;
        private TextView nombreTextView;

        public  ViewHolder(View itemView){
            super(itemView);
            fotoImageView=itemView.findViewById(R.id.fotoImageView);
            nombreTextView=itemView.findViewById(R.id.nombreTextView);

        }
    }
}