package com.example.catjam2.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.example.catjam2.R;
import com.example.catjam2.classes.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    EditText usernameField;
    EditText passwordField;
    public static final String USERNAME = "username";
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference("users");
    ValueEventListener listener;
    CheckBox rememberMeCheckbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameField = findViewById(R.id.username);
        passwordField = findViewById(R.id.password);
        rememberMeCheckbox = findViewById(R.id.remember_me_checkbox);

        SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
        String checkbox = preferences.getString("remember", "");
        if(checkbox.equals("true")){
            if(preferences.getString("username", "").isEmpty()){
                Toast.makeText(this, "Couldn't retrieve username", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra(USERNAME, preferences.getString("username", ""));
                startActivity(intent);
                finish();
            }
        } else if(checkbox.equals("false")){
            Toast.makeText(this, "Please sign in or make a new account!", Toast.LENGTH_SHORT).show();
        }

        rememberMeCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(buttonView.isChecked()){
                    SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("remember", "true");
                    editor.putString("username", usernameField.getText().toString());
                    editor.apply();
                    Toast.makeText(LoginActivity.this, "Checked!", Toast.LENGTH_SHORT).show();
                } else {
                    SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("remember", "false");
                    editor.apply();
                    Toast.makeText(LoginActivity.this, "Unchecked!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void login(View view) {
        String inputUsername = usernameField.getText().toString();
        String inputPassword = passwordField.getText().toString();

        listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.child(inputUsername).getValue(User.class);
                if(user==null){
                    Toast.makeText(LoginActivity.this, "User with that username doesn't exist!", Toast.LENGTH_SHORT).show();
                }
                else {
                    if(user.getPassword().equals(inputPassword)){
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra(USERNAME, user.getUsername());
                        startActivity(intent);
                        finish();
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
        };

        ref.addListenerForSingleValueEvent(listener);


    }

    public void register(View view) {
        Intent launchRegisterActivity = new Intent(this, RegisterActivity.class);
        startActivity(launchRegisterActivity);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(listener!=null){
            ref.removeEventListener(listener);
        }
    }
}