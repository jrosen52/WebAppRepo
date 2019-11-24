package com.example.webapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

interface NetResponse
{
    void netResult(Integer code, JSONArray json);
}

public class MainActivity extends AppCompatActivity implements NetResponse {

    NetTask netTask;
    public int counter;
    Button button;
    TextView textView;
    private TextView coors;
    private LinearLayout canvasLayout = null;
    CanvasClass customSurfaceView = null;
    int score;
    String updateString;

    public void netResult(Integer code, JSONArray json)
    {
        System.out.println("Got a result from the web");
        updateString = "";

        for (int i = 0; i < json.length(); ++i)
        {
            try {
                JSONObject item = json.getJSONObject(i);

                if (item.getString("result") != null) {
                    System.out.println("Found a match");
                    System.out.println(item.getString("result"));
                    updateString = item.getString("result");


                }
            }
            catch (JSONException e)
            {
                updateString = "JSON Error!";
            }

            this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    computeResult.setText(updateString);

                }
            });
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Play Screen");

        initControls();
        button = (Button) findViewById(R.id.button);
        textView = (TextView) findViewById(R.id.textView);
        coors = (TextView) findViewById(R.id.coor);
        Button scoreButton = findViewById(R.id.score_button);

        customSurfaceView = new CanvasClass(getApplicationContext());
        customSurfaceView.setOnTouchListener(this);

        scoreButton.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                               startActivity(new Intent(MainActivity.this, ScorePage.class));
                                           }
                                       });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customSurfaceView.gameStarted = true;
                new CountDownTimer(30000, 1000) {
                    public void onTick(long millisUntilFinished) {
                        textView.setText(String.valueOf(30-counter));
                        counter++;
                    }

                    public void onFinish() {
                        textView.setText("Done!");
                        customSurfaceView.gameStarted = false;
                        if(score > ScorePage.getHigh())
                        {
                            ScorePage.setHigh(score);
                        }
                    }
                }.start();
            }
        });
    }

    private void initControls()
    {
        // This layout is used to contain custom surfaceview object.
        if(canvasLayout == null)
        {
            canvasLayout = (LinearLayout)findViewById(R.id.customViewLayout);
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        // If user touch the custom SurfaceView object.
        if (view instanceof SurfaceView) {
            view.invalidate();

            float x = motionEvent.getX();

            float y = motionEvent.getY();

            String coorNums = (int) x + ", " + (int) y;
            coors.setText(coorNums);
            return true;
        }
        else
        {
            return false;
        }
    }

    public class NetTask extends AsyncTask<Void, Void, Boolean> {
        private final String urlString;
        private final String reqString;
        private NetResponse changeListener;

        NetTask(String url, String request, NetResponse responseListener) {
            urlString = url; reqString = request; changeListener = responseListener;
        }
        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                System.out.println("JSON Query: " + reqString);
                // JSONObject json = readJsonFromUrl("https://graph.facebook.com/19292868552");
                // JSONObject json = readJsonFromUrl("https://cnn.com");
                // System.out.println(reqString);
                JSONArray json = readJsonFromUrl(reqString);
                System.out.println("Finished getting json.");
                if (json != null)
                    System.out.println(json.toString());

                if (changeListener != null)
                    changeListener.netResult(0, json);

                //System.out.println("Notify that JSON has come in");
                // if (noteConnector != null)
                //    noteConnector.ncnotify(0, "");

            } catch (IOException e) {
                System.out.println("IO exception");
                //System.out.println(e);
                if (changeListener != null)
                    changeListener.netResult(1, null);
            } catch (JSONException e) {
                System.out.println("JSON Didn't work");
                //System.out.println(e);
                if (changeListener != null)
                    changeListener.netResult(2, null);
            }
            return true;
        }

        private String readAll(Reader rd) throws IOException {
            StringBuilder sb = new StringBuilder();
            int cp;
            while ((cp = rd.read()) != -1) {
                sb.append((char) cp);
            }
            System.out.println("Read from the URL");
            System.out.println(sb.toString());
            System.out.println("Going to try to turn it into json");
            return sb.toString();
        }

        public JSONArray readJsonFromUrl(String request) throws IOException, JSONException {
            URL nurl = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) nurl.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            System.out.println("Network request to " + urlString + " with request " + reqString);
            OutputStream urlout = connection.getOutputStream();

            //String s = "id=3452&second=fjfjfjfj";
            urlout.write(request.getBytes());
            urlout.close();
            InputStream is = connection.getInputStream();

            System.out.println("Waiting for network stream");
            try {
                BufferedReader rd = new BufferedReader(new InputStreamReader(is));
                String jsonText = readAll(rd);
                System.out.println("JSON is " + jsonText);

                JSONArray jarray = new JSONArray(jsonText);


                System.out.println("Got the object");
                return jarray;
            } finally {
                is.close();
                // System.out.println("Did not get the object.");
            }


        }
    }

}