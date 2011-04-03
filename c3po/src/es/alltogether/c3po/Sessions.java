package es.alltogether.c3po;

import java.util.Calendar;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
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

		// Retrieve data from model
		SessionTable sessionTable = new SessionTable(this);
		List<Session> sessions = sessionTable.findByCriteria(null);
		subject = sessions.get(0).getSubject();
		Session s = new Session();
		s.setName("Biología 1");
		s.setDate(Calendar.getInstance().getTime());
		sessions.add(s);
		s = new Session();
		s.setName("Biología 2");
		s.setDate(Calendar.getInstance().getTime());
		sessions.add(s);
		s = new Session();
		s.setName("Biología 3");
		s.setDate(Calendar.getInstance().getTime());
		sessions.add(s);

		SessionAdapter adapter = new SessionAdapter(this, R.layout.subject_row,
				sessions);

		listView = (ListView) findViewById(R.id.list_view_sessions);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		Intent intent = new Intent(Sessions.this, RecordAndPlay.class);
		intent.putExtra("session", subject.getSessions().get(position));
		startActivity(intent);
	}
}
