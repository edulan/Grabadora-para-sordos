package es.alltogether.c3po;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import es.alltogether.c3po.db.SessionTable;
import es.alltogether.c3po.models.Session;
import es.alltogether.c3po.models.Subject;

public class Sessions extends Activity implements OnItemClickListener {

	private ListView listView;
	private Subject subject;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.session);

		SessionTable sessionTable = new SessionTable(this);
		subject = (Subject) getIntent().getSerializableExtra("subject");

		List<Session> sessions = sessionTable.findByCriteria("subject_id = ?",
				new String[] { subject.getId().toString() });
		subject.setSessions(sessions);

		SessionAdapter adapter = new SessionAdapter(this, R.layout.subject_row,
				sessions);

		listView = (ListView) findViewById(R.id.list_view_sessions);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.session_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.add_session:
			Intent intent = new Intent(this, SessionDialog.class);
			intent.putExtra("subject", subject);
			startActivity(intent);
			return true;
		}
		return false;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		Intent intent = new Intent(Sessions.this, RecordAndPlay.class);
		intent.putExtra("session", subject.getSessions().get(position));
		startActivity(intent);
	}
}
