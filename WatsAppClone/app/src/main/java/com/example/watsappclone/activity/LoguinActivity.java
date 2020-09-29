package com.example.watsappclone.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.watsappclone.R;
import com.example.watsappclone.config.ConfiguracaoFirebase;
import com.example.watsappclone.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class LoguinActivity extends AppCompatActivity {

    private TextInputEditText campoEmail, campoSenha;
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loguin);

        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();

        campoEmail = findViewById(R.id.editLoguinEmail);
        campoSenha = findViewById(R.id.editLoguinSenha);
    }

    public void logarUsuario(Usuario usuario)
    {
        autenticacao.signInWithEmailAndPassword(
                usuario.getEmail(), usuario.getSenha()
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                {
                    abrirTelaPrincipal();
                } else
                {
                    String excecao = "";

                    try {
                        throw task.getException();
                    } catch (FirebaseAuthInvalidUserException e) {
                        excecao = "Usuário não está cadastrado!";
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        excecao = "Email e senha não correspondem!";
                    } catch (Exception e) {
                        excecao = "Erro ao autenticar usuário" + e.getMessage();

                        e.printStackTrace();
                    }

                    Toast.makeText(LoguinActivity.this, excecao , Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void validarAutenticacaoUsuario(View view)
    {
        //Recuperar textos dos campos
        String textoEmail = campoEmail.getText().toString();
        String textoSenha = campoSenha.getText().toString();

        //Validar se e-mail e senha foram digitados
        if (!textoEmail.isEmpty()) //Verifica e-mail
        {
            if (!textoSenha.isEmpty()) //Verifica senha
            {
                Usuario usuario = new Usuario();
                usuario.setEmail(textoEmail);
                usuario.setSenha(textoSenha);

                logarUsuario(usuario);
            } else
            {
                Toast.makeText(LoguinActivity.this, "Preencha a senha!", Toast.LENGTH_SHORT).show();
            }
        } else
        {
            Toast.makeText(LoguinActivity.this, "Preencha o e-mail!", Toast.LENGTH_SHORT).show();
        }
    }

    public void abrirTelaCadastro(View view)
    {
        Intent intent = new Intent(LoguinActivity.this, CadastroActivity.class);
        startActivity(intent);
    }

    public void abrirTelaPrincipal()
    {
        Intent intent = new Intent(LoguinActivity.this, MainActivity.class);
        startActivity(intent);
    }
}