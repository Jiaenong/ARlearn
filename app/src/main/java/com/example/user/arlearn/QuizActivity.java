package com.example.user.arlearn;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

public class QuizActivity extends AppCompatActivity {

    private QuizLibrary quiz = new QuizLibrary();
    private TextView textViewQuestion, textViewScore;
    private Button buttonChoice1, buttonChoice2, buttonChoice3;
    private ProgressDialog pDialog;
    private String ID = "Q";
    private int question = 0;
    private static final String GET_URL = "https://arlearn.000webhostapp.com/getQuiz.php?quizID=";
    QuizLibrary quizLibrary;
    private int score = 0;
    private int questionNumber = 0;
    private int click = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        textViewQuestion = (TextView)findViewById(R.id.question);
        textViewScore = (TextView)findViewById(R.id.score);
        buttonChoice1 = (Button)findViewById(R.id.choice1);
        buttonChoice2 = (Button)findViewById(R.id.choise2);
        buttonChoice3 = (Button)findViewById(R.id.choice3);
        pDialog = new ProgressDialog(QuizActivity.this);
        getQuestion(this, GET_URL);

}

    private void getQuestion(Context context, String getUrl) {
        question++;
        String url = getUrl+"'"+ID+question+"'";
        RequestQueue queue = Volley.newRequestQueue(context);
        if (!pDialog.isShowing())
            pDialog.setMessage("Syn with server...");
        pDialog.show();

        JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try{
                    for(int i = 0; i<response.length(); i++)
                    {
                        JSONObject questionResponse = (JSONObject)response.get(i);
                        String question = questionResponse.getString("question");
                        String choice1 = questionResponse.getString("choice1");
                        String choice2 = questionResponse.getString("choice2");
                        String choise3 = questionResponse.getString("choise3");
                        String answer = questionResponse.getString("answer");
                        quizLibrary = new QuizLibrary(question, choice1,choice2,choise3,answer);
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
        queue.add(request);
    }

    private void loadfile() {
        textViewQuestion.setText(quizLibrary.getQuestion());
        buttonChoice1.setText(quizLibrary.getChoice1());
        buttonChoice2.setText(quizLibrary.getChoice2());
        buttonChoice3.setText(quizLibrary.getChoise3());
    }

    public void updateQuestion(View view)
    {
        click++;
        if(click==4)
        {
            String text1 = buttonChoice1.getText().toString();
            if (text1.equals(quizLibrary.getAnswer())) {
                score += 1;
                textViewScore.setText("" + score);
                Toast.makeText(QuizActivity.this, "Correct", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(QuizActivity.this, "Wrong", Toast.LENGTH_LONG).show();
            }
            Intent intent = new Intent(QuizActivity.this,ScoreboardActivity.class);
            intent.putExtra("score",""+score);
            startActivity(intent);
            /*AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(QuizActivity.this);
            alertDialogBuilder.setMessage("Congratulation! Your Score is "+score);
            alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(QuizActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            });
            alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();*/
        }else {
            getQuestion(this, GET_URL);
            String text1 = buttonChoice1.getText().toString();
            if (text1.equals(quizLibrary.getAnswer())) {
                score += 1;
                textViewScore.setText("" + score);
                Toast.makeText(QuizActivity.this, "Correct", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(QuizActivity.this, "Wrong", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void updateQuestion2(View view)
    {
        click++;
        if(click==4)
        {
            String text2 = buttonChoice2.getText().toString();
            if (text2.equals(quizLibrary.getAnswer())) {
                score += 1;
                textViewScore.setText("" + score);
                Toast.makeText(QuizActivity.this, "Correct", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(QuizActivity.this, "Wrong", Toast.LENGTH_LONG).show();
            }
            Intent intent = new Intent(QuizActivity.this,ScoreboardActivity.class);
            intent.putExtra("score",""+score);
            startActivity(intent);
        }else {
            getQuestion(this, GET_URL);
            String text2 = buttonChoice2.getText().toString();
            if (text2.equals(quizLibrary.getAnswer())) {
                score += 1;
                textViewScore.setText("" + score);
                Toast.makeText(QuizActivity.this, "Correct", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(QuizActivity.this, "Wrong", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void updateQuestion3(View view)
    {
        click++;
        if(click==4)
        {
            String text3 = buttonChoice3.getText().toString();
            if (text3.equals(quizLibrary.getAnswer())) {
                score += 1;
                textViewScore.setText("" + score);
                Toast.makeText(QuizActivity.this, "Correct", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(QuizActivity.this, "Wrong", Toast.LENGTH_LONG).show();
            }
            Intent intent = new Intent(QuizActivity.this,ScoreboardActivity.class);
            intent.putExtra("score",""+score);
            startActivity(intent);
        }else {
            getQuestion(this, GET_URL);
            String text3 = buttonChoice3.getText().toString();
            if (text3.equals(quizLibrary.getAnswer())) {
                score += 1;
                textViewScore.setText("" + score);
                Toast.makeText(QuizActivity.this, "Correct", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(QuizActivity.this, "Wrong", Toast.LENGTH_LONG).show();
            }
        }
    }
}
