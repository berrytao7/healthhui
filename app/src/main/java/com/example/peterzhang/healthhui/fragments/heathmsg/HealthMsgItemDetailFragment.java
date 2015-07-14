package com.example.peterzhang.healthhui.fragments.heathmsg;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.peterzhang.healthhui.R;

import org.json.JSONObject;

public class HealthMsgItemDetailFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextView mContentTextView;
    private TextView mTitleTextView;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HealthMsgItemDetail.
     */
    // TODO: Rename and change types and number of parameters
    public static HealthMsgItemDetailFragment newInstance(String param1, String param2) {
        HealthMsgItemDetailFragment fragment = new HealthMsgItemDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public HealthMsgItemDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_health_msg_item_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContentTextView = (TextView)view.findViewById(R.id.item_content);
        mTitleTextView = (TextView)view.findViewById(R.id.item_title);
        int id = getArguments().getInt("id");
        detailQuery(id);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void detailQuery(int id){
        RequestQueue requestQueue = Volley.newRequestQueue(this.getActivity());
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
