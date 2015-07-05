package com.example.peterzhang.anysearch;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class IDSearchActivity extends ActionBarActivity {

    private EditText idEditText;
    private TextView mIDDetailTextview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_id_search);
        idEditText = (EditText)this.findViewById(R.id.ideditText);
        mIDDetailTextview = (TextView)this.findViewById(R.id.id_detail_info);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public void idSearch(View view){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String idDataSearchString = "http://apis.baidu.com/apistore/idservice/id";
        if(!idEditText.getText().toString().isEmpty()){
            idDataSearchString = idDataSearchString + "?" + "id="+idEditText.getText().toString();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                idDataSearchString,
                null,
                new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            JSONObject detail = (JSONObject)jsonObject.getJSONObject("retData");
                            String address = detail.getString("address");
                            String sex = detail.getString("sex").equals("M")?IDSearchActivity.this.getResources().getString(R.string.sex_m)
                                    :IDSearchActivity.this.getResources().getString(R.string.sex_f);
                            String birthday = detail.getString("birthday");
                            Log.d("TEST","address="+address
                                    +"sex="+sex
                                    +"birthday="+birthday);
                            mIDDetailTextview.setText(address+"\n"+sex+"\n"+birthday);
                        }catch (Exception e){
                            e.printStackTrace();
                        }

//                        mIDDetailTextview.setText();
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
