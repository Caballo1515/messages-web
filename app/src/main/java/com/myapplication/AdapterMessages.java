package com.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterMessages extends RecyclerView.Adapter<AdapterMessages.ViewHolderItem> {

    List<Message> listMessage;
    private Context context;

    public AdapterMessages(List<Message> listMessage) {
        this.listMessage = listMessage;
    }

    @NonNull
    @Override
    public AdapterMessages.ViewHolderItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message, null, false);
        context = parent.getContext();
        return new ViewHolderItem(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterMessages.ViewHolderItem holder, int position) {
        holder.asignar(listMessage.get(position));
    }

    @Override
    public int getItemCount() {
        return listMessage.size();
    }

    public class ViewHolderItem extends RecyclerView.ViewHolder {
        TextView name, mail, subject, messageView;
        public ViewHolderItem(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.nombre_text);
            subject = itemView.findViewById(R.id.subject_text);
            mail = itemView.findViewById(R.id.mail_text);
            messageView = itemView.findViewById(R.id.mesage_text);

        }


        public void asignar(Message message) {
            name.setText(message.getName());
            subject.setText(message.getSubject());
            mail.setText(message.getMail());
            messageView.setText(message.getMessage());
        }
    }
}
