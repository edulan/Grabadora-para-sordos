package es.alltogether.c3p0.utilities;

import java.util.Calendar;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;
import es.alltogether.c3po.R;

public class FileUtility {

	public static String createNewFilePath(Context ctx) {
		String state = Environment.getExternalStorageState();

		if (Environment.MEDIA_MOUNTED.equals(state)) {
			// Environment.getExternalStoragePublicDirectory
			return ctx.getExternalFilesDir(Environment.DIRECTORY_MUSIC)
					.getAbsolutePath()
					+ "/class"
					+ Calendar.getInstance().getTimeInMillis()
					+ ".3gp";
		} else {
			Toast.makeText(ctx, R.string.storageSpace, Toast.LENGTH_LONG)
					.show();
			return ctx.getFilesDir().getAbsolutePath();
		}
	}
}
