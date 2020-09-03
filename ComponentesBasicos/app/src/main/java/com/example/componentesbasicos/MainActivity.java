package com.example.componentesbasicos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    private EditText campoNome;
    private TextInputEditText campoEmail;
    private TextView textoResultado;

    private CheckBox checkVerde, checkAmarelo, checkAzul, checkPreto, checkVermelho;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        campoNome = findViewById(R.id.editNome);
        campoEmail = findViewById(R.id.editEmail);
        textoResultado = findViewById(R.id.textResult);

        // Checkbox
        checkAmarelo = findViewById(R.id.checkAmarelo);
        checkVerde = findViewById(R.id.checkVerde);
        checkAzul = findViewById(R.id.checkAzul);
        checkPreto = findViewById(R.id.checkPreto);
        checkVermelho = findViewById(R.id.checkVermelho);
    }

    public void checkbox()
    {
        String texto = "";

        if(checkVerde.isChecked())
        {
            //String corSelecionada = checkVerde.getText().toString();
            texto += "Verde selecionado - ";
        }

        if(checkPreto.isChecked())
        {
            texto += "Preto selecionado - ";
        }

        if(checkVermelho.isChecked())
        {
            texto += "Vermelho selecionado - ";
        }

        if(checkAmarelo.isChecked())
        {
            texto += "Amarelo selecionado - ";
        }

        if(checkAzul.isChecked())
        {
            texto += "Azul selecionado - ";
        }

        textoResultado.setText(texto);
    }

    public void enviar(View view)
    {
        this.checkbox();

        /*
        String nome = campoNome.getText().toString();
        String email = campoEmail.getText().toString();

        textoResultado.setText("nome: " + nome + "E-mail: " + email);
         */
    }

    public void limpar(View view)
    {
        campoNome.setText("");
        campoEmail.setText("");
    }
}
