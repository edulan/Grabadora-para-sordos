package es.alltogether.c3po.test;

import java.io.File;

import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.widget.Button;
import es.alltogether.c3po.RecordDialog;

public class RecordDialogTest extends
		ActivityInstrumentationTestCase2<RecordDialog> {
	private RecordDialog recordDialog;
	private Button recordButton;
	
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
		this.setActivityInitialTouchMode(true);
		TouchUtils.clickView(this, recordButton);
		try {
			Thread.sleep(1000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		TouchUtils.clickView(this, recordButton);
		File file = new File(recordDialog.getClassSession().getFile());
		assertTrue(file.exists());
		file.delete();
	}
}