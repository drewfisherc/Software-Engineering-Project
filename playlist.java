import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javafx.scene.media.Media;

public class playlist {
	private ArrayList<Songs> play;
	private String listName;
	private JSONArray jo;
	private String loc;
	private String emp = "this list contains no songs";
	
	public playlist(String n) {
		this.listName = n;
		this.play = new ArrayList<Songs>();
		this.loc = "./Playlist/"+n+"/";
		this.load();
	}
	
	public ArrayList<Songs> getPlayList() {
		return this.play;
	}
	
	public void add(Songs s) {
		play.add(s);
		if(this.play.get(0).getName() == emp) {
			this.play.remove(0);
		}
			
		
	}
	
	public void delete(Songs s) {
		this.play.remove(s);
	}
	public String getname() {
		return this.listName;
	}
	
	public void save(){
		this.buildDir();
		jo = new JSONArray();
		for(int x = 0; x<=play.size()-1;x++) {
			jo.put(play.get(x));
		}
		ObjectMapper m = new ObjectMapper();
		try {
			
			String test = m.writeValueAsString(jo);
			for(int x =0; x<=this.jo.length()-1;x++) {
				Songs s = (Songs) jo.get(x);
				String nam = s.getName().replaceAll(".mp3", "");
				File file = new File(loc+nam+".jason");
				m.writeValue(file, jo.get(x));
			}
			
			
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
			

	}
	private void load() {
		if(this.exist(listName) == true) {
			File list = new File("./Playlist/" + this.listName);
			File[] f = list.listFiles();
			for(int x = 0; x<=f.length-1; x++) {
				String path = f[x].getPath().replaceAll("Playlist", "MusicLibary");
				path = path.replaceAll(listName, "");
				String temp = new String();
				for(int y = 0; y<= path.length()-1;y++) {
					if(y!= 13) {
						temp = temp+ path.charAt(y);
					}
				}
				path = temp;
				Songs s = new Songs(f[x].getName().replaceAll(".jason", ".mp3"),path.replaceAll(".jason", ".mp3"),"");
				this.add(s);
			}
		}
		else {
			Songs empty = new Songs(emp,"","");
			this.add(empty);
		}
		
	}
	private boolean exist(String n) {
		
		File list = new File(this.loc);
		if(!list.exists()) {
			list.mkdir();
			return false;
		}
		else {
			return true;
		}
	}
	
	
	private void buildDir() {
		File list = new File(this.loc);
		if(!list.exists()) {
			list.mkdir();
			System.out.println("1");
		}
		else {
			System.out.println("2");
			list.delete();
			list.mkdir();
		}
	}
	
	
	public void test() throws JSONException {
		
		Songs s = (Songs) jo.get(0);
		System.out.println(s.getName());
	}
//	private String encode(ArrayList play) {
//		
//		
//	}
}
