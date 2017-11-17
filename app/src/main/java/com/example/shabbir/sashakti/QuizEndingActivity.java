package com.example.shabbir.sashakti;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class QuizEndingActivity extends AppCompatActivity {
    private Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_ending);

        TextView high=(TextView)findViewById(R.id.highScore);
        Bundle b=getIntent().getExtras();
        int score = Integer.parseInt(b.getString("score"));
        int highScore = b.getInt("highScore");
        Boolean test = b.getBoolean("highScore");
        if(score > highScore)
        {

            high.setText("HighScore:"+"\n"+"\t\t\t"+ String.valueOf(score));
            ImageView im=(ImageView)findViewById(R.id.cup);
            im.setVisibility(View.VISIBLE);
        }
        else {
            high.setText("Highscore:"+"\n"+"\t\t\t"+ String.valueOf(highScore));
        }
        TextView t=(TextView)findViewById(R.id.score);
        t.setText("Score :"+"\n"+"\t\t"+ String.valueOf(score));
    }

    @Override
    public void onBackPressed() {

        i=new Intent(QuizEndingActivity.this,ST_Acts.class);
        startActivity(i);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
    }
    public void returnHome(View view){
        onBackPressed();
    }
}

