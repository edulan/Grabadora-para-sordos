package es.alltogether.c3po;

import java.util.Calendar;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
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
import es.alltogether.c3p0.utilities.FileUtility;
import es.alltogether.c3p0.utilities.PlayerUtility;
import es.alltogether.c3p0.utilities.RecordWavUtility;
import es.alltogether.c3po.db.RecordingTable;
import es.alltogether.c3po.models.Recording;
import es.alltogether.c3po.models.Session;

public class RecordAndPlay extends Activity {

	private RecordWavUtility record;
	private PlayerUtility player;
	private RecordingTable recordingTable;
	private RecordingAdapter adapter;
	private Session session;
	private Recording recording;
	private AlertDialog alertDialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.record);

		// record = new RecordUtility();
		record = new RecordWavUtility();
		player = new PlayerUtility();
		recordingTable = new RecordingTable(this);
		final Activity myActivity = this;

		session = (Session) getIntent().getSerializableExtra("session");
		List<Recording> recordings = recordingTable.findByCriteria(
				"session_id = ?", new String[] { session.getId().toString() });
		session.setRecordings(recordings);

		ListView listView = (ListView) findViewById(R.id.listViewRecord);
		adapter = new RecordingAdapter(this, R.layout.row, session
				.getRecordings());
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				recording = session.getRecordings().get(position);
				play(recording);
				TimeDialog playingDialog = new TimeDialog();
				alertDialog = playingDialog
						.createPlayingDialog((RecordAndPlay) myActivity);
			}
		});

		registerForContextMenu(listView);

		OnClickListener clicker = new OnClickListener() {
			@Override
			public void onClick(View v) {
				String path = FileUtility.createNewFilePath(myActivity);
				recording = startClassRecording(path);
				TimeDialog recordingDialog = new TimeDialog();
				recordingDialog.createRecordingDialog(
						(RecordAndPlay) myActivity, recording);
			}
		};
		Button recordButton = (Button) findViewById(R.id.recordButton);
		recordButton.setOnClickListener(clicker);
	}

	public void stopClassRecording(Recording recording) {
		record.stopRecording();
		recording.setEndDate(Calendar.getInstance().getTime());
		recordingTable.save(recording);
		adapter.notifyDataSetChanged();
	}

	private Recording startClassRecording(String path) {
		record.startRecording(path);
		recording = new Recording();
		recording.setStartDate(Calendar.getInstance().getTime());
		recording.setSession(session);
		recording.setFile(path);
		return recording;
	}

	public void play(final Recording recording) {
		player.startPlaying(recording.getFile());
		player.getPlayer().setOnCompletionListener(new OnCompletionListener() {
			@Override
			public void onCompletion(MediaPlayer mp) {
				stop();
				alertDialog.hide();
			}
		});
	}

	public void stop() {
		player.stopPlaying();
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
				.getMenuInfo();
		recording = session.getRecordings().remove(info.position);
		recordingTable.delete(recording);
		adapter.notifyDataSetChanged();
		Toast.makeText(getApplicationContext(), "Grabación eliminada",
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

	public Recording getRecording() {
		return recording;
	}

	public AlertDialog getAlertDialog() {
		return alertDialog;
	}

	public void setSession(Session session) {
		this.session = session;
	}

}