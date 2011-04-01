package es.alltogether.c3po;

import java.io.IOException;

import android.app.Activity;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class RecordDialog extends Activity {
	/** Called when the activity is first created. */

	private MediaRecorder recorder = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		OnClickListener clicker = new OnClickListener() {
			public void onClick(View v) {
				onRecord(recording);
				Button recordButton = (Button) findViewById(R.id.recordButton);
				if (recording) {
					recordButton.setText(R.string.recording);
				} else {
					recordButton.setText(R.string.stoppedRecording);
				}
				recording = !recording;
			}
		};
		Button recordButton = (Button) findViewById(R.id.recordButton);
		recordButton.setOnClickListener(clicker);

	}

	boolean recording = true;

	private void onRecord(boolean start) {
		if (start) {
			startRecording();
		} else {
			stopRecording();
		}
	}

	private void startRecording() {
		recorder = new MediaRecorder();
		recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
		String mFileName = Environment.getExternalStorageDirectory()
				.getAbsolutePath();
		mFileName += "/audiorecordtest.3gp";
		recorder.setOutputFile(mFileName);
		recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

		try {
			recorder.prepare();
		} catch (IOException e) {
			Log.e("AudioRecordTest", "prepare() failed");
		}

		recorder.start();
	}

	private void stopRecording() {
		recorder.stop();
		recorder.release();
		recorder = null;
	}

}