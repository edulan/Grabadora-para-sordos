package es.alltogether.c3p0.utilities;


public class RecordWavUtility {

	ExtAudioRecorder recorder;

	public void startRecording(String path) {
		recorder = ExtAudioRecorder.getInstanse(false);

		recorder.setOutputFile(path);
		recorder.prepare();
		recorder.start();
	}

	public void stopRecording() {
		recorder.stop();
		recorder.release();
		recorder = null;
	}

	public void pauseRecorder() {
		if (recorder != null) {
			recorder.release();
			recorder = null;
		}
	}

}
