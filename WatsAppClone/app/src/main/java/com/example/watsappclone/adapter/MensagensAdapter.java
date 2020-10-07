package com.example.watsappclone.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.watsappclone.R;
import com.example.watsappclone.helper.UsuarioFirebase;
import com.example.watsappclone.model.Mensagem;

import java.net.URL;
import java.util.List;

public class MensagensAdapter extends RecyclerView.Adapter<MensagensAdapter.MyViewHolder> {

    private List<Mensagem> mensagens;
    private Context context;
    private static final int TIPO_REMETENTE = 0;
    private static final int TIPO_DESTINATARIO = 1;

    public MensagensAdapter(List<Mensagem> lista, Context c) {
        this.mensagens = lista;
        this.context = c;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View item = null;

        if (viewType == TIPO_REMETENTE)
        {
            item = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_mensagem_remetente, parent, false);
        } else if (viewType == TIPO_DESTINATARIO)
        {
            item = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_mensagem_destinatario, parent, false);
        }

        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Mensagem mensagem = mensagens.get(position);

        String msg = mensagem.getMensagem();
        String imagem = mensagem.getMensagem();

        if (imagem != null)
        {
            Uri url = Uri.parse(imagem);
            Glide.with(context).load(url).into(holder.imagem);

            //Esconder o texto
            holder.mensagem.setVisibility(View.GONE);
        } else
        {
            holder.mensagem.setText(msg);

            //Esconder a imagem
            holder.imagem.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mensagens.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView mensagem;
        ImageView imagem;

        public MyViewHolder(View itemView)
        {
            super(itemView);

            mensagem = itemView.findViewById(R.id.textMensagemTexto);
            imagem = itemView.findViewById(R.id.imageMensagemFoto);
        }
    }

    public int getItemViewType(int position)
    {
        Mensagem mensagem = mensagens.get(position);

        String idUsuario = UsuarioFirebase.getIdentificadorUsuario();

        if (idUsuario.equals(mensagem.getIdUsuario()))
        {
            return TIPO_REMETENTE;
        }

        return TIPO_DESTINATARIO;
    }
}
