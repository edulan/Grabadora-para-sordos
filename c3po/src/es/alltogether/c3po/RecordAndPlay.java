package es.alltogether.c3po;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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

public class RecordAndPlay extends Activity implements Runnable {

	private RecordWavUtility record;
	private PlayerUtility player;
	private RecordingTable recordingTable;
	private RecordingAdapter adapter;
	private Session session;
	private Recording recording;
	private AlertDialog alertDialog;
	private ProgressDialog pd;
	private HttpResponse response;
	private String responseText;

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
		recording = session.getRecordings().get(info.position);
		switch (item.getItemId()) {
		case R.id.deleteRecording:

			recording = session.getRecordings().remove(info.position);
			recordingTable.delete(recording);
			adapter.notifyDataSetChanged();
			Toast.makeText(getApplicationContext(), "Grabación eliminada",
					Toast.LENGTH_SHORT).show();
			break;
		case R.id.convertRecording:
			HttpClient client = new DefaultHttpClient();
			String url = "http://192.168.1.45:8080";
			HttpPost post = new HttpPost(url);
			try {
				File file = new File(recording.getFile());

				MultipartEntity entity = new MultipartEntity(
						HttpMultipartMode.BROWSER_COMPATIBLE);
				entity.addPart("file", new FileBody(file,
						"application/octet-stream", "UTF-8"));

				post.setEntity(entity);
				response = client.execute(post);
				responseText = EntityUtils.toString(response.getEntity());
//				pd = ProgressDialog.show(this, "Working..", "Calculating Pi",
//						true, false);
//
//				Thread thread = new Thread(this);
//				thread.start();

				JSONObject jsonTokener = (JSONObject) new JSONTokener(
						responseText).nextValue();
				Double confidence = (Double) jsonTokener.get("confidence");
				JSONArray capturedJSON = (JSONArray) jsonTokener
						.get("captured_json");
				Toast.makeText(
						this,
						"Hemos interpretado: '"
								+ ((JSONArray) capturedJSON.get(0))
										.getString(0)
								+ "' con una confianza del " + confidence,
						Toast.LENGTH_LONG).show();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

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

	public void run() {
		handler.sendEmptyMessage(0);
	}

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			pd.dismiss();
		}
	};

}