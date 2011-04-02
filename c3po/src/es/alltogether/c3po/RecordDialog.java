package es.alltogether.c3po;

import java.util.Calendar;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import es.alltogether.c3po.db.RecordingTable;
import es.alltogether.c3po.models.Recording;
import es.alltogether.c3po.models.Session;

public class RecordDialog extends Activity {

	private RecordUtility record;
	private PlayerUtility player;
	private Session session;
	private Recording recording;
	private RecordingTable recordingTable;
	private RecordingAdapter adapter;

	private boolean stateRecording = true;
	private boolean statePlaying = true;
	public static final String PREFS_NAME = "SHARED_PREFERENCES";

	private ListView listView;

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
				.getMenuInfo();
		recording = session.getRecordings().remove(info.position);
		recordingTable.delete(recording);
		adapter.notifyDataSetChanged();
		Toast
				.makeText(getApplicationContext(), "Grabación eliminada",
						Toast.LENGTH_SHORT).show();
		return true;
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menudeleterecordings, menu);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.record);

		record = new RecordUtility();
		player = new PlayerUtility();
		recordingTable = new RecordingTable(this);

		// FIXME PASAME LA SESION
		session = new Session();
		session.setDate(Calendar.getInstance().getTime());
		session.setId(1l);
		session.setName("CLASE");

		listView = (ListView) findViewById(R.id.listViewRecord);
		adapter = new RecordingAdapter(this, R.layout.row, session
				.getRecordings());
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				recording = session.getRecordings().get(position);
				play();
			}
		});
		final Activity myActivity = this;
		registerForContextMenu(listView);

		OnClickListener clicker = new OnClickListener() {
			@Override
			public void onClick(View v) {
				Button recordButton = (Button) findViewById(R.id.recordButton);
				if (stateRecording) {
					String path = FileUtility.createNewFilePath(myActivity);
					record.startRecording(path);
					recording = startClassRecording(path);
					recordButton.setText(R.string.stopRecording);
				} else {
					record.stopRecording();
					stopClassRecording(recording);
					recordButton.setText(R.string.startRecording);
					adapter.notifyDataSetChanged();
				}
				stateRecording = !stateRecording;
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

	@Override
	public void onPause() {
		super.onPause();
		// record.pauseRecorder();
		player.pausePlayer();
	}

	private void play() {
		Button playButton = (Button) findViewById(R.id.playButton);
		if (statePlaying) {
			player.startPlaying(recording.getFile());
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
		statePlaying = !statePlaying;
	}

	public Recording getClassSession() {
		return recording;
	}

	public void setClassSession(Recording classSession) {
		this.recording = classSession;
	}

}