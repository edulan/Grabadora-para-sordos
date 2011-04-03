package es.alltogether.c3po.test;

import java.io.File;

import android.app.AlertDialog;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.widget.Button;
import es.alltogether.c3po.RecordAndPlay;
import es.alltogether.c3po.models.Session;

public class RecordDialogTest extends
		ActivityInstrumentationTestCase2<RecordAndPlay> {
	private RecordAndPlay recordDialog;
	private Button recordButton;
	private Button alertButton;

	public RecordDialogTest() {
		super("es.alltogether.c3po", RecordAndPlay.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		recordDialog = this.getActivity();
		recordDialog.setSession(new Session());
		recordButton = (Button) recordDialog
				.findViewById(es.alltogether.c3po.R.id.recordButton);
		alertButton = (Button) recordDialog.getAlertDialog().getButton(
				AlertDialog.BUTTON_POSITIVE);
	}

	public void testRecord() {
		this.setActivityInitialTouchMode(true);
		TouchUtils.clickView(this, recordButton);
		try {
			Thread.sleep(1000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		TouchUtils.clickView(this, alertButton);
		File file = new File(recordDialog.getRecording().getFile());
		assertTrue(file.exists());
		file.delete();
	}
}