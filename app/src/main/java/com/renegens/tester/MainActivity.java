package com.renegens.tester;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    final static String RECENT_API_ENDPOINT = "http://marsweather.ingenology.com/v1/latest/";
    TextView mTxtDegress, mTxtWeather, mTxtError;
    ImageView mImageView;
    MarsWeather helper = MarsWeather.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mTxtDegress = (TextView) findViewById(R.id.degrees);
        mTxtWeather = (TextView) findViewById(R.id.weather);
        mTxtError = (TextView) findViewById(R.id.error);

        loadWeatherData();
        loadImage();




}

    private void loadImage(){
        String url = "http://i.imgur.com/Nwk25LA.jpg";
        mImageView = (ImageView) findViewById(R.id.main_bg);

        ImageRequest request = new ImageRequest(url,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        mImageView.setImageBitmap(response);
                    }
                }, 0, 0, ImageView.ScaleType.FIT_XY, Bitmap.Config.ARGB_8888, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mImageView.setBackgroundColor(Color.parseColor("#ff0000"));
                error.printStackTrace();
            }
        });


        helper.add(request);
    }

    private void loadWeatherData() {

        CustomJsonRequest request = new CustomJsonRequest
                (Request.Method.GET, RECENT_API_ENDPOINT, null , new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String minTemp, maxTemp, atmo;
                            int avgTemp;
                            response = response.getJSONObject("report");
                            minTemp = response.getString("min_temp");
                            minTemp = minTemp.substring(0, minTemp.indexOf("."));
                            maxTemp = response.getString("max_temp");
                            maxTemp = maxTemp.substring(0, maxTemp.indexOf("."));

                            avgTemp = (Integer.parseInt(minTemp) + Integer.parseInt(maxTemp)) / 2;

                            atmo = response.getString("atmo_opacity");

                            mTxtDegress.setText(avgTemp + "°");
                            mTxtWeather.setText(atmo);


                        } catch (Exception e) {
                            txtError(e);
                        }
                    }
                },new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        txtError(error);
                    }
                });
        request.setPriority(Request.Priority.HIGH);
        helper.add(request);
    }



    private void txtError(Exception e) {
        mTxtError.setVisibility(View.VISIBLE);
        e.printStackTrace();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
