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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import es.alltogether.c3po.db.SubjectTable;
import es.alltogether.c3po.models.Subject;

public class Subjects extends Activity implements OnItemClickListener {

	private static final int SUBJECT_DIALOG_REQUEST_CODE = 1;

	List<Subject> subjects;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.subject);
		// Retrieve data from model
		SubjectTable subjectTable = new SubjectTable(this);
		subjects = subjectTable.findByCriteria(null);
		SubjectAdapter adapter = new SubjectAdapter(this, R.layout.subject_row,
				subjects);
		ListView listView = (ListView) findViewById(R.id.list_view_subjects);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(this);
		TextView infoText = (TextView) findViewById(R.id.text_view_info);

		if (!subjects.isEmpty()) {
			infoText.setVisibility(View.GONE);
		} else {
			infoText.setVisibility(View.VISIBLE);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		// Retrieve data from model
		SubjectTable subjectTable = new SubjectTable(this);
		subjects = subjectTable.findByCriteria(null);
		SubjectAdapter adapter = new SubjectAdapter(this, R.layout.subject_row,
				subjects);
		ListView listView = (ListView) findViewById(R.id.list_view_subjects);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(this);
		TextView infoText = (TextView) findViewById(R.id.text_view_info);

		if (!subjects.isEmpty()) {
			infoText.setVisibility(View.GONE);
		} else {
			infoText.setVisibility(View.VISIBLE);
		}

		if (resultCode == RESULT_OK) {
			Toast.makeText(getApplicationContext(), "Asignatura a�adida.",
					Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.subject_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.add_subject:
			Intent intent = new Intent(this, SubjectDialog.class);
			startActivityForResult(intent, SUBJECT_DIALOG_REQUEST_CODE);
			return true;
		}
		return false;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		Intent intent = new Intent(Subjects.this, Sessions.class);
		intent.putExtra("subject", subjects.get(position));
		startActivity(intent);
	}
}
