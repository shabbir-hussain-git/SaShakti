package com.example.shabbir.sashakti;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ST_Acts extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_st__acts);
    }

    public void CaseStudy(View view) {
        String[] arr={"I was woken at four in the morning and then I had to wash the clothes, sweep and mop the floor"
                ,"Child Welfare Committee issues directions for registration of case",
                "Story of K.Sandhya from Chandupatla Village, Nalgonda",
                "Citing Muslim Personal Law Delhi Court Justifies Marriage Of A 15-Yr-Old Girl.",
                "Tamil Nadu Ranks First in Immoral Trafficing.",
                "Initiatives pertaining to beedi work",
                "Assessment and affirmative action by a UNHCR Health officers",
                "Individual Cases alert UNHCR of Critical gaps in IYCF system ",
                "Child Welfare Committee issues directions for registration of case",
                "Five cases of child molestation in Indian schools"};

        Intent i=new Intent(this,CaseStudy.class);
        i.putExtra("data",arr);
        startActivity(i);


    }
}
