package es.alltogether.c3po;

import android.app.Activity;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class RecordDialog extends Activity {

	private RecordUtility record;
	private PlayerUtility player;

	private boolean recording = true;
	private boolean playing = true;
	public static final String PREFS_NAME = "SHARED_PREFERENCES";
	private String path = Environment.getExternalStorageDirectory()
			.getAbsolutePath()
			+ "/audiorecordtest.3gp";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.record);
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		settings.getString("path", path);
		record = new RecordUtility(path);
		player = new PlayerUtility(path);
		OnClickListener clicker = new OnClickListener() {
			public void onClick(View v) {
				record.onRecord(recording);
				Button recordButton = (Button) findViewById(R.id.recordButton);
				if (recording) {
					recordButton.setText(R.string.stopRecording);
				} else {
					recordButton.setText(R.string.startRecording);
				}
				recording = !recording;
			}
		};
		Button recordButton = (Button) findViewById(R.id.recordButton);
		recordButton.setOnClickListener(clicker);
		OnClickListener playClicker = new OnClickListener() {
			public void onClick(View v) {
				play();
			}
		};
		Button playButton = (Button) findViewById(R.id.playButton);
		playButton.setOnClickListener(playClicker);

	}

	public void setPath(String path) {
		this.path = path;
		record.setPath(path);
	}

	@Override
	public void onPause() {
		super.onPause();
		record.pauseRecorder();
		player.pausePlayer();
	}

	private void play() {
		player.onPlay(playing);
		Button playButton = (Button) findViewById(R.id.playButton);
		if (playing) {
			playButton.setText(R.string.stopPlaying);
			player.getPlayer().setOnCompletionListener(
					new OnCompletionListener() {
						@Override
						public void onCompletion(MediaPlayer mp) {
							play();
						}
					});
		} else {
			playButton.setText(R.string.startPlaying);
		}
		playing = !playing;
	}


}