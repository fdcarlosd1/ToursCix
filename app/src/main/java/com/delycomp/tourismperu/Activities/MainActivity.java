package com.delycomp.tourismperu.Activities;
import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.delycomp.tourismperu.R;

import java.util.Locale;

import layout.InicioFragment;
import layout.MapFragment;
import layout.PrincipalFragment;

public class MainActivity extends AppCompatActivity implements InicioFragment.OnFragmentInteractionListener, PrincipalFragment.OnFragmentInteractionListener, NavigationView.OnNavigationItemSelectedListener, MapFragment.OnFragmentInteractionListener {

    public static String idioma = "Español";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.CAMERA, android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.READ_EXTERNAL_STORAGE}, 3);
            } else {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.CAMERA, android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.READ_EXTERNAL_STORAGE}, 3);
            }
        }
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        //REEMPLAZAR EL FRAGMENT
        Fragment fragmnet = new InicioFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.content_main, fragmnet).commit();

        //HABILITAR EL NAVIGATION VIEW
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragmnet = null;
        Boolean fragmentoselecionado = false;
        String mClassToLaunchPackage = getPackageName();
        if (id == R.id.realidad) {
            Intent intent = new Intent(this,RaWikiActivity.class);
            startActivity(intent);
        } else if (id == R.id.map) {
            fragmentoselecionado = true;
            fragmnet = new MapFragment();
        } else if (id == R.id.inicio) {
            fragmentoselecionado = true;
            fragmnet = new PrincipalFragment();
        } else if (id == R.id.InfoApp) {
            fragmentoselecionado = true;
            fragmnet = new InicioFragment();
        } else if (id == R.id.idioma) {
            final CharSequence[] items = {"Español", "English"};
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Languages");
            int hola;
            if (idioma.equals("Español")) {
                hola = 0;
            } else {
                hola = 1;
            }
            builder.setSingleChoiceItems(items, hola, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int item) {
                    Resources res = getResources();
// Change locale settings in the app.
                    DisplayMetrics dm = res.getDisplayMetrics();
                    android.content.res.Configuration conf = res.getConfiguration();
                    if (items[item].toString().equals("Español")) {
                        if(!MainActivity.idioma.equals("Español")) {
                            conf.setLocale(new Locale("es", "ES")); // API 17+ only.
// Use conf.locale = new Locale(...) if targeting lower versions
                            res.updateConfiguration(conf, dm);
                            idioma = "Español";
                            Intent g_Es = new Intent(getBaseContext(), MainActivity.class);
                            startActivity(g_Es);
                            finish();
                        }
                    } else {
                        if(!MainActivity.idioma.equals("Ingles")) {
                            idioma = "Ingles";
                            conf.setLocale(new Locale("en", "EN")); // API 17+ only.
// Use conf.locale = new Locale(...) if targeting lower versions
                            res.updateConfiguration(conf, dm);
                            Intent g_Es = new Intent(getBaseContext(), MainActivity.class);
                            startActivity(g_Es);
                            finish();
                        }
                    }
                    dialog.cancel();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }
        if (fragmentoselecionado) {
            getSupportFragmentManager().beginTransaction().replace(R.id.content_main, fragmnet).commit();

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void auxx(Fragment aux) {
        getSupportFragmentManager().beginTransaction().replace(R.id.content_main, aux).commit();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == 3) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            } else {
                Toast.makeText(this, "Se recomienda habilitar todos los permisos para el funcionamiento de la aplicación.", Toast.LENGTH_SHORT).show();
            }
            if (grantResults.length > 0 && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            } else {
                Toast.makeText(this, "Se recomienda habilitar todos los permisos para el funcionamiento de la aplicación.", Toast.LENGTH_SHORT).show();
            }
            if (grantResults.length > 0 && grantResults[2] == PackageManager.PERMISSION_GRANTED) {
            } else {
                Toast.makeText(this, "Se recomienda habilitar todos los permisos para el funcionamiento de la aplicación.", Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

}
