package com.example.shabbir.sashakti;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class CaseStudy extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_case_study);

        Bundle b=getIntent().getExtras();
        String[] arr=b.getStringArray("data");
        Toast.makeText(this,arr[2],Toast.LENGTH_LONG).show();
    }
}
