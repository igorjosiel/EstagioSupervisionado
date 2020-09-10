package com.example.mediaplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private SeekBar seekbarVolume;
    private AudioManager audioManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.teste);

        this.inicializarSeekbar();
    }

    private void inicializarSeekbar()
    {
        seekbarVolume = findViewById(R.id.seekBarVolume);

        //Configurar o audio manager
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        //Recupera os valores de volume máximo e o volume atual
        int volumeMaximo = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int volumeAtual = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        //Configurar os valores máximos para o Seekbar
        seekbarVolume.setMax(volumeMaximo);

        //Configurar o processo atual do seekbar
        seekbarVolume.setProgress(volumeAtual);

        seekbarVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void executarSom(View view)
    {
        if (mediaPlayer != null) mediaPlayer.start();
    }

    public void pausarMusica(View view)
    {
        if (mediaPlayer.isPlaying()) mediaPlayer.pause();
    }

    public void pararMusica(View view)
    {
        if (mediaPlayer.isPlaying()) mediaPlayer.stop();

        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.teste);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mediaPlayer != null && mediaPlayer.isPlaying())
        {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (mediaPlayer.isPlaying()) mediaPlayer.pause();
    }
}
