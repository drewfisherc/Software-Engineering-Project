
public class Songs {
	private String name,path,time;
	private Double duration;
	
	public Songs(String name, String path,String time) {
		this.name = name;
		this.path = path;
		this.time = time;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getpath() {
		return this.path;
	}
	
	public String gettime() {
		return this.time;
	}
	public void setdur(String f) {
		this.time = f;
	}
	public Songs() {
		
	}
	public String toString() {
		return this.name +", " + this.path +", "+ this.time;
	}
}
