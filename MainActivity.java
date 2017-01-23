package com.example.ase.hellohttp;

import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    final String urlSTR = "http://faf5b0ec.ngrok.io/asko1/test1.txt";
    private TextView txtview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.button);
        txtview = (TextView) findViewById(R.id.textView);

        button.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View view) {
                new Ping().execute(urlSTR);
            }
        });



    }

    public class Ping extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... params) {
            BufferedReader br = null;
            HttpURLConnection httpConnection = null;
            URL url = null;
            try {
                url = new URL(params[0]);
                httpConnection = (HttpURLConnection) url.openConnection();
                httpConnection.connect();
                InputStream is = httpConnection.getInputStream();
                br = new BufferedReader(new InputStreamReader(is));

                String line = "";
                StringBuffer buffer = new StringBuffer();
                while ((line = br.readLine()) != null) {
                    buffer.append(line);
                }
                return buffer.toString();
            }
            catch(MalformedURLException e) { e.printStackTrace(); }
            catch(IOException e) {e.printStackTrace(); }
            finally {
                if (httpConnection != null) {
                    httpConnection.disconnect();
                }
                try {
                    if (br != null) {
                        br.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            txtview.setText(s);
        }
    }

}
