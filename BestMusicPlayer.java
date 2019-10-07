import java.io.*;
import java.nio.file.Paths;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.util.*;
import java.lang.*;
import javafx.scene.media.*;
import javafx.scene.media.MediaPlayer.Status;
import javafx.embed.swing.JFXPanel;
import javafx.application.*;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.*;
import javafx.stage.*;
import javafx.collections.*;
import javafx.beans.value.*;

/**
 * Launches a music player that does all the things I want it to.  Command-line usage: java BestMusicPlayer
 * This is specifically designed to be poorly coded.  If that sentence remains in the version that gets turned in, I'm going to be very suspicious...
 *
 * @author Kyle Burke <paithanq@gmail.com>
 * @param <AudioFile>
 */
public class BestMusicPlayer extends Application {
	
private static String lName = "No song currently playing.";

private static Label nowPlaying = new Label(lName);
private static String songname = "Pride of the Wolverines";
private static String MONKEY_TITLE = "The Best Music Player Ever!";

private Songs s = new Songs("Pride of the Wolverines","./songs/PrideOfTheWolverines.mp3","Play Wolverines Pride (3:42)");
private Songs v = new Songs("Dance","./songs/Dance.mp3","Dance Dance Baby (0:57)");
private Songs x = new Songs("Horror","./songs/Horror.mp3","BOOOOOOOO!!!!!!!! (1:03)");
private Songs d = new Songs();
private Boolean has = false;
private File lastDirectory = null;
private ListView<String> list = new ListView<String>();
private ListView<String> list2 = new ListView<String>();
private ListView<String> list6 = new ListView<String>();
private String currentlySelected;
private ArrayList<playlist> masterplaylist = new ArrayList<playlist>();
private ObservableList<String> items = FXCollections.observableArrayList();
ObservableList<String> items4 = FXCollections.observableArrayList();
private String selectedlist;

private MusicPlayer m = new MusicPlayer();
private static MediaPlayer all;
private MusicLibary q = new MusicLibary();

private MusicLibary temp = new MusicLibary("b");

/**
 * Main method to launch the program.
 *
 * @param args  Command-line arguments for the program.  Currently unused.
 */

private static void playAll(playlist p,int x) {
	if(!(x > p.getPlayList().size()-1)) {
		Songs s = p.getPlayList().get(x);
		Media cur = new Media(new File(s.getpath()).toURI().toString());
		all = new MediaPlayer(cur);
		all.play();
		nowPlaying.setText(s.getName().replaceAll(".mp3", ""));
		all.setOnEndOfMedia(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				System.out.println("stop");
				all.stop();
				playAll(p,x+1);
			}
		});
	}
}

private void getDuration(ArrayList<Songs> g){
	//Get Duration here
	
	for(int x =0; x<= g.size()-1;x++) {
		int j = x;
		Media org = new Media(new File(g.get(j).getpath()).toURI().toString());
		MediaPlayer setting = new MediaPlayer(org);
		setting.setOnReady(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				
				Double meta = org.getDuration().toSeconds();
				
				int mins = (int) (meta/60);
				int secs = (int) (60 * (((meta/60)-(int)mins)));
				String ans;
				if(secs > 9) {
					ans = "(" + mins + ":" +secs + ")";
				}
				else {
					ans = "(" + mins + ":0" +secs + ")";
				}
				Songs h = new Songs(g.get(j).getName(),g.get(j).getpath(),ans);
				temp.add(h);
				
				if(j == g.size()-1) {
					q = temp;
					
					for(int k =0; k<= j;k++) {
						list.getItems().set(k, q.get(k).getName()+q.get(k).gettime());
					}
				}
				q.deClone();
			}});
		
	}
	this.BuildPLayList();
	this.setplaylistDuration();
}

private void BuildPLayList() {
	File playloc = new File("./Playlist");
	String[] directories = playloc.list(new FilenameFilter() {

		@Override
		public boolean accept(File arg0, String arg1) {
			// TODO Auto-generated method stub
			return new File(arg0,arg1).isDirectory();
		}
	});
	for(int x =0; x<=directories.length-1;x++) {
		playlist tp = new playlist(directories[x].toString().replace("./playlist/", ""));
		masterplaylist.add(tp);
	}
}
private void setplaylistDuration() {
	for(int x =0; x<=masterplaylist.size()-1;x++){
		for(int y =0; y<=q.length()-1;y++) {
			for(int z = 0; z<=masterplaylist.get(x).getPlayList().size()-1;z++) {
				System.out.println("test1: "+q.get(y));
				System.out.println("test 2: "+masterplaylist.get(x).getPlayList().get(z));
				if(q.get(y).getName() == masterplaylist.get(x).getPlayList().get(z).getName()) {
					masterplaylist.get(x).delete(masterplaylist.get(x).getPlayList().get(z));
					masterplaylist.get(x).add(q.get(z));
				}
			}
		}
	}
}

public static void main(String[] args) {launch(args);}
    
    @Override
    public void start(Stage primaryStage) 
    {
    	//solution from stackoverflow user Sagar Damani at: https://stackoverflow.com/questions/14025718/javafx-toolkit-not-initialized-when-trying-to-play-an-mp3-file-through-mediap
    	this.getDuration(q.getList());
    	final JFXPanel bananarama = new JFXPanel();
        
        primaryStage.setTitle(this.MONKEY_TITLE);
        music_button button = new music_button(0);
        button.setText(s.gettime());        
        
		
			
		Songs x1 = new Songs();
		int count = 0;
			
		if(q != null){
			while(count < q.length()){
				x1 = q.get(count);
				items.add(x1.getName());
				count++;
			}
		}
			
		list.setItems(items);
		list.setPrefWidth(250);
		list.setPrefHeight(150);
		
		list.getSelectionModel().selectedItemProperty().addListener(
            new ChangeListener<String>() {
                public void changed(ObservableValue<? extends String> ov, 
                    String old_val, String new_val) {
                	String hj = "";
                	for(int x = 0; x<= q.getList().size()-1;x++) {
                		if(new_val.contains(q.get(x).gettime())) {
                			hj = new_val.replace(q.get(x).gettime(), "");
                			new_val=hj;
                    	}
                	}
						currentlySelected = new_val;
            }
        });
		
		
        
		Button find = new Button();
		find.setText("Play your own mp3");
		find.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				JFileChooser c = new JFileChooser();
				
				if(lastDirectory != null){
					c.setCurrentDirectory(lastDirectory);
				}
				
				FileNameExtensionFilter f = new FileNameExtensionFilter("MP3 files","mp3");
				c.setFileFilter(f);
				int rv = c.showOpenDialog(bananarama);
				if(rv == JFileChooser.APPROVE_OPTION) {
					d = new Songs(c.getSelectedFile().getName(),c.getSelectedFile().getPath(),c.getSelectedFile().getName());
					if(q.hasSong(d) == true) {
						lastDirectory = c.getSelectedFile();
						JOptionPane.showMessageDialog(null, "that file is already in your music Libary", "Nope", JOptionPane.ERROR_MESSAGE);
					}
					else {
						q.add(d);
						//myb.setText(d.getName());
						has = true;
						items.add(d.getName());
						list.setItems(items);
						lastDirectory = c.getSelectedFile();
						temp= new MusicLibary("b");
						getDuration(q.getList());
					}
					
				}
			}
        	
        });
		
		
		
		Button stop = new Button();
		stop.setText("Stop Song");
		stop.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				m.Stop();
				nowPlaying.setText(lName);
				for(int x =0;x<=q.length()-1;x++) {
					System.out.println(q.get(x));
				}
			}
			
		});
		
		Button play = new Button();
		play.setText("Play");
		play.setOnAction(new EventHandler<ActionEvent>(){
			
			@Override
			public void handle(ActionEvent event) {
				
				for(int x = 0; x<=q.getList().size()-1;x++) {
					if(currentlySelected.equals(q.getList().get(x).getName())) {
						m.play(q.get(x));
						nowPlaying.setText(currentlySelected);
					}
				}
			}
			
		});
		
		VBox box = new VBox();
		box.getChildren().addAll(list);
		box.setPrefWidth(250);
		
		ObservableList<String> items2 = FXCollections.observableArrayList();
		items2.add("MusicLibary");
		for(int x =0; x<=this.masterplaylist.size()-1;x++) {
			items2.add(this.masterplaylist.get(x).getname());
		}
		list2.setItems(items2);
		list2.setPrefWidth(250);
		list2.setPrefHeight(150);
		
		list2.getSelectionModel().selectedItemProperty().addListener(
	            new ChangeListener<String>() {
	                public void changed(ObservableValue<? extends String> ov, 
	                    String old_val, String new_val) {
	                	
	                	
	                	ObservableList<String> items4 = FXCollections.observableArrayList();
	                	if(new_val == "MusicLibary") {
	                		items4 = items;
	                	}
	                	
	                	else {
	                		selectedlist = new_val;
	                		for(int x = 0; x<=masterplaylist.size()-1;x++) {
	                			if(new_val.equals(masterplaylist.get(x).getname())) {
	                				playlist tp = masterplaylist.get(x);
	                				
	                				for(int y = 0;y<=tp.getPlayList().size()-1;y++) {
	                					items4.add(tp.getPlayList().get(y).getName());
	                				}
	                			}
	                		}
	                	}
	                	list.setItems(items4);
	            }
	        });
		
		VBox box3 = new VBox();
		box3.getChildren().addAll(list2);
		box3.setPrefWidth(250);
		
		VBox box5 = new VBox();
		box5.getChildren().addAll(list6);
		box5.setPrefWidth(250);
		
		ObservableList<String> items5 = items;
		
		list6.setItems(items5);
		list6.setPrefWidth(250);
		list6.setPrefHeight(150);
		
		list6.getSelectionModel().selectedItemProperty().addListener(
	            new ChangeListener<String>() {
	                public void changed(ObservableValue<? extends String> ov, 
	                    String old_val, String new_val) {
	                	String hj = "";
	                	for(int x = 0; x<= q.getList().size()-1;x++) {
	                		if(new_val.contains(q.get(x).gettime())) {
	                			hj = new_val.replace(q.get(x).gettime(), "");
	                			new_val=hj;
	                    	}
	                	}
							currentlySelected = new_val;
	            }
	        });
		
		GridPane playlistView = new GridPane();
		playlistView.setAlignment(Pos.CENTER);
		playlistView.setVgap(10);
		playlistView.add(box5, 0, 0);
		Scene scene2 = new Scene(playlistView, 500, 550);
		
		Button addtoplaylist = new Button();
		addtoplaylist.setText("add to playlist");
		addtoplaylist.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				primaryStage.setScene(scene2);
				primaryStage.show();
			}
        	
        });
		
		Button PlaycurList = new Button();
		PlaycurList.setText("Play selected List");
		PlaycurList.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
				playlist durp = new playlist("");
				System.out.println("hit ");
				for(int x = 0; x<=masterplaylist.size()-1;x++) {
					if(selectedlist == masterplaylist.get(x).getname()) {
						durp = masterplaylist.get(x);
					}
				}
				
				playAll(durp,0);
			}
        	
        });
		
		
		Label nowPlaying2 = new Label(lName);
		Label nowPlaying3 = new Label(lName);
		nowPlaying2.setText("PlayList: ");
		nowPlaying3.setText("Songs: ");
		
		Label nowPlaying4 = new Label(lName);
		nowPlaying4.setText("Whats Playing now: ");
		
		GridPane paneofgridmonkeys = new GridPane();
        paneofgridmonkeys.setAlignment(Pos.CENTER);
        paneofgridmonkeys.setVgap(10);
        paneofgridmonkeys.add(nowPlaying2, 0, 1);
        paneofgridmonkeys.add(nowPlaying3, 1, 1);
        paneofgridmonkeys.add(find, 0, 4);
        paneofgridmonkeys.add(nowPlaying4, 0, 5);
        paneofgridmonkeys.add(nowPlaying, 0, 6);
		paneofgridmonkeys.add(play, 0, 3);
        paneofgridmonkeys.add(stop, 1, 3);
		paneofgridmonkeys.add(box, 1, 2);
		paneofgridmonkeys.add(box3, 0, 2);
		paneofgridmonkeys.add(addtoplaylist, 0, 10);
		paneofgridmonkeys.add(PlaycurList, 1, 10);
		
		Scene scene1 = new Scene(paneofgridmonkeys, 500, 550);
		
		Button back = new Button();
		back.setText("Cancle");
		playlistView.add(back, 0, 1);
		back.setOnAction(new EventHandler<ActionEvent>(){
			
			@Override
			public void handle(ActionEvent event) {
				
				primaryStage.setScene(scene1);
				primaryStage.show();
				
			}
			
		});
		
		Button addToList =new Button();
		addToList.setText("Add Song");
		playlistView.add(addToList, 1, 1);
		addToList.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
				Songs s = new Songs();
				for(int x =0; x<=q.length()-1;x++) {
					if(q.get(x).getName().equals(currentlySelected)) {
						s = q.get(x);
					}
				}
				for(int x = 0; x<= masterplaylist.size()-1;x++) {
					if(selectedlist.equals(masterplaylist.get(x).getname())) {
						masterplaylist.get(x).add(s);
						masterplaylist.get(x).save();
						list.getItems().add(s.getName().toString());
					}
				}
				primaryStage.setScene(scene1);
				primaryStage.show();
			}
        	
        });
        
        
		
		
		
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Create New Playlist");
		dialog.setHeaderText("Adding New Playlist");
		dialog.setContentText("New Playlist Name:");
		
		Button createPlaylist = new Button();
		createPlaylist.setText("Create New Playlist");
		createPlaylist.setOnAction(new EventHandler<ActionEvent>(){
			
			@Override
			public void handle(ActionEvent event) {
				Boolean triped = false;
				Optional<String> result = dialog.showAndWait();
				String nnm;
				if (result.isPresent()){
					if(result.get() != null && !result.get().isEmpty()){
						nnm = result.get();
						dialog.getEditor().clear();
						playlist tp = new playlist(nnm);
						masterplaylist.add(tp);
						items2.add(tp.getname());
					}
				}
			}
		});
		
		paneofgridmonkeys.add(createPlaylist, 0, 8);
		
		
		
			
		
        primaryStage.setScene(scene1);
        primaryStage.show();
    }

} //end of BestMusicPlayer.java