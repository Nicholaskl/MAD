package com.nicholas.prac6;

import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity
{
    private static final String TAG = "MainActivity";
    private static final String API_KEY = "01189998819991197253";

    private ProgressBar progressBar;
    private TextView textArea;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textArea = findViewById(R.id.textArea);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        Button downloadBtn = findViewById(R.id.downloadBtn);
        downloadBtn.setOnClickListener(new View.OnClickListener()
        {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                new MyTask().execute();
            }
        });
    }

    private class MyTask extends AsyncTask<Void, Integer, String> {
        private int totalBytes;

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(Void... params) {
            String data = "";

            try {
                String urlString = Uri.parse("https://192.168.56.1:8000/testwebservice/rest")
                        .buildUpon()
                        .appendQueryParameter("method", "thedata.getit")
                        .appendQueryParameter("api_key", API_KEY)
                        .appendQueryParameter("format", "json")
                        .build().toString();
                URL url = new URL(urlString);
                HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
                DownloadUtils.addCertificate(MainActivity.this, conn);

                totalBytes = conn.getContentLength();
                int nBytes = 0;

                try {
                    if(conn.getResponseCode() != HttpsURLConnection.HTTP_OK) {
                        //throw Exception;
                    }
                    else {
                        data = download(conn);
                    }
                }
                finally {
                    conn.disconnect();
                }
            } catch (IOException | GeneralSecurityException e) {
                e.printStackTrace();
            }

            return data;
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        public String download(HttpsURLConnection conn) throws IOException {
            InputStream input = conn.getInputStream();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            int totalBytes = 0;
            byte[] buffer = new byte[1024];
            int bytesRead = input.read(buffer);
            while(bytesRead > 0)
            {
                totalBytes += bytesRead;
                publishProgress(totalBytes);
                System.out.println(bytesRead);
                baos.write(buffer, 0, bytesRead);
                bytesRead = input.read(buffer);
            }
            baos.close();

            return new String(baos.toByteArray());
        }

        protected void onProgressUpdate(Integer... params) {
            if(progressBar.getVisibility() != View.VISIBLE) {
                progressBar.setVisibility(View.VISIBLE);
                progressBar.setMax(totalBytes);
            }
            progressBar.setProgress(params[0]);
        }

        @Override
        protected void onPostExecute(String download) {
            String result = "";
            try {
                JSONObject jBase = new JSONObject(download);
                JSONArray jFactions = jBase.getJSONArray("factions");
                List<FactionClass> factions = new ArrayList<>();

                for(int i=0; i < jFactions.length(); i++) {
                    JSONObject jFaction = jFactions.getJSONObject(i);
                    factions.add(new FactionClass(jFaction.getString("name"),
                                                  jFaction.getInt("strength"),
                                                  jFaction.getString("relationship")));
                    result += factions.get(i).output();
                }
            }
            catch (JSONException e) {
                e.getStackTrace();
            }

            textArea.setText(result);
        }
    }

    public class FactionClass {
        String name;
        int strength;
        String relationship;

        public FactionClass(String name, int strength, String relationship) {
            this.name = name;
            this.strength = strength;
            this.relationship = relationship;
        }

        public String output() {
            return name + ": strength=" + strength + ", " + relationship + "\n";
        }
    }
}

