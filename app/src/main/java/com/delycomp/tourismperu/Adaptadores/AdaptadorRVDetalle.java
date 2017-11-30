package com.delycomp.tourismperu.Adaptadores;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.delycomp.tourismperu.Activities.SlideImage;
import com.delycomp.tourismperu.Entidades.FotoTuristica;
import com.delycomp.tourismperu.R;
import com.delycomp.tourismperu.Activities.SpaceFhoto;

import java.util.ArrayList;
import java.util.List;

public class AdaptadorRVDetalle extends RecyclerView.Adapter<AdaptadorRVDetalle.FotosLugar>{
    private static List<FotoTuristica> listFotos;
    private static Context mContext;
    public AdaptadorRVDetalle(List<FotoTuristica> listfotos) {
        this.listFotos = listfotos;
    }

    @Override
    public FotosLugar onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.vistadetalle ,parent,false);
        mContext=v.getContext();
        AdaptadorRVDetalle.FotosLugar fotosLugar = new AdaptadorRVDetalle.FotosLugar(v);
        return fotosLugar;
    }

    @Override
    public void onBindViewHolder(FotosLugar holder, int position) {
        ImageView imageView = holder.imageDetalle;
        Glide.with(mContext)
                .load(listFotos.get(position).getRuta())
                .placeholder(R.drawable.loadingfull)
                .into(imageView);
    }
    @Override
    public int getItemCount() {
        return listFotos.size();
    }

    public static class FotosLugar extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageDetalle;
        public FotosLugar(View itemView) {
            super(itemView);
            imageDetalle =  (ImageView) itemView.findViewById(R.id.imageDetalle);
            itemView.setOnClickListener(this);
            imageDetalle.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            String cadena="";
            List<String> listImages = new ArrayList<>();
            imageDetalle =  (ImageView) itemView.findViewById(R.id.imageDetalle);
            int position = getAdapterPosition();
            if(position != RecyclerView.NO_POSITION) {
                FotoTuristica fotoTuristica = listFotos.get(position);
                switch(v.getId()){
                    case R.id.imageDetalle:
                        listImages.add(fotoTuristica.getRuta());
                        for (int i = 0; i < listFotos.size(); i++) {
                            if (i != position) {
                                listImages.add(listFotos.get(i).getRuta());
                            }
                        }
                        cadena=fotoTuristica.getRuta();
                        break;
                }
                if(!cadena.equals("")){
                    Intent intent = new Intent(mContext, SlideImage.class);
                    intent.putStringArrayListExtra(SlideImage.EXTRA_LISTA_IMAGES, (ArrayList<String>) listImages);
                    mContext.startActivity(intent);
                }
            }
        }
    }

    }