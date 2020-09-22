package com.example.organize.config;

import com.google.firebase.auth.FirebaseAuth;

public class ConfiguracaoFirebase {
    private static FirebaseAuth autenticacao;

    //Retorna a instância do Firebase
    public static FirebaseAuth getFirebaseAutenticacao()
    {
        if(autenticacao == null)
        {
            autenticacao = FirebaseAuth.getInstance();
        }
        return autenticacao;
    }
}
