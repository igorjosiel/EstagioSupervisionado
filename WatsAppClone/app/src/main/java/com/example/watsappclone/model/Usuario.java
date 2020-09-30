package com.example.watsappclone.model;

import com.example.watsappclone.config.ConfiguracaoFirebase;
import com.google.firebase.database.DatabaseReference;

public class Usuario {

    private String id;
    private String nome;
    private String senha;
    private String email;

    public Usuario() {
    }

    @Exclude
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Exclude
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void salvar()
    {
        DatabaseReference firebase = ConfiguracaoFirebase.getFirebaseDatabase();
        DatabaseReference usuario = firebase.child("usuarios").child(getId());

        usuario.setValue(this);
    }
}
