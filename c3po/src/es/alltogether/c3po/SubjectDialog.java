package es.alltogether.c3po;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import es.alltogether.c3po.db.SubjectTable;
import es.alltogether.c3po.models.Subject;

public class SubjectDialog extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.subject_dialog);

		Button saveButton = (Button) findViewById(R.id.button_save);
		Button cancelButton = (Button) findViewById(R.id.button_cancel);

		saveButton.setOnClickListener(this);
		cancelButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent intent;

		switch (v.getId()) {
		case R.id.button_save:
			EditText nameText = (EditText) findViewById(R.id.subject_text);

			Subject subject = new Subject();
			subject.setName(nameText.getText().toString());

			SubjectTable subjectTable = new SubjectTable(this);
			subjectTable.save(subject);

			intent = new Intent();
			setResult(RESULT_OK, intent);
			finish();
			break;
		case R.id.button_cancel:
			intent = new Intent();
			setResult(RESULT_CANCELED, intent);
			finish();
			break;
		}
	}
}
