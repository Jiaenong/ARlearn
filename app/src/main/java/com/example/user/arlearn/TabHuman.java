package com.example.user.arlearn;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class TabHuman extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private SwipeRefreshLayout swipeRefreshLayout;
    private ImageAdapter imageAdapter;
    private ListView humanListView;
    private List<subjectFile> humanList;
    private static final String GET_URL_HUMAN = "https://arlearn.000webhostapp.com/getHumanImage.php";
    Context thiscontext;

    public TabHuman() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_human, container, false);
        thiscontext = container.getContext();
        humanList = new ArrayList<>();
        humanListView = (ListView)view.findViewById(R.id.listViewHuman);
        humanListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                subjectFile subjectfile = imageAdapter.getItem(position);
                Log.i("Database", subjectfile.getSubjectID());
                Intent intent = new Intent(getActivity(), SubjectActivity.class);
                intent.putExtra("subjectID",subjectfile.getSubjectID());
                startActivity(intent);
            }
        });

        swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.swipe_refresh_layoutHuman);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                fetchHumanSubject(thiscontext, GET_URL_HUMAN);
            }
        });
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onRefresh() {
        fetchHumanSubject(thiscontext, GET_URL_HUMAN);
    }

    private void fetchHumanSubject(Context context, String url) {
        RequestQueue queue = Volley.newRequestQueue(context);
        swipeRefreshLayout.setRefreshing(true);
        final JsonArrayRequest request = new JsonArrayRequest(
                url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            humanList.clear();
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject imageResponse = (JSONObject) response.get(i);
                                String id = imageResponse.getString("subjectID");
                                String name = imageResponse.getString("name");
                                String image = imageResponse.getString("image");
                                String type = imageResponse.getString("type");
                                String description1 = imageResponse.getString("Description1");
                                String description2 = imageResponse.getString("Description2");
                                String version = imageResponse.getString("version");
                                subjectFile subjectfile = new subjectFile(id, name, image, type,  description1, description2, version);
                                humanList.add(subjectfile);
                            }
                            loadImageFiles();
                        } catch (Exception e) {
                            Toast.makeText(thiscontext, "Error:" + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                        swipeRefreshLayout.setRefreshing(false);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(thiscontext, "Error" + volleyError.getMessage(), Toast.LENGTH_LONG).show();
                        //Toast.makeText(getActivity().getApplicationContext(), "Empty", Toast.LENGTH_LONG).show();
                        volleyError.printStackTrace();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
        request.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
        queue.add(request);
    }

    private void loadImageFiles() {
        imageAdapter = new ImageAdapter(getActivity(), R.layout.tab_human, humanList);
        humanListView.setAdapter(imageAdapter);
    }


}
