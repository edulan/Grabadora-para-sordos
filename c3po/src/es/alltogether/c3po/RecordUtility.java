package es.alltogether.c3po;

import java.io.IOException;

import android.media.MediaRecorder;
import android.media.MediaRecorder.OnInfoListener;
import android.util.Log;

public class RecordUtility {

	String path;

	public RecordUtility(String path) {
		this.path = path;
	}

	private MediaRecorder recorder = null;

	public void onRecord(boolean start) {
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
		recorder.setOutputFile(path);
		recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

		try {
			recorder.prepare();
		} catch (IOException e) {
			Log.e("AUDIO_RECORD", "prepare() failed");
		}

		recorder.start();
	}

	private void stopRecording() {
		recorder.stop();
		recorder.release();
		recorder = null;
	}

	public void pauseRecorder() {
		if (recorder != null) {
			recorder.release();
			recorder = null;
		}
	}
}
