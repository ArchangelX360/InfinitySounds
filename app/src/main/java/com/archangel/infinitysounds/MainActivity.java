package com.archangel.infinitysounds;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;


public class MainActivity extends Activity {

    private ListView listView;

    private ArrayAdapter<Sound> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.listView = (ListView) findViewById(R.id.list);
        this.listView.setEmptyView(findViewById(R.id.no_sound));
        adapter = new ArrayAdapter<Sound>(this, android.R.layout.simple_list_item_1);
        adapter.addAll(getSounds());
        adapter.notifyDataSetChanged();
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
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private static final String TAG = "MainActivity";

    private Sound[] getSounds() {
        InputStream json = getResources().openRawResource(R.raw.sounds);
        BufferedReader reader = new BufferedReader(new InputStreamReader(json, Charset.forName("UTF-8")));
        Sound[] test =  new Gson().fromJson(reader, Sound[].class);
        Log.v(TAG, test[0].toString());
        Log.v(TAG, test[1].toString());
        Log.v(TAG, test[2].toString());
        return test;
    }
}
