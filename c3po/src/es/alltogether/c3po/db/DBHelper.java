package es.alltogether.c3po.db;

import static android.provider.BaseColumns._ID;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import es.alltogether.c3po.models.Recording;
import es.alltogether.c3po.models.Session;
import es.alltogether.c3po.models.Subject;

public class DBHelper extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "events.db";
	private static final int DATABASE_VERSION = 2;

	/** Create a helper object for the Events database */
	public DBHelper(Context ctx) {
		super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE " + RecordingTable.TABLE_NAME + " (" + _ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + Recording.START_DATE
				+ " INTEGER NOT NULL, " + Recording.END_DATE
				+ " INTEGER NOT NULL, " + Recording.FILE + " TEXT NOT NULL, "
				+ Recording.SESSION_ID + " INTEGER NOT NULL);");
		db.execSQL("CREATE TABLE " + SessionTable.TABLE_NAME + " (" + _ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + Session.DATE
				+ " INTEGER, " + Session.NAME + " TEXT NOT NULL, "
				+ Session.SUBJECT_ID + " INTEGER NOT NULL);");
		db.execSQL("CREATE TABLE " + SubjectTable.TABLE_NAME + " (" + _ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + Subject.NAME
				+ " text not null);");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + "recordings");
		onCreate(db);
	}

}