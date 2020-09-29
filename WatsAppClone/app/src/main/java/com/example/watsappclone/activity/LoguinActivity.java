package com.example.watsappclone.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.watsappclone.R;

public class LoguinActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loguin);
    }

    public void abrirTelaCadastro(View view)
    {
        Intent intent = new Intent(LoguinActivity.this, CadastroActivity.class);
        startActivity(intent);
    }
}