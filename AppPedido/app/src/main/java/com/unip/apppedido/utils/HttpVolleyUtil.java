package com.unip.apppedido.utils;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;

public class HttpVolleyUtil extends JsonRequest<String> {
    public static final String BASE_URL = "http://192.168.0.109/aps/api";

    public HttpVolleyUtil(int method, String endPoint, String jsonRequest,
                          Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method, BASE_URL + "/" + endPoint, jsonRequest, listener, errorListener);
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET));
            return Response.success(jsonString, HttpHeaderParser.parseCacheHeaders(response));
        } catch (Exception e) {
            return Response.error(new ParseError(e));
        }
    }
}