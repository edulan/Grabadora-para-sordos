package es.alltogether.c3po.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;

public class Session implements BaseColumns,Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String DATE = "date";
	public static final String NAME = "name";
	public static final String SUBJECT_ID = "subject_id";

	private Long id;
	private Date date = new Date();
	private String name;
	private Subject subject = new Subject();
	private List<Recording> recordings = new ArrayList<Recording>();

	public Session(Cursor cursorResources) {
		setId(cursorResources.getLong(0));
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(cursorResources.getLong(1));
		setDate(calendar.getTime());
		setName(cursorResources.getString(2));
		getSubject().setId(cursorResources.getLong(3));
	}

	public Session() {
	}

	public ContentValues getContentValues() {
		ContentValues contentValues = new ContentValues();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		contentValues.put(DATE, calendar.getTimeInMillis());
		contentValues.put(NAME, name);
		contentValues.put(SUBJECT_ID, subject.getId());
		return contentValues;
	}

	public List<Recording> getRecordings() {
		return recordings;
	}

	public void setRecordings(List<Recording> recordings) {
		this.recordings = recordings;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
		subject.getSessions().add(this);
	}

	public boolean isSaved() {
		return id != null && id > 0;
	}
}
