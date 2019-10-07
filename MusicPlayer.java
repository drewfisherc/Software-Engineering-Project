import java.io.File;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class MusicPlayer {
	private MediaPlayer m;
	
	public MusicPlayer() {
		
	}
	
	public void play(Songs s) {
		if(m!=null) {
			m.stop();
			Media media = new Media(new File(s.getpath()).toURI().toString());
			this.m = new MediaPlayer(media);
			m.play();
		}
		else {
			Media media = new Media(new File(s.getpath()).toURI().toString());
			this.m = new MediaPlayer(media);
			m.play();
			System.out.println(s.getpath());
		}
		
	}
	
	public void Stop() {
		m.stop();
	}
	
	public void Pause() {
		m.pause();
	}
	
}
