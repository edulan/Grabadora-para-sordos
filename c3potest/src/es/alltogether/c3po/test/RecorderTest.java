package es.alltogether.c3po.test;

import es.alltogether.c3po.Recorder;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;

public class RecorderTest extends ActivityInstrumentationTestCase2<Recorder> {
	// Views
    private Recorder recorderActivity;	// the activity under test
    private TextView helloTextView;		// the hello TextView
    private TextView byeTextView;		// the bye TextView
    // Resources
    private String helloResource;
    private String byeResource;

    public RecorderTest() {
      super("es.alltogether.c3po", Recorder.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        recorderActivity = this.getActivity();
        helloTextView = (TextView) recorderActivity.findViewById(es.alltogether.c3po.R.id.hello_text);
        byeTextView = (TextView) recorderActivity.findViewById(es.alltogether.c3po.R.id.bye_text);
        helloResource = recorderActivity.getString(es.alltogether.c3po.R.string.hello);
        byeResource = recorderActivity.getString(es.alltogether.c3po.R.string.bye);
    }
    public void testPreconditions() {
      assertNotNull(helloTextView);
    }
    public void testText() {
      assertEquals(helloResource,(String)helloTextView.getText());
      assertEquals(byeResource, (String)byeTextView.getText());
    }
}