package es.alltogether.c3po.models;

import java.util.Calendar;
import java.util.Date;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;

public class Recording implements BaseColumns {

	// Table columns
	public static final String FILE = "file";
	public static final String START_DATE = "start_date";
	public static final String END_DATE = "end_date";
	public static final String SESSION_ID = "session_id";

	private Long id;
	private Date startDate;
	private Date endDate;
	private String file;
	private Session session = new Session();

	public Recording(Cursor cursorResources) {
		setId(cursorResources.getLong(0));
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(cursorResources.getLong(1));
		setStartDate(calendar.getTime());
		calendar.setTimeInMillis(cursorResources.getLong(2));
		setEndDate(calendar.getTime());
		setFile(cursorResources.getString(3));
		getSession().setId(cursorResources.getLong(4));
	}

	public ContentValues getContentValues() {
		ContentValues contentValues = new ContentValues();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(startDate);
		contentValues.put(START_DATE, calendar.getTimeInMillis());
		calendar.setTime(endDate);
		contentValues.put(END_DATE, calendar.getTimeInMillis());
		contentValues.put(FILE, getFile());
		contentValues.put(SESSION_ID, session.getId());
		return contentValues;
	}

	public Recording() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
		session.getRecordings().add(this);
	}

}
