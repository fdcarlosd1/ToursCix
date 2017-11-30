package layout;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.delycomp.tourismperu.R;
import com.delycomp.tourismperu.Activities.RaWikiActivity;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link InicioFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link InicioFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InicioFragment extends Fragment  implements  View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ImageView imageAR, imageGeo, imageLista;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private OnFragmentInteractionListener mListener;

    public InicioFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InicioFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InicioFragment newInstance(String param1, String param2) {
        InicioFragment fragment = new InicioFragment();
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
        View view = inflater.inflate(R.layout.fragment_inicio, container, false);
        imageAR =(ImageView) view.findViewById((R.id.imageAR));
        imageGeo =(ImageView) view.findViewById((R.id.imageGeo));
        imageLista = (ImageView) view.findViewById((R.id.imageLista));


//        imageLista.setOnTouchListener(new View.OnTouchListener() {
//
//            @Override public boolean onTouch(View v, MotionEvent event) {
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                        ImageView view = (ImageView) v;
//                        view.getDrawable().setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP);
//                        view.invalidate();
//                        break;
//                    case  MotionEvent.ACTION_UP:
//                        break;
//                    case MotionEvent.ACTION_CANCEL:
//                        ImageView view1 = (ImageView) v;
//                        //clear the overlay
//                        view1.getDrawable().clearColorFilter();
//                        view1.invalidate();
//
//                } return false;
//            }
//        });



        imageLista.setOnClickListener(this);
        imageAR.setOnClickListener(this);
        imageGeo.setOnClickListener(this);
        return view;
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
//    public FragmentManager getSupportFragmentManager() {
//        return mFragments.getSupportFragmentManager();
//    }

    @Override
    public void onClick(View v) {

        //get the image view ImageView imageView = (ImageView)findViewById(R.id.ImageView); //set the ontouch listener imageView.setOnTouchListener(new OnTouchListener() { @Override public boolean onTouch(View v, MotionEvent event) { switch (event.getAction()) { case MotionEvent.ACTION_DOWN: { ImageView view = (ImageView) v; //overlay is black with transparency of 0x77 (119) view.getDrawable().setColorFilter(0x77000000,PorterDuff.Mode.SRC_ATOP); view.invalidate(); break; } case MotionEvent.ACTION_UP: case MotionEvent.ACTION_CANCEL: { ImageView view = (ImageView) v; //clear the overlay view.getDrawable().clearColorFilter(); view.invalidate(); break; } } return false; } });

        FragmentTransaction trans = getFragmentManager().beginTransaction();

        switch (v.getId()) {
            case R.id.imageLista:
                Fragment  fragment1= new PrincipalFragment();
                trans.replace(R.id.fragment_inicio, fragment1).commit();

                break;
            case R.id.imageAR:
                Intent intent = new Intent(getContext(),RaWikiActivity.class);
                startActivity(intent);
                break;
            case R.id.imageGeo:

                Fragment  fragment= new MapFragment();
                trans.replace(R.id.fragment_inicio, fragment).commit();

                break;
            default:
                Toast.makeText(v.getContext(), ""+v.getId(), Toast.LENGTH_SHORT).show();
                break;
        }
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
}
//