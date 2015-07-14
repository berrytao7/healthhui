package com.example.peterzhang.healthhui;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.peterzhang.healthhui.adapter.HealthMsgItemAdapter;
import com.example.peterzhang.healthhui.fragments.heathmsg.HealthMsgCategoryDetailFragment;
import com.example.peterzhang.healthhui.fragments.heathmsg.HealthMsgCategoryListFragment;
import com.example.peterzhang.healthhui.fragments.heathmsg.HealthMsgItemDetailFragment;

public class HealthMsgActivity extends ActionBarActivity implements HealthMsgCategoryListFragment.OnCategoryItemSeletctedListener,
        HealthMsgCategoryDetailFragment.OnHealthMsgItemClickListener
        {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_msg);
        if(savedInstanceState == null){
            Fragment fragment = new HealthMsgCategoryListFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container,fragment);
            transaction.commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_health_msg, menu);
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

    @Override
    public void OnCategoryItemSeletcted(String keyword) {
        HealthMsgCategoryDetailFragment fragment = new HealthMsgCategoryDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString("keyword", keyword);
        fragment.setArguments(bundle);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container,fragment);
        transaction.addToBackStack(fragment.getTag());
        transaction.commit();

    }

    @Override
    public void OnHealthMsgItemClick(int id) {

        HealthMsgItemDetailFragment fragment = new HealthMsgItemDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        fragment.setArguments(bundle);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(fragment.getTag());
        transaction.commit();
    }

}
