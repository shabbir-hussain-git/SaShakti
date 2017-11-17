package com.example.shabbir.sashakti;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class CaseStudyDetails extends AppCompatActivity implements GestureDetector.OnGestureListener,GestureDetector.OnDoubleTapListener {

    private String info;
    //

    FloatingActionButton button;
    private GestureDetectorCompat gestureDetector;
    SeekBar seekBar1;
    FrameLayout frameLayout;
    Switch aSwitch;
    ViewGroup lt;
    String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_case_study_details);

        //Extracting the postion of element clicked on list view
        Bundle bundle = getIntent().getExtras();
        String jsonfile = bundle.getString("json");
        String getPosition = bundle.getString("position");

        //TypeFace
        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/avenir.otf");

        //Parsing the json and displaying the result on view
        try {
            JSONObject jsonObject = new JSONObject(jsonfile);
            JSONArray jsonArray = jsonObject.getJSONArray("CaseStudy");
            JSONObject c = jsonArray.getJSONObject(Integer.parseInt(getPosition));
            info = new String(c.getString("info"));
            TextView t1 = (TextView) findViewById(R.id.caseStudyTitle);
            t1.setText(c.getString("name"));
            t1.setTypeface(custom_font);

            text = c.getString("info");

            TextView t2 = (TextView) findViewById(R.id.caseStudyDescription);
            t2.setText(c.getString("info"));
            t2.setTypeface(custom_font);

        } catch (Exception e) {
            e.printStackTrace();
        }
        frameLayout=(FrameLayout) findViewById(R.id.frameLayout);
        ///All Touch Swipe SeekBar... Code goes here
        //Floating Button Animation
//        final ViewGroup transitionsContainer = (ViewGroup)findViewById(R.id.transitions_container);
//        final TextView text = (TextView) transitionsContainer.findViewById(R.id.text);
//         button = (FloatingActionButton) transitionsContainer.findViewById(R.id.button);
//
//        frameLayout=(FrameLayout) findViewById(R.id.frameLayout);
//
//
//        button.setOnClickListener(new View.OnClickListener() {
//
//            boolean visible;
//
//            @Override
//            public void onClick(View v) {
//                TransitionManager.beginDelayedTransition(transitionsContainer);
//                visible = !visible;
//                frameLayout.setVisibility(visible ? View.VISIBLE : View.GONE);
//            }
//
//        });
//        //Ends Here
        //SeekBAr Functionality
        seekBar1 = (SeekBar) findViewById(R.id.seekBar);
        seekBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Toast.makeText(getApplicationContext(),"seekbar progress: "+progress, Toast.LENGTH_SHORT).show();
                TextView txt = (TextView) findViewById(R.id.caseStudyDescription);
                txt.setTextSize(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        //SeekBar Functionality End
        //Switch Button Functionality
        aSwitch = (Switch) findViewById(R.id.myswitch);
        aSwitch.setChecked(false);
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                TextView txt = (TextView) findViewById(R.id.caseStudyDescription);
                if (isChecked) {
                    txt.setTextColor(Color.WHITE);
                    txt.setBackgroundColor(Color.BLACK);
                } else {
                    txt.setTextColor(Color.BLACK);
                    txt.setBackgroundColor(Color.WHITE);
                }
            }
        });
        //Ends Here
        //Gesture Detector Code
        this.gestureDetector = new GestureDetectorCompat(this, this);
        gestureDetector.setOnDoubleTapListener(this);

        new Handler().postDelayed(new Runnable() {

            //Initial Viewing of Float Button
            @Override
            public void run() {
                lt = (ViewGroup) findViewById(R.id.transitions_container);
                TransitionManager.beginDelayedTransition(lt);
                lt.setVisibility(View.GONE);
            }
        }, 1000);
    }


    public void shareContent(View view) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, info);
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // overridePendingTransition(  R.anim.slide_out_up,R.anim.slide_in_up );
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }


    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        lt = (ViewGroup) findViewById(R.id.transitions_container);
        TransitionManager.beginDelayedTransition(lt);
        if (lt.getVisibility() == View.GONE) {
            lt.setVisibility(View.VISIBLE);
            frameLayout.setVisibility(View.VISIBLE);
        } else {
            lt.setVisibility(View.GONE);
            frameLayout.setVisibility(View.GONE);
        }

        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
//        this.gestureDetector.onTouchEvent(event);
//        return super.onTouchEvent(event);
        super.dispatchTouchEvent(event);
        lt = (ViewGroup) findViewById(R.id.transitions_container);
        return gestureDetector.onTouchEvent(event);
//        if(lt.getVisibility() == View.GONE)
//            return gestureDetector.onTouchEvent(event);
//        else
//            return false;
    }
//
//    public void goGone(View view){
//        TransitionManager.beginDelayedTransition(lt);
//        button.performClick();
//        lt.setVisibility(View.GONE);
//    }

    public void speech(View view){
        Intent i=new Intent(this,TextToSpeach.class);
        i.putExtra("text",text);
        startActivity(i);

    }
}

