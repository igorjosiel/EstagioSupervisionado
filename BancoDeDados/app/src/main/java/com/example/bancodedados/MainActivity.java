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
            bancoDados.execSQL("CREATE TABLE IF NOT EXISTS pessoas (id INTEGER PRIMARY KEY AUTOINCREMENT, nome VARCHAR, idade INT(3))");

            //bancoDados.execSQL("DROP TABLE pessoas");

            //Inserir dados
            //bancoDados.execSQL("INSERT INTO pessoas(nome, idade) VALUES ('Igor', 20)");
            //bancoDados.execSQL("INSERT INTO pessoas(nome, idade) VALUES ('Lucas', 40)");

            //bancoDados.execSQL("UPDATE pessoas SET nome = 'Mariana Silva' WHERE id = 1");
            //bancoDados.execSQL("DELETE FROM pessoas WHERE id = 2");
            bancoDados.execSQL("DELETE FROM pessoas");

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
            //String consulta = "SELECT nome, idade FROM pessoas WHERE 1=1 ORDER BY idade ASC LIMIT 3";
            String consulta = "SELECT id, nome, idade FROM pessoas WHERE 1=1 ORDER BY idade ";

            Cursor cursor = bancoDados.rawQuery(consulta, null);

            //Índices da tabela
            int indiceId = cursor.getColumnIndex("id");
            int indiceNome = cursor.getColumnIndex("nome");
            int indiceIdade = cursor.getColumnIndex("idade");

            cursor.moveToFirst();

            while (cursor != null)
            {
                String nome = cursor.getString(indiceNome);
                String idade = cursor.getString(indiceIdade);
                String id = cursor.getString(indiceId);

                Log.i("Resultado - id " + id + " / nome ", nome + " / idade " + idade);

                cursor.moveToNext();
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }

    }
}
