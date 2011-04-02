package es.alltogether.c3po;

import java.io.IOException;

import android.media.MediaPlayer;
import android.util.Log;

public class PlayerUtility {

	private MediaPlayer player = new MediaPlayer();;

	String path;

	public PlayerUtility(String path) {
		this.path = path;
	}

	public void onPlay(boolean start) {
		if (start) {
			startPlaying();
		} else {
			stopPlaying();
		}
	}

	private void startPlaying() {
		try {
			if (player == null) {
				player = new MediaPlayer();
			}
			player.setDataSource(path);
			player.prepare();
			player.start();

		} catch (IOException e) {
			Log.e("AUDIO_PLAY", "prepare() failed");
		}
	}

	public MediaPlayer getPlayer() {
		return player;
	}

	public void setPlayer(MediaPlayer player) {
		this.player = player;
	}

	public void stopPlaying() {
		player.release();
		player = null;
	}

	public void pausePlayer() {
		if (player != null) {
			player.release();
			player = null;
		}
	}

}
