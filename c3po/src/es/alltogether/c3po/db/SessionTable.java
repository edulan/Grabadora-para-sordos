package es.alltogether.c3po.db;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.database.Cursor;
import es.alltogether.c3po.models.Session;

public class SessionTable {

	public static final String TABLE_NAME = "Sessions";

	DBHelper dbHelper;

	public SessionTable(Activity activity) {
		dbHelper = new DBHelper(activity);
	}

	public List<Session> findByCriteria(String where) {
		Cursor cursorResources = dbHelper.getReadableDatabase().query(
				TABLE_NAME, null, where, null, null, null, null);
		List<Session> resources = new ArrayList<Session>();
		while (cursorResources.moveToNext()) {
			resources.add(new Session(cursorResources));
		}
		return resources;
	}

	/**
	 * 
	 * @param id
	 * @return The matching Session or null.
	 */
	public Session findById(Long id) {
		String[] selectionArgs = { id.toString() };

		Cursor cursor = dbHelper.getReadableDatabase().query(TABLE_NAME, null,
				Session._ID + " = ?", selectionArgs, null, null, null);
		if (cursor.getCount() > 0) {
			return new Session(cursor);
		}
		return null;
	}

	/**
	 * 
	 * @param Session
	 * @return The number of rows affected.
	 */
	public int save(Session session) {
		int count = 0;

		if (session.isSaved()) {
			String[] selectionArgs = { session.getId().toString() };
			// Create
			count = dbHelper.getWritableDatabase().update(TABLE_NAME,
					session.getContentValues(), Session._ID + " = ?",
					selectionArgs);
		} else {
			Long id;
			// Update
			id = dbHelper.getWritableDatabase().insert(TABLE_NAME, null,
					session.getContentValues());
			if (id != -1) {
				session.setId(id);
				count = 1;
			}
		}
		return count;
	}

	/**
	 * 
	 * @param session
	 * @return The number of rows affected.
	 */
	public int delete(Session session) {
		String[] selectionArgs = { session.getId().toString() };

		int count = dbHelper.getWritableDatabase().delete(TABLE_NAME,
				Session._ID + " = ?", selectionArgs);
		return count;
	}

	public void clean() {
		dbHelper.getWritableDatabase().delete(TABLE_NAME, "1", null);
	}
}
