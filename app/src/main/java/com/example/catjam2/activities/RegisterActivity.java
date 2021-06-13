package com.example.catjam2.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.catjam2.R;
import com.example.catjam2.classes.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {


    EditText username;
    EditText email;
    EditText password;
    EditText repeatPassword;
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference ref = db.getReference("users");
    public static final String USERNAME = "username";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        username = findViewById(R.id.register_username);
        email = findViewById(R.id.register_email);
        password = findViewById(R.id.register_password);
        repeatPassword = findViewById(R.id.register_repeat_password);
    }

    public void register(View view){
        String inputUsername = username.getText().toString();
        String inputEmail = email.getText().toString();
        String inputPassword = password.getText().toString();
        String inputRepeatPassword = repeatPassword.getText().toString();

        if(inputPassword.equals(inputRepeatPassword)){
            User user = new User(inputUsername,inputPassword,inputEmail);
            ref.child(inputUsername).setValue(user);
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra(USERNAME, inputUsername);
            startActivity(intent);
            Toast.makeText(this, "You have successfully registered!", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "Enter the same password!", Toast.LENGTH_SHORT).show();
        }
    }
}