package com.youtube.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.youtube.R;
import com.youtube.adapter.AdapterVideo;
import com.youtube.model.Video;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //Widgets
    private RecyclerView recyclerVideos;

    private List<Video> videos = new ArrayList<>();
    private AdapterVideo adapterVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Inicializa os componentes
        recyclerVideos = findViewById(R.id.recyclerVideos);

        //Configura toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Youtube");
        setSupportActionBar( toolbar );

        //Configura Recyclerview
        recuperarVideos();
        adapterVideo = new AdapterVideo(videos, this);
        recyclerVideos.setHasFixedSize( true );
        recyclerVideos.setLayoutManager(new LinearLayoutManager(this));
        recyclerVideos.setAdapter( adapterVideo );
    }

    private void recuperarVideos(){
        Video video1 = new Video();
        video1.setTitulo("Vídeo 1 muito interessante!");
        videos.add( video1 );

        Video video2 = new Video();
        video2.setTitulo("Vídeo 2 muito interessante!");
        videos.add( video2 );
    }
}
