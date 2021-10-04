package com.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class AdapterMessages extends RecyclerView.Adapter<AdapterMessages.ViewHolderItem> {

    List<Message> listMessage;
    private FirebaseFirestore db;
    private Context context;

    public AdapterMessages(List<Message> listMessage , FirebaseFirestore db ) {
        this.listMessage = listMessage;
        this.db = db;
    }

    @NonNull
    @Override
    public AdapterMessages.ViewHolderItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message, null, false);
        context = parent.getContext();
        return new ViewHolderItem(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterMessages.ViewHolderItem holder, @SuppressLint("RecyclerView") int position) {
        holder.asignar(listMessage.get(position));
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.collection("users").document(listMessage.get(position).getId())
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("TAG", "DocumentSnapshot successfully deleted!");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("TAG", "Error deleting document", e);
                            }
                        });
            }
        });
    }

    @Override
    public int getItemCount() {
        return listMessage.size();
    }

    public class ViewHolderItem extends RecyclerView.ViewHolder {
        TextView name, mail, subject, messageView, time;
        Button delete;

        public ViewHolderItem(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.nombre_text);
            subject = itemView.findViewById(R.id.subject_text);
            mail = itemView.findViewById(R.id.mail_text);
            messageView = itemView.findViewById(R.id.mesage_text);
            delete = itemView.findViewById(R.id.delete);
            time = itemView.findViewById(R.id.time);
        }


        public void asignar(Message message) {
            name.setText(message.getName());
            subject.setText(message.getSubject());
            mail.setText(message.getMail());
            messageView.setText(message.getMessage());
            time.setText(message.getTime());
        }
    }
}
