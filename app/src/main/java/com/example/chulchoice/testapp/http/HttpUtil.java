package com.example.chulchoice.testapp.http;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static com.example.chulchoice.testapp.http.HttpUtilConst.ConnType;
import static com.example.chulchoice.testapp.http.HttpUtilConst.ResponseType;

/**
 * Created by chulchoice on 2016-05-05.
 */
public class HttpUtil extends AsyncTask<String, Void, String>{

    private static final String TAG = "HttpUtil";
    ConnType mConnType;
    ResponseType mResponseType;
    int mTimeout;
    OnDataLoadedListener mListener;
    String mRequestBody;

    public interface OnDataLoadedListener{
        void onDataLoaded(String result);
    }

    public HttpUtil(OnDataLoadedListener listener){
        //mConnType = connType;
        //mResponseType = resType;
        mTimeout = 30000;
        mListener = listener;
    }

    public void setRequestHeader(Bundle bd){

    }

    public void setConnTimeout(int timeout){
        mTimeout = timeout;
    }

    public void setResponseType(ResponseType type){
        mResponseType = type;
    }

    public void setConnType(ConnType type){
        mConnType = type;
    }

    /**
     * setRequestBody
     * @param params
     * @return
     */
    public void setRequestBody(String params){
        mRequestBody = params;
    }

    @Override
    protected String doInBackground(String... params) {

        URL url = null;
        String response = null;
        BufferedReader br = null;
        try {
            url = new URL(params[0]);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setReadTimeout(mTimeout);
            conn.setConnectTimeout(mTimeout);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoInput(true);
            OutputStream os = null;
            BufferedWriter bw = null;
            switch(mConnType){
                case GET:
                    conn.setRequestMethod(ConnType.GET.getName());
                    break;
                case POST:
                    conn.setRequestMethod(ConnType.POST.getName());
                    conn.setDoOutput(true);
                    os = conn.getOutputStream();
                    bw = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                    bw.write(mRequestBody);
                    bw.flush();
                    bw.close();
                    break;
                case PUT:
                    conn.setRequestMethod(ConnType.PUT.getName());
                    conn.setDoOutput(true);
                    os = conn.getOutputStream();
                    bw = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                    bw.write(mRequestBody);
                    bw.flush();
                    bw.close();
                    break;
                case DELETE:
                    conn.setRequestMethod(ConnType.DELETE.getName());
                    break;
            }
            int res = conn.getResponseCode();
            Log.v(TAG, "res=" + res);
            InputStream is = conn.getInputStream();
            br = new BufferedReader(new InputStreamReader(is));
            String line=null;
            StringBuffer lines = new StringBuffer();
            while((line = br.readLine()) != null){
                lines.append(line);
            }

            response = lines.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(br!=null) br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return response;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        mListener.onDataLoaded(s);
    }

}
