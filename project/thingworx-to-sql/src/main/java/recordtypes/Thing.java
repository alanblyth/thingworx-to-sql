package recordtypes;

public class Thing {
	
	public static String NAME = "name";
	public static String DESCRIPTION = "description";
	public static String HOMEMASHUP = "homeMashup";
	public static String ISSYSTEMOBJECT = "isSystemObject";
	public static String TAGS = "tags";
	public static String AVATAR = "avatar";
	
	private String avatar;
	private String description;
	private String homeMashup;
	private boolean isSystemObject;
	private String name;


	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getHomeMashup() {
		return homeMashup;
	}
	public void setHomeMashup(String homeMashup) {
		this.homeMashup = homeMashup;
	}
	public boolean isSystemObject() {
		return isSystemObject;
	}
	public void setSystemObject(boolean isSystemObject) {
		this.isSystemObject = isSystemObject;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
