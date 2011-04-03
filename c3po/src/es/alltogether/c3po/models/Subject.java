package es.alltogether.c3po.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;

public class Subject implements BaseColumns, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String NAME = "name";

	private Long id;
	private String name;
	private List<Session> sessions = new ArrayList<Session>();

	public List<Session> getSessions() {
		return sessions;
	}

	public void setSessions(List<Session> sessions) {
		this.sessions = sessions;
	}

	public Subject(Cursor cursorResources) {
		setId(cursorResources.getLong(0));
		setName(cursorResources.getString(1));
	}

	public ContentValues getContentValues() {
		ContentValues contentValues = new ContentValues();
		contentValues.put(NAME, name);
		return contentValues;
	}

	public Subject() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isSaved() {
		return id != null && id > 0;
	}
}
