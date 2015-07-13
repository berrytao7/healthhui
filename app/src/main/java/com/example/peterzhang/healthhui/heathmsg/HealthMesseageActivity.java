package com.example.peterzhang.healthhui.heathmsg;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.peterzhang.healthhui.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class HealthMesseageActivity extends ActionBarActivity implements AdapterView.OnItemClickListener{

    private static final String TAG = "HealthMesseageActivity";
    private SimpleAdapter mCategoryAdapter;
    private ListView mListView;
    private ProgressBar mLoading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_messeage);
        mListView = (ListView)this.findViewById(R.id.category_listView);
        mListView.setOnItemClickListener(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mLoading = (ProgressBar)this.findViewById(R.id.loading);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mLoading.setVisibility(View.VISIBLE);
        searchCategory();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_health_messeage, menu);
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
        }else if(id == android.R.id.home){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void searchCategory(){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String idDataSearchString = "http://apis.baidu.com/yi18/news/newsclass/id=1&name=health";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                idDataSearchString,
                null,
                new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            JSONArray category = (JSONArray)jsonObject.getJSONArray("yi18");
                            ArrayList<HashMap<String,Object>> itemList = new ArrayList<HashMap<String,Object>>();
                            for(int i=0;i<category.length();i++){
                                JSONObject itemJson = (JSONObject)category.get(i);
                                HashMap<String,Object> map = new HashMap<String,Object>();
                                map.put("category_name",itemJson.getString("name"));
                                itemList.add(map);
                            }
                            mCategoryAdapter = new SimpleAdapter(HealthMesseageActivity.this,itemList,
                                    android.R.layout.simple_list_item_1,new String[]{"category_name"},
                                    new int[]{android.R.id.text1});
                            mListView.setAdapter(mCategoryAdapter);
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                        mLoading.setVisibility(View.GONE);

                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        volleyError.printStackTrace();
                        mLoading.setVisibility(View.GONE);
                    }
                }

        );
        requestQueue.add(jsonObjectRequest);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TextView name = (TextView)view.findViewById(android.R.id.text1);
        Intent intent = new Intent();
        intent.setClass(HealthMesseageActivity.this,HealthMsgCategoryDetailActivity.class);
        intent.putExtra("keyword",name.getText().toString());
        startActivity(intent);
    }
}
