package com.helpmeapp.gtbcn.helpmeapp;

import android.content.Intent;
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

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final Button registerbutt = (Button) findViewById(R.id.submitRegisterButton);

        // REGISTER BUTTON LISTENER
        registerbutt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                final EditText regName = (EditText) findViewById(R.id.registerName);
                final EditText regEmail = (EditText) findViewById(R.id.registerEmail);
                final EditText regPass = (EditText) findViewById(R.id.registerPassword);
                final EditText regPass2 = (EditText) findViewById(R.id.registerPassword2);
                final EditText regPhone = (EditText) findViewById(R.id.registerPhone);

                String pass1 = regPass.getText().toString();
                String pass2 = regPass2.getText().toString();

                if (pass1.equals(pass2) == false) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Passwords do not match.", Toast.LENGTH_LONG);
                    toast.show();
                } else {
                    String name = regName.getText().toString();
                    String email = regEmail.getText().toString();
                    String phone = regPhone.getText().toString();

                    try {
                        URL url = new URL("http://localhost:3000/users/register"); // Change this URL before definitive version!
                        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                        urlConnection.setRequestMethod("POST");

                        String charset = "UTF-8";
                        String query = String.format("name=%s&email=%s&pass=%s&phone=%s",
                                URLEncoder.encode(name, charset), URLEncoder.encode(email, charset),
                                URLEncoder.encode(pass1, charset), URLEncoder.encode(phone, charset));


                        try {
                            urlConnection.connect();
                            int response = urlConnection.getResponseCode();

                            String text;

                            if (response == 200) {
                                text = "You registered successfully.";
                                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
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

            }

        });
    }
}
