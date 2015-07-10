package com.example.peterzhang.anysearch;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.peterzhang.anysearch.adapter.HealthMsgItemAdapter;
import com.example.peterzhang.anysearch.mode.HealthMsgItem;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;


public class HealthCategoryDetailActivity extends ActionBarActivity implements AbsListView.OnScrollListener{

    private static final String TAG = "HealthCategoryDetail";
    private ListView mListView;
    private HealthMsgItemAdapter mHealthMsgItemAdapter;
    private Context mContext;
    private int mCurrentPage = 1;
    private String mKeyword;
    private boolean mUpdate;
    private ProgressBar mLoadProgress;
    private TextView mAllLoadedText;
    private LayoutInflater mLayoutInflater;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_category_detail);
        mContext = this;
        mListView = (ListView)this.findViewById(R.id.category_detail);
        String keyword = getIntent().getStringExtra("keyword");
        mKeyword = keyword;
        queryHealthMsg(keyword,1);
        mListView.setOnScrollListener(this);
        mLayoutInflater = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        RelativeLayout foot = (RelativeLayout)mLayoutInflater.inflate(R.layout.load_item,null);
        mAllLoadedText = (TextView)foot.findViewById(R.id.all_loaded_text);
        mLoadProgress = (ProgressBar)foot.findViewById(R.id.loading);
        mListView.addFooterView(foot);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_health_category_detail, menu);
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

    private void queryHealthMsg(String keyword,int page){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String sarchString = "http://apis.baidu.com/yi18/news/search?"+"page="+page+"&limit=20&keyword="+keyword;
        Log.d(TAG,"sarchString="+sarchString+" page="+page);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                sarchString,
                null,
                new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            JSONArray category = (JSONArray)jsonObject.getJSONArray("yi18");
                            ArrayList<HealthMsgItem> arrayList = new ArrayList<HealthMsgItem>();
                            for(int i=0;i<category.length();i++){
                                JSONObject itemJson = (JSONObject)category.get(i);
                                HealthMsgItem item = new HealthMsgItem();
                                item.setId(itemJson.getInt("id"));
                                item.setTitle(itemJson.getString("title"));
                                item.setContent(itemJson.getString("content"));
                                item.setImgURL(itemJson.getString("img"));
                                arrayList.add(item);
                            }
                            if(mHealthMsgItemAdapter == null){
                                mHealthMsgItemAdapter = HealthMsgItemAdapter.build(mContext,arrayList);
                                mListView.setAdapter(mHealthMsgItemAdapter);
                            }else {
                                mHealthMsgItemAdapter.addHealthItemFromJsonArray(category);
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        mLoadProgress.setVisibility(View.GONE);
                        mUpdate = false;

                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        volleyError.printStackTrace();
                        mCurrentPage--;
                        mLoadProgress.setVisibility(View.GONE);
                        mUpdate = false;
                    }
                }

        );
        requestQueue.add(jsonObjectRequest);
    }



    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        if(mUpdate){
            return;
        }

        if (totalItemCount != 0
                && firstVisibleItem + visibleItemCount >= totalItemCount) {
            mLoadProgress.setVisibility(View.VISIBLE);
            searchNextpage();
            mUpdate = true;
        }
    }

    private void searchNextpage(){
        queryHealthMsg(mKeyword,mCurrentPage++);
    }

    @Override
    public void finish() {
        super.finish();
        mUpdate = false;
    }
}
