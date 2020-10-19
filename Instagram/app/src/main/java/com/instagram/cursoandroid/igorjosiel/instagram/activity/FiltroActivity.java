package com.instagram.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.instagram.R;
import com.instagram.adapter.AdapterMiniaturas;
import com.instagram.helper.RecyclerItemClickListener;
import com.zomato.photofilters.FilterPack;
import com.zomato.photofilters.imageprocessors.Filter;
import com.zomato.photofilters.utils.ThumbnailItem;
import com.zomato.photofilters.utils.ThumbnailsManager;

import java.util.ArrayList;
import java.util.List;

public class FiltroActivity extends AppCompatActivity {

    static
    {
        System.loadLibrary("NativeImageProcessor");
    }

    private ImageView imageFotoEscolhida;
    private Bitmap imagem;
    private Bitmap imagemFiltro;
    private List<ThumbnailItem> listaFiltros;

    private RecyclerView recyclerFiltros;
    private AdapterMiniaturas adapterMiniaturas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtro);

        listaFiltros = new ArrayList<>();

        imageFotoEscolhida = findViewById(R.id.imageFotoEscolhida);
        recyclerFiltros = findViewById(R.id.recyclerFiltros);

        Toolbar toolbar = findViewById(R.id.toolbarPrincipal);
        toolbar.setTitle("Filtros");
        setSupportActionBar( toolbar );

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_black_24dp);

        //Recupera a imagem escolhida pelo usuário
        Bundle bundle = getIntent().getExtras();
        if( bundle != null ){
            byte[] dadosImagem = bundle.getByteArray("fotoEscolhida");
            imagem = BitmapFactory.decodeByteArray(dadosImagem, 0, dadosImagem.length );
            imageFotoEscolhida.setImageBitmap( imagem );

            //Configura recyclerview de filtros
            adapterMiniaturas = new AdapterMiniaturas(listaFiltros, getApplicationContext());
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false );
            recyclerFiltros.setLayoutManager( layoutManager );
            recyclerFiltros.setAdapter( adapterMiniaturas );

            //Adiciona evento de clique no recyclerview
            recyclerFiltros.addOnItemTouchListener(
                    new RecyclerItemClickListener(
                            getApplicationContext(),
                            recyclerFiltros,
                            new RecyclerItemClickListener.OnItemClickListener() {
                                @Override
                                public void onItemClick(View view, int position) {

                                    ThumbnailItem item = listaFiltros.get(position);

                                    imagemFiltro = imagem.copy(imagem.getConfig(), true );
                                    Filter filtro = item.filter;
                                    imageFotoEscolhida.setImageBitmap( filtro.processFilter(imagemFiltro) );
                                }

                                @Override
                                public void onLongItemClick(View view, int position) {
                                }

                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                }
                            }
                    )
            );

            recuperarFiltros();
        }
    }

    private void recuperarFiltros(){

        ThumbnailsManager.clearThumbs();
        listaFiltros.clear();

        //Configurar filtro normal
        ThumbnailItem item = new ThumbnailItem();
        item.image = imagem;
        item.filterName = "Normal";
        ThumbnailsManager.addThumb( item );

        //Lista todos os filtros
        List<Filter> filtros = FilterPack.getFilterPack(getApplicationContext());
        for (Filter filtro: filtros ){

            ThumbnailItem itemFiltro = new ThumbnailItem();
            itemFiltro.image = imagem;
            itemFiltro.filter = filtro;
            itemFiltro.filterName = filtro.getName();

            ThumbnailsManager.addThumb( itemFiltro );
        }

        listaFiltros.addAll( ThumbnailsManager.processThumbs(getApplicationContext()) );
        adapterMiniaturas.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_filtro, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch ( item.getItemId() ){
            case R.id.ic_salvar_postagem :

                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return false;
    }
}
