package com.belonginterview.utils;

import android.util.Log;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;


/*Class implements Get functionality from API, the jars are in lib folder of project*/
public class HttpAgent {
    private static final String TAG = HttpAgent.class.getSimpleName();

    public static String get(String url){
        String response = null;
        HttpClient httpClient = new DefaultHttpClient(new BasicHttpParams());
        try {
            HttpGet httpGet = new HttpGet(url);
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            response = httpClient.execute(httpGet, responseHandler);
        } catch (Exception e) {
            Log.e(TAG, "HttpClient Exception GET : for request " + url, e);
        } finally {
            httpClient.getConnectionManager().shutdown();
        }
        return response;
    }
}
