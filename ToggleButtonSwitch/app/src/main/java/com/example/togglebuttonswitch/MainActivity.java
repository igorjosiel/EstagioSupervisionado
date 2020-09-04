package com.example.togglebuttonswitch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    private ToggleButton toggleSenha;
    private Switch switchSenha;
    private TextView textResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toggleSenha = findViewById(R.id.toggleButtonSenha);
        switchSenha = findViewById(R.id.switchSenha);
        textResult = findViewById(R.id.result);

        this.adicionarListener();
    }

    public void adicionarListener()
    {
        switchSenha.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(switchSenha.isChecked()) textResult.setText("Switch desligado");
                if(!switchSenha.isChecked()) textResult.setText("Switch desligado");
            }
        });
    }

    public void enviar(View view)
    {
        //if(switchSenha.isChecked()) textResult.setText("Switch desligado");
        //if(!switchSenha.isChecked()) textResult.setText("Switch desligado");

        if(toggleSenha.isChecked()) textResult.setText("ToggleButton desligado");
        if(!toggleSenha.isChecked()) textResult.setText("ToggleButton desligado");
    }
}
