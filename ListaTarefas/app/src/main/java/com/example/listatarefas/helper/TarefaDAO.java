package com.example.listatarefas.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.listatarefas.model.Tarefa;

import java.util.ArrayList;
import java.util.List;

public class TarefaDAO implements ITarefaDAO {

    private SQLiteDatabase escreve;
    private SQLiteDatabase le;

    public TarefaDAO(Context context) {
        DB_Helper db = new DB_Helper(context);

        escreve = db.getWritableDatabase();
        le = db.getReadableDatabase();
    }

    @Override
    public boolean salvar(Tarefa tarefa) {

        ContentValues cv = new ContentValues();
        cv.put("nome", tarefa.getNomeTarefa());

        try {
            escreve.insert(DB_Helper.TABELA_TAREFAS, null, cv);

            Log.e("INFO", "Sucesso ao salvar a tarefa");
        } catch (Exception e) {
            Log.e("INFO", "Erro ao salvar tarefa " + e.getMessage());

            return false;
        }

        return true;
    }

    @Override
    public boolean atualizar(Tarefa tarefa) {

        ContentValues cv = new ContentValues();
        cv.put("nome", tarefa.getNomeTarefa());

        try {
            String[] args = {tarefa.getId().toString()};

            escreve.update(DB_Helper.TABELA_TAREFAS, cv, "id=?", args);

            Log.e("INFO", "Tarefa atualizada com sucesso!");
        } catch (Exception e) {
            Log.e("INFO", "Erro ao atualizar tarefa " + e.getMessage());

            return false;
        }

        return true;
    }

    @Override
    public boolean deletar(Tarefa tarefa) {

        try {
            String[] args = {tarefa.getId().toString()};

            escreve.delete(DB_Helper.TABELA_TAREFAS, "id=?", args);

            Log.e("INFO", "Tarefa excluída com sucesso!");
        } catch (Exception e) {
            Log.e("INFO", "Erro ao excluir tarefa " + e.getMessage());

            return false;
        }

        return true;
    }

    @Override
    public List<Tarefa> listar() {
        List<Tarefa> tarefas = new ArrayList<>();

        String sql = "SELECT * FROM " + DB_Helper.TABELA_TAREFAS + " ;";
        Cursor c = le.rawQuery(sql, null);

        while (c.moveToNext())
        {
            Tarefa tarefa = new Tarefa();

            Long id = c.getLong(c.getColumnIndex("id"));
            String nomeTarefa = c.getString(c.getColumnIndex("nome"));

            tarefa.setId(id);
            tarefa.setNomeTarefa(nomeTarefa);

            tarefas.add(tarefa);
        }

        return tarefas;
    }
}
