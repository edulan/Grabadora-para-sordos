package es.alltogether.c3po.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import es.alltogether.c3po.models.Recording;
import es.alltogether.c3po.models.Session;
import es.alltogether.c3po.models.Subject;

public class DBHelper extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "recorder.db";
	private static final int DATABASE_VERSION = 2;

	/** Create a helper object for the Events database */
	public DBHelper(Context ctx) {
		super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// Recordings
		db.execSQL("CREATE TABLE " + RecordingTable.TABLE_NAME + " ("
				+ Recording._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ Recording.START_DATE + " INTEGER NOT NULL, "
				+ Recording.END_DATE + " INTEGER NOT NULL, " + Recording.FILE
				+ " TEXT NOT NULL, " + Recording.SESSION_ID
				+ " INTEGER NOT NULL" + ");");
		// Sessions
		db.execSQL("CREATE TABLE " + SessionTable.TABLE_NAME + " ("
				+ Session._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ Session.DATE + " INTEGER, " + Session.NAME
				+ " TEXT NOT NULL, " + Session.SUBJECT_ID + " INTEGER NOT NULL"
				+ ");");
		// Subjects
		db.execSQL("CREATE TABLE " + SubjectTable.TABLE_NAME + " ("
				+ Subject._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ Subject.NAME + " TEXT NOT NULL" + ");");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + RecordingTable.TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + SessionTable.TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + SubjectTable.TABLE_NAME);
		onCreate(db);
	}

}