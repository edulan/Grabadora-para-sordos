package es.alltogether.c3po;

import android.app.Activity;
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
	private String fileName = Environment.getExternalStorageDirectory()
			.getAbsolutePath()
			+ "/audiorecordtest.3gp";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.record);

		record = new RecordUtility(fileName);
		player = new PlayerUtility(fileName);
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
				player.onPlay(playing);
				Button recordButton = (Button) findViewById(R.id.playButton);
				if (playing) {
					recordButton.setText(R.string.stopPlaying);
				} else {
					recordButton.setText(R.string.startPlaying);
				}
				playing = !playing;
			}
		};
		Button playButton = (Button) findViewById(R.id.playButton);
		playButton.setOnClickListener(playClicker);

	}

	@Override
	public void onPause() {
		super.onPause();
		record.pauseRecorder();
		player.pausePlayer();
	}

	
}