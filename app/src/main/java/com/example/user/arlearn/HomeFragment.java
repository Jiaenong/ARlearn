package com.example.user.arlearn;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout swipeRefreshLayout;
    private ListView listView;
    private ImageAdapter imageAdapter;
    private List<subjectFile> list;
    private subjectFile subjectfile;
    private static final String GET_URL = "https://arlearn.000webhostapp.com/getnewImage.php";
    Context thiscontext;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        thiscontext = container.getContext();

        list = new ArrayList<>();
        listView = (ListView)view.findViewById(R.id.listViewHome);
        swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                fetchSubject(thiscontext,GET_URL);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                subjectFile subjectfile = imageAdapter.getItem(position);
                Log.i("Database", subjectfile.getSubjectID());
                Intent intent = new Intent(getActivity(), SubjectActivity.class);
                intent.putExtra("subjectID",subjectfile.getSubjectID());
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onRefresh() {
        fetchSubject(thiscontext, GET_URL);
    }

    private void fetchSubject(Context context, String url) {
        RequestQueue queue = Volley.newRequestQueue(context);
        swipeRefreshLayout.setRefreshing(true);
        final JsonArrayRequest request = new JsonArrayRequest(
                url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            list.clear();
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject imageResponse = (JSONObject) response.get(i);
                                String id = imageResponse.getString("subjectID");
                                String name = imageResponse.getString("name");
                                String image = imageResponse.getString("image");
                                String type = imageResponse.getString("type");
                                String description1 = imageResponse.getString("Description1");
                                String description2 = imageResponse.getString("Description2");
                                String version = imageResponse.getString("version");
                                subjectfile = new subjectFile(id, name, image, type, description1, description2, version);
                                list.add(subjectfile);

                            }
                            loadImageFiles();
                            swipeRefreshLayout.setRefreshing(false);
                        } catch (Exception e) {
                            Toast.makeText(thiscontext, "Error:" + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(thiscontext, "Error" + volleyError.getMessage(), Toast.LENGTH_LONG).show();
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
        imageAdapter = new ImageAdapter(getActivity(),R.layout.fragment_home,list);
        listView.setAdapter(imageAdapter);
    }


}
