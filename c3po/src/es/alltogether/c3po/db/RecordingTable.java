package es.alltogether.c3po.db;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.database.Cursor;
import es.alltogether.c3po.models.Recording;

public class RecordingTable {

	public static final String TABLE_NAME = "recordings";

	DBHelper dbHelper;

	public RecordingTable(Activity activity) {
		dbHelper = new DBHelper(activity);
	}

	public List<Recording> findByCriteria(String where, String[] strings) {
		Cursor cursorResources = dbHelper.getReadableDatabase().query(
				TABLE_NAME, null, where, strings, null, null, null);
		List<Recording> resources = new ArrayList<Recording>();
		while (cursorResources.moveToNext()) {
			resources.add(new Recording(cursorResources));
		}
		return resources;
	}

	/**
	 * 
	 * @param id
	 * @return The matching recording or null.
	 */
	public Recording findById(Long id) {
		String[] selectionArgs = { id.toString() };

		Cursor cursor = dbHelper.getReadableDatabase().query(TABLE_NAME, null,
				Recording._ID + " = ?", selectionArgs, null, null, null);
		if (cursor.getCount() > 0) {
			return new Recording(cursor);
		}
		return null;
	}

	/**
	 * 
	 * @param recording
	 * @return The number of rows affected.
	 */
	public int save(Recording recording) {
		int count = 0;

		if (recording.isSaved()) {
			String[] selectionArgs = { recording.getId().toString() };
			// Create
			count = dbHelper.getWritableDatabase().update(TABLE_NAME,
					recording.getContentValues(), Recording._ID + " = ?",
					selectionArgs);
		} else {
			Long id;
			// Update
			id = dbHelper.getWritableDatabase().insert(TABLE_NAME, null,
					recording.getContentValues());
			if (id != -1) {
				recording.setId(id);
				count = 1;
			}
		}
		return count;
	}

	/**
	 * 
	 * @param recording
	 * @return The number of rows affected.
	 */
	public int delete(Recording recording) {
		String[] selectionArgs = { recording.getId().toString() };

		int count = dbHelper.getWritableDatabase().delete(TABLE_NAME,
				Recording._ID + " = ?", selectionArgs);
		return count;
	}

	public void clean() {
		dbHelper.getWritableDatabase().delete(TABLE_NAME, "1", null);
	}
}
