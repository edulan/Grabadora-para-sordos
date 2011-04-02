package es.alltogether.c3po.test;

import java.util.List;

import android.test.ActivityInstrumentationTestCase2;
import es.alltogether.c3po.Recorder;
import es.alltogether.c3po.db.RecordingTable;
import es.alltogether.c3po.models.Recording;
import es.alltogether.c3po.models.Session;

public class RecordingTableTest extends ActivityInstrumentationTestCase2<Recorder> {
	// Views
	private Recorder recorderActivity;
	private RecordingTable recordingTable;

	public RecordingTableTest() {
		super("es.alltogether.c3po", Recorder.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		recorderActivity = getActivity();
		recordingTable = new RecordingTable(recorderActivity);
		recordingTable.clean();
	}

	@Override
	protected void tearDown() throws Exception {
		recordingTable.clean();
		super.tearDown();
	}

	public void testSaveNewRecording() {
		// Prepare fixtures
		Session session = new Session();
		session.setId(1l);
		Recording recording = new Recording();
		recording.setFile("foo.3gp");
		recording.setSession(session);
		// Assertions
		assertTrue(recordingTable.save(recording) > 0);
	}

	public void testSaveExistingRecording() {
		// Prepare fixtures
		Session session = new Session();
		session.setId(1l);
		Recording recording = new Recording();
		recording.setFile("foo.3gp");
		recording.setSession(session);
		recordingTable.save(recording);
		recording.setFile("bar.3gp");
		// Assertions
		assertTrue(recordingTable.save(recording) > 0);
	}

	public void testSaveFailWithNoSession() {
		// Prepare fixtures
		Recording recording = new Recording();
		recording.setFile("foo.3gp");
		// Assertions
		assertTrue(recordingTable.save(recording) == 0);
	}

	public void testFindByCriteria() {
		// Prepare fixtures
		Session session = new Session();
		session.setId(1l);
		Recording recording = new Recording();
		recording.setFile("foo.3gp");
		recording.setSession(session);
		recordingTable.save(recording);
		// Test method
		List<Recording> recordings = recordingTable.findByCriteria(null);
		// Assertions
		assertTrue(!recordings.isEmpty());
	}

	// FIXME
	public void testFindById() {
		/*
		 * // Prepare fixture Session session = new Session();
		 * session.setId(1l); Recording recording = new Recording();
		 * recording.setFile("foo.3gp"); recording.setSession(session);
		 * recordingTable.save(recording); // Test method recording =
		 * recordingTable.findById(recording.getId()); // Assertions
		 * assertNotNull(recording);
		 */
	}

	public void testDelete() {
		// Prepare fixture
		Session session = new Session();
		session.setId(1l);
		Recording recording = new Recording();
		recording.setFile("foo.3gp");
		recording.setSession(session);
		recordingTable.save(recording);
		// Assertions
		assertTrue(recordingTable.delete(recording) > 0);
	}

	public void testDeleteFailWithEmptyRecording() {
		// Prepare fixture
		Recording recording = new Recording();
		// Assertions
		assertTrue(recordingTable.delete(recording) == 0);
	}
}