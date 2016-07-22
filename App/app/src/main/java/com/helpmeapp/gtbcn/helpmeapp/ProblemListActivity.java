package com.helpmeapp.gtbcn.helpmeapp;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ProblemListActivity extends AppCompatActivity {

    static final String API_URL = "http://rgalarcia.com/gtbcn";

    ProgressBar progressBar;
    ListView listView;

    ArrayAdapter<String> adapter;
    ArrayList<String> listItems = new ArrayList<>();

    ArrayList<String> listItemsId = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problem_list);

        listView = (ListView) findViewById(R.id.list_item);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        progressBar.setVisibility(View.GONE);
        progressBar.setMax(100);

        new ProblemList().execute();

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, listItems);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                String itemValue = (String) listView.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(), "Position :" + listItemsId.get(position) + "  ListItem : " +itemValue , Toast.LENGTH_LONG).show();
            }

        });
    }

    //AsyncTask<Params, Progress, Result>
    public class ProblemList extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(Void... params) {

            try {
                URL url = new URL(API_URL + "/retrieveproblems.php");
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
                Toast.makeText(ProblemListActivity.this, "There was a problem with the server, try it again!", Toast.LENGTH_SHORT).show();
                finish();
            }
            else {
                try {
                    JSONObject object = (JSONObject) new JSONTokener(response).nextValue();
                    JSONArray problems = object.getJSONArray("result");
                    for (int i = 0; i < problems.length(); i++) {
                        JSONObject problem = problems.getJSONObject(i);
                        //problem.getString("title");    problem.getString("id");
                        listItems.add(problem.getString("title"));
                        listItemsId.add(problem.getString("id"));
                        adapter.notifyDataSetChanged();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
