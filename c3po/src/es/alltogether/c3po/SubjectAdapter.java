package es.alltogether.c3po;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import es.alltogether.c3po.db.SessionTable;
import es.alltogether.c3po.models.Session;
import es.alltogether.c3po.models.Subject;

public class SubjectAdapter extends ArrayAdapter<Subject> {

	private final List<Subject> subjects;
	private final Context ctx;

	public SubjectAdapter(Context ctx, int textViewResourceId,
			List<Subject> subjects) {
		super(ctx, textViewResourceId, subjects);
		this.ctx = ctx;
		this.subjects = subjects;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// Create view for first time
		if (convertView == null) {
			LayoutInflater layoutInflater = (LayoutInflater) ctx
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = layoutInflater.inflate(R.layout.subject_row, null);
		}
		// Retrieve sessions for subject
		Subject subject = getItemAtPosition(position);
		SessionTable sessionTable = new SessionTable(ctx);
		String[] selectionArgs = { subject.getId().toString() };
		List<Session> sessions = sessionTable.findByCriteria(Session._ID
				+ " = ?", selectionArgs);
		// Populate view with subject data
		if (subject != null) {
			TextView topText = (TextView) convertView
					.findViewById(R.id.top_text);
			TextView bottomText = (TextView) convertView
					.findViewById(R.id.description_text);
			topText.setText(subject.getName());
			bottomText.setText(String.valueOf(sessions.size()));
		}
		return convertView;
	}

	public Subject getItemAtPosition(int position) {
		return subjects.get(position);
	}
}
