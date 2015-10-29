package com.renegens.tester;

import android.app.Application;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by user on 29/10/2015.
 */
public class MarsWeather extends Application {

    private RequestQueue mRequestQueue;
    private static MarsWeather mInstance;
    public static final String TAG = MarsWeather.class.getName();

    @Override
    public void onCreate(){
        super.onCreate();
        mInstance = this;
        mRequestQueue = Volley.newRequestQueue(getApplicationContext());
    }

    public static  synchronized MarsWeather getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue(){
        return mRequestQueue;
    }

    public <T> void add(Request<T> req){
        req.setTag(TAG);
        getRequestQueue().add(req);

    }


}
