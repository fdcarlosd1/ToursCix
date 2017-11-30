package com.delycomp.tourismperu.Adaptadores;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.delycomp.tourismperu.Activities.DetalleLugarActivity;
import com.delycomp.tourismperu.Activities.SlideImage;
import com.delycomp.tourismperu.Entidades.LugarTuristico;
import com.delycomp.tourismperu.Activities.MainActivity;
import com.delycomp.tourismperu.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CarlosDavid on 02/08/2017.
 */

public class AdaptadorRVLista extends RecyclerView.Adapter<AdaptadorRVLista.PrincipalLugares> {
   private static  List<LugarTuristico> listLugares;
    String idioma = MainActivity.idioma;
    private static Context mContext;
    public AdaptadorRVLista(List<LugarTuristico> listLugares) {
        this.listLugares = listLugares;
    }

    @Override
    public PrincipalLugares onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.vistarv ,parent,false);
        mContext=v.getContext();
        PrincipalLugares principalLugares = new PrincipalLugares(v);
        return principalLugares;
    }

    @Override
    public void onBindViewHolder(PrincipalLugares holder, int position) {
        String des="";
        holder.titulo.setText(listLugares.get(position).getName_lugar());
        if (idioma.equals("Español")){
            des = listLugares.get(position).getDesc_es();

        }else{
            des = listLugares.get(position).getDesc_en();
        }
        if(des.length()>120){
            des = des.substring(0,110)+"... (Leer mas)";
        }
        holder.descr.setText(des);
        ImageView imageView = holder.image1;
        Glide.with(mContext)
                .load("http://delycomp.com/imagenes/"+listLugares.get(position).getCod_lugar()+"_1.jpg")
                .placeholder(R.drawable.loadingfull)
                .into(imageView);

        ImageView imageView1 = holder.image2;
        Glide.with(mContext)
                .load("http://delycomp.com/imagenes/"+listLugares.get(position).getCod_lugar()+"_2.jpg")
                .placeholder(R.drawable.loadingfull)
                .into(imageView1);
    }


    @Override
    public int getItemCount() {
        return listLugares.size();
    }

    public static class PrincipalLugares extends RecyclerView.ViewHolder implements View.OnClickListener {
         TextView titulo,descr;
        ImageView image1,image2;
        public PrincipalLugares(View itemView) {
            super(itemView);
            titulo =  (TextView) itemView.findViewById(R.id.TituloText);
            descr =  (TextView) itemView.findViewById(R.id.DescText);
            image1 =  (ImageButton) itemView.findViewById(R.id.imageX1);
            image2 =  (ImageButton) itemView.findViewById(R.id.imageX2);
            itemView.setOnClickListener(this);
            image1.setOnClickListener(this);
            image2.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            String cadena="";
            List<String> listImages = new ArrayList<>();
            image2 =  (ImageButton) itemView.findViewById(R.id.imageX2);
            int position = getAdapterPosition();
            if(position != RecyclerView.NO_POSITION) {
                LugarTuristico lugarturisitco = listLugares.get(position);
                switch(view.getId()){
                    case R.id.imageX1:
                        cadena="http://delycomp.com/imagenes/"+lugarturisitco.getCod_lugar()+"_1.jpg";
                        listImages.add("http://delycomp.com/imagenes/"+lugarturisitco.getCod_lugar()+"_1.jpg");
                        listImages.add("http://delycomp.com/imagenes/"+lugarturisitco.getCod_lugar()+"_2.jpg");
                        break;
                    case R.id.imageX2:
                        cadena="http://delycomp.com/imagenes/"+lugarturisitco.getCod_lugar()+"_2.jpg";
                        listImages.add("http://delycomp.com/imagenes/"+lugarturisitco.getCod_lugar()+"_2.jpg");
                        listImages.add("http://delycomp.com/imagenes/"+lugarturisitco.getCod_lugar()+"_1.jpg");
                        break;
                    default:
                        Intent intent1 = new Intent(mContext,DetalleLugarActivity.class);
                        intent1.putExtra(DetalleLugarActivity.EXTRA_LUGART,lugarturisitco);
                        mContext.startActivity(intent1);
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
