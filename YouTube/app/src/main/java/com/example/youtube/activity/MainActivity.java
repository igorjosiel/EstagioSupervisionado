package com.youtube.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.youtube.R;
import com.youtube.adapter.AdapterVideo;
import com.youtube.api.YoutubeService;
import com.youtube.helper.RetrofitConfig;
import com.youtube.helper.YoutubeConfig;
import com.youtube.model.Item;
import com.youtube.model.Resultado;
import com.youtube.model.Video;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerVideos;
    private MaterialSearchView searchView;

    private List<Item> videos = new ArrayList<>();
    private Resultado resultado;
    private AdapterVideo adapterVideo;

    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerVideos = findViewById(R.id.recyclerVideos);
        searchView = findViewById(R.id.searchView);

        retrofit = RetrofitConfig.getRetrofit();

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Youtube");
        setSupportActionBar( toolbar );

        recuperarVideos();

        //Configura métodos para SearchView
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
            }

            @Override
            public void onSearchViewClosed() {
            }
        });
    }

    private void recuperarVideos(){

        YoutubeService youtubeService = retrofit.create( YoutubeService.class );

        youtubeService.recuperarVideos(
            "snippet", "date", "20",
                YoutubeConfig.CHAVE_YOUTUBE_API, YoutubeConfig.CANAL_ID
        ).enqueue(new Callback<Resultado>() {
            @Override
            public void onResponse(Call<Resultado> call, Response<Resultado> response) {
                Log.d("resultado", "resultado: " + response.toString() );
                if( response.isSuccessful() ){
                    resultado = response.body();
                    videos = resultado.items;
                    configurarRecyclerView();
                }
            }

            @Override
            public void onFailure(Call<Resultado> call, Throwable t) {

            }
        });
    }

    public void configurarRecyclerView(){
        adapterVideo = new AdapterVideo(videos, this);
        recyclerVideos.setHasFixedSize( true );
        recyclerVideos.setLayoutManager(new LinearLayoutManager(this));
        recyclerVideos.setAdapter( adapterVideo );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        MenuItem item = menu.findItem(R.id.menu_search);
        searchView.setMenuItem( item );

        return true;
    }
}
