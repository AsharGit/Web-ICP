package com.example.login;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button login = (Button) findViewById(R.id.btnLogin);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get username and password fields
                EditText userId = (EditText) findViewById(R.id.editTextUsername);
                EditText passId = (EditText) findViewById(R.id.editTextPassword);
                String username = userId.getText().toString();
                String password = passId.getText().toString();

                // Check for empty fields
                if (!username.isEmpty() && !password.isEmpty()) {
                    // Match username and password to be redirected to home
                    if (username.equals("Admin") && password.equals("Admin")) {
                        enter(view);
                    }
                    else {
                        notification("Invalid Username or Password.");
                    }
                }
                else {
                    notification("Please fill all fields.");
                }
            }
        });


    }

    // Redirect to home activity
    public void enter(View view) {
        Intent redirect = new Intent(MainActivity.this, HomeActivity.class);
        startActivity(redirect);
    }

    // Notify user with message
    public void notification(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setCancelable(true);
        builder.create().show();
    }


}