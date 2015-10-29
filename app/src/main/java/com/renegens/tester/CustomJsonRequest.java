package com.renegens.tester;

import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

/**
 * Created by user on 29/10/2015.
 */
public class CustomJsonRequest extends JsonObjectRequest {

    public CustomJsonRequest(int method, String url, JSONObject jsonRequest,
                             Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(method, url, jsonRequest, listener, errorListener);
    }

    private Priority mPriority;

    public void setPriority(Priority priority){
        mPriority = priority;
    }

    @Override
    public Priority getPriority(){

        if (mPriority == null){
            return Priority.NORMAL;
        }else{
            return mPriority;
        }
    }
}
