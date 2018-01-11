package com.example.user.arlearn;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Locale;

import javax.security.auth.Subject;

public class SubjectActivity extends AppCompatActivity implements View.OnClickListener, TextToSpeech.OnInitListener {
    private ImageView imageViewDefault;
    private TextView textViewDefaultName;
    private TextView textViewDefaultDescription1;
    private TextView textViewDefaultDescription2;
    private TextView textViewOutput;
    private Button buttonSpeak, buttonSpeak2;
    private final int CHECK_CODE = 0x1;
    private TextToSpeech tts;
    private UtteranceProgressListener utteranceProgressListener;
    private static final String GET_URL_SUBJECT = "https://arlearn.000webhostapp.com/getSubject.php?subjectID=";
    private static final int TAG1 = 1;
    private ProgressDialog pDialog;
    private subjectFile subjectfile;
    private String result = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imageViewDefault = (ImageView)findViewById(R.id.imageViewSubject);
        textViewDefaultName = (TextView)findViewById(R.id.textViewName);
        textViewDefaultDescription1 = (TextView)findViewById(R.id.textViewContent1);
        textViewDefaultDescription2 = (TextView)findViewById(R.id.textViewContent2);
        textViewOutput = (TextView)findViewById(R.id.textViewoutput);
        textViewOutput.setVisibility(View.GONE);
        pDialog = new ProgressDialog(this);
        Intent intent = getIntent();
        String subjectID = intent.getStringExtra("subjectID");
        String url = GET_URL_SUBJECT +"'"+subjectID+"'";
        getSubjectContent(getApplicationContext(), url);
        Button button = (Button)findViewById(R.id.buttonViewAR);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(SubjectActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(SubjectActivity.this, new String[] { Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE }, 0);
                }else {
                    Intent intent = new Intent(SubjectActivity.this, ImageTargets.class);
                    startActivity(intent);
                }
            }
        });
        buttonSpeak = (Button)findViewById(R.id.buttonSpeech);
        buttonSpeak2 = (Button)findViewById(R.id.buttonSpeech2);
        buttonSpeak2.setOnClickListener(this);
        buttonSpeak.setOnClickListener(this);
        checkTTS();

    }

    private void getSubjectContent(Context context, String url) {
        RequestQueue request = Volley.newRequestQueue(context);

        if (!pDialog.isShowing())
            pDialog.setMessage("Syn with server...");
        pDialog.show();


        JsonArrayRequest requestsubject = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {

                                JSONObject subjectResponse = (JSONObject) response.get(i);
                                String name = subjectResponse.getString("name");
                                String image = subjectResponse.getString("image");
                                String description1 = subjectResponse.getString("Description1");
                                String description2 = subjectResponse.getString("Description2");
                                subjectfile = new subjectFile(name, image, description1, description2);
                            }
                            loadFile();
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
                Log.i("tagconvertstr", "["+result+"]");
                Toast.makeText(getApplicationContext(), "Error" + error.getMessage(), Toast.LENGTH_LONG).show();
                error.printStackTrace();
                if (pDialog.isShowing())
                    pDialog.dismiss();
            }
        }
    );
        requestsubject.setRetryPolicy(new RetryPolicy() {
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

            }});
        request.add(requestsubject);
    }

    private void loadFile() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] imageBytes = baos.toByteArray();
        imageBytes = Base64.decode(subjectfile.getImage(), Base64.DEFAULT);
        Bitmap decodeImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        //Picasso.with(this).load(subjectfile.getImage()).into(imageViewDefault);
        imageViewDefault.setImageBitmap(decodeImage);
        textViewDefaultName.setText(subjectfile.getName());
        textViewDefaultDescription1.setText(subjectfile.getDescription1());
        textViewDefaultDescription2.setText(subjectfile.getDescription2());
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.buttonSpeech)
        {
            String text;
            text = textViewDefaultDescription1.getText().toString();
            if(tts.isSpeaking())
            {
                Toast.makeText(this, "System busy. Please try again later.", Toast.LENGTH_SHORT).show();
                return;
            }
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                    tts.speak(text, TextToSpeech.QUEUE_ADD, null);
            }else{
                HashMap<String, String> hash = new HashMap<String, String>();
                hash.put(TextToSpeech.Engine.KEY_PARAM_STREAM,String.valueOf(AudioManager.STREAM_NOTIFICATION));
                    tts.speak(text, TextToSpeech.QUEUE_ADD, hash);
            }
        }
        else if(id == R.id.buttonSpeech2)
        {
            String text2;
            text2 = textViewDefaultDescription2.getText().toString();
            if(tts.isSpeaking())
            {
                Toast.makeText(this, "System busy. Please try again later.", Toast.LENGTH_SHORT).show();
                return;
            }
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                    tts.speak(text2, TextToSpeech.QUEUE_ADD, null);
            }else{
                HashMap<String, String> hash = new HashMap<String, String>();
                hash.put(TextToSpeech.Engine.KEY_PARAM_STREAM,String.valueOf(AudioManager.STREAM_NOTIFICATION));

                    tts.speak(text2, TextToSpeech.QUEUE_ADD, hash);
            }
        }
    }

    private void checkTTS() {
        Intent check = new Intent();
        check.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(check, CHECK_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CHECK_CODE){
            if(resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS){
                tts = new TextToSpeech(this, this);
            }
        }else{
            Intent install = new Intent();
            install.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
            startActivity(install);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(tts != null)
        {
            tts.stop();
            tts.shutdown();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onInit(int status) {
        if(status == TextToSpeech.SUCCESS)
        {
            tts.setLanguage(Locale.US);
            utteranceProgressListener = new UtteranceProgressListener() {
                @Override
                public void onStart(final String utteranceId) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //textViewOutput.setText(utteranceId);
                        }
                    });
                }

                @Override
                public void onDone(String utteranceId) {

                }

                @Override
                public void onError(String utteranceId) {

                }
            };
            tts.setOnUtteranceProgressListener(utteranceProgressListener);
        }else{
            Log.e("TTS", "Initialization Failed!");
        }
    }
}
