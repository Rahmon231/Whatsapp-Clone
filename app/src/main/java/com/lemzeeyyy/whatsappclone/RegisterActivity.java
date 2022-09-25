package com.lemzeeyyy.whatsappclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    private EditText emailET, passwordET, userNameET;
    private Button registerBtn;
    FirebaseAuth auth;
    DatabaseReference myReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        emailET = findViewById(R.id.emailEditText);
        passwordET = findViewById(R.id.passwordET);
        userNameET = findViewById(R.id.usernameET);
        registerBtn = findViewById(R.id.buttonRegister);

        auth = FirebaseAuth.getInstance();
        ValueEventListener seenListenner;

        registerBtn.setOnClickListener(v -> {
            String email = emailET.getText().toString().trim();
            String password = passwordET.getText().toString().trim();
            String username = userNameET.getText().toString().trim();
           if(TextUtils.isEmpty(email)|| TextUtils.isEmpty(password) || TextUtils.isEmpty(username)){
               Toast.makeText(RegisterActivity.this, "Please Fill all Fields",
                       Toast.LENGTH_SHORT).show();
           }else {
               RegisterUser(username,email,password);
           }
        });
    }

    private void RegisterUser(final String username,String email, String password) {
        auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(task -> {

                    if(task.isSuccessful()){
                        FirebaseUser user = auth.getCurrentUser();
                        String userId = user.getUid();
                        myReference = FirebaseDatabase.getInstance()
                                .getReference("MyUsers")
                                .child(userId);

                        HashMap<String, String> usersInfo = new HashMap<>();
                        usersInfo.put("id",userId);
                        usersInfo.put("imageURL", "default");
                        usersInfo.put("status" , "offline");
                        usersInfo.put("username",username);

                        myReference.setValue(usersInfo)
                                .addOnCompleteListener(task1 -> {
                                    if(task1.isSuccessful()){
                                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();
                                    }
                                }).addOnFailureListener(e ->
                                        Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show());

                    }else {
                        Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(e ->
                        Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show());
    }
}