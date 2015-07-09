package com.example.peterzhang.anysearch.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.Image;
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
import com.example.peterzhang.anysearch.R;
import com.example.peterzhang.anysearch.mode.HealthMsgItem;

import java.util.ArrayList;

/**
 * Created by peterzhang on 15-7-8.
 */
public class HealthMsgItemAdapter extends BaseAdapter {

    private static final String TAG = "HealthMsgItemAdapter";
    private Context mContext;
    private ArrayList<HealthMsgItem> msgItemArrayList;
    LayoutInflater mLayoutInflater;

    public static HealthMsgItemAdapter build(Context context,ArrayList<HealthMsgItem> healthMsgItems){

        return new HealthMsgItemAdapter(context,healthMsgItems);
    }

    private HealthMsgItemAdapter(Context context,ArrayList<HealthMsgItem> healthMsgItems){

        mContext = context;
        msgItemArrayList = healthMsgItems;
        mLayoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
            contentTextView = (TextView)convertView.findViewById(R.id.content);
            thumb = (ImageView)convertView.findViewById(R.id.thumb);
            viewHolder = new ViewHolder(titleTextView,contentTextView,thumb);
            convertView.setTag(viewHolder);

        }else {
            viewHolder = (ViewHolder)convertView.getTag();
            titleTextView = viewHolder.title;
            contentTextView = viewHolder.content;
            thumb = viewHolder.thumb;

        }

        HealthMsgItem healthMsgItem = (HealthMsgItem)getItem(position);
        titleTextView.setText(Html.fromHtml(healthMsgItem.getTitle()));
        contentTextView.setText(Html.fromHtml(healthMsgItem.getContent()));

        RequestQueue queue = Volley.newRequestQueue(mContext);
        ImageLoader imageLoader = new ImageLoader(queue,new BitmapCache());
        ImageLoader.ImageListener listener = ImageLoader.getImageListener(thumb,R.mipmap.ic_launcher, R.mipmap.ic_launcher);
        imageLoader.get("http://apis.baidu.com/yi18/news/search/news/img/default.jpg",listener);
        convertView.setOnClickListener(new HealthItemOnclickListener());
        return convertView;
    }

    private static class ViewHolder{
        public TextView title;
        public TextView content;
        public ImageView thumb;

        public ViewHolder(TextView title,TextView content,ImageView thumb){
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

    private class HealthItemOnclickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {

        }
    }
}
