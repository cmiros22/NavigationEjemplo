package com.example.navigationejemplo;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MenuPActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ProgressDialog progreso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menup);

        agregarToolbar();
        drawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);

        NavigationView navigationView=(NavigationView)findViewById(R.id.nav_view);
        View hview= navigationView.inflateHeaderView(R.layout.cabera_drawer);
        TextView text=(TextView)hview.findViewById(R.id.etiqueta_username);
        text.setText("EJEMPLO DE INICIO");

        if(navigationView!=null){
            prepararDrawer(navigationView);
            seleccionarItem(navigationView.getMenu().getItem(0));
        }
    }

    private void prepararDrawer(NavigationView navigationView){
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                menuItem.setChecked(true);
                seleccionarItem(menuItem);
                drawerLayout.closeDrawers();
                return true;
            }
        });
    }

    public void seleccionarItem(MenuItem itemDrawer){
        Fragment fragmentoGenerico=null;
        FragmentManager fragmentManager=getSupportFragmentManager();

        switch (itemDrawer.getItemId()){
            case R.id.item_inicio:
                fragmentoGenerico = new FragmentoInicio();
                break;
            case R.id.item_agregar:
                fragmentoGenerico=new AgregarVehiculo();
                break;
            case R.id.item_reciente:
                fragmentoGenerico=new FragmentRecientes();
                break;
            case R.id.item_sincronizar:
                //
                break;

        }
        if(fragmentoGenerico!=null){
            fragmentManager.beginTransaction().replace(R.id.contenedor_principal,fragmentoGenerico).commit();
        }
        setTitle(itemDrawer.getTitle());
    }

    private void agregarToolbar(){
        Toolbar toolbar=(Toolbar)findViewById(R.id.toobar);
        setSupportActionBar(toolbar);
        final ActionBar ab=getSupportActionBar();
        if(ab!=null){
            ab.setHomeAsUpIndicator(R.drawable.drawer_toggle);
            ab.setDisplayHomeAsUpEnabled(true);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        //getMenuInflater().inflate(R.menu.menu_actividad_principal,menu);
        getMenuInflater().inflate(R.menu.overflow, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.item1:
                progreso=new ProgressDialog(this);
                progreso.setMessage("Cerrando Sesión...");
                progreso.show();
                finish();
                startActivity(new Intent(this,MainActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
