package com.myapplication;

import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView recycler;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recycler = this.findViewById(R.id.recicler);
        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        db.collection("users").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.w("hols", "Listen failed.", error);
                    return;
                }
                List<Message> list = new ArrayList<>();
                for (int cont=0;cont <value.size();  cont++){
                    DocumentSnapshot document = value.getDocuments().get(cont);
                    Message message = new Message();
                    message.setName((String) document.getData().get("name"));
                    message.setId(document.getId());
                    message.setMail((String) document.getData().get("mail"));
                    message.setSubject((String) document.getData().get("subject"));
                    message.setMessage((String) document.getData().get("message"));
                    list.add(message);
                }
                AdapterMessages adapterMessages = new AdapterMessages(list);
                recycler.setAdapter(adapterMessages);
            }
        });

        /*
        db.collection("users").get().addOnSuccessListener(queryDocumentSnapshots -> {
            for (int cont=0;cont <queryDocumentSnapshots.size();  cont++){
                DocumentSnapshot document = queryDocumentSnapshots.getDocuments().get(cont);
                Message message = new Message();
                message.setName((String) document.getData().get("name"));
                message.setId(document.getId());
                message.setMail((String) document.getData().get("mail"));
                message.setSubject((String) document.getData().get("subject"));
                message.setMessage((String) document.getData().get("message"));
                list.add(message);
            }
            AdapterMessages adapterMessages = new AdapterMessages(list);
            recycler.setAdapter(adapterMessages);
        });

         */
        System.out.println("Hola");

    }

    public void notificar(){
    }
}