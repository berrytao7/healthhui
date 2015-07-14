package com.example.peterzhang.healthhui.fragments.heathmsg;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
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

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link com.example.peterzhang.healthhui.fragments.heathmsg.HealthMsgCategoryListFragment.OnCategoryItemSeletctedListener} interface
 * to handle interaction events.
 * Use the {@link HealthMsgCategoryListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HealthMsgCategoryListFragment extends Fragment implements AdapterView.OnItemClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnCategoryItemSeletctedListener mListener;

    private static final String TAG = "HealthMsgCateListFra";
    private SimpleAdapter mCategoryAdapter;
    private ListView mListView;
    private ProgressBar mLoading;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HealthMsgCategoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HealthMsgCategoryListFragment newInstance(String param1, String param2) {
        HealthMsgCategoryListFragment fragment = new HealthMsgCategoryListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public HealthMsgCategoryListFragment() {
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
        return inflater.inflate(R.layout.fragment_health_msg_category_list, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(String keyword) {
        if (mListener != null) {
            mListener.OnCategoryItemSeletcted(keyword);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mListView = (ListView)view.findViewById(R.id.category_listView);
        mListView.setOnItemClickListener(this);
        mLoading = (ProgressBar)view.findViewById(R.id.loading);
        mLoading.setVisibility(View.VISIBLE);
        searchCategory();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnCategoryItemSeletctedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnCategoryItemSeletctedListener {
        // TODO: Update argument type and name
        public void OnCategoryItemSeletcted(String keyword);
    }

    private void searchCategory(){
        RequestQueue requestQueue = Volley.newRequestQueue(this.getActivity());
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
                            mCategoryAdapter = new SimpleAdapter(HealthMsgCategoryListFragment.this.getActivity(),itemList,
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
        mListener.OnCategoryItemSeletcted(name.getText().toString());
    }

}
