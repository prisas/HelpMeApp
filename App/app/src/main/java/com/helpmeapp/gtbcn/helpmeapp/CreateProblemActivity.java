package com.helpmeapp.gtbcn.helpmeapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CreateProblemActivity extends AppCompatActivity {

    static final String API_URL = "http://rgalarcia.com/gtbcn";

    ProgressBar progressBar;
    EditText title, description;
    ImageButton photo;
    Button create;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_problem);

        title = (EditText) findViewById(R.id.createTitle);
        description = (EditText) findViewById(R.id.createDescription);
        photo = (ImageButton) findViewById(R.id.createPhoto);
        create = (Button) findViewById(R.id.createProblem);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        progressBar.setVisibility(View.GONE);
        progressBar.setMax(100);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String problemTitle = title.getText().toString();
                String problemDescription = description.getText().toString();
                new Problem().execute(problemTitle, problemDescription);
            }
        });
    }

    //AsyncTask<Params, Progress, Result>
    public class Problem extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... problemData) {
            String title = problemData[0];
            String description = problemData[1];

            try {
                URL url = new URL(API_URL + "/addproblem.php?title=" + title + "&description=" + description +
                                  "&long=" + 0 + "&lat=" + 0 + "&user_id" + 1);
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

            if (response == null || response.equals("{\"code\":\"500\"}")) {
                Toast.makeText(CreateProblemActivity.this, "There was a problem with the server, try it again!", Toast.LENGTH_SHORT).show();
            } else {
                new AlertDialog.Builder(getApplicationContext())
                        .setTitle("Problem updated")
                        .setMessage("Problem successfully created. Please wait a moment until someone comes to help you!")
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
                finish();
            }
        }
    }
}
