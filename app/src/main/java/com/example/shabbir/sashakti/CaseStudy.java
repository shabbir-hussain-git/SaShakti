package com.example.shabbir.sashakti;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.LayoutTransition;
import android.annotation.TargetApi;
import android.app.SearchManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CaseStudy extends AppCompatActivity {

    String entireFile;

    List<General> generalList;
    private RecyclerView recyclerView;
    private GeneralAdapter mAdapter;
    Toolbar myToolbar;
    private float TOOLBAR_ELEVATION=14f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_case_study);

        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);



        Bundle b=getIntent().getExtras();
        String[] arr=b.getStringArray("data");
        String startname=b.getString("startname");
        entireFile = b.getString("entireFile");

        generalList=new ArrayList<>();
        for(int i=0;i<arr.length;i++){
            int k=i+1;
            String uri = "@mipmap/"+startname+String.valueOf(k);  // where myresource (without the extension) is the file
            int imageResource = getResources().getIdentifier(uri, null, getPackageName());
            generalList.add(new General(arr[i],imageResource));
        }
        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
//        StaggeredGridLayoutManager gridLayoutManager =
//                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        mAdapter = new GeneralAdapter(generalList);
        recyclerView.setAdapter(mAdapter);

        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                String pos=String.valueOf(position);
                //Redirecting the string representation to deatil page activity along with the position is clicked
                Intent i=new Intent(CaseStudy.this,CaseStudyDetails.class);
                i.putExtra("json",entireFile);
                i.putExtra("position",pos);
                startActivity(i);
                //Animate the transition to next page
                overridePendingTransition( R.anim.rtol, R.anim.activity_close );
            }
        });

        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {

            // Keeps track of the overall vertical offset in the list
            int verticalOffset;

            // Determines the scroll UP/DOWN direction
            boolean scrollingUp;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (scrollingUp) {
                        if (verticalOffset > myToolbar.getHeight()) {
                            toolbarAnimateHide();
                        } else {
                            toolbarAnimateShow(verticalOffset);
                        }
                    } else {
                        if (myToolbar.getTranslationY() < myToolbar.getHeight() * -0.6 && verticalOffset > myToolbar.getHeight()) {
                            toolbarAnimateHide();
                        } else {
                            toolbarAnimateShow(verticalOffset);
                        }
                    }
                }
            }


            @Override
            public final void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                verticalOffset += dy;
                scrollingUp = dy > 0;
                int toolbarYOffset = (int) (dy - myToolbar.getTranslationY());
                myToolbar.animate().cancel();
                if (scrollingUp) {
                    if (toolbarYOffset < myToolbar.getHeight()) {
                        if (verticalOffset > myToolbar.getHeight()) {
                            toolbarSetElevation(TOOLBAR_ELEVATION);
                        }
                        myToolbar.setTranslationY(-toolbarYOffset);
                    } else {
                        toolbarSetElevation(0);
                        myToolbar.setTranslationY(-myToolbar.getHeight());
                    }
                } else {
                    if (toolbarYOffset < 0) {
                        if (verticalOffset <= 0) {
                            toolbarSetElevation(0);
                        }
                        myToolbar.setTranslationY(0);
                    } else {
                        if (verticalOffset > myToolbar.getHeight()) {
                            toolbarSetElevation(TOOLBAR_ELEVATION);
                        }
                        myToolbar.setTranslationY(-toolbarYOffset);
                    }
                }
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void toolbarSetElevation(float elevation) {
        // setElevation() only works on Lollipop
    }

    private void toolbarAnimateShow(final int verticalOffset) {
        myToolbar.animate()
                .translationY(0)
                .setInterpolator(new LinearInterpolator())
                .setDuration(180)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        toolbarSetElevation(verticalOffset == 0 ? 0 : TOOLBAR_ELEVATION);
                    }
                });

    }

    private void toolbarAnimateHide() {
        myToolbar.animate()
                .translationY(-myToolbar.getHeight())
                .setInterpolator(new LinearInterpolator())
                .setDuration(180)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        toolbarSetElevation(0);
                    }
                });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);

        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.menuSearch));
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        MenuItem item=menu.findItem(R.id.menuSearch);
        searchView = (SearchView)item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //adapter.getFilter().filter(newText);
                filter(newText.toLowerCase());
                return false;
            }
        });

        LinearLayout searchBar = (LinearLayout) searchView.findViewById(R.id.search_bar);
        searchBar.setLayoutTransition(new LayoutTransition());
        return true;
    }

    void filter(String text){
        List<General> temp = new ArrayList();
        for(General d: generalList){
            //or use .equal(text) with you want equal match
            //use .toLowerCase() for better matches
            if(d.getCaseInName().contains(text)){
                temp.add(d);
            }
        }
        //update recyclerview
        mAdapter.updateList(temp);
    }

}
