package com.ajai__lee.quiz.controller;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;


public class AppController extends Application {
    public static final String TAG = AppController.class.getSimpleName();
    private static AppController mInstance;
    private RequestQueue mrequestQueue;
// creating instance for the request
    public static synchronized AppController getInstance(){
       // if(mInstance==null){
       //     mInstance =  new AppController();//if not
       // }
        return mInstance;

    }
    //passing the created mInstance to the onCreate method
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }
    // this method is use to create a request queue or to return the present queue
    public RequestQueue getRequestQueue(){
        if(mrequestQueue==null){
            mrequestQueue= Volley.newRequestQueue(getApplicationContext());
        }
        return mrequestQueue;
    }
    // to add the request to the Request queue
    public <T> void addToRequestQueue(Request<T> req,String tag){
        // set the default tag if tag not avaliable
        req.setTag(TextUtils.isEmpty(tag)? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addTORequestQueue(Request<T> req){
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

}
