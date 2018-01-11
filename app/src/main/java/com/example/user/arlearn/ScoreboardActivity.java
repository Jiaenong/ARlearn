package com.example.user.arlearn;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.timqi.sectorprogressview.ColorfulRingProgressView;

public class ScoreboardActivity extends AppCompatActivity {

    private TextView textViewCongrac;
    private TextView textViewMessage;
    //private ImageView imageViewPercent;
    private TextView textViewPercent;
    private ColorfulRingProgressView crpv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        textViewCongrac = (TextView)findViewById(R.id.textViewCongrac);
        textViewMessage = (TextView)findViewById(R.id.textViewMessage);
        textViewPercent = (TextView)findViewById(R.id.textViewPercent);
        //imageViewPercent = (ImageView)findViewById(R.id.imageViewPercent);
        crpv = (ColorfulRingProgressView)findViewById(R.id.crpv);
        Intent intent = getIntent();
        String input = intent.getStringExtra("score");
        //int score = Integer.parseInt(input);
        //int mark = (score/4)*100;
        if(input.equals("0"))
        {
            textViewCongrac.setText("Poor");
            //imageViewPercent.setImageResource(R.drawable.zero_percent);
            crpv.setPercent(0);
            textViewPercent.setText(0+"%");
            textViewMessage.setText("Please try more harder");
        }
        else if(input.equals("1"))
        {
            textViewCongrac.setText("Moderate");
            //imageViewPercent.setImageResource(R.drawable.download);
            crpv.setPercent(25);
            textViewPercent.setText(25+"%");
            textViewMessage.setText("You can do it");
        }
        else if(input.equals("2"))
        {
            textViewCongrac.setText("Good");
            //imageViewPercent.setImageResource(R.drawable.fiftypercent);
            crpv.setPercent(50);
            textViewPercent.setText(50+"%");
            textViewMessage.setText("You can be better");
        }
        else if(input.equals("3"))
        {
            textViewCongrac.setText("Better");
            //imageViewPercent.setImageResource(R.drawable.seventy_five);
            crpv.setPercent(75);
            textViewPercent.setText(75+"%");
            textViewMessage.setText("Jusr need abit of revision");
        }
        else if(input.equals("4"))
        {
            textViewCongrac.setText("Perfect");
            //imageViewPercent.setImageResource(R.drawable.hundred);
            crpv.setPercent(100);
            textViewPercent.setText(100+"%");
            textViewMessage.setText("You are Genius!!");
        }
        Button button = (Button)findViewById(R.id.buttonOK);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScoreboardActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }

}
