package com.example.catjam2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    EditText usernameField;
    EditText passwordField;
    public static final String USERNAME = "username";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameField = findViewById(R.id.username);
        passwordField = findViewById(R.id.password);
    }

    public void login(View view) {
        String inputUsername = usernameField.getText().toString();
        String inputPassword = passwordField.getText().toString();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("users");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.child(inputUsername).getValue(User.class);
                if(user==null){
                    Toast.makeText(LoginActivity.this, "User with that username doesn't exist!", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(user.getPassword().equals(inputPassword)){
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra(USERNAME, user.getUsername());
                        startActivity(intent);
                        Toast.makeText(LoginActivity.this, "Log in successful!", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(LoginActivity.this, "Incorrect password!", Toast.LENGTH_SHORT).show();
                    }
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // calling on cancelled method when we receive
                // any error or we are not able to get the data.
                Toast.makeText(LoginActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void register(View view) {
        Intent launchRegisterActivity = new Intent(this, RegisterActivity.class);
        startActivity(launchRegisterActivity);
    }
}