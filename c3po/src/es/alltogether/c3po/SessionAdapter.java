package es.alltogether.c3po;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import es.alltogether.c3po.models.Session;

public class SessionAdapter extends ArrayAdapter<Session> {

	private final List<Session> sessions;
	private final Context ctx;

	public SessionAdapter(Context ctx, int textViewResourceId,
			List<Session> sessions) {
		super(ctx, textViewResourceId, sessions);
		this.ctx = ctx;
		this.sessions = sessions;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// Create view for first time
		if (convertView == null) {
			LayoutInflater layoutInflater = (LayoutInflater) ctx
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = layoutInflater.inflate(R.layout.subject_row, null);
		}
		// Populate view with session data
		Session session = getItemAtPosition(position);
		if (session != null) {
			TextView topText = (TextView) convertView
					.findViewById(R.id.top_text);
			TextView bottomText = (TextView) convertView
					.findViewById(R.id.description_text);
			topText.setText(session.getName());
			bottomText.setText(session.getDate().toString());
		}
		return convertView;
	}

	public Session getItemAtPosition(int position) {
		return sessions.get(position);
	}
}
