package com.myapplication;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private static final  String CHANNEL_ID = "NOTIFICACION";
    RecyclerView recycler;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String aux_notifica = "";
    private String notifica = "";
    private int cont=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recycler = this.findViewById(R.id.recicler);
        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        createNotificationChannel();

        db.collection("users").addSnapshotListener((value, error) -> {
            if (error != null) {
                Log.w("hols", "Listen failed.", error);
                return;
            }
            List<Message> list = new ArrayList<>();
            for (int cont = 0; cont < Objects.requireNonNull(value).size(); cont++){
                DocumentSnapshot document = value.getDocuments().get(cont);
                Message message = new Message();
                message.setName((String) Objects.requireNonNull(document.getData()).get("name"));
                message.setId(document.getId());
                message.setMail((String) document.getData().get("mail"));
                message.setSubject((String) document.getData().get("subject"));
                message.setMessage((String) document.getData().get("message"));
                message.setTime((String) document.getData().get("date"));
                list.add(message);
            }
            try {
                list.sort((message, t1) -> t1.getTime().compareToIgnoreCase(message.getTime()));
            }catch (Exception ignored){
            }
            AdapterMessages adapterMessages = new AdapterMessages(list, db);
            recycler.setAdapter(adapterMessages);

            notifica=list.get(0).getTime();
            if(!(cont==0||notifica.equalsIgnoreCase("")||notifica.equalsIgnoreCase(aux_notifica))){
                String text = "";
                if(notifica.equalsIgnoreCase("ERROR")){
                    text = "Somebody is looking your website";
                }else{
                    text = "New message from " + list.get(0).getName();
                }
                notificar(text);

            }
            try {
                aux_notifica = list.get(1).getTime();
            }catch (Exception e){
                aux_notifica ="aux";
            }

            cont++;

        });
    }

    public void notificar(String text){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark)
                .setContentTitle("New message")
                .setContentText(text)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(1111, builder.build());
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        CharSequence name = getString(R.string.channel_name);
        String description = getString(R.string.channel_description);
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
        channel.setDescription(description);
        // Register the channel with the system; you can't change the importance
        // or other notification behaviors after this
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }

}