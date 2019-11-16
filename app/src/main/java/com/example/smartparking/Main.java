package com.example.smartparking;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toolbar;

import com.example.smartparking.Adaptere.ViewPagerAdapter;
import com.example.smartparking.Fragmente.Dashboard;
import com.example.smartparking.Fragmente.Harta;
import com.example.smartparking.Fragmente.Logare;
import com.example.smartparking.Fragmente.ParcariList;
import com.example.smartparking.Interfete.Inregistrare;


public class Main extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{


    private TabLayout taburi;

    private ViewPager view;
    private DrawerLayout drawer;
    private Toolbar toolbar;
    private ActionBarDrawerToggle mtoggle;
    private ViewPagerAdapter adapter;
    private NavigationView meniulateral;
    private BdComunica comunica;
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(!esteconectat(Main.this))
        {
            buildDialog(Main.this).show();
        }
        comunica=new BdComunica();
        meniulateral=findViewById(R.id.meniulateral);
        menu=meniulateral.getMenu();
        taburi=(TabLayout) findViewById(R.id.taburi);
        drawer=(DrawerLayout) findViewById(R.id.drawer_layout);


        view=(ViewPager) findViewById(R.id.pagina);
        adapter=new ViewPagerAdapter(getSupportFragmentManager());
        adapter.Adauga((new ParcariList()),"Parcari");
        adapter.Adauga(new Harta(),"Harta");
        if(comunica.useruid()==null) {
                adapter.Adauga(new Logare(), "Logare");
                menu.removeItem(R.id.selec5);
                menu.removeItem(R.id.selec4);
        }
        else {
            adapter.Adauga(new Dashboard(),"Contul Meu");
            menu.removeItem(R.id.selec6);


        }
        view.setAdapter(adapter);
        taburi.setupWithViewPager(view);

        mtoggle=new ActionBarDrawerToggle(this,drawer,R.string.open,R.string.close);
        drawer.addDrawerListener(mtoggle);
        mtoggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        meniulateral.bringToFront();
        meniulateral.setNavigationItemSelectedListener(this);
        taburi.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                toolbar= (Toolbar) findViewById(R.id.toolbar);

                if(taburi.getSelectedTabPosition()==2)
                {
                    toolbar.setVisibility(View.GONE);

                }
                if(taburi.getSelectedTabPosition()==0)
                {
                    toolbar.setVisibility(View.VISIBLE);

                }

            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {


            }
        });



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mtoggle.onOptionsItemSelected(item))
        {

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public  boolean esteconectat(Context context)
    {

        ConnectivityManager cm= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo=cm.getActiveNetworkInfo();

        if(netinfo!=null&&netinfo.isConnectedOrConnecting())
        {
            android.net.NetworkInfo wifi=cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile=cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if((mobile!=null&&mobile.isConnectedOrConnecting())||(wifi!=null&&wifi.isConnectedOrConnecting()))
        {
            return  true;
        }else
        {
            return false;
        }
        }
return  false;

    }

    public AlertDialog.Builder buildDialog(Context c)
    {

        AlertDialog.Builder builder=new AlertDialog.Builder(c);
        builder.setTitle("Conexiune Internet");
        builder.setMessage("Trebuie sa aveti conexiune de internet pentru a putea rula aplicatia.Apasati OK pentru a iesi");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        return builder;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        int id=menuItem.getItemId();

        if(id==R.id.parcare1)
        {
         taburi.getTabAt(0).select();
            drawer.closeDrawers();
        }

        if(id==R.id.selec4)
        {
            taburi.getTabAt(2).select();
            drawer.closeDrawers();
        }
        if(id==R.id.selec2)
        {
            Intent intent = new Intent(this, Inregistrare.class);
            startActivity(intent);

        }

            if (id == R.id.selec5) {
                if (comunica.useruid() != null) {
                    comunica.signout();
                }
                Intent intent = new Intent(this, Main.class);
                startActivity(intent);
                this.finish();
            }
            if(id==R.id.selec6)
            {
                taburi.getTabAt(2).select();
                drawer.closeDrawers();
            }
        return false;
    }
}






