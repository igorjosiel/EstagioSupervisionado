package com.example.watsappclone.activity;

import android.os.Bundle;

import com.example.watsappclone.adapter.ContatosAdapter;
import com.example.watsappclone.adapter.GrupoSelecionadoAdapter;
import com.example.watsappclone.config.ConfiguracaoFirebase;
import com.example.watsappclone.helper.RecyclerItemClickListener;
import com.example.watsappclone.helper.UsuarioFirebase;
import com.example.watsappclone.model.Usuario;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.AdapterView;

import com.example.watsappclone.R;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class GroupActivity extends AppCompatActivity {

    private RecyclerView recyclerViewMembros, recyclerViewMembrosSelecionados;
    private ContatosAdapter contatosAdapter;
    private GrupoSelecionadoAdapter grupoSelecionadoAdapter;
    private List<Usuario> listaMembros = new ArrayList<>();
    private List<Usuario> listaMembrosSelecionados = new ArrayList<>();
    private ValueEventListener valueEventListenerMembros;
    private DatabaseReference usuariosRef;
    private FirebaseUser usuarioAtual;
    private Toolbar toolbar;

    public void atualizarMembrosToolbar()
    {
        int totalSelecionados = listaMembrosSelecionados.size();
        int total = listaMembros.size() + totalSelecionados;

        toolbar.setSubtitle(totalSelecionados + " de " + total + " selecionados");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Novo grupo");
        setSupportActionBar(toolbar);

        //Configurações iniciais
        recyclerViewMembros = findViewById(R.id.recyclerMembros);
        recyclerViewMembrosSelecionados = findViewById(R.id.recyclerMembrosSelecionados);

        usuariosRef = ConfiguracaoFirebase.getFirebaseDatabase().child("usuarios");
        usuarioAtual = UsuarioFirebase.getUsuarioAtual();

        //Configurar o adapter
        contatosAdapter = new ContatosAdapter(listaMembros, getApplicationContext());

        //Configurar o recyclerview
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewMembros.setLayoutManager(layoutManager);
        recyclerViewMembros.setHasFixedSize(true);
        recyclerViewMembros.setAdapter(contatosAdapter);

        recyclerViewMembros.addOnItemTouchListener(new RecyclerItemClickListener(
                getApplicationContext(),
                recyclerViewMembros,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Usuario usuarioSelecionado = listaMembros.get(position);

                        //Remover usuário selecionado da lista
                        listaMembros.remove(usuarioSelecionado);
                        contatosAdapter.notifyDataSetChanged();

                        //Adiciona usuário na nova lista de selecionados
                        listaMembrosSelecionados.add(usuarioSelecionado);
                        grupoSelecionadoAdapter.notifyDataSetChanged();

                        atualizarMembrosToolbar();
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    }
                }
        ));

        //Configurar o recyclerview para os membros selecionados
        grupoSelecionadoAdapter = new GrupoSelecionadoAdapter(listaMembrosSelecionados, getApplicationContext());

        RecyclerView.LayoutManager layoutManagerHorizontal = new LinearLayoutManager(
                getApplicationContext(),
                LinearLayoutManager.HORIZONTAL,
                false
        );
        recyclerViewMembrosSelecionados.setLayoutManager(layoutManagerHorizontal);
        recyclerViewMembrosSelecionados.setHasFixedSize(true);
        recyclerViewMembrosSelecionados.setAdapter(grupoSelecionadoAdapter);

        recyclerViewMembrosSelecionados.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        ersas e getApplicationContext(),
                        recyclerViewMembrosSelecionados,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Usuario usuarioSelecionado = listaMembrosSelecionados.get(position);

                                //Remover da listagem de membros selecionados
                                listaMembrosSelecionados.remove(usuarioSelecionado);
                                grupoSelecionadoAdapter.notifyDataSetChanged();

                                //Adicionar à listagem de membros
                                listaMembros.add(usuarioSelecionado);

                                atualizarMembrosToolbar();
                            }

                            @Override
                            public void onLongItemClick(View view, int position) {

                            }

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            }
                        }
                )
        );

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void recuperarContatos()
    {
        valueEventListenerMembros = usuariosRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dados: dataSnapshot.getChildren())
                {
                    Usuario usuario = dados.getValue(Usuario.class);

                    String emailUsuarioAtual = usuarioAtual.getEmail();

                    if(!emailUsuarioAtual.equals(usuario.getEmail())) listaMembros.add(usuario);
                }

                contatosAdapter.notifyDataSetChanged();

                atualizarMembrosToolbar();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void onStart()
    {
        super.onStart();
        recuperarContatos();
    }

    public void onStop()
    {
        super.onStop();
        usuariosRef.removeEventListener(valueEventListenerMembros);
    }
}