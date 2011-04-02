package es.alltogether.c3po;

import java.util.Calendar;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

public class FileUtility {

	public static String createNewFilePath(Context ctx) {
		boolean externalStorageAvailable = false;
		boolean externalStorageWriteable = false;
		String state = Environment.getExternalStorageState();

		if (Environment.MEDIA_MOUNTED.equals(state)) {
			// We can read and write the media
			externalStorageAvailable = externalStorageWriteable = true;
			// Environment.getExternalStoragePublicDirectory
			return ctx.getExternalFilesDir(Environment.DIRECTORY_MUSIC)
					.getAbsolutePath()
					+ "/class"
					+ Calendar.getInstance().getTimeInMillis()
					+ ".3gp";
		} else {
			// We can only read the media
			externalStorageAvailable = true;
			externalStorageWriteable = false;
			Toast.makeText(ctx, R.string.storageSpace, Toast.LENGTH_LONG)
					.show();
			return ctx.getFilesDir().getAbsolutePath();
		}
	}
}
