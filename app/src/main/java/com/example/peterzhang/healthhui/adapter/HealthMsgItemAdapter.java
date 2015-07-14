package com.example.peterzhang.healthhui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.text.Html;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.example.peterzhang.healthhui.R;
import com.example.peterzhang.healthhui.mode.HealthMsgItem;

import org.json.JSONArray;

import java.util.ArrayList;

/**
 * Created by peterzhang on 15-7-8.
 */
public class HealthMsgItemAdapter extends BaseAdapter {

    private static final String TAG = "HealthMsgItemAdapter";
    private Context mContext;
    private ArrayList<HealthMsgItem> msgItemArrayList;
    private LayoutInflater mLayoutInflater;
    private RequestQueue mQueue;

    public static HealthMsgItemAdapter build(Activity context,ArrayList<HealthMsgItem> healthMsgItems){

        return new HealthMsgItemAdapter(context,healthMsgItems);
    }

    private HealthMsgItemAdapter(Activity context,ArrayList<HealthMsgItem> healthMsgItems){
        mContext = context;
        msgItemArrayList = healthMsgItems;
        mLayoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mQueue = Volley.newRequestQueue(mContext);
    }

    @Override
    public int getCount() {
        return msgItemArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return msgItemArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView titleTextView;
        TextView contentTextView;
        ImageView thumb;
        ViewHolder viewHolder;
        if(convertView == null){
            convertView = mLayoutInflater.inflate(R.layout.health_msg_item,parent,false);
            titleTextView = (TextView)convertView.findViewById(R.id.title);
            thumb = (ImageView)convertView.findViewById(R.id.thumb);
            viewHolder = new ViewHolder(titleTextView,thumb);
            convertView.setTag(viewHolder);

        }else {
            viewHolder = (ViewHolder)convertView.getTag();
            titleTextView = viewHolder.title;
            contentTextView = viewHolder.content;
            thumb = viewHolder.thumb;

        }

        HealthMsgItem healthMsgItem = (HealthMsgItem)getItem(position);
        titleTextView.setText(Html.fromHtml(healthMsgItem.getTitle()));


        ImageLoader imageLoader = new ImageLoader(mQueue,new BitmapCache());
        ImageLoader.ImageListener listener = ImageLoader.getImageListener(thumb,R.mipmap.ic_launcher, R.mipmap.ic_launcher);
        imageLoader.get("http://www.yi18.net/"+healthMsgItem.getImgURL(),listener);
//        convertView.setOnClickListener(new HealthItemOnclickListener(healthMsgItem));
        return convertView;
    }

    private static class ViewHolder{
        public TextView title;
        public TextView content;
        public ImageView thumb;

        public ViewHolder(TextView title,ImageView thumb){
            this.title = title;
            this.content = content;
            this.thumb = thumb;
        }
    }

    public class BitmapCache implements ImageLoader.ImageCache {
        private LruCache<String, Bitmap> mCache;

        public BitmapCache() {
            int maxSize = 10 * 1024 * 1024;
            mCache = new LruCache<String, Bitmap>(maxSize) {
                @Override
                protected int sizeOf(String key, Bitmap value) {
                    return value.getRowBytes() * value.getHeight();
                }

            };
        }

        @Override
        public Bitmap getBitmap(String url) {
            return mCache.get(url);
        }

        @Override
        public void putBitmap(String url, Bitmap bitmap) {
            mCache.put(url, bitmap);
        }

    }

    public void addHealthItemFromJsonArray(JSONArray jsonArray){
        ArrayList<HealthMsgItem> arrayList = new ArrayList<HealthMsgItem>();
        arrayList = HealthMsgItem.build(jsonArray);
        msgItemArrayList.addAll(arrayList);
        notifyDataSetChanged();
    }

    public interface OnHealthMsgItemClickListener {
        // TODO: Update argument type and name
        public void OnHealthMsgItemClick(int id);
    }
}
