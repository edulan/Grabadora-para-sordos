package es.alltogether.c3po;

import java.util.Calendar;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import es.alltogether.c3po.db.RecordingTable;
import es.alltogether.c3po.models.Recording;
import es.alltogether.c3po.models.Session;

public class RecordDialog extends Activity {

	private RecordUtility record;
	private PlayerUtility player;
	private Session session;
	private Recording classSession;
	private RecordingTable recordingTable;

	private boolean recording = true;
	private boolean playing = true;
	public static final String PREFS_NAME = "SHARED_PREFERENCES";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.record);
		record = new RecordUtility();
		player = new PlayerUtility();
		recordingTable = new RecordingTable(this);
		OnClickListener clicker = new OnClickListener() {
			@Override
			public void onClick(View v) {
				Button recordButton = (Button) findViewById(R.id.recordButton);
				if (recording) {
					String path = createNewFilePath();
					record.startRecording(path);
					classSession = startClassRecording(path);
					recordButton.setText(R.string.stopRecording);
				} else {
					record.stopRecording();
					stopClassRecording(classSession);
					recordButton.setText(R.string.startRecording);
				}
				recording = !recording;
			}

			private void stopClassRecording(Recording recording) {
				recording.setEndDate(Calendar.getInstance().getTime());
				recordingTable.save(recording);
			}

			private Recording startClassRecording(String path) {
				Recording classSession = new Recording();
				classSession.setStartDate(Calendar.getInstance().getTime());
				classSession.setSession(session);
				classSession.setFile(path);
				return classSession;
			}
		};
		Button recordButton = (Button) findViewById(R.id.recordButton);
		recordButton.setOnClickListener(clicker);
		OnClickListener playClicker = new OnClickListener() {
			@Override
			public void onClick(View v) {
				play();
			}
		};
		Button playButton = (Button) findViewById(R.id.playButton);
		playButton.setOnClickListener(playClicker);

	}

	public String createNewFilePath() {
		boolean externalStorageAvailable = false;
		boolean externalStorageWriteable = false;
		String state = Environment.getExternalStorageState();

		if (Environment.MEDIA_MOUNTED.equals(state)) {
			// We can read and write the media
			externalStorageAvailable = externalStorageWriteable = true;
			return Environment.getExternalStoragePublicDirectory(
					Environment.DIRECTORY_MUSIC).getAbsolutePath()
					+ "/class"
					+ Calendar.getInstance().getTimeInMillis()
					+ ".3gp";
		} else {
			// We can only read the media
			externalStorageAvailable = true;
			externalStorageWriteable = false;
			Toast.makeText(this, R.string.storageSpace, Toast.LENGTH_LONG)
					.show();
			return getFilesDir().getAbsolutePath();
		}

	}

	@Override
	public void onPause() {
		super.onPause();
		record.pauseRecorder();
		player.pausePlayer();
	}

	private void play() {
		Button playButton = (Button) findViewById(R.id.playButton);
		if (playing) {
			player.startPlaying(classSession.getFile());
			playButton.setText(R.string.stopPlaying);
			player.getPlayer().setOnCompletionListener(
					new OnCompletionListener() {
						@Override
						public void onCompletion(MediaPlayer mp) {
							play();
						}
					});
		} else {
			player.stopPlaying();
			playButton.setText(R.string.startPlaying);
		}
		playing = !playing;
	}

	public Recording getClassSession() {
		return classSession;
	}

	public void setClassSession(Recording classSession) {
		this.classSession = classSession;
	}

}