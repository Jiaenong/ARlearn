package com.example.user.arlearn;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SearchResultActivity extends AppCompatActivity {

    private ListView listViewSearch;
    private List<subjectFile> list;

    public static final int READ_TIMEOUT = 15000;
    public static final int CONNECTION_TIMEOUT = 10000;
    private searchAdpater adapter;
    android.support.v7.widget.SearchView searchView = null;
    private static final String GET_URL = "https://arlearn.000webhostapp.com/searchResult.php?name=";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        list = new ArrayList<>();
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchManager searchManager = (SearchManager)SearchResultActivity.this.getSystemService(Context.SEARCH_SERVICE);
        if(searchItem != null)
        {
            searchView = (android.support.v7.widget.SearchView)searchItem.getActionView();
        }
        if(searchView != null)
        {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(SearchResultActivity.this.getComponentName()));
            searchView.setIconified(false);
        }
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        return super.onOptionsItemSelected(item);
    }

    protected void onNewIntent(Intent intent)
    {
        if(Intent.ACTION_SEARCH.equals(intent.getAction()))
        {
            String query = intent.getStringExtra(SearchManager.QUERY);
            if(searchView != null)
            {
                searchView.clearFocus();
            }
            new imageFetch(query);
        }
    }

    private class imageFetch{
        ProgressDialog pDialog = new ProgressDialog(SearchResultActivity.this);
        String searchQuery;
        String GET_URL = "https://arlearn.000webhostapp.com/searchResult.php?name=";

        public imageFetch(String query)
        {
            this.searchQuery = query;
            getSearchContent(getApplicationContext(),searchQuery);
        }

        private void getSearchContent(Context context, String query) {

            RequestQueue request = Volley.newRequestQueue(context);
            String url = GET_URL + "'"+query+"'";
            if (!pDialog.isShowing())
                pDialog.setMessage("Syn with server...");
            pDialog.show();

            JsonArrayRequest requestSearch = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    try{
                        list.clear();
                        for(int i = 0; i<response.length();i++)
                        {
                            JSONObject searchResponse = (JSONObject)response.get(i);
                            String id = searchResponse.getString("subjectID");
                            String image = searchResponse.getString("image");
                            subjectFile subjectfile = new subjectFile(id,image);
                            list.add(subjectfile);
                        }
                        loadfile();
                        if (pDialog.isShowing())
                            pDialog.dismiss();
                    }catch(Exception e)
                    {
                        Toast.makeText(getApplicationContext(), "Error:" + e.getMessage(), Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), "Error" + error.getMessage(), Toast.LENGTH_LONG).show();
                    error.printStackTrace();
                    if (pDialog.isShowing())
                        pDialog.dismiss();
                }
            });
            requestSearch.setRetryPolicy(new RetryPolicy() {
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

            request.add(requestSearch);
        }

        private void loadfile() {
            listViewSearch = (ListView) findViewById(R.id.listViewSearch);
            listViewSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    subjectFile subjectfile = adapter.getItem(position);
                    //Log.i("Database", subjectfile.getSubjectID());
                    Intent intent = new Intent(SearchResultActivity.this,SubjectActivity.class);
                    intent.putExtra("subjectID",subjectfile.getSubjectID());
                    startActivity(intent);
                }
            });
            adapter = new searchAdpater(SearchResultActivity.this,R.layout.content_search_result,list);
            listViewSearch.setAdapter(adapter);
            //recycleViewSearch.setLayoutManager(new LinearLayoutManager(SearchResultActivity.this));
        }

    }

    /*private class AsyncFetch extends AsyncTask<String,String,String>
    {
        ProgressDialog pDialog = new ProgressDialog(SearchResultActivity.this);
        HttpURLConnection conn;
        URL url = null;
        String searchQuery;

        public AsyncFetch(String searchQuery){
            this.searchQuery = searchQuery;
        }

        protected void onPreExecute(){
            super.onPreExecute();

            pDialog.setMessage("\tLoading...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            try{
                url = new URL("https://arlearn.000webhostapp.com/searchResult.php?");

            }catch (MalformedURLException e){
                e.printStackTrace();
                return e.toString();
            }

            try{
                conn = (HttpURLConnection)url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("POST");

                conn.setDoInput(true);
                conn.setDoOutput(true);

                Uri.Builder builder = new Uri.Builder().appendQueryParameter("searchQuery",searchQuery);

                String query = builder.build().getEncodedQuery();

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();
                conn.connect();

            }catch(IOException el){
                el.printStackTrace();
                return el.toString();
            }

            try{
                int response_code = conn.getResponseCode();
                if(response_code == HttpURLConnection.HTTP_OK)
                {
                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;

                    while((line = reader.readLine())!= null)
                    {
                        result.append(line);
                    }
                    return result.toString();
                }
                else{
                    return("Connection error");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }finally{
                conn.disconnect();
            }
            return null;
        }

        protected void onPostExecute(String result)
        {
            pDialog.dismiss();
            list = new ArrayList<>();

            pDialog.dismiss();
            if(result.equals("no rows"))
            {
                Toast.makeText(SearchResultActivity.this, "No Results found for entered query", Toast.LENGTH_LONG).show();
            }
            else{
                try{
                    JSONArray jArray = new JSONArray(result);
                    for(int i = 0; i<jArray.length(); i++)
                    {
                        JSONObject json_Data = jArray.getJSONObject(i);
                        String image = json_Data.getString("image");
                        subjectFile subjectfile = new subjectFile(image);
                        list.add(subjectfile);
                    }
                    recycleViewSearch = (RecyclerView) findViewById(R.id.recyclerViewSearch);
                    adapter = new searchAdpater(SearchResultActivity.this,list);
                    recycleViewSearch.setAdapter(adapter);
                    recycleViewSearch.setLayoutManager(new LinearLayoutManager(SearchResultActivity.this));
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(SearchResultActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                    Toast.makeText(SearchResultActivity.this, result.toString(), Toast.LENGTH_LONG).show();
                }
            }
        }
    }*/

}
