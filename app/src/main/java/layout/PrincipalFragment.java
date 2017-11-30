package layout;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.delycomp.tourismperu.Adaptadores.AdaptadorRVLista;
import com.delycomp.tourismperu.Entidades.LugarTuristico;
import com.delycomp.tourismperu.R;
import org.json.JSONArray;
import org.json.JSONException;
import java.util.ArrayList;
import java.util.List;
import static com.delycomp.tourismperu.R.string.lu_rec;
import static com.delycomp.tourismperu.R.string.lu_tod;
import static com.delycomp.tourismperu.R.string.lugar_recom;
import static com.delycomp.tourismperu.R.string.lugar_todos;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PrincipalFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PrincipalFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PrincipalFragment extends Fragment implements  View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    List<LugarTuristico> listLugares = new ArrayList<>();
    private static String OPCION_LUGAR = "Recomendado";
    // The system "short" animation time duration, in milliseconds. This
    // duration is ideal for subtle animations or animations that occur
    // very frequently.
    Button butonbonito;
    RecyclerView rv;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View mContentView;
    private View mLoadingView;
    AdaptadorRVLista adaptadorRVLista;

    private OnFragmentInteractionListener mListener;

    public PrincipalFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PrincipalFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PrincipalFragment newInstance(String param1, String param2) {
        PrincipalFragment fragment = new PrincipalFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_principal, container, false);
        butonbonito = (Button) v.findViewById(R.id.butonBonito);
        //crosffeing
        mContentView = v.findViewById(R.id.recycler);
        mLoadingView = v.findViewById(R.id.loading_spinnerLista);
        rv = (RecyclerView) v.findViewById(R.id.recycler);
        rv.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity().getApplicationContext());
        rv.setLayoutManager(llm);
        Listar("http://delycomp.com/prueba/LugaresFotos.php");


        // Initially hide the content view.
        mContentView.setVisibility(View.GONE);

        // Retrieve and cache the system's default "short" animation time.

        mContentView.setAlpha(0f);
        mContentView.setVisibility(View.VISIBLE);

        // Animate the content view to 100% opacity, and clear any animation
        // listener set on the view.
        mContentView.animate()
                .alpha(1f)
                .setDuration(2000)
                .setListener(null);

        // Animate the loading view to 0% opacity. After the animation ends,
        // set its visibility to GONE as an optimization step (it won't
        // participate in layout passes, etc.)
        mLoadingView.animate()
                .alpha(0f)
                .setDuration(2000)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mLoadingView.setVisibility(View.GONE);
                    }
                });


        //finmish crossfing

        butonbonito.setOnClickListener(this);
        return v;
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

    private void Listar(String url) {
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        response = response.replace("][", ",");
                        if (response.length() == 1) {
                            Toast.makeText(getContext(), "Vacio todo", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(stringRequest);
    }
    private void CargarMarcadores(JSONArray ja){
        listLugares.clear();
        for (int i = 0; i < ja.length(); i+=12) {
            try {
                LugarTuristico usuario1 = new LugarTuristico(ja.getString(i),ja.getString(i+1),Double.parseDouble(ja.getString(i+2)),Double.parseDouble(ja.getString(i+3)),ja.getString(i+4),ja.getString(i+5),ja.getString(i+6),ja.getString(i+7),ja.getString(i+8),ja.getString(i+9),ja.getString(i+10),ja.getString(i+11));
                listLugares.add(usuario1);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        rv = (RecyclerView) getActivity().findViewById(R.id.recycler);
         adaptadorRVLista = new AdaptadorRVLista(listLugares);
        rv.setAdapter(adaptadorRVLista);
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.butonBonito:
                final CharSequence[] items = {getString(lu_rec), getString(lu_tod)};
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                int hola;
                if (OPCION_LUGAR.equals("Recomendado")) {
                    hola = 0;
                } else {
                    hola = 1;
                }
                builder.setSingleChoiceItems(items, hola, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        if (items[item].toString().equals("Mostrar Recomendados")) {
                            if (!OPCION_LUGAR.equals("Recomendado")) {
                                OPCION_LUGAR = "Recomendado";
                                butonbonito.setText(getString(lugar_recom));
                                Listar("http://delycomp.com/prueba/LugaresFotos.php");
                            }
                        } else {
                            if (!OPCION_LUGAR.equals("Todos")) {
                                OPCION_LUGAR = "Todos";
                                butonbonito.setText(getString(lugar_todos));
                                Listar("http://delycomp.com/prueba/lugaresfotostodos.php");
                            }
                        }
                        dialog.cancel();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
                break;
        }
    }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
