package com.helpmeapp.gtbcn.helpmeapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class RegisterActivity extends AppCompatActivity {

    static final String API_URL = "localhost";

    ProgressBar progressBar;
    EditText userName, userEmail, userPwd, userPwd2, userPhone;
    Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        userName = (EditText) findViewById(R.id.userName);
        userEmail = (EditText) findViewById(R.id.userEmail);
        userPwd = (EditText) findViewById(R.id.userPassword);
        userPwd2 = (EditText) findViewById(R.id.userPassword2);
        userPhone = (EditText) findViewById(R.id.userPhone);
        registerButton = (Button) findViewById(R.id.registerButton);

        // REGISTER BUTTON LISTENER
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = userName.getText().toString();
                String email = userEmail.getText().toString();
                String pass1 = userPwd.getText().toString();
                String pass2 = userPwd2.getText().toString();
                String phone = userPhone.getText().toString();

                if (!pass1.equals(pass2)) {
                    Toast.makeText(getApplicationContext(), "Passwords do not match.", Toast.LENGTH_LONG).show();
                } else {
                    new Register().execute(name, email, pass1, phone);
                }

            }

        });
    }

    //AsyncTask<Params, Progress, Result>
    public class Register extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... registerData) {
            String name = registerData[0];
            String email = registerData[1];
            String password = registerData[2];
            String phone = registerData[3];

            try {
                URL url = new URL(API_URL + "/register.php?name=" + name + "&email=" + email +
                        "&password=" + password + "&phone=" + phone);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line).append('\n');
                    }
                    bufferedReader.close();
                    return stringBuilder.toString();
                } finally {
                    urlConnection.disconnect();
                }
            } catch (Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                return null;
            }
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);

            progressBar.setVisibility(View.GONE);

            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            /*
            if (response == null) {
                Toast.makeText(LoginActivity.this, "There was a problem with the serve, try it again!", Toast.LENGTH_SHORT).show();
            } else {
                try {
                    JSONArray responseJSON = new JSONArray(response);
                    JSONObject responseCodeJSON = responseJSON.getJSONObject(0);
                    String code = responseCodeJSON.getString("code");
                    if (code.equals("200")) {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(LoginActivity.this, "Your details don't match, try it again!", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }*/
        }
    }
}
