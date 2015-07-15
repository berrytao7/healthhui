package com.example.peterzhang.healthhui;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import java.util.ArrayList;
import java.util.HashMap;


public class MainActivity extends ActionBarActivity {

    private static final String TAG = "MainActivity";
    private GridView mGridView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mGridView = (GridView)this.findViewById(R.id.gridView);
        ArrayList<HashMap<String,Object>> itemList = new ArrayList<HashMap<String,Object>>();
        HashMap<String,Object> mapHealth = new HashMap<String,Object>();
        mapHealth.put("itemImage",R.mipmap.ic_launcher);
        mapHealth.put("itemText", this.getResources().getString(R.string.health_search));
        itemList.add(mapHealth);
        SimpleAdapter adapter = new SimpleAdapter(this,itemList,R.layout.item_layout,
                new String[]{"itemImage","itemText"},
                new int[]{R.id.itemImage,R.id.itemText}
                );
        mGridView.setAdapter(adapter);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              if(position == 0){
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this,HealthMsgActivity.class);
                    MainActivity.this.startActivity(intent);
                }
            }
        });
        getSupportActionBar().setBackgroundDrawable(this.getResources().getDrawable(R.color.actionbar_bg));

//        BuildConfig
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
}
