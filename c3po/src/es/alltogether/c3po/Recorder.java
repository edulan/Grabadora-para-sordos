package es.alltogether.c3po;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;

public class Recorder extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		HttpClient client = new DefaultHttpClient();

		HttpPost post = new HttpPost(
				"https://www.google.com/speech-api/v1/recognize?xjerr=1&client=speech2text&lang=en-US");
		post.setHeader("Content-Type", "audio/x-flac; rate=16000");
		post.setHeader("User-Agent", "https://github.com/edulan/Grabadora-para-sordos");
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		try {

			String content = extractBytes(new File(this.getExternalFilesDir(
					Environment.DIRECTORY_MUSIC).getAbsolutePath()
					+ "/class2.flac"));
			post.setEntity(new StringEntity("Content=" + content));
			// pairs.add(new BasicNameValuePair("Content=", content));
			post.setEntity(new UrlEncodedFormEntity(pairs));
			HttpResponse response = client.execute(post);
			String getResponseText = EntityUtils.toString(response.getEntity());
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Button goRecording = (Button) findViewById(R.id.goRecording);
		// goRecording.setOnClickListener(new OnClickListener() {
		// @Override
		// public void onClick(View v) {
		// Intent intent = new Intent(Recorder.this, Subjects.class);
		// startActivity(intent);
		// }
		// });
	}

	private String extractBytes(File file) throws FileNotFoundException,
			IOException {
		FileInputStream fileIs = new FileInputStream(file);
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