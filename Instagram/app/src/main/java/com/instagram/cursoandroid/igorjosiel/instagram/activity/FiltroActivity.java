package com.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.instagram.R;
import com.instagram.adapter.AdapterMiniaturas;
import com.instagram.helper.ConfiguracaoFirebase;
import com.instagram.helper.RecyclerItemClickListener;
import com.instagram.helper.UsuarioFirebase;
import com.instagram.model.Postagem;
import com.instagram.model.Usuario;
import com.zomato.photofilters.FilterPack;
import com.zomato.photofilters.imageprocessors.Filter;
import com.zomato.photofilters.utils.ThumbnailItem;
import com.zomato.photofilters.utils.ThumbnailsManager;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class FiltroActivity extends AppCompatActivity {

    static
    {
        System.loadLibrary("NativeImageProcessor");
    }

    private ImageView imageFotoEscolhida;
    private Bitmap imagem;
    private Bitmap imagemFiltro;
    private TextInputEditText textDescricaoFiltro;
    private List<ThumbnailItem> listaFiltros;
    private String idUsuarioLogado;
    private Usuario usuarioLogado;
    private ProgressBar progressBar;
    private boolean estaCarregado;

    private RecyclerView recyclerFiltros;
    private AdapterMiniaturas adapterMiniaturas;

    private DatabaseReference usuariosRef;
    private DatabaseReference usuarioLogadoRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtro);

        //Configurações iniciais
        listaFiltros = new ArrayList<>();
        idUsuarioLogado = UsuarioFirebase.getIdentificadorUsuario();
        usuariosRef = ConfiguracaoFirebase.getFirebase().child("usuarios");

        imageFotoEscolhida = findViewById(R.id.imageFotoEscolhida);
        recyclerFiltros = findViewById(R.id.recyclerFiltros);
        textDescricaoFiltro = findViewById(R.id.textDescricaoFiltro);
        progressBar = findViewById(R.id.progressFiltro);

        recuperarDadosUsuarioLogado();

        Toolbar toolbar = findViewById(R.id.toolbarPrincipal);
        toolbar.setTitle("Filtros");
        setSupportActionBar( toolbar );

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_black_24dp);

        //Recupera a imagem escolhida pelo usuário
        Bundle bundle = getIntent().getExtras();
        if( bundle != null ){
            byte[] dadosImagem = bundle.getByteArray("fotoEscolhida");
            imagem = BitmapFactory.decodeByteArray(dadosImagem, 0, dadosImagem.length );
            imageFotoEscolhida.setImageBitmap( imagem );
            imagemFiltro = imagem.copy(imagem.getConfig(), true );

            //Configura recyclerview de filtros
            adapterMiniaturas = new AdapterMiniaturas(listaFiltros, getApplicationContext());
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false );
            recyclerFiltros.setLayoutManager( layoutManager );
            recyclerFiltros.setAdapter( adapterMiniaturas );

            //Adiciona evento de clique no recyclerview
            recyclerFiltros.addOnItemTouchListener(
                    new RecyclerItemClickListener(
                            getApplicationContext(),
                            recyclerFiltros,
                            new RecyclerItemClickListener.OnItemClickListener() {
                                @Override
                                public void onItemClick(View view, int position) {

                                    ThumbnailItem item = listaFiltros.get(position);

                                    imagemFiltro = imagem.copy(imagem.getConfig(), true );
                                    Filter filtro = item.filter;
                                    imageFotoEscolhida.setImageBitmap( filtro.processFilter(imagemFiltro) );
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

            recuperarFiltros();
        }
    }

    private void  carregando(boolean estado){

        if( estado ){
            estaCarregado = true;
            progressBar.setVisibility(View.VISIBLE);
        }else {
            estaCarregado = false;
            progressBar.setVisibility(View.GONE);
        }
    }

    private void recuperarDadosUsuarioLogado(){

        carregando(true);
        usuarioLogadoRef = usuariosRef.child( idUsuarioLogado );
        usuarioLogadoRef.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        //Recupera dados de usuário logado
                        usuarioLogado = dataSnapshot.getValue( Usuario.class );
                        carregando(false);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                }
        );
    }

    private void recuperarFiltros(){

        ThumbnailsManager.clearThumbs();
        listaFiltros.clear();

        //Configurar filtro normal
        ThumbnailItem item = new ThumbnailItem();
        item.image = imagem;
        item.filterName = "Normal";
        ThumbnailsManager.addThumb( item );

        //Lista todos os filtros
        List<Filter> filtros = FilterPack.getFilterPack(getApplicationContext());
        for (Filter filtro: filtros ){

            ThumbnailItem itemFiltro = new ThumbnailItem();
            itemFiltro.image = imagem;
            itemFiltro.filter = filtro;
            itemFiltro.filterName = filtro.getName();

            ThumbnailsManager.addThumb( itemFiltro );
        }

        listaFiltros.addAll( ThumbnailsManager.processThumbs(getApplicationContext()) );
        adapterMiniaturas.notifyDataSetChanged();
    }

    private void publicarPostagem(){

        if( estaCarregado ){
            Toast.makeText(getApplicationContext(),
                    "Carregando dados, aguarde!",
                    Toast.LENGTH_SHORT ).show();
        }else {

            final Postagem postagem = new Postagem();
            postagem.setIdUsuario( idUsuarioLogado );
            postagem.setDescricao( textDescricaoFiltro.getText().toString() );

            //Recuperar dados da imagem para o firebase
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            imagemFiltro.compress(Bitmap.CompressFormat.JPEG, 70, baos);
            byte[] dadosImagem = baos.toByteArray();

            //Salvar imagem no firebase storage
            StorageReference storageRef = ConfiguracaoFirebase.getFirebaseStorage();
            StorageReference imagemRef = storageRef
                    .child("imagens")
                    .child("postagens")
                    .child( postagem.getId() + ".jpeg");

            UploadTask uploadTask = imagemRef.putBytes( dadosImagem );
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(FiltroActivity.this,
                            "Erro ao salvar a imagem, tente novamente!",
                            Toast.LENGTH_SHORT).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    //Recuperar local da foto
                    Uri url = taskSnapshot.getDownloadUrl();
                    postagem.setCaminhoFoto( url.toString() );

                    //Salvar postagem
                    if( postagem.salvar() ){

                        //Atualizar qtde de postagens
                        int qtdPostagem = usuarioLogado.getPostagens() + 1;
                        usuarioLogado.setPostagens( qtdPostagem );
                        usuarioLogado.atualizarQtdPostagem();

                        Toast.makeText(FiltroActivity.this,
                                "Sucesso ao salvar postagem!",
                                Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_filtro, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch ( item.getItemId() ){
            case R.id.ic_salvar_postagem :
                publicarPostagem();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return false;
    }
}
