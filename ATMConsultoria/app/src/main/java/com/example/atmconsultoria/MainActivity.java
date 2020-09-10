package com.example.atmconsultoria;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enviarEmail();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_principal, R.id.nav_servico, R.id.nav_clientes,
                R.id.nav_contato, R.id.nav_sobre)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    public void enviarEmail()
    {
        //String celular = "tel:11996352894";
        //String imagem = "https://www.viagenscinematograficas.com.br/2020/02/10-melhores-praias-do-brasil.html";
        //String endereco = "https://www.google.pt/maps/place/Ventanilla,+Peru/@-11.8825598,-77.2110074,12z/data=!3m1!4b1!4m13!1m7!3m6!1s0x9105c5f619ee3ec7:0x14206cb9cc452e4a!2sLima,+Peru!3b1!8m2!3d-12.0463731!4d-77.042754!3m4!1s0x9105d39b15cfb90d:0xe832fbc8423fbf88!8m2!3d-11.8784382!4d-77.1264267?hl=pt-PT";

        //Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(celular));
        //Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(imagem));
        //Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(endereco));

        Intent intent = new Intent(Intent.ACTION_SEND);

        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"viniciusnemtrindade@gmail.com"});
        intent.putExtra(Intent.EXTRA_SUBJECT, "TCC");
        intent.putExtra(Intent.EXTRA_TEXT, "Bora pegar no TCC?");

        intent.setType("message/rfc822");
        //intent.setType("text/plain");
        //intent.setType("image/*");
        //intent.setType("aplication/pdf");

        startActivity(Intent.createChooser(intent, "Escolha um app de E-mail"));
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
