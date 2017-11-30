package com.delycomp.tourismperu.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.delycomp.tourismperu.Adaptadores.AdaptadorRVDetalle;
import com.delycomp.tourismperu.Entidades.FotoTuristica;
import com.delycomp.tourismperu.Entidades.LugarTuristico;
import com.delycomp.tourismperu.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class DetalleLugarActivity extends AppCompatActivity   implements OnMapReadyCallback {
    public static final String EXTRA_LUGART = "DetalleLugar.Lugarturistico";
    RecyclerView rv;
    private GoogleMap mMap;
    private static LugarTuristico lugarTuristico;
    List<FotoTuristica> fotoTuristicas = new ArrayList<>();
    AdaptadorRVDetalle adaptadorRV;
    FotoTuristica usuario1;
    private TextView descr_det,tel_det,tar_det,horario_det;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_lugar);

        Log.d("AUX", " "+fotoTuristicas.size() );

//        toolbar.setTitleTextColor(getResources().getColor(R.color.ColorPrimary));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        descr_det=(TextView) findViewById(R.id.descr_detalle);
        tel_det=(TextView) findViewById(R.id.telefono_det);
        tar_det=(TextView) findViewById(R.id.tarifaaaa);
        horario_det=(TextView) findViewById(R.id.horarioatencion);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras!=null){
            lugarTuristico = extras.getParcelable(EXTRA_LUGART);
            getSupportActionBar().setTitle(lugarTuristico.getName_lugar());
            Log.d("AUX", " "+"nombre");
            String tarifa,telefono, horario;
            //VALIDAR IDIOMA
            if (MainActivity.idioma.equals("Español")){
                horario= lugarTuristico.getHorario();

                tarifa="    Tarifa: "+lugarTuristico.getTarifa();
                telefono="Tel: "+lugarTuristico.getTelefono();
                descr_det.setText(lugarTuristico.getDesc_es());
            }else{
                horario= lugarTuristico.getHorario().replace("Lunes","Monday");
                horario=horario.replace("Domingo","Sunday");
                horario=horario.replace(" a "," to ");
                horario=horario.replace("de","from");
                horario=horario.replace("-","to");
                tarifa="   Rate: "+lugarTuristico.getTarifa();
                telefono="Phone: "+lugarTuristico.getTelefono();
                descr_det.setText(lugarTuristico.getDesc_en());
            }
            //VALIDAR TELEFONO
            if (lugarTuristico.getTelefono().equals("0")){
                tel_det.setText(R.string.telefonoVacio);
            }else{
                tel_det.setText(telefono);
            }
            //VALIDAR TARIFA
            if (lugarTuristico.getTarifa().equals("0.0") || lugarTuristico.getTarifa().equals("0")){
                tar_det.setText(R.string.tarifaVacio);
            }else{
                tar_det.setText(tarifa);
            }
            //VALIDAR HORARIO DE ATENCION
            if (  lugarTuristico.getHorario()==null ||lugarTuristico.getHorario().equals("")||lugarTuristico.getHorario().equals("null")){
                horario_det.setVisibility(View.GONE);

            }else{
                horario_det.setText(horario);

            }

//            //INICIAR Fotos
            LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
            rv= (RecyclerView) findViewById(R.id.recycler_det);
            rv.setLayoutManager(layoutManager);
            Listar("http://delycomp.com/prueba/fotosID.php?id="+lugarTuristico.getCod_lugar());
//            Iniciar MAPa
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map11);
            mapFragment.getMapAsync(this);
        }
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }
    private void Listar(String url) {
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        response = response.replace("][", ",");
                        if (response.length() == 1) {
                        } else {
                            JSONArray ja = null;
                            try {
                                ja = new JSONArray(response);

                                CargarMarcadores(ja);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        queue.add(stringRequest);
    }
    private void CargarMarcadores(JSONArray ja){
        for (int i = 0; i < ja.length(); i+=4) {
            try {
                usuario1 = new FotoTuristica(ja.getString(i),ja.getString(i+1),ja.getString(i+2),ja.getString(i+3));
                fotoTuristicas.add(usuario1);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Log.d("AUXloca", " "+fotoTuristicas.size() );
            rv = (RecyclerView) findViewById(R.id.recycler_det);
            adaptadorRV = new AdaptadorRVDetalle(fotoTuristicas);
            rv.setAdapter(adaptadorRV);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if(lugarTuristico!=null) {
            // Add a marker in Sydney and move the camera
            LatLng ubication= new LatLng(lugarTuristico.getLatitud(),lugarTuristico.getLongitud());
            Integer icono = 0;
            switch(lugarTuristico.getTipo_lugar()){
                case "Parque":
                    icono = R.mipmap.parque;
                    break;
                case "Parroquia":
                    icono = R.mipmap.iglesia;
                    break;
                case "Iglesia":
                    icono = R.mipmap.iglesia;
                    break;
                case "Monumento":
                    icono = R.mipmap.monumento;
                    break;
                case "Convento":
                    icono = R.mipmap.convento;
                    break;
                case "Casonas":
                    icono = R.mipmap.casona;
                    break;
                case "Reserva Ecologica":
                    icono = R.mipmap.reserva;
                    break;
                case "Complejo Arqueologico":
                    icono = R.mipmap.arqueologico;
                    break;

            }
            Marker marcador = mMap.addMarker(new MarkerOptions()
                    .position(ubication)
                    .title(lugarTuristico.getName_lugar())
                    .icon(BitmapDescriptorFactory.fromResource(icono)));
            CameraUpdate miubicacion = CameraUpdateFactory.newLatLngZoom(ubication, 16);
            mMap.animateCamera(miubicacion);
        }else{
            Toast.makeText(this, "NULLO UBICATION", Toast.LENGTH_SHORT).show();
        }

    }
}
