package es.alltogether.c3po;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.http.util.ByteArrayBuffer;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Intent;
import android.net.http.SslCertificate;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Recorder extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		

//		Button goRecording = (Button) findViewById(R.id.goRecording);
//		goRecording.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				Intent intent = new Intent(Recorder.this, Subjects.class);
//				startActivity(intent);
//			}
//		});
	}

	

}