import javafx.scene.control.Button;

public class music_button extends Button{
	private int index;
	
	public music_button(int index) {
		this.index = index;
	}
	public int getindex() {
		return this.index;
	}
}
