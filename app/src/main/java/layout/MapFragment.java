package layout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.delycomp.tourismperu.Entidades.LugarTuristico;
import com.delycomp.tourismperu.Activities.MainActivity;
import com.delycomp.tourismperu.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import org.json.JSONArray;
import org.json.JSONException;
import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MapFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapFragment extends Fragment  implements OnMapReadyCallback {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private static final int SOLICITUD_PERMISO_WRITE_CALL_LOG = 0;
    private GoogleMap mMap;
    private Marker marcador,marcador1;
    private MapView m;
    double lat = 0.0;
    double lng = 0.0;
    private int creciente = 0;
    LocationManager locationManager;
    private OnFragmentInteractionListener mListener;
    LugarTuristico usuario1;
    public MapFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MapFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MapFragment newInstance(String param1, String param2) {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

    }
    @Override
    public void onResume() {
        super.onResume();
        m.onResume();
    }
    @Override
    public void onPause() {
        super.onPause();
        m.onPause();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        m.onDestroy();
    }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        m.onLowMemory();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
   if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED) {
            solicitarPermiso(android.Manifest.permission.ACCESS_FINE_LOCATION, "Sin el permiso"+
                            " administrar llamadas no podrá ubicar los lugar turisticos mas cerca",
                    SOLICITUD_PERMISO_WRITE_CALL_LOG, getActivity());
        }
        View rootView= inflater.inflate(R.layout.fragment_map, container, false);

        try {

            MapsInitializer.initialize(this.getActivity());
            m = (MapView) rootView.findViewById(R.id.mapView);
            m.onCreate(savedInstanceState);
            m.getMapAsync(this);
        }
        catch (InflateException e){
            Log.e(TAG, "Inflate exception");
        }
        checkLocation();
        creciente=0;
        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        Listar("http://delycomp.com/prueba/listarlugaresturisticos.php/");
        miUbicacion();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    //AGREGAR UBICACIÓN DE GPS
    public void MultiplesMarcadores(double lat, double lng, String name, Integer icon,String desc) {
        LatLng coordenadas = new LatLng(lat, lng);
             marcador1 = mMap.addMarker(new MarkerOptions()
                .position(coordenadas)
                .title(name)
                     .snippet(desc)
                .icon(BitmapDescriptorFactory.fromResource(icon)));
    }
    public void agregarMarcador(double lat, double lng, String name, Integer icon) {
        LatLng coordenadas = new LatLng(lat, lng);
        if (marcador != null) marcador.remove();

        marcador = mMap.addMarker(new MarkerOptions()
                .position(coordenadas)
                .title(name)
                .icon(BitmapDescriptorFactory.fromResource(icon)));
        if(creciente<1) {
            CameraUpdate miubicacion = CameraUpdateFactory.newLatLngZoom(coordenadas, 16);
            mMap.animateCamera(miubicacion);
        }
        creciente = creciente +1;
    }
    private void Listar(String url){
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        response = response.replace("][",",");
                        if (response.length()==1){
                            Toast.makeText(getContext(), "Vacio todo", Toast.LENGTH_SHORT).show();
                        }else{
                            JSONArray ja =null;
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
                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(stringRequest);
    }
    private void CargarMarcadores(JSONArray ja){
        Integer icono = 0;
        String idioma = MainActivity.idioma;
        if (idioma.equals("Español")){

        }
        for (int i = 0; i < ja.length(); i+=12) {
            try {
                usuario1 = new LugarTuristico(ja.getString(i),ja.getString(i+1),Double.parseDouble(ja.getString(i+2)),Double.parseDouble(ja.getString(i+3)),ja.getString(i+4),ja.getString(i+5),ja.getString(i+6),ja.getString(i+7),ja.getString(i+8),ja.getString(i+9),ja.getString(i+10),ja.getString(i+11));

                switch(usuario1.getTipo_lugar()){
                    case "Parque":
                        icono = R.mipmap.parque;
                        break;
                    case "Parroquia":
                        icono = R.mipmap.iglesia;
                        break;
                    case "Monumento":
                        icono = R.mipmap.monumento;
                        break;
                    case "Iglesia":
                        icono = R.mipmap.iglesia;
                        break;
                    case "Convento":
                        icono = R.mipmap.convento;
                        break;
                    case "Reserva Ecologica":
                        icono = R.mipmap.reserva;
                        break;
                    case "Complejo Arqueologico":
                        icono = R.mipmap.arqueologico;
                        break;

                    case "Casonas":
                        icono = R.mipmap.casona;
                        break;
                }
                if (idioma.equals("Español")){
                    MultiplesMarcadores(usuario1.getLatitud(),usuario1.getLongitud(),usuario1.getName_lugar(),icono,usuario1.getDesc_es())   ;
                }else{
                    MultiplesMarcadores(usuario1.getLatitud(),usuario1.getLongitud(),usuario1.getName_lugar(),icono,usuario1.getDesc_en())   ;
                }

//    listaLugares.add(usuario1);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
    private void actualizarUbicacion(Location location) {
        if (location != null) {
            lat = location.getLatitude();
            lng = location.getLongitude();
            agregarMarcador(lat, lng,"holaa",R.mipmap.ubicacion);
        }
    }
    LocationListener locListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            actualizarUbicacion(location);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };
    private void miUbicacion() {
        if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        actualizarUbicacion(location);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,3000,0, locListener);
    }
    //PERMISO PARA UBICACION
    public static void solicitarPermiso(final String permiso, String justificacion,final int requestCode, final Activity actividad) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(actividad, permiso)){
            new AlertDialog.Builder(actividad)
                    .setTitle("Solicitud de permiso")
                    .setMessage(justificacion)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            ActivityCompat.requestPermissions(actividad,
                                    new String[]{permiso}, requestCode);
                        }})
                    .show();
        } else {
            ActivityCompat.requestPermissions(actividad,
                    new String[]{permiso}, requestCode);
        }
    }
    @Override public void onRequestPermissionsResult(int requestCode,String[] permissions, int[] grantResults) {
        if (requestCode == SOLICITUD_PERMISO_WRITE_CALL_LOG) {
            if (grantResults.length== 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(getActivity().getApplicationContext(),MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            } else {
                Toast.makeText(getActivity().getApplicationContext(), "Sin el permiso, no puedo realizar la " +
                        "acción", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private boolean checkLocation() {
        if (!isLocationEnabled())
            showAlert();
        return isLocationEnabled();
    }

    private void showAlert() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle("Activar Ubicación")
                .setMessage("Su ubicación esta desactivada.\npor favor active su ubicación " +
                        "usa esta app")
                .setPositiveButton("Configuración de ubicación", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(myIntent);
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    }
                });
        dialog.show();
    }

    private boolean isLocationEnabled() {
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }
}
