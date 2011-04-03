package es.alltogether.c3p0.utilities;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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
					+ ".wav";
		} else {
			Toast.makeText(ctx, R.string.storageSpace, Toast.LENGTH_LONG)
					.show();
			return ctx.getFilesDir().getAbsolutePath();
		}
	}

	private String extractBytes(File file) throws FileNotFoundException,
			IOException {
		FileInputStream fileIs = new FileInputStream(file);
		long tcalc;
		long begin = System.currentTimeMillis();
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		int bytesread = 0;
		byte[] buff = new byte[512];

		while (true) {
			bytesread = fileIs.read(buff);
			if (bytesread == -1) // if EOF
				break;
			bout.write(buff, 0, bytesread);
		}

		fileIs.close();
		bout.close();
		return bout.toString();
	}
}
