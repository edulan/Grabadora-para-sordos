package es.alltogether.c3po.test;

import java.util.List;

import android.test.ActivityInstrumentationTestCase2;
import es.alltogether.c3po.Recorder;
import es.alltogether.c3po.db.RecordingTable;
import es.alltogether.c3po.models.Recording;

public class RecorderDBTest extends ActivityInstrumentationTestCase2<Recorder> {
	// Views
	private Recorder recorderActivity;
	private RecordingTable recordingTable;

	public RecorderDBTest() {
		super("es.alltogether.c3po", Recorder.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		recorderActivity = getActivity();
	}

	// public void testPreconditions() {
	// assertNotNull(helloTextView);
	// }

	public void testText() {
		recordingTable = new RecordingTable(recorderActivity);
		Recording resource = new Recording();
		resource.setFile("HOLA");
		recordingTable.save(resource);
		List<Recording> recursos = recordingTable.findByCriteria(null);
		assertFalse(recursos.isEmpty());
		recordingTable.delete(resource);
		recursos = recordingTable.findByCriteria(null);
		assertTrue(recursos.isEmpty());
	}
}