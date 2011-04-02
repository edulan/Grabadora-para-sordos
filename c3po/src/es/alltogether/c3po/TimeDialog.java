package es.alltogether.c3po;

import es.alltogether.c3po.models.Recording;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.SystemClock;

public class TimeDialog {

	private long startTime;
	private AlertDialog alertDialog;
	private Handler handler = new Handler();
	private String endMessage;

	public AlertDialog createPlayingDialog(final RecordAndPlay myActivity) {
		endMessage = "Reproduciendo... ";
		return createDialog(myActivity, "Reprodución iniciada", endMessage,
				null);
	}

	public AlertDialog createRecordingDialog(final RecordAndPlay myActivity,
			Recording recording) {
		endMessage = "Grabando... ";
		return createDialog(myActivity, "Grabación iniciada", endMessage,
				recording);
	}

	public AlertDialog createDialog(final RecordAndPlay myActivity,
			String initMessage, String endMessage, final Recording recording) {
		Builder builder = new AlertDialog.Builder(myActivity);
		builder.setTitle(initMessage);
		builder.setMessage(endMessage);
		builder.setPositiveButton("Terminar",
				new AlertDialog.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						handler.removeCallbacks(updateTimeTask);
						startTime = 0;
						if (recording != null) {
							myActivity.stopClassRecording(recording);
						} else {
							myActivity.stop();
						}
					}
				});
		alertDialog = builder.create();
		if (startTime == 0L) {
			startTime = System.currentTimeMillis();
			handler.removeCallbacks(updateTimeTask);
			handler.postDelayed(updateTimeTask, 100);
		}
		alertDialog.show();
		return alertDialog;
	}

	private Runnable updateTimeTask = new Runnable() {
		public void run() {
			final long start = startTime;
			long millis = System.currentTimeMillis() - start;
			int seconds = (int) (millis / 1000);
			int minutes = seconds / 60;
			seconds = seconds % 60;
			if (seconds < 10) {
				alertDialog.setMessage(endMessage + minutes + ":0" + seconds);
			} else {
				alertDialog.setMessage(endMessage + minutes + ":" + seconds);
			}

			handler.postAtTime(this, SystemClock.uptimeMillis() + 500);
		}
	};

}
