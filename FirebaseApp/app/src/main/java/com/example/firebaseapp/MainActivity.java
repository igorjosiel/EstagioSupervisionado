package com.example.firebaseapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    //private DatabaseReference referencia = FirebaseDatabase.getInstance().getReference();
    //private FirebaseAuth usuario = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //DatabaseReference usuarios = referencia.child("usuarios");

        //DatabaseReference usuarioPesquisa = usuarios.child("-L2LBNI8du5WSBtZqpKg");
        //Query usuarioPesquisa = usuarios.orderByChild("nome").equalTo("Igor");
        //Query usuarioPesquisa = usuarios.orderByKey().limitToFirst(2);
        //Query usuarioPesquisa = usuarios.orderByKey().limitToLast(2);

        /*Maior ou igual*/
        //Query usuarioPesquisa = usuarios.orderByChild("idade").startAt(40);

        /*Menor ou igual*/
        //Query usuarioPesquisa = usuarios.orderByChild("idade").endAt(22);

        /*Entre dois valores*/
        //Query usuarioPesquisa = usuarios.orderByChild("idade").startAt(18).endAt(30);

        /*Filtrar por palavras*/
        /*Query usuarioPesquisa = usuarios.orderByChild("nome").startAt("J").endAt("J" + "\uf8ff");

        usuarioPesquisa.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Usuario dadosUsuario = dataSnapshot.getValue(Usuario.class);

                //Log.i("Dados usuário: ", "nome" + dadosUsuario.getNome() + " idade: " + dadosUsuario.getIdade());
                Log.i("Dados usuário: ", dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        })

        /*Usuario usuario = new Usuario();
        usuario.setNome("José Renato");
        usuario.setSobrenome("Silva");
        usuario.setIdade(31);

        usuarios.push().setValue(usuario);

        Deslogar usuário
        usuario.signOut();

        Logar  usuário
        usuario.signInWithEmailAndPassword("igorjosielvitalino@gmail.com", "igorjosiel").addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                {
                    Log.i("SignIn", "Usuário ao logar usuário");
                } else
                {
                    Log.i("SignIn", "Erro ao logar usuário");
                }
            }
        });

        Verifica usuario logado
        if (usuario.getCurrentUser() != null)
        {
            Log.i("CreateUser", "Usuário logado");
        } else
        {
            Log.i("CreateUser", "Usuário não está logado");
        }

        Cadastro de usuário
        usuario.createUserWithEmailAndPassword("igorjosielvitalino@gmail.com", "igorjosiel").addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                {
                    Log.i("CreateUser", "Sucesso ao cadastrar usuário");
                } else
                {
                    Log.i("CreateUser", "Erro ao cadastrar usuário");
                }
            }
        });

        referencia.child("usuarios2").child("001").child("nome").setValue("200");

        Usuario usuario = new Usuario();
        usuario.setNome("Lucas");
        usuario.setSobrenome("Souza");
        usuario.setIdade(30);

        usuarios.child("001").setValue(usuario);

        DatabaseReference usuarios = referencia.child("usuarios");

        usuarios.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i("FIREBASE", dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/
    }
}
