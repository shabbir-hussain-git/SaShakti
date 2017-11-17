package com.example.shabbir.sashakti;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

public class QuizSplash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_splash);

        Bundle b=getIntent().getExtras();
       final String  entireFile=b.getString("entireFile");
        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent i = new Intent(QuizSplash.this, Quiz.class);
                i.putExtra("entireFile",entireFile);
                startActivity(i);

                // close this activity
                finish();
            }
        },1000);
    }
}
