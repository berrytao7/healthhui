package com.example.peterzhang.healthhui.heathmsg;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.peterzhang.healthhui.R;

import org.json.JSONObject;


public class HealthMsgItemDetailActivity extends ActionBarActivity{

    private TextView mContentTextView;
    private TextView mTitleTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_msg_item_detail);
        mContentTextView = (TextView)this.findViewById(R.id.item_content);
        mTitleTextView = (TextView)this.findViewById(R.id.item_title);
        int id = getIntent().getIntExtra("id",0);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        detailQuery(id);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_health_msg_item_detail, menu);
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

    public void detailQuery(int id){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String idDataSearchString = "http://apis.baidu.com/yi18/news/detail?id="+id;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                idDataSearchString,
                null,
                new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            JSONObject detail = (JSONObject)jsonObject.getJSONObject("yi18");
                            String title = detail.getString("title");
                            String message = detail.getString("message");
                            mTitleTextView.setText(Html.fromHtml(title));
                            mContentTextView.setText(Html.fromHtml(message));
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        volleyError.printStackTrace();
                    }
                }

        );
        requestQueue.add(jsonObjectRequest);


    }

}
