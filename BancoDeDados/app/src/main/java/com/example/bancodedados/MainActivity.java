package com.example.bancodedados;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try{

            //Criar banco de dados
            SQLiteDatabase bancoDados = openOrCreateDatabase("app", MODE_PRIVATE, null);

            //Criar tabela
            bancoDados.execSQL("CREATE TABLE IF NOT EXISTS pessoas (nome VARCHAR, idade INT(3))");

            //Inserir dados
            //bancoDados.execSQL("INSERT INTO pessoas(nome, idade) VALUES ('Mariana', 18)");
            //bancoDados.execSQL("INSERT INTO pessoas(nome, idade) VALUES ('Pedro', 50)");

            //Recuperar pessoas
            //String consulta = "SELECT nome, idade FROM pessoas WHERE nome = 'Igor' AND idade = 23";
            //String consulta = "SELECT nome, idade FROM pessoas WHERE idade >= 30 OR idade = 18";
            //String consulta = "SELECT nome, idade FROM pessoas WHERE nome IN('Igor, Maria')";
            //String consulta = "SELECT nome, idade FROM pessoas WHERE idade BETWEEN 20 AND 30";

            String filtro = "mar";

            //String consulta = "SELECT nome, idade FROM pessoas WHERE nome LIKE '%" + filtro + "%' ";
            //String consulta = "SELECT nome, idade FROM pessoas WHERE 1=1 ORDER BY idade ASC";
            //String consulta = "SELECT nome, idade FROM pessoas WHERE 1=1 ORDER BY nome ASC";
            //String consulta = "SELECT nome, idade FROM pessoas WHERE 1=1 ORDER BY idade ASC";
            //String consulta = "SELECT nome, idade FROM pessoas WHERE 1=1 ORDER BY idade DESC";
            String consulta = "SELECT nome, idade FROM pessoas WHERE 1=1 ORDER BY idade ASC LIMIT 3";

            Cursor cursor = bancoDados.rawQuery(consulta, null);

            //Índices da tabela
            int indiceNome = cursor.getColumnIndex("nome");
            int indiceIdade = cursor.getColumnIndex("idade");

            cursor.moveToFirst();

            while (cursor != null)
            {
                String nome = cursor.getString(indiceNome);
                String idade = cursor.getString(indiceIdade);

                Log.i("Resultado - nome ", nome + " / idade: " + idade);

                cursor.moveToNext();
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }

    }
}
