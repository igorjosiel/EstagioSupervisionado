package com.example.pedrapapeltesoura;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void selecionarPedra(View view)
    {
        this.opcaoSelecionada("pedra");
    }

    public void selecionarPapel(View view)
    {
        this.opcaoSelecionada("papel");
    }

    public void selecionarTesoura(View view)
    {
        this.opcaoSelecionada("tesoura");
    }

    public void opcaoSelecionada(String opcaoUsuario)
    {
        ImageView imageResult = findViewById(R.id.imageResult);
        TextView textResult = findViewById(R.id.textResult);

        int numero = new Random().nextInt(3);

        String[] opcoes = {"pedra", "papel", "tesoura"};
        String opcaoApp = opcoes[numero];

        switch (opcaoApp)
        {
            case "pedra" :
                imageResult.setImageResource(R.drawable.pedra);
                break;
            case "papel" :
                imageResult.setImageResource(R.drawable.papel);
                break;
            case "tesoura" :
                imageResult.setImageResource(R.drawable.tesoura);
                break;
        }

        if(// App ganhador
                (opcaoApp == "papel" && opcaoUsuario == "pedra") ||
                (opcaoApp == "pedra" && opcaoUsuario == "tesoura") ||
                (opcaoApp == "tesoura" && opcaoUsuario == "papel"))
        {
            textResult.setText("Você perdeu!");
        }
        else if(// Usuário ganhador
                (opcaoApp == "pedra" && opcaoUsuario == "papel") ||
                (opcaoApp == "papel" && opcaoUsuario == "tesoura") ||
                (opcaoApp == "tesoura" && opcaoUsuario == "pedra"))
        {
            textResult.setText("Você venceu!");
        }
        else // Empate
        {
            textResult.setText("Vocês empataram!");
        }

        System.out.println("Item clicado: " + opcaoApp);
    }
}
