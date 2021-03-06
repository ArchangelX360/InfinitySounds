package com.archangel.infinitysounds;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;


public class MainActivity extends Activity implements AdapterView.OnItemClickListener {

	private static final String LOG_TAG = MainActivity.class.getSimpleName();

	private ListView listView;

	private ArrayAdapter<Sound> adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		this.listView = (ListView) findViewById(R.id.list);
		this.listView.setEmptyView(findViewById(R.id.no_sound));
		adapter = new ArrayAdapter<Sound>(this, android.R.layout.simple_list_item_1, getSounds());
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(this);
	}

	@Override
	synchronized public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Sound soundToPlay = adapter.getItem(position);
		try {
			final MediaPlayer mediaPlayer = new MediaPlayer();
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			AssetFileDescriptor afd = getAssets().openFd("sounds/" + soundToPlay.getFile());
			mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(),
									  afd.getLength());
			mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
				@Override
				public void onCompletion(MediaPlayer mp) {
					mediaPlayer.stop();
					mediaPlayer.release();
				}
			});
			mediaPlayer.prepare();
			Log.i(LOG_TAG, "duration: " + mediaPlayer.getDuration());
			mediaPlayer.start();
		} catch (IOException e) {
			Log.e(LOG_TAG, e.getMessage(), e);
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
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private Sound[] getSounds() {
		InputStream json = getResources().openRawResource(R.raw.sounds);
		BufferedReader reader = new BufferedReader(
				new InputStreamReader(json, Charset.forName("UTF-8")));
		return new Gson().fromJson(reader, Sound[].class);
	}
}
