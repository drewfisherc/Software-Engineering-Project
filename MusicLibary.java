import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.util.ArrayList;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

import javafx.beans.InvalidationListener;
import javafx.collections.MapChangeListener;
import javafx.collections.MapChangeListener.Change;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class MusicLibary {
	private ArrayList<Songs> MusicLibary;
	private String Libary = "./MusicLibary";
	private String duration;
	private Boolean Clone = false;
	
	
	public MusicLibary() {
		this.MusicLibary = new ArrayList<Songs>();
		this.create_Libary();
		this.Load();
	}
	
	public MusicLibary(String b) {
		this.MusicLibary = new ArrayList<Songs>();
		this.Clone = true;
	}
	
	public ArrayList<Songs> getList() {
		return this.MusicLibary;
	}
	
	public void deClone() {
		this.Clone = false;
	}
	public void reclone() {
		this.Clone = true;
	}
	
	public void create_Libary() {
		File lib = new File(Libary);
		if(!lib.exists()) {
			lib.mkdir();
			System.out.println(lib.exists());
		}
		
	}
	
	public void add(Songs s) {
		MusicLibary.add(s);
		if(Clone == false) {
			try {
				this.save(s);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	
	public Songs get(int index) {
		return this.MusicLibary.get(index);
	}
	
	public boolean hasSong(Songs d) {
		String look = d.getName();
		for(int x = 0 ; x<=this.MusicLibary.size()-1;x++) {
			if(look.equals(this.MusicLibary.get(x).getName())){
				return true;
			}
			else {
				return false;
			}
		}
		return false;
	}
	
	public void save(Songs s) throws IOException {
		File lib = new File(s.getpath());
		String loc = "./MusicLibary/" + lib.getName();
		File dest = new File(loc);
		Files.copy(lib.toPath(), dest.toPath());
	}
	public void Load() {
		File lib = new File(Libary);
		File[] f = lib.listFiles();
		this.convert(f);
	}
	
	public void convert(File[] f) {
		for(int x = 0; x<=f.length-1; x++) {
			Songs s;
			
					Media g = new Media(f[x].toURI().toString());
					s = new Songs(f[x].getName(),f[x].getPath().toString(),"");
					this.MusicLibary.add(s);
					System.out.println(s);
		}
	}
	
	public int songSearch(String s1, int length){
		int count = 0;
		Songs currentSong = new Songs();
		int match = Integer.MAX_VALUE;
		
		for(count = 0; count < length; count++){
			currentSong = this.MusicLibary.get(count);
			if(s1 == currentSong.getName()){
				match = count;
			}
		}
		return match;
	}
	
	
	
	


	public int length() {
		// TODO Auto-generated method stub
		return this.MusicLibary.size();
	}
}
