package com.torito;



import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements IntroFunciones.onFragmentInteractionListener{
    DBHelper dbManager;
    SharedPreferences preferences;
    private boolean isFirstTime()
    {

        boolean ranBefore = preferences.getBoolean("RanBefore", false);
        boolean terminos = preferences.getBoolean("Terminos",false);
        if (!ranBefore) {
            // first time
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("RanBefore", true);
            editor.commit();
        }
        //Segun yo ya quedo
        return !ranBefore || !terminos;
    }

    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);

        preferences = getPreferences(MODE_PRIVATE);
        setContentView(R.layout.activity_main);
        /*//Esta sirve para ver si ya presiono rescatenme
        boolean rescate = preferences.getBoolean("Rescate", false);*/
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Atras"));
        tabLayout.addTab(tabLayout.newTab().setText("Torito Rescues"));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        if (isFirstTime()) {
            //Creamos la base de datos
            dbManager =new DBHelper(this);
            Log.i("BaseDatos","Creada con exito");
            /*Cursor res;
            res = dbManager.getData();
            res.moveToFirst();
            int idus = res.getInt(res.getColumnIndex(dbManager.CLIENT_COLUMN_IDUS));
            String name=res.getString(res.getColumnIndex(dbManager.CLIENT_COLUMN_NAME));
            Log.i("BaseDatos","Cliente: "+name+"  Idus: "+idus);*/
            Introduccion();
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        BotonRescatenme rescatenme = new BotonRescatenme();
        transaction.add(R.id.fragment_container, rescatenme,"rescateBoton")
                .commit();

    }

      @Override
      protected void onResume() {
          super.onResume();


      }


    public void Introduccion(){
        //Método de actividad a mostrar
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        IntroFunciones funcFrag = new IntroFunciones();
        transaction.add(R.id.fragment_container, funcFrag,"fragIntro")
                .commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
