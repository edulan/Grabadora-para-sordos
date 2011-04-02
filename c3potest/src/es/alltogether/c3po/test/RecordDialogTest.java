package es.alltogether.c3po.test;

import java.io.File;
import java.util.Calendar;

import android.content.SharedPreferences;
import android.os.Environment;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.widget.Button;
import es.alltogether.c3po.RecordDialog;

public class RecordDialogTest extends
		ActivityInstrumentationTestCase2<RecordDialog> {
	private RecordDialog recordDialog;
	private Button recordButton;
	private String path;

	public RecordDialogTest() {
		super("es.alltogether.c3po", RecordDialog.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		recordDialog = this.getActivity();
		recordButton = (Button) recordDialog
				.findViewById(es.alltogether.c3po.R.id.recordButton);
	}

	public void testRecord() {
		path = Environment.getExternalStorageDirectory().getAbsolutePath()
				+ "/audiorecordtest" + Calendar.getInstance().getTimeInMillis()
				+ ".3gp";
		SharedPreferences settings = recordDialog.getSharedPreferences(
				RecordDialog.PREFS_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString("path", path);
		recordDialog.setPath(path);
		this.setActivityInitialTouchMode(true);
		TouchUtils.clickView(this, recordButton);
		TouchUtils.clickView(this, recordButton);
		File file = new File(path);
		assertTrue(file.exists());
	}
}