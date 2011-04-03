package es.alltogether.c3po.utilities;

import java.io.IOException;

import android.media.MediaPlayer;
import android.util.Log;

public class PlayerUtility {

	private MediaPlayer player = new MediaPlayer();;

	public void startPlaying(String path) {
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
		if (player != null) {
			player.release();
			player = null;
		}
	}

	public void pausePlayer() {
		if (player != null) {
			player.release();
			player = null;
		}
	}

}
