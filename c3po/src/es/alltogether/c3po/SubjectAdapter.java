package es.alltogether.c3po;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
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
		// Populate view with subject data
		Subject subject = getItemAtPosition(position);
		if (subject != null) {
			TextView topText = (TextView) convertView
					.findViewById(R.id.top_text);
			TextView bottomText = (TextView) convertView
					.findViewById(R.id.description_text);
			topText.setText(subject.getName());
			bottomText.setText("12 clases");
		}
		return convertView;
	}

	public Subject getItemAtPosition(int position) {
		return subjects.get(position);
	}
}
