package com.example.chulchoice.testapp;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.example.chulchoice.testapp.http.HttpUtil;
import com.example.chulchoice.testapp.http.HttpUtilConst;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


public class MainActivity extends ActionBarActivity {
    private static final String TAG = "MainActivity";
    SwipeRefreshLayout mSwipeLayout;
    TextView mTv;
    ListView mLv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSwipeLayout = (SwipeRefreshLayout)findViewById(R.id.swipe);
        mTv = (TextView)findViewById(R.id.tv);
        mLv = (ListView)findViewById(R.id.lv);

        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mTv.setText("merong");
                String url = null;
                try {
                    url = "https://apis.daum.net/search/book?apikey="+"b7b60ae02bb023bcab33bd788a7b55e2"+"&q="
                            + URLEncoder.encode("위인", "UTF-8")
                            + "&output=json&pageno=1&result=20";
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Log.v(TAG, "url="+url);
                HttpUtil httpEngine = new HttpUtil(new HttpUtil.OnDataLoadedListener() {
                    @Override
                    public void onDataLoaded(String result) {
                        Log.v(TAG, "result=" + result);
                        mSwipeLayout.setRefreshing(false);
                        mTv.setText(result);
                    }
                });
                httpEngine.setConnType(HttpUtilConst.ConnType.GET);
                //httpEngine.setResponseType(HttpUtilConst.ResponseType.JSON);
                //httpEngine.setConnTimeout(30);
                //httpEngine.setRequestHeader();
                httpEngine.execute(url);
            }
        });
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
