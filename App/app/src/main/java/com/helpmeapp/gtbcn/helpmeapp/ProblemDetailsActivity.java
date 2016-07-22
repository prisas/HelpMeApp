package com.helpmeapp.gtbcn.helpmeapp;

import android.content.Intent;
import android.media.Image;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ProblemDetailsActivity extends AppCompatActivity {

    static final String API_URL = "http://rgalarcia.com/gtbcn";

    ProgressBar progressBar;
    TextView detailsProblemTitle, detailsProblemName, detailsProblemDescription;
    ImageView detailsProblemImage;
    Button detailsProblemSeeLocation;

    String lng, lat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problem_details);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        detailsProblemTitle = (TextView) findViewById(R.id.detailsProblemTitle);
        detailsProblemName = (TextView) findViewById(R.id.detailsProblemName);
        detailsProblemDescription = (TextView) findViewById(R.id.detailsProblemDescription);
        detailsProblemImage = (ImageView) findViewById(R.id.detailsProblemImage);
        detailsProblemSeeLocation = (Button) findViewById(R.id.showLocationButton);

        progressBar.setVisibility(View.GONE);
        progressBar.setMax(100);

        Intent intent = getIntent();
        String problemId = intent.getStringExtra("problemId");

        new ProblemDetails().execute(problemId);

        detailsProblemSeeLocation.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MapActivity.class);
                intent.putExtra("lat", lat);
                intent.putExtra("lng", lng);
                startActivity(intent);
            }
        });
    }

    //AsyncTask<Params, Progress, Result>
    public class ProblemDetails extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {
            String problemId = params[0];

            try {
                URL url = new URL(API_URL + "/retrieveproblems.php?id=" + problemId);
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

            if (response == null || response.equals("{\"code\":\"500\",\"result\":[]}")) {
                Toast.makeText(ProblemDetailsActivity.this, "There was a problem with the server, try it again!", Toast.LENGTH_SHORT).show();
                finish();
            }
            else {
                try {
                    JSONObject object = (JSONObject) new JSONTokener(response).nextValue();
                    JSONObject fields = object.getJSONObject("result");
                    detailsProblemTitle.setText(fields.getString("title"));
                    detailsProblemName.setText(fields.getString("name"));
                    detailsProblemDescription.setText(fields.getString("description"));

                    lat = fields.getString("lat");
                    lng = fields.getString("long");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
