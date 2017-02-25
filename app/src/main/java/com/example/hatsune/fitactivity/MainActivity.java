package com.example.hatsune.fitactivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.hatsune.fitactivity.dto.ActivityDTO;
import com.example.hatsune.fitactivity.fragment.HistoryFragment;
import com.example.hatsune.fitactivity.fragment.TimerFragment;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FragmentTransaction fragmentTransaction;
    private HistoryFragment historyFragment;
    private TimerFragment timerFragment;
    private Toolbar toolbar;
    private List<ActivityDTO> data;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        data = new LinkedList<>();
        historyFragment = HistoryFragment.newInstance();
        timerFragment = TimerFragment.newInstance();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        fragmentTransaction = getSupportFragmentManager().beginTransaction();

        switch (item.getItemId()){
            case R.id.nav_history:
                new ActivityTask().execute();
                //toolbar.setTitle(R.string.history);
                fragmentTransaction.replace(R.id.content_main, historyFragment);
                fragmentTransaction.addToBackStack("tag_back");
                fragmentTransaction.commit();
                //historyFragment.refreshData(data);
                break;
            case R.id.nav_activity:

                //toolbar.setTitle(R.string.activity);
                fragmentTransaction.replace(R.id.content_main, timerFragment);
                fragmentTransaction.addToBackStack("tag_back");
                fragmentTransaction.commit();
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private class ActivityTask extends AsyncTask<Void, Void, List<ActivityDTO>>{

        @Override
        protected List<ActivityDTO> doInBackground(Void... voids) {
            RestTemplate template = new RestTemplate();
            template.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            Log.i("Object: ", "HJHHJHJjhKJHGVCFGHJJH");
            ResponseEntity<ActivityDTO[]> responseEntity = template.getForEntity(Constants.URL.GET_ACTYVITY, ActivityDTO[].class);
            ActivityDTO[] objects = responseEntity.getBody();
            Log.i("Object: ", String.valueOf(objects.length));

            return Arrays.asList(objects);
        }

        @Override
        protected void onPostExecute(List<ActivityDTO> list) {
            //List<ActivityDTO> list = new LinkedList<>();
            //list.add(activityDTO);
            historyFragment.setData(list);
            historyFragment.refreshData(list);
        }

    }



    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }
}
