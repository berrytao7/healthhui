package com.example.peterzhang.healthhui.fragments.heathmsg;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.peterzhang.healthhui.R;
import com.example.peterzhang.healthhui.adapter.HealthMsgItemAdapter;
import com.example.peterzhang.healthhui.mode.HealthMsgItem;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import android.support.v4.app.Fragment;
/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link com.example.peterzhang.healthhui.fragments.heathmsg.HealthMsgCategoryDetailFragment.OnHealthMsgItemClickListener} interface
 * to handle interaction events.
 * Use the {@link HealthMsgCategoryDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HealthMsgCategoryDetailFragment extends Fragment implements AbsListView.OnScrollListener
        ,AdapterView.OnItemClickListener{
    private static final String TAG = "HealthMsgCategoryDetailFragment";
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnHealthMsgItemClickListener mListener;

    private ListView mListView;
    private HealthMsgItemAdapter mHealthMsgItemAdapter;
    private Context mContext;
    private int mCurrentPage = 0;
    private String mKeyword;
    private boolean mUpdate;
    private ProgressBar mFootLoadProgress;
    private ProgressBar mLoadProgress;
    private TextView mAllLoadedText;
    private LayoutInflater mLayoutInflater;
    private int mClickItemPosition;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HealthMsgCategoryDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HealthMsgCategoryDetailFragment newInstance(String param1, String param2) {
        HealthMsgCategoryDetailFragment fragment = new HealthMsgCategoryDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public HealthMsgCategoryDetailFragment() {
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
        return inflater.inflate(R.layout.fragment_health_msg_category_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContext = this.getActivity();
        mListView = (ListView)view.findViewById(R.id.category_detail);
        mListView.setOnScrollListener(this);
        mLayoutInflater = (LayoutInflater)this.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        RelativeLayout foot = (RelativeLayout)mLayoutInflater.inflate(R.layout.load_item,null);
        mAllLoadedText = (TextView)foot.findViewById(R.id.all_loaded_text);
        mFootLoadProgress = (ProgressBar)foot.findViewById(R.id.foot_loading);
        mLoadProgress = (ProgressBar)view.findViewById(R.id.loading);
        mListView.addFooterView(foot);
        mListView.setOnItemClickListener(this);
        String keyword = getArguments().getString("keyword");
        mKeyword = keyword;
        if (!keyword.isEmpty()){
            queryHealthMsg(mKeyword);
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(int id) {
        if (mListener != null) {
            mListener.OnHealthMsgItemClick(id);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnHealthMsgItemClickListener) activity;
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
    public interface OnHealthMsgItemClickListener {
        // TODO: Update argument type and name
        public void OnHealthMsgItemClick(int id);
    }

    private void queryHealthMsg(String keyword,int page, final boolean newPage){
        Log.i("TEST","keyword"+keyword+" page="+page);
        RequestQueue requestQueue = Volley.newRequestQueue(this.getActivity());
        String sarchString = "http://apis.baidu.com/yi18/news/search?"+"page="+page+"&limit=20&keyword="+keyword;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                sarchString,
                null,
                new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Log.i("TEST","onResponse");
                        try {
                            JSONArray category = (JSONArray)jsonObject.getJSONArray("yi18");
                            ArrayList<HealthMsgItem> arrayList = new ArrayList<HealthMsgItem>();
                            for(int i=0;i<category.length();i++){
                                JSONObject itemJson = (JSONObject)category.get(i);
                                HealthMsgItem item = new HealthMsgItem();
                                item.setId(itemJson.getInt("id"));
                                item.setTitle(itemJson.getString("title"));
                                item.setContent(itemJson.getString("content"));
                                item.setImgURL(itemJson.getString("img"));
                                arrayList.add(item);
                            }
                            if(mHealthMsgItemAdapter == null){
                                mHealthMsgItemAdapter = HealthMsgItemAdapter.build(HealthMsgCategoryDetailFragment.this.getActivity(),arrayList);
                                mListView.setAdapter(mHealthMsgItemAdapter);
                            }else {
                                if(newPage){
                                    mHealthMsgItemAdapter.addHealthItemFromJsonArray(category);
                                }else {
                                    mListView.setAdapter(mHealthMsgItemAdapter);
                                    mListView.setSelection(mClickItemPosition);
                                }
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        mLoadProgress.setVisibility(View.GONE);
                        mUpdate = false;

                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.i("TEST","onErrorResponse");
                        volleyError.printStackTrace();
                        mCurrentPage--;
                        mLoadProgress.setVisibility(View.GONE);
                        mUpdate = false;
                    }
                }

        );
        requestQueue.add(jsonObjectRequest);
    }



    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        if(mUpdate){
            return;
        }

        if (totalItemCount != 0
                && firstVisibleItem + visibleItemCount >= totalItemCount) {
            mFootLoadProgress.setVisibility(View.VISIBLE);
            searchNextpage();
            mUpdate = true;
        }
    }

    private void searchNextpage(){
        queryHealthMsg(mKeyword,++mCurrentPage,true);
    }

    public void queryHealthMsg(String keyword){
        mLoadProgress.setVisibility(View.VISIBLE);
        queryHealthMsg(keyword,++mCurrentPage,false);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i("TEST","onItemClick position="+position);
        HealthMsgItem item = (HealthMsgItem)mHealthMsgItemAdapter.getItem(position);
        mListener.OnHealthMsgItemClick(item.getId());
        mClickItemPosition = mListView.getFirstVisiblePosition();
    }
}
