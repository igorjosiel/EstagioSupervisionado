package com.example.watsappclone.model;

import com.example.watsappclone.config.ConfiguracaoFirebase;
import com.example.watsappclone.helper.UsuarioFirebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Usuario implements Serializable {

    private String id;
    private String nome;
    private String senha;
    private String email;
    private String foto;

    public Usuario() {
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
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

    public void atualizar()
    {
        String identificadorUsuario = UsuarioFirebase.getIdentificadorUsuario();
        DatabaseReference database = ConfiguracaoFirebase.getFirebaseDatabase();

        DatabaseReference usuariosRef = database.child("usuarios")
                .child(identificadorUsuario);

        Map<String, Object> valoresUsuarios = converteraraMap();

        usuariosRef.updateChildren(valoresUsuarios);
    }

    @Exclude
    public Map<String, Object> converteraraMap()
    {
        HashMap<String, Object> usuarioMap = new HashMap<>();
        usuarioMap.put("email", getEmail());
        usuarioMap.put("nome", getNome());
        usuarioMap.put("foto", getFoto());

        return usuarioMap;
    }
}
