package com.example.peterzhang.healthhui.mode;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by peterzhang on 15-7-8.
 */
public class HealthMsgItem implements Parcelable{

    private int id;
    private String title;
    private String imgURL;
    private String keywords;
    private String description;
    private String content;
    private String type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }

    public static ArrayList<HealthMsgItem> build(JSONArray jsonArray){

        ArrayList<HealthMsgItem> healthMsgItems = new ArrayList<HealthMsgItem>();
        for (int i = 0;i<jsonArray.length();i++){
            try {
                JSONObject jsonObject = (JSONObject)jsonArray.getJSONObject(i);
                HealthMsgItem item = build(jsonObject);
                healthMsgItems.add(item);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return healthMsgItems;
    }

    public static HealthMsgItem build(JSONObject itemJson){

        HealthMsgItem item = new HealthMsgItem();
        try {
            item.setId(itemJson.getInt("id"));
            item.setTitle(itemJson.getString("title"));
            item.setContent(itemJson.getString("content"));
            item.setImgURL(itemJson.getString("img"));
        }catch(Exception e){
            e.printStackTrace();
        }
        return item;

    }
}

