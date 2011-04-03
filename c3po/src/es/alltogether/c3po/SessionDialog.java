package es.alltogether.c3po;

import java.util.Calendar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import es.alltogether.c3po.db.SessionTable;
import es.alltogether.c3po.models.Session;
import es.alltogether.c3po.models.Subject;

public class SessionDialog extends Activity implements OnClickListener {

	private Subject subject;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.session_dialog);

		subject = (Subject) getIntent().getSerializableExtra("subject");

		Button saveButton = (Button) findViewById(R.id.saveSession);
		Button cancelButton = (Button) findViewById(R.id.cancelSession);

		saveButton.setOnClickListener(this);
		cancelButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.saveSession:
			EditText nameText = (EditText) findViewById(R.id.sessionName);

			Session session = new Session();
			session.setName(nameText.getText().toString());
			DatePicker datePicker = (DatePicker) findViewById(R.id.sessionDate);
			Calendar calendar = Calendar.getInstance();
			calendar.set(datePicker.getYear(), datePicker.getMonth(),
					datePicker.getDayOfMonth());
			session.setDate(calendar.getTime());
			session.setSubject(subject);

			SessionTable sessionTable = new SessionTable(this);
			sessionTable.save(session);

			Intent intent = new Intent(this, Sessions.class);
			intent.putExtra("subject", subject);
			startActivity(intent);
			break;
		case R.id.button_cancel:
			Intent intent2 = new Intent(this, Sessions.class);
			intent2.putExtra("subject", subject);
			startActivity(intent2);
			break;
		}
	}
}
