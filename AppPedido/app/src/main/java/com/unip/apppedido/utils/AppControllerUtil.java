package com.unip.apppedido.utils;

import android.content.Context;
import android.support.annotation.NonNull;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class AppControllerUtil {
    private static AppControllerUtil mInstance;
    private static Context mCtx;
    private RequestQueue mRequestQueue;

    private AppControllerUtil(Context context) {
        mCtx = context;
        mRequestQueue = getRequestQueue();
    }

    // if an instance is already create , it will return it . if no instance was created , it will create a new one then reurn it
    public static synchronized AppControllerUtil getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new AppControllerUtil(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return mRequestQueue;
    }

    public  void addToRequestQueue(@NonNull final Request request) {
        getRequestQueue().add(request);
    }

    public  void addToRequestQueueWithTag(@NonNull final Request request, String tag) {
        request.setTag(tag);
        getRequestQueue().add(request);
    }
}
