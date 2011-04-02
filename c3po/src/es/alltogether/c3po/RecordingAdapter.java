package es.alltogether.c3po;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import es.alltogether.c3po.models.Recording;

public class RecordingAdapter extends ArrayAdapter<Recording> {

	private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");

	private List<Recording> recordings;

	private Context ctx;

	public RecordingAdapter(Context ctx, int textViewResourceId,
			List<Recording> recordings) {
		super(ctx, textViewResourceId, recordings);
		this.ctx = ctx;
		this.recordings = recordings;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater layoutInflater = (LayoutInflater) ctx
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = layoutInflater.inflate(R.layout.row, null);
		}
		Recording recording = recordings.get(position);
		if (recording != null) {
			TextView topText = (TextView) convertView
					.findViewById(R.id.topText);
			TextView bottomText = (TextView) convertView
					.findViewById(R.id.bottomText);
			if (topText != null) {
				topText.setText("Comienzo: "
						+ sdf.format(recording.getStartDate()));
				Calendar startDate = Calendar.getInstance();
				startDate.setTime(recording.getStartDate());
				Calendar endDate = Calendar.getInstance();
				endDate.setTime(recording.getEndDate());
				bottomText.setText("Duración: "
						+ (endDate.getTimeInMillis() - startDate
								.getTimeInMillis()) / (60 * 1000) + " minutos");
			}
		}
		return convertView;
	}

	public Recording getItemAtPosition(int position) {
		return recordings.get(position);
	}
}
