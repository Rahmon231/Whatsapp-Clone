package com.lemzeeyyy.whatsappclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lemzeeyyy.whatsappclone.model.Users;

import java.util.HashMap;
import java.util.Objects;

public class MessageActivity extends AppCompatActivity {
    private ImageView profilePic;
    private TextView userName;
    private EditText editMessage;
    private ImageButton sendBtn;
    private RecyclerView recyclerView;
    FirebaseUser firebaseUser;
    DatabaseReference reference;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        profilePic = findViewById(R.id.imageview_profile);
        userName = findViewById(R.id.username_mes);
        editMessage = findViewById(R.id.text_send);
        sendBtn = findViewById(R.id.btn_send);
        recyclerView = findViewById(R.id.recycler_view_mes);

//        Toolbar toolbar = findViewById(R.id.toolbar3);
//        setSupportActionBar(toolbar);
//        toolbar.setTitle("");
//        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
        intent = getIntent();
        String userid = intent.getStringExtra("userid");
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("MyUsers")
                .child(userid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users user = snapshot.getValue(Users.class);
                assert user != null;
                userName.setText(user.getUsername());

                if(user.getImageURL().equals("default")){
                    profilePic.setImageResource(R.mipmap.ic_launcher);
                }else {
                    Glide.with(MessageActivity.this)
                            .load(user.getImageURL())
                            .into(profilePic);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = editMessage.getText().toString().trim();
                if(!message.equals("")){
                    sendMessage(firebaseUser.getUid(),userid,message);
                }else {
                    Toast.makeText(MessageActivity.this, "You can't send an empty message", Toast.LENGTH_SHORT).show();
                }
                editMessage.setText("");
            }
        });

    }

    private void sendMessage(String sender, String receiver, String message) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("senderId",sender);
        hashMap.put("receiverId",receiver);
        hashMap.put("message",message);
        hashMap.put("isseen",false);

        reference.child("Chats").push().setValue(hashMap);
    }
}