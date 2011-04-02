package es.alltogether.c3po.db;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.database.Cursor;
import es.alltogether.c3po.models.Subject;

public class SubjectTable {

	public static final String TABLE_NAME = "subjects";

	DBHelper dbHelper;

	public SubjectTable(Activity activity) {
		dbHelper = new DBHelper(activity);
	}

	public List<Subject> findByCriteria(String where) {
		Cursor cursorResources = dbHelper.getReadableDatabase().query(
				TABLE_NAME, null, where, null, null, null, null);
		List<Subject> resources = new ArrayList<Subject>();
		while (cursorResources.moveToNext()) {
			resources.add(new Subject(cursorResources));
		}
		return resources;
	}

	/**
	 * 
	 * @param id
	 * @return The matching subject or null.
	 */
	public Subject findById(Long id) {
		String[] selectionArgs = { id.toString() };

		Cursor cursor = dbHelper.getReadableDatabase().query(TABLE_NAME, null,
				Subject._ID + " = ?", selectionArgs, null, null, null);
		if (cursor.getCount() > 0) {
			return new Subject(cursor);
		}
		return null;
	}

	/**
	 * 
	 * @param subject
	 * @return The number of rows affected.
	 */
	public int save(Subject subject) {
		int count = 0;

		if (subject.isSaved()) {
			String[] selectionArgs = { subject.getId().toString() };
			// Create
			count = dbHelper.getWritableDatabase().update(TABLE_NAME,
					subject.getContentValues(), Subject._ID + " = ?",
					selectionArgs);
		} else {
			Long id;
			// Update
			id = dbHelper.getWritableDatabase().insert(TABLE_NAME, null,
					subject.getContentValues());
			if (id != -1) {
				subject.setId(id);
				count = 1;
			}
		}
		return count;
	}

	/**
	 * 
	 * @param subject
	 * @return The number of rows affected.
	 */
	public int delete(Subject subject) {
		String[] selectionArgs = { subject.getId().toString() };

		int count = dbHelper.getWritableDatabase().delete(TABLE_NAME,
				Subject._ID + " = ?", selectionArgs);
		return count;
	}

	public void clean() {
		dbHelper.getWritableDatabase().delete(TABLE_NAME, "1", null);
	}
}
