package com.delycomp.tourismperu.Activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.delycomp.tourismperu.Entidades.LugarTuristico;
import com.delycomp.tourismperu.R;
import com.wikitude.architect.ArchitectJavaScriptInterfaceListener;
import com.wikitude.architect.ArchitectStartupConfiguration;
import com.wikitude.architect.ArchitectView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class RaWikiActivity extends AppCompatActivity implements ArchitectJavaScriptInterfaceListener {
    private ArchitectView architectView;
    private LocationProviderRA locationProvider;
    private View mContentView;
    private View mLoadingView;
    private static final String TAG = "InstantTracking";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ra_wiki);

        architectView = (ArchitectView) this.findViewById( R.id.architectView );
        mContentView = findViewById(R.id.architectView);
        mLoadingView = findViewById(R.id.loading_spinner);

        //crosffeing
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


        final ArchitectStartupConfiguration config = new ArchitectStartupConfiguration();
        config.setFeatures(ArchitectStartupConfiguration.Features.Geo);
        config.setLicenseKey("rUdOjcVxDgx7eGAF33LoxypNOUuxdj82jv8PXjHl2g1bKQRuzEWHXfalGtcwPik+bep1XM1OjuGgaoorq54q0m8eazvKW1UCBq3XuEdMFipAIVq+rKWYix00FPxjKPYCGU1VoJAgXz7mQOdbjaMqoTJnPpgXOl+39LobwwFy2YhTYWx0ZWRfXyvc3DcZeo6Ssma2S8pVHGDNdI0mo8QXHbuf8+HtTHredCUKTdjyNRUupplt8nKiRSp7zEHDvBGvPljv7PhFlOfvdEwUs9oBYbs6pMxF0NhUq/hK0xJcGg2eTPq0U3527mNIgtIPUkLVq0RZFKKf36AGmWpSclk1IPAZJlfDgPlhAJGeVa9QTV2fQmAFxrRApGoHVbCeKtj+s/mj8FVHoKF0v7XxJOoyHBRX4S6OWbS6Rq8vGNagH7DqVvyVkFXT2PmcbSutAzTYwEeG3k/LQaEz90jyI2liCIJUB/ixl8gmCoStVBu6MO6+E7yqMLmz3k2/HdyBtf5ZBk0wXUmH6tcXnBUABYskBUD4g1VKyZtTCHkBoZ4+rZ8+kQxXLxAgYhYNnlZMC0xcZ6Rt2qZoxoxjz3bHiFHomxANLhFWq+gMz79RZ/2loNeZW8CT8IGlz90oOx4m/aDJA9HGfn3dbqpmbiAzJvAr5uIqTAmF6tM/W0gd3Qk6d3wg1rC1/ZhO0lJUASXSAtGD");

        architectView.onCreate( config );
        this.architectView.addArchitectJavaScriptInterfaceListener( this );

        locationProvider = new LocationProviderRA(this, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                if (location!=null && RaWikiActivity.this.architectView != null ) {
                    // check if location has altitude at certain accuracy level & call right architect method (the one with altitude information)
                    if ( location.hasAltitude() && location.hasAccuracy() && location.getAccuracy()<7) {
                        RaWikiActivity.this.architectView.setLocation( location.getLatitude(), location.getLongitude(), location.getAltitude(), location.getAccuracy() );
                    } else {
                        RaWikiActivity.this.architectView.setLocation( location.getLatitude(), location.getLongitude(), location.hasAccuracy() ? location.getAccuracy() : 1000 );
                    }
                }
            }

            @Override public void onStatusChanged(String s, int i, Bundle bundle) {}
            @Override public void onProviderEnabled(String s) {}
            @Override public void onProviderDisabled(String s) {}
        });
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        architectView.onPostCreate();
        try {
            if (MainActivity.idioma.equals("Español")) {
                architectView.load( "file:///android_asset/10_BrowsingPois_5_NativeDetailScreen/index.html" );
            }else{
                architectView.load( "file:///android_asset/10_BrowsingPois_5_NativeDetailScreen/indexEn.html" );
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        architectView.onResume();
        // start location updates
        locationProvider.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        architectView.onPause();
        // stop location updates
        locationProvider.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        architectView.onDestroy();
    }

    @Override
    public void onJSONObjectReceived(JSONObject jsonObject) {
        try {
            switch (jsonObject.getString("name")) {
                case "markerselected":

                    String id = jsonObject.getString("id");
                    Double latitudx = Double.parseDouble(jsonObject.getString("latitud"));
                    Double longitudex = Double.parseDouble(jsonObject.getString("longitud"));
                    String nombre = jsonObject.getString("title");
                    String telefono = jsonObject.getString("telefono");
                    String tipo_lugar = jsonObject.getString("tipo_lugar");
                    String tarifa =  jsonObject.getString("tarifa");
                    String departamento =  jsonObject.getString("departamento");
                    String valoracion= jsonObject.getString("Valoracion");
                    String hratencion =  jsonObject.getString("Hratencion");
                    String descripcion_lugarEN =  jsonObject.getString("descripcion_lugarEN");
                    String descripcion_lugarES = jsonObject.getString("description");

                    LugarTuristico lugarTuristico = new LugarTuristico(id,nombre,latitudx,longitudex,tipo_lugar,telefono,descripcion_lugarES,descripcion_lugarEN,tarifa,departamento,valoracion,hratencion);

                    Intent intent1 = new Intent(this,DetalleLugarActivity.class);
                    intent1.putExtra(DetalleLugarActivity.EXTRA_LUGART,lugarTuristico);
                    startActivity(intent1);

            }
        } catch (JSONException e) {
            Log.e(TAG, "onJSONObjectReceived: ", e);
        }
    }

}
