package com.helpmeapp.gtbcn.helpmeapp;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class LoginActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final Button button = (Button) findViewById(R.id.loginButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                final EditText userEmail = (EditText) findViewById(R.id.userEmail);
                final EditText userPassword = (EditText) findViewById(R.id.userPassword);

                String email = userEmail.getText().toString();
                String passw = userPassword.getText().toString();

                try {
                    URL url = new URL("http://localhost:3000/users"); // Change this URL before definitive version!
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("POST");

                    String charset = "UTF-8";
                    String query = String.format("email=%s&password=%s", URLEncoder.encode(email, charset), URLEncoder.encode(passw, charset));


                    try {
                        urlConnection.connect();
                        int response = urlConnection.getResponseCode();

                        String text;

                        if (response == 200) {
                            text = "You logged in successfully.";
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);

                        } else if (response == 403) {
                            text = "Bad username or password. Try again.";
                        } else {
                            text = "The server has encountered an error.";
                        }


                        Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG);
                        toast.show();


                    } finally {
                        urlConnection.disconnect();
                    }

                } catch (Exception e) {

                    Log.e("ERROR", e.getMessage(), e);
                }

            }


        });

    }

}